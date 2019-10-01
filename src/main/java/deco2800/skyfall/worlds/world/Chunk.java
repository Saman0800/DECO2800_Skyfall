package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.Saveable;
import deco2800.skyfall.entities.worlditems.EntitySpawnRule;
import deco2800.skyfall.entities.worlditems.EntitySpawnTable;
import deco2800.skyfall.worlds.Tile;
import org.javatuples.Pair;

import java.util.*;

public class Chunk implements Saveable<Chunk.ChunkMemento> {
    /** The length of a side of the chunk (i.e. the chunk will contain this squared tiles). */
    public static final int CHUNK_SIDE_LENGTH = 10;

    // The world which contains this chunk.
    private World world;

    // x and y values of the chunk (in chunk coordinates).
    private int x;
    private int y;

    // Contents of the chunk.
    private ArrayList<Tile> tiles;
    private ArrayList<AbstractEntity> entities;

    /**
     * Loads the chunk at the specified coordinates and adds it to the world. If the chunk has not been generated yet,
     * this also generates it.
     *
     * @param world the world to which the chunk is added
     * @param x     the x coordinate of the chunk (in chunk coordinates)
     * @param y     the y coordinate of the chunk (in chunk coordinates)
     *
     * @return the loaded chunk
     */
    static Chunk loadChunkAt(World world, int x, int y) {
        return DatabaseManager.get().getDataBaseConnector().loadChunk(world, x, y);
    }

    /**
     * Gets the chunk coordinates from world coordinates.
     *
     * @param col the world column of the point to check
     * @param row the world row of the point to check
     *
     * @return the coordinates of the corresponding chunk
     */
    public static Pair<Integer, Integer> getChunkForCoordinates(double col, double row) {
        double tileCol = Math.round(col);
        double tileRowOffset = tileCol % 2 == 0 ? 0 : 0.5;
        double tileRow = Math.round(row - tileRowOffset);

        int chunkX = (int) Math.floor(tileCol / CHUNK_SIDE_LENGTH);
        int chunkY = (int) Math.floor(tileRow / CHUNK_SIDE_LENGTH);

        return new Pair<>(chunkX, chunkY);
    }

    /**
     * Constructs a new chunk from a save.
     *
     * @param world   the world to which the chunk is added
     * @param memento the memento from which to load the chunk
     */
    public Chunk(World world, ChunkMemento memento) {
        this(world, memento.x, memento.y);
        this.load(memento);
    }

    /**
     * Gets the world of the chunk
     * @return The world that the chunk is in
     */
    public World getWorld() {
        return world;
    }

    /**
     * Constructs a new chunk in the specified world at the specified coordinates and backed by the specified {@link
     * ArrayList}s (i.e. they are used internally).
     *
     * @param world    the world to which the chunk is added
     * @param x        the x coordinate of the chunk
     * @param y        the y coordinate of the chunk
     * @param tiles    the list used internally to store the tiles
     * @param entities the list used internally to store the entities
     */
    public Chunk(World world, int x, int y, ArrayList<Tile> tiles, ArrayList<AbstractEntity> entities) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.tiles = tiles;
        this.entities = entities;

