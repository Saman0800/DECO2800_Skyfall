package deco2800.skyfall.worlds.world;

import com.badlogic.gdx.Gdx;
//import com.sun.tools.internal.jxc.ap.Const;
import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.ConstructionManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.MountainBiome;
import deco2800.skyfall.worlds.biomes.OceanBiome;
import deco2800.skyfall.worlds.biomes.SwampBiome;
import deco2800.skyfall.worlds.biomes.VolcanicMountainsBiome;
import deco2800.skyfall.worlds.generation.BiomeGenerator;
import deco2800.skyfall.worlds.generation.DeadEndGenerationException;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.*;
import java.io.FileWriter;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level
 * items.
 */
public class World implements TouchDownObserver {

    protected List<AbstractEntity> entities;

    protected int width;
    protected int length;

    //The length/2 of the world, (worldSize * 2)^2 to get the world area
    protected int worldSize;

    //The distance between the nodes
    protected int nodeSpacing;

    //A list corrosponding to the size of each biome
    protected int[] biomeSizes;

    //The number of lakes
    protected int numOfLakes;

    //The size of each lake
    protected int[] lakeSizes;

    //The number of rivers
    protected int noRivers;

    //The width of all the rivers
    protected int riverWidth;

    //The width of the beach
    protected int beachWidth;

    //The seed of the world, using with the random number generator to create deterministic worlds
    private long seed;

    //Used to generate random numbers
    private Random random;

    // List that contains the world biomes
    protected ArrayList<AbstractBiome> biomes;
    public Map<String, Float> frictionMap;

    //A list of all the tiles within a world
    protected CopyOnWriteArrayList<Tile> tiles;
    protected CopyOnWriteArrayList<WorldGenNode> worldGenNodes;
    protected CopyOnWriteArrayList<VoronoiEdge> voronoiEdges;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    /**
     * The constructor for a world
     * @param seed The seed of the world, used in conjuction with a random number generator allows for deterministic
     *             generation
     * @param worldSize The length/2 of the world, (worldSize * 2)^2 to get the world area/number of tiles
     * @param nodeSpacing The spacing between the nodes
     * @param biomeSizes The size of the biomes
     * @param numOfLakes The number of lakes
     * @param lakeSizes The respective sizing of each lake
     * @param biomes The biomes in the world
     * @param entities The entities in the world
     * @param noRivers The number of rivers
     * @param riverWidth The width of the rivers in terms of nodes
     */
    public World(long seed, int worldSize, int nodeSpacing, int[] biomeSizes, int numOfLakes, int[] lakeSizes,
                ArrayList<AbstractBiome> biomes, CopyOnWriteArrayList<AbstractEntity> entities, int noRivers, int riverWidth, int beachWidth) {

        random = new Random(seed);
        this.seed = seed;
        this.biomeSizes = biomeSizes;
        this.numOfLakes = numOfLakes;
        this.lakeSizes = lakeSizes;
        this.biomes = biomes;
        this.entities = entities;
        this.noRivers = noRivers;
        this.riverWidth = riverWidth;
        this.beachWidth = beachWidth;

        this.worldSize = worldSize;
        this.nodeSpacing = nodeSpacing;

        tiles = new CopyOnWriteArrayList<>();
        worldGenNodes = new CopyOnWriteArrayList<>();

        tiles = new CopyOnWriteArrayList<Tile>();
    	voronoiEdges = new CopyOnWriteArrayList<>();

        generateWorld(random);
        generateTileTypes(random);
        generateNeighbours();
        generateTileIndexes();
        initialiseFrictionmap();
    }

