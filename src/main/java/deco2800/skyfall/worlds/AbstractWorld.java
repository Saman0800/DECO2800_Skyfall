package deco2800.skyfall.worlds;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.ScreenManager;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
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
public abstract class AbstractWorld {

    protected List<AbstractEntity> entities = new CopyOnWriteArrayList<>();

    protected int width;
    protected int length;

    protected int worldSize;
    protected int nodeSpacing;
    protected int[] biomeSizes;
    protected int numOfLakes;
    protected int lakeSize;

    private long seed;

    // List that contains the world biomes
    protected ArrayList<AbstractBiome> biomes;
    protected Map<String, Float> frictionMap;

    protected CopyOnWriteArrayList<Tile> tiles;
    protected CopyOnWriteArrayList<WorldGenNode> worldGenNodes;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    protected AbstractWorld(long seed, int worldSize, int nodeSpacing, int[] biomeSizes, int numOfLakes, int lakeSize) {
        Random random = new Random(seed);
        this.seed = seed;
        this.biomeSizes = biomeSizes;
        this.numOfLakes = numOfLakes;
        this.lakeSize = lakeSize;

        this.worldSize = worldSize;
        this.nodeSpacing = nodeSpacing;

        tiles = new CopyOnWriteArrayList<>();
        worldGenNodes = new CopyOnWriteArrayList<>();

        tiles = new CopyOnWriteArrayList<Tile>();
        // worldGenNodes = new CopyOnWriteArrayList<>();
        biomes = new ArrayList<>();

        long startTime = System.nanoTime();
        generateWorld(random);
        generateNeighbours();
        generateTileIndexes();

        generateTileTypes(random);
        initialiseFrictionmap();

        // Saving the world for test, and likely saving and loading later
        // try {
        // saveWorld("ExampleWorldOutput.txt");
        // } catch (IOException e){
        // System.out.println("Could not save world");
        // }

        System.out.println((System.nanoTime() - startTime) / 1000000);
    }

    protected abstract void generateWorld(Random random);

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

    public void initialiseFrictionmap() {
        frictionMap = new HashMap<>();
        frictionMap.put("grass", 0.6f);
        frictionMap.put("water", 0.2f);
        frictionMap.put("rock", 0.3f);
        frictionMap.put("mountain", 0.4f);
        frictionMap.put("ice", 0.8f);
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


            if (e1.getCollider() == null) {
                break;
            }
            Collider c1 = e1.getCollider();
            for (AbstractEntity e2 : this.getEntities()) {
                if (e2.getCollider() == null) {
                    break;
                }
                Collider c2 = e2.getCollider();

                if (e1 != e2 && c1.overlaps(c2)) {
                    if (e1 instanceof ScreenManager.MainCharacter || e2 instanceof ScreenManager.MainCharacter) {
                        break;
                    }
                    // collision handler
                    this.handleCollision(e1, e2);
                    break;
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
            Projectile projectile = (Projectile) e1;
            EnemyEntity enemy = (EnemyEntity) e2;

            enemy.takeDamage(projectile.getDamage());
            projectile.destroy();

        } else if (e2 instanceof Projectile && e1 instanceof EnemyEntity) {
            Projectile projectile = (Projectile) e2;
            EnemyEntity enemy = (EnemyEntity) e1;

            enemy.takeDamage(projectile.getDamage());
            projectile.destroy();
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
}
