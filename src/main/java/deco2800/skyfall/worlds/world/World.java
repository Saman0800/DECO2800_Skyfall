package deco2800.skyfall.worlds.world;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.entities.*;
import deco2800.skyfall.gamemenu.GameMenuScreen;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.Projectile;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.weapons.Weapon;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.BiomeGenerator;
import deco2800.skyfall.worlds.generation.DeadEndGenerationException;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.graphics.HasPointLight;
import deco2800.skyfall.graphics.types.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level
 * items.
 */
public class World implements TouchDownObserver {

    protected int width;
    protected int length;

    //Used to generate random numbers
    protected Random random;

    public Map<String, Float> frictionMap;

    //A list of all the tiles within a world
    protected CopyOnWriteArrayList<Tile> tiles;
    protected CopyOnWriteArrayList<WorldGenNode> worldGenNodes;
    protected CopyOnWriteArrayList<VoronoiEdge> voronoiEdges;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    protected WorldParameters worldParameters;

    private GameMenuManager gmm = GameManager.getManagerFromInstance(GameMenuManager.class);

    //private MainCharacter mc = gmm.getMainCharacter();

    /**
     * The constructor for a world
     * @param worldParameters A class that contains the world parameters
     */
    public World(WorldParameters worldParameters) {
        this.worldParameters = worldParameters;

        random = new Random(worldParameters.getSeed());

        tiles = new CopyOnWriteArrayList<>();
        worldGenNodes = new CopyOnWriteArrayList<>();
        voronoiEdges = new CopyOnWriteArrayList<>();

        generateWorld();
        generateTileTypes();
        generateNeighbours();
        generateTileIndexes();
        initialiseFrictionmap();
    }

    /**
     * Generates the tiles and biomes in the world and adds the world to a listener to allow for interaction.
     * Continuously repeats generation until it reaches a stable world
     */
    protected void generateWorld() {
        while (true) {
            try {
                generateTiles();
                break;
            } catch (WorldGenException | DeadEndGenerationException | NotEnoughPointsException ignored) {
            }
        }

        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);

    }

    /**
     * Generates the tiles and biomes in a world
     * @throws NotEnoughPointsException When there are not enough points
     * @throws DeadEndGenerationException
     * @throws WorldGenException
     */
    private void generateTiles() throws NotEnoughPointsException, DeadEndGenerationException, WorldGenException {
        //TODO clean the biomes and tiles on every iteration
        ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();

        for (Tile tile : getTileMap()) {
            tile.setBiome(null);
        }

        for (AbstractBiome biome : worldParameters.getBiomes()) {
            biome.getTiles().clear();
        }

        int worldSize = worldParameters.getWorldSize();
        int nodeSpacing = worldParameters.getNodeSpacing();
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
                float oddCol = (q % 2 != 0 ? 0.5f : 0);
                Tile tile = new Tile(q, r + oddCol);
                tiles.add(tile);
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
            BiomeGenerator biomeGenerator = new BiomeGenerator(worldGenNodes, voronoiEdges, random, worldParameters);
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
    public void generateTileTypes() {
        for (AbstractBiome biome : worldParameters.getBiomes()) {
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
        return new CopyOnWriteArrayList<>(this.worldParameters.getEntities());
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
        return new CopyOnWriteArrayList<>(this.worldParameters.getEntities());
    }

    /**
     * Returns a list of entities in this world, ordered by their render level
     *
     * @return all entities in the world
     */
    public List<AgentEntity> getSortedAgentEntities() {
        List<AgentEntity> e = this.worldParameters.getEntities().stream().filter(p -> p instanceof AgentEntity)
                .map(p -> (AgentEntity) p).collect(Collectors.toList());

        Collections.sort(e);
        return e;
    }

    /**
     * Adds an entity to the world
     *
     * @param entity the entity to add
     */
    public void addEntity(AbstractEntity entity) {
        worldParameters.addEntity(entity);
        // Keep the entities sorted by render order
        Collections.sort(worldParameters.getEntities());
    }

    /**
     * Gets all the luminous entities from the world.
     * 
     * @return A list a luminous entities from the game world
     */
    public List<AbstractEntity> getLuminousEntities() {
        return worldParameters.getLuminousEntities();
    }

    /**
     * Removes an entity from the world
     *
     * @param entity the entity to remove
     */
    public void removeEntity(AbstractEntity entity) {
        worldParameters.removeEntity(entity);
        // Keep the entities sorted by render order
        Collections.sort(worldParameters.getEntities());
    }

    public void setEntities(List<AbstractEntity> entities) {
        this.worldParameters.setEntities(entities);

        for (AbstractEntity entity : entities) {
            if ((entity instanceof HasPointLight) && !entities.contains(entity)) {
                this.worldParameters.addLuminousEntity(entity);
            }
        }
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
        for (AbstractEntity e : this.worldParameters.getEntities()) {
            if (e.getEntityID() == entity.getEntityID()) {
                worldParameters.removeEntity(e);
                this.worldParameters.getEntities().add(entity);
                return;
            }
        }
        this.worldParameters.addEntity(entity);

        // Since MultiEntities need to be attached to the tiles they live on, setup that
        // connection.
        if (entity instanceof StaticEntity) {
            ((StaticEntity) entity).setup();
        }
    }

    public void onTick(long i) {
        for (AbstractEntity e : entitiesToDelete) {
            worldParameters.removeEntity(e);
        }

        for (Tile t : tilesToDelete) {
            tiles.remove(t);
        }

        //
        for (AbstractEntity e1 : this.getEntities()){
            e1.onTick(i);
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
        worldParameters.addBiome(biome);
    }

    /**
     * Gets the list of biomes in a world
     */
    public List<AbstractBiome> getBiomes() {
        return this.worldParameters.getBiomes();
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
        return worldParameters.getSeed();
    }

    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
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
                System.out.println(entity.getPosition());
                System.out.println();
                removeEntity(entity);
                List<AbstractEntity> drops = ((Harvestable) entity).harvest(tile);

                for (AbstractEntity drop : drops) {
                    addEntity(drop);
                }
            } else if (entity instanceof Chest) {
                GameMenuManager menuManager = GameManager.get().getManagerFromInstance(GameMenuManager.class);
                menuManager.open(new GameMenuScreen(menuManager).getChestTable((Chest) entity));
            } else if (entity instanceof Weapon) {
                MainCharacter mc = gmm.getMainCharacter();
                if (tile.getCoordinates().distance(mc.getPosition()) > 2) {
                    continue;
                }
                removeEntity(entity);
                gmm.getInventory().inventoryAdd((Item) entity);
                if (!mc.getEquipped().equals(((Weapon) entity).getName())) {
                    gmm.getInventory().quickAccessRemove(mc.getEquipped());
                    gmm.getInventory().quickAccessAdd(((Weapon) entity).getName());
                    mc.setEquipped(((Weapon) entity).getName());
                }
            }
        }
    }
}
