package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import org.javatuples.Pair;

import java.util.*;

public class Chunk {
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

    // TODO:Ontonator Make this take the world as a parameter or something.
    public static Chunk loadChunkAt(World world, int x, int y) {
        // FIXME:Ontonator Implement.
        return new Chunk(world, x, y);
    }

    public static Pair<Integer, Integer> getChunkForCoordinates(double col, double row) {
        // TODO:Ontonator Check that this works.
        double tileCol = Math.round(col);
        double tileRowOffset = tileCol % 2 == 0 ? 0 : 0.5;
        double tileRow = Math.round(row - tileRowOffset);

        int chunkX = (int) Math.floor(tileCol / CHUNK_SIDE_LENGTH);
        int chunkY = (int) Math.floor(tileRow / CHUNK_SIDE_LENGTH);

        return new Pair<>(chunkX, chunkY);
    }

    public Chunk(World world, int x, int y, ArrayList<Tile> tiles, ArrayList<AbstractEntity> entities) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.tiles = tiles;
        this.entities = entities;
    }

    public Chunk(World world, int x, int y) {
        this(world, x, y, new ArrayList<>(), new ArrayList<>());
        generateTiles();
    }

    public void generateTiles() {
        // TODO:Ontonator Check that this works.
        createBlankTiles();
        generateNeighbours();
    }

    /**
     * Creates blank tiles which fill the area of this chunk.
     */
    private void createBlankTiles() {
        // TODO:Ontonator Does this need to check whether the chuk is completely within the world size?
        for (int row = y * CHUNK_SIDE_LENGTH; row < (y + 1) * CHUNK_SIDE_LENGTH; row++) {
            for (int col = x * CHUNK_SIDE_LENGTH; col < (x + 1) * CHUNK_SIDE_LENGTH; col++) {
                float oddCol = (col % 2 != 0 ? 0.5f : 0);

                Tile tile = new Tile(col, row + oddCol);
                tiles.add(tile);
                tile.assignNode(world.worldGenNodes, world.worldParameters.getNodeSpacing());
            }
        }
    }

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

    public void generateEntities() {
        // FIXME:Ontonator Implement.
    }

    public void unload() {
        for (Tile tile : tiles) {
            tile.removeReferanceFromNeighbours();
        }

        // FIXME:Ontonator Save.
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<AbstractEntity> getEntities() {
        return entities;
    }

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

    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