    /**
     * Generates the tiles and biomes in the world and adds the world to a listener to allow for interaction.
     * Continuously repeats generation until it reaches a stable world
     * @param random
     */
    protected void generateWorld(Random random){
        while (true){
            try {
                generateTiles();
                break;
            } catch (WorldGenException | DeadEndGenerationException | NotEnoughPointsException ignored){
            }
        }

        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);

    };


    /**
     * Generates the tiles and biomes in a world
     * @throws NotEnoughPointsException When there are not enough points
     * @throws DeadEndGenerationException
     * @throws WorldGenException
     */
    private void generateTiles() throws NotEnoughPointsException, DeadEndGenerationException, WorldGenException{
            //TODO clean the biomes and tiles on every iteration
            ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();
            ArrayList<Tile> tiles = new ArrayList<>();

            for (Tile tile : getTileMap()){
                tile.setBiome(null);
            }

            for (AbstractBiome biome : biomes) {
                biome.getTiles().clear();
            }

            int nodeCount = Math.round((float) worldSize * worldSize * 4 / nodeSpacing / nodeSpacing);
            // TODO: if nodeCount is less than the number of biomes, throw an exception

            for (int i = 0; i < nodeCount; i++) {
                // Sets coordinates to a random number from -WORLD_SIZE to WORLD_SIZE
                float x = (float) (random.nextFloat() - 0.5) * 2 * worldSize;
                float y = (float) (random.nextFloat() - 0.5) * 2 * worldSize;
                worldGenNodes.add(new WorldGenNode(x, y));
            }

            // Apply Delaunay triangulation to the nodes, so that vertices and
            // adjacencies can be calculated. Also apply Lloyd Relaxation twice
            // for more smooth looking polygons
            try {
                WorldGenNode.calculateVertices(worldGenNodes, worldSize);
                WorldGenNode.lloydRelaxation(worldGenNodes, 2, worldSize);
            } catch (WorldGenException e) {
                throw e;
            }

            for (int q = -worldSize; q <= worldSize; q++) {
                for (int r = -worldSize; r <= worldSize; r++) {
                    // if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <=
                    // worldSize) {
                    float oddCol = (q % 2 != 0 ? 0.5f : 0);

                    Tile tile = new Tile(q, r + oddCol);
                    tiles.add(tile);
                    // }
                }
            }
            // TODO Fix this.
            generateNeighbours(tiles);

            try {
                WorldGenNode.assignTiles(worldGenNodes, tiles, random, nodeSpacing);
                WorldGenNode.removeZeroTileNodes(worldGenNodes, worldSize);
                WorldGenNode.assignNeighbours(worldGenNodes, voronoiEdges);
            } catch (WorldGenException e) {
                throw e;
            }
            VoronoiEdge.assignTiles(voronoiEdges, tiles, worldSize);
            VoronoiEdge.assignNeighbours(voronoiEdges);



            try {
                BiomeGenerator biomeGenerator = new BiomeGenerator(worldGenNodes, voronoiEdges, random, biomeSizes, biomes, numOfLakes, lakeSizes, noRivers, riverWidth, beachWidth);
                biomeGenerator.generateBiomes();
            } catch (NotEnoughPointsException | DeadEndGenerationException e) {
                 throw e;
            }

            this.worldGenNodes.addAll(worldGenNodes);
            this.tiles.addAll(tiles);
    }





    /**
     * Loops through all the biomes within the world and adds textures to the tiles
     * which determine their properties
     */
    public void generateTileTypes(Random random) {
        for (AbstractBiome biome : biomes) {
            biome.setTileTextures(random);
        }
    }

    // TODO Fix this.
    public void generateNeighbours() {
        generateNeighbours(tiles);
    }

    public void generateNeighbours(List<Tile> tiles) {
        // multiply coords by 2 to remove floats
        Map<Integer, Map<Integer, Tile>> tileMap = new HashMap<>();
        Map<Integer, Tile> columnMap;
        for (Tile tile : tiles) {
            columnMap = tileMap.getOrDefault((int) tile.getCol() * 2, new HashMap<Integer, Tile>());
            columnMap.put((int) (tile.getRow() * 2), tile);
            tileMap.put((int) (tile.getCol() * 2), columnMap);
        }

        for (Tile tile : tiles) {
            int col = (int) (tile.getCol() * 2);
            int row = (int) (tile.getRow() * 2);

            // West
            if (tileMap.containsKey(col - 2)) {
                // North West
                if (tileMap.get(col - 2).containsKey(row + 1)) {
                    tile.addNeighbour(Tile.NORTH_WEST, tileMap.get(col - 2).get(row + 1));
                }

                // South West
                if (tileMap.get(col - 2).containsKey(row - 1)) {
                    tile.addNeighbour(Tile.SOUTH_WEST, tileMap.get(col - 2).get(row - 1));
                }
            }

            // Central
            if (tileMap.containsKey(col)) {
                // North
                if (tileMap.get(col).containsKey(row + 2)) {
                    tile.addNeighbour(Tile.NORTH, tileMap.get(col).get(row + 2));
                }

                // South
                if (tileMap.get(col).containsKey(row - 2)) {
                    tile.addNeighbour(Tile.SOUTH, tileMap.get(col).get(row - 2));
                }
            }

            // East
            if (tileMap.containsKey(col + 2)) {
                // North East
                if (tileMap.get(col + 2).containsKey(row + 1)) {
                    tile.addNeighbour(Tile.NORTH_EAST, tileMap.get(col + 2).get(row + 1));
                }

                // South East
                if (tileMap.get(col + 2).containsKey(row - 1)) {
                    tile.addNeighbour(Tile.SOUTH_EAST, tileMap.get(col + 2).get(row - 1));
                }
            }
        }
    }

    private void generateTileIndexes() {
        for (Tile tile : tiles) {
            tile.calculateIndex();
        }
    }

    /**
     * Returns a list of entities in this world
     *
     * @return All Entities in the world
     */
    public List<AbstractEntity> getEntities() {
        return new CopyOnWriteArrayList<>(this.entities);
    }

    /**
     * Creates a friction map that assigns a friction to every tile type
     */
    public void initialiseFrictionmap() {
        frictionMap = new HashMap<>();
        frictionMap.put("grass", 0.8f);
        frictionMap.put("forest", 0.76f);
        frictionMap.put("water", 0.4f);
        frictionMap.put("ocean", 0.4f);
        frictionMap.put("lake", 0.4f);
        frictionMap.put("volcanic", 0.6f);
        frictionMap.put("mountain", 0.67f);
        frictionMap.put("desert", 0.59f);
        frictionMap.put("ice", 1f);
        frictionMap.put("snow", 1f);
        this.frictionMap.putAll(frictionMap);
    }

    /**
     * Returns a list of entities in this world, ordered by their render level
     *
     * @return all entities in the world
     */
    public List<AbstractEntity> getSortedEntities() {
        List<AbstractEntity> e = new CopyOnWriteArrayList<>(this.entities);
        return e;
    }

    /**
     * Returns a list of entities in this world, ordered by their render level
     *
     * @return all entities in the world
     */
    public List<AgentEntity> getSortedAgentEntities() {
        List<AgentEntity> e = this.entities.stream().filter(p -> p instanceof AgentEntity).map(p -> (AgentEntity) p)
                .collect(Collectors.toList());

        Collections.sort(e);
        return e;
    }

    /**
     * Adds an entity to the world
     *
     * @param entity the entity to add
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
        // Keep the entities sorted by render order
        Collections.sort(entities);
    }

    /**
     * Removes an entity from the world
     *
     * @param entity the entity to remove
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
        // Keep the entities sorted by render order
        Collections.sort(entities);
    }

    public void setEntities(List<AbstractEntity> entities) {
        this.entities = entities;
    }

    public List<Tile> getTileMap() {
        return tiles;
    }

    public Tile getTile(float col, float row) {
        return getTile(new HexVector(col, row));
    }

    public Tile getTile(HexVector position) {
        for (Tile t : tiles) {
            if (t.getCoordinates().equals(position)) {
                return t;
            }
        }
        return null;
    }

    public Tile getTile(int index) {
        for (Tile t : tiles) {
            if (t.getTileID() == index) {
                return t;
            }
        }
        return null;
    }

    public void setTileMap(CopyOnWriteArrayList<Tile> tileMap) {
        this.tiles = tileMap;
    }

    // TODO ADDRESS THIS..?
    public void updateTile(Tile tile) {
        for (Tile t : this.tiles) {
            if (t.getTileID() == tile.getTileID()) {
                if (!t.equals(tile)) {
                    this.tiles.remove(t);
                    this.tiles.add(tile);
                }
                return;
            }
        }
        this.tiles.add(tile);
    }

    // TODO ADDRESS THIS..?
    public void updateEntity(AbstractEntity entity) {
        for (AbstractEntity e : this.entities) {
            if (e.getEntityID() == entity.getEntityID()) {
                this.entities.remove(e);
                this.entities.add(entity);
                return;
            }
        }
        this.entities.add(entity);

        // Since MultiEntities need to be attached to the tiles they live on, setup that
        // connection.
        if (entity instanceof StaticEntity) {
            ((StaticEntity) entity).setup();
        }
    }

    public void onTick(long i) {
        for (AbstractEntity e : entitiesToDelete) {
            entities.remove(e);
        }

        for (Tile t : tilesToDelete) {
            tiles.remove(t);
        }

        // Collision detection for entities
        for (AbstractEntity e1 : this.getEntities()) {
            if (e1 instanceof StaticEntity) {
                // Static entities can't move into other entities. Only worry
                // about entities that can move themselves into other entities
                continue;
            }
            e1.onTick(0);
            //if (e1.getCollider() == null) {
            //    break;
            //}
            Collider c1 = e1.getCollider();
            for (AbstractEntity e2 : this.getEntities()) {
                if (e2.getCollider() == null) {
                    break;
                }
                Collider c2 = e2.getCollider();
                if (e1 != e2 && c1.overlaps(c2)) {
                    if (e1 instanceof MainCharacter || e2 instanceof
                    MainCharacter) {
                        break;
                    }
                    //collision handler
                //    this.handleCollision(e1, e2);
                //    break;
                }
            }
            // no collision here
        }
    }

    public void deleteTile(int tileid) {
        Tile tile = GameManager.get().getWorld().getTile(tileid);
        if (tile != null) {
            tile.dispose();
        }
    }

    public void deleteEntity(int entityID) {
        for (AbstractEntity e : this.getEntities()) {
            if (e.getEntityID() == entityID) {
                e.dispose();
            }
        }
    }

    public void queueEntitiesForDelete(List<AbstractEntity> entities) {
        entitiesToDelete.addAll(entities);
    }

    public void queueTilesForDelete(List<Tile> tiles) {
        tilesToDelete.addAll(tiles);
    }

    /**
     * Adds a biome to a world
     *
     * @param biome The biome getting added
     */
    public void addBiome(AbstractBiome biome) {
        this.biomes.add(biome);
    }

    /**
     * Gets the list of biomes in a world
     */
    public List<AbstractBiome> getBiomes() {
        return this.biomes;
    }

    public void handleCollision(AbstractEntity e1, AbstractEntity e2) {
        // TODO: implement proper game logic for collisions between different types of
        // entities.

        // TODO: this needs to be internalized into classes for cleaner code.
        if (e1 instanceof Projectile && e2 instanceof EnemyEntity) {
            if(((EnemyEntity) e2).getHealth()>0){
                ((EnemyEntity) e2).takeDamage(((Projectile) e1).getDamage());
                ((EnemyEntity) e2).setAttacked(true);
                ((Projectile) e1).destroy();
            }else{
                ((EnemyEntity) e2).setDead(true);
            }


        } else if (e2 instanceof Projectile && e1 instanceof EnemyEntity) {
            if(((EnemyEntity) e1).getHealth()>0){
                ((EnemyEntity) e1).takeDamage(((EnemyEntity) e1).getDamage());
                ((EnemyEntity) e1).setAttacked(true);
                ((Projectile) e2).destroy();
            }else{
                ((EnemyEntity) e1).setDead(true);
            }


        }
    }

    public void saveWorld(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(worldToString());
        }
    }

    public String worldToString() {
        StringBuilder string = new StringBuilder();
        for (Tile tile : tiles) {
            String out = String.format("%f, %f, %s, %s\n", tile.getCol(), tile.getRow(), tile.getBiome().getBiomeName(),
                    tile.getTextureName());
            string.append(out);
        }
        return string.toString();
    }

    /**
     * Returns the seed used in the world
     *
     * @return
     */
    public long getSeed() {
        return seed;
    }


    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0) {
            if (GameManager.getManagerFromInstance(ConstructionManager.class).getStatus() == 1) {
                float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
                float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
                GameManager.getManagerFromInstance(ConstructionManager.class).build(this,
                        (int)clickedPosition[0], (int)clickedPosition[1]);
                GameManager.getManagerFromInstance(ConstructionManager.class).setNull();
                return;
            }
        }

        // only allow right clicks to collect resources
        if (button != 1) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        Tile tile = getTile(clickedPosition[0], clickedPosition[1]);

        if (tile == null) {
            return;
        }
        // todo: more efficient way to find entities
        for (AbstractEntity entity : getEntities()) {
            if (!tile.getCoordinates().equals(entity.getPosition())) {
                continue;
            }

            if (entity instanceof Harvestable) {
                removeEntity(entity);
                List<AbstractEntity> drops = ((Harvestable) entity).harvest(tile);

                for (AbstractEntity drop : drops) {
                    addEntity(drop);
                }
            }
        }
    }
}