        // Add this now so that the static entity generation works.
        world.getLoadedChunks().put(new Pair<>(x, y), this);
    }

    /**
     * Constructs a new cunk in the specified world at the specified coordinates.
     *
     * @param world the world to which the chunk is added
     * @param x     the x coordinate of the chunk
     * @param y     the y coordinate of the chunk
     */
    public Chunk(World world, int x, int y) {
        this(world, x, y, new ArrayList<>(), new ArrayList<>());
        generateTiles();
    }

    /**
     * Generates the tiles to fill this chunk.
     */
    private void generateTiles() {
        for (int row = y * CHUNK_SIDE_LENGTH; row < (y + 1) * CHUNK_SIDE_LENGTH; row++) {
            for (int col = x * CHUNK_SIDE_LENGTH; col < (x + 1) * CHUNK_SIDE_LENGTH; col++) {
                float oddCol = (col % 2 != 0 ? 0.5f : 0);

                Tile tile = new Tile(world, col, row + oddCol);
                tiles.add(tile);
                tile.assignNode(world.worldGenNodes, world.worldParameters.getNodeSpacing());
                tile.assignEdge(world.riverEdges, world.beachEdges, world.worldParameters.getNodeSpacing(),
                                world.worldParameters.getRiverWidth(), world.worldParameters.getBeachWidth());
            }
        }

        generateNeighbours();
    }

    /**
     * Generates the neighbours for the tiles.
     */
    private void generateNeighbours() {
        // Multiply coords by 2 to remove floats.
        Map<Integer, Map<Integer, Tile>> tileMap = new HashMap<>();
        Map<Integer, Tile> columnMap;

        for (Tile tile : tiles) {
            columnMap = tileMap.getOrDefault((int) tile.getCol() * 2, new HashMap<>());
            columnMap.put((int) (tile.getRow() * 2), tile);
            tileMap.put((int) (tile.getCol() * 2), columnMap);
        }

        // Add bordering tiles from other chunks.
        ArrayList<Chunk> neighbouringChunks = new ArrayList<>();
        neighbouringChunks.add(world.getLoadedChunk(x - 1, y - 1));
        neighbouringChunks.add(world.getLoadedChunk(x, y - 1));
        neighbouringChunks.add(world.getLoadedChunk(x + 1, y - 1));
        neighbouringChunks.add(world.getLoadedChunk(x - 1, y));
        neighbouringChunks.add(world.getLoadedChunk(x + 1, y));
        neighbouringChunks.add(world.getLoadedChunk(x - 1, y + 1));
        neighbouringChunks.add(world.getLoadedChunk(x, y + 1));
        neighbouringChunks.add(world.getLoadedChunk(x + 1, y + 1));
        neighbouringChunks.removeIf(Objects::isNull);
        for (Chunk chunk : neighbouringChunks) {
            for (Tile tile : chunk.tiles) {
                if (tile.getCol() >= x * CHUNK_SIDE_LENGTH - 2 && tile.getCol() <= x * CHUNK_SIDE_LENGTH + 1 &&
                        tile.getRow() >= y * CHUNK_SIDE_LENGTH - 2 && tile.getRow() <= y * CHUNK_SIDE_LENGTH + 1) {
                    columnMap = tileMap.getOrDefault((int) tile.getCol() * 2, new HashMap<>());
                    columnMap.put((int) (tile.getRow() * 2), tile);
                    tileMap.put((int) (tile.getCol() * 2), columnMap);
                }
            }
        }

        for (Tile tile : getTiles()) {
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

    /**
     * Generates the static entities within this chunk.
     */
    public void generateEntities() {
        for (Tile tile : getTiles()) {
            for (EntitySpawnRule rule : world.getSpawnRules().get(tile.getBiome())) {
                EntitySpawnTable.spawnEntity(rule, world, tile);
            }
        }
    }

    /**
     * Saves this chunk and removes it from the world.
     */
    public void unload() {
        for (Tile tile : tiles) {
            tile.removeReferanceFromNeighbours();
        }

        DatabaseManager.get().getDataBaseConnector().saveChunk(this);

        world.getLoadedChunks().remove(new Pair<>(x, y));
    }

    /**
     * Gets the tiles in this chunk. This is the list used internally, so modifing it will modify the chunk. <em>Do NOT
     * modify this list without fully understanding how the chunks work.</em> If you want to modify this list but do not
     * want to modify the chunk, make a copy first.
     *
     * @return the list of tiles
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Gets the entities in this chunk. This is the list used internally, so modifing it will modify the chunk. <em>Do
     * NOT modify this list without fully understanding how the chunks work.</em> If you want to modify this list but do
     * not want to modify the chunk, make a copy first.
     *
     * @return the list of entities
     */
    public List<AbstractEntity> getEntities() {
        return entities;
    }

    /**
     * Adds an entity to this chunk. <em>Ensure that the entity belongs in this chunk before adding it.</em>
     *
     * @param entity the entity to add to this chunk
     */
    public void addEntity(AbstractEntity entity) {
        // Binary searching for the correct index and inserting is O(n), compared to O(nlog(n)) for inserting at the end
        // then sorting the list (or possibly O(n²), depending on the implementation of the sort, considering that the
        // worst case performance of quicksort is for the already-sorted list).

        int startRange = 0;
        int endRange = entities.size();

        while (startRange < endRange) {
            int middle = (startRange + endRange) / 2;
            if (entity.compareTo(entities.get(middle)) < 0) {
                endRange = middle;
            } else {
                startRange = middle + 1;
            }
        }

        entities.add(entity);
    }

    /**
     * Removes the entity from this chunk if is is present.
     *
     * @param entity the entity to remove.
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

    /**
     * Gets the x coordinate of this chunk in chunk coordinates.
     *
     * @return the x coordinate of this chunk
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of this chunk in chunk coordinates.
     *
     * @return the y coordinate of this chunk
     */
    public int getY() {
        return y;
    }

    /**
     * Constructs a memento containing the information required to reproduce this chunk.
     *
     * @return the memento containing the information require to reproduce this chunk
     */
    @Override
    public ChunkMemento save() {
        return new ChunkMemento(this);
    }

    /**
     * Loads the data from the given memento into this chunk.
     *
     * @param memento the memento from which to load the information
     */
    @Override
    public void load(ChunkMemento memento) {
        this.x = memento.x;
        this.y = memento.y;
    }

    /**
     * The memento storing the information required to reproduce a chunk.
     */
    public static class ChunkMemento extends AbstractMemento {
        private int x;
        private int y;

        /**
         * Constructs a memento with the information required to reproduce the given chunk.
         *
         * @param chunk the chunk to be saved
         */
        public ChunkMemento(Chunk chunk) {
            this.x = chunk.x;
            this.y = chunk.y;
        }
    }
}
