package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.OceanBiome;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.rmi.activation.UnknownGroupException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class LakeAndRiverTest {
    private static final int TEST_COUNT = 5;
    private static final int[] NODE_COUNTS = { 10, 10, 10, 5, 5 };

    private static final int WORLD_SIZE = 80;
    private static final int NODE_SPACING = 5;

    // private static final int[] LAKE_SIZES = { 2, 2 };
    // private static final int LAKE_COUNT = 2;
    private static final int[] LAKE_SIZES = { 2, 2 };
    private static final int LAKE_COUNT = 2;

    // Rivers split biomes and break contiguity so they must be disabled for these tests.
    private static final int RIVER_WIDTH = 1;
    private static final int RIVER_COUNT = 2;

    private static ArrayList<ArrayList<AbstractBiome>> biomeLists;
    private static ArrayList<ArrayList<WorldGenNode>> worldGenNodesList;
    private static ArrayList<HashMap<WorldGenNode, AbstractBiome>> nodesBiomesList;
    private static List<Tile> originTiles;

    @BeforeClass
    public static void setup() {
        Random random = new Random(3);

        biomeLists = new ArrayList<>(TEST_COUNT);
        worldGenNodesList = new ArrayList<>();
        nodesBiomesList = new ArrayList<>();
        originTiles = new ArrayList<>();

        for (int i = 0; i < TEST_COUNT; i++) {
            while (true) {
                ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();

                int nodeCount = Math.round((float) WORLD_SIZE * WORLD_SIZE * 4 / NODE_SPACING / NODE_SPACING);

                for (int j = 0; j < nodeCount; j++) {
                    // Sets coordinates to a random number from -WORLD_SIZE to WORLD_SIZE
                    float x = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
                    float y = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
                    worldGenNodes.add(new WorldGenNode(x, y));
                }

                // Apply Delaunay triangulation to the nodes, so that vertices and
                // adjacencies can be calculated. Also apply Lloyd Relaxation twice
                // for more smooth looking polygons
                try {
                    WorldGenNode.calculateVertices(worldGenNodes, WORLD_SIZE);
                    WorldGenNode.lloydRelaxation(worldGenNodes, 2, WORLD_SIZE);
                } catch (WorldGenException e) {
                    continue;
                }
                worldGenNodesList.add(worldGenNodes);

                ArrayList<Tile> tiles = new ArrayList<>();
                for (int q = -WORLD_SIZE; q <= WORLD_SIZE; q++) {
                    for (int r = -WORLD_SIZE; r <= WORLD_SIZE; r++) {
                        float oddCol = (q % 2 != 0 ? 0.5f : 0);

                        Tile tile = new Tile(q, r + oddCol);
                        tiles.add(tile);
                        if (q == 0 && r == 0) {
                            originTiles.add(tile);
                        }
                    }
                }

                generateTileNeighbours(tiles);
                // TODO this
                List<VoronoiEdge> edges = new ArrayList<>();

                try {
                    WorldGenNode.assignTiles(worldGenNodes, tiles, random, NODE_SPACING);
                    WorldGenNode.removeZeroTileNodes(worldGenNodes, WORLD_SIZE);
                    WorldGenNode.assignNeighbours(worldGenNodes, edges);
                } catch (WorldGenException e) {
                    continue;
                }

                ArrayList<AbstractBiome> biomes = new ArrayList<>(NODE_COUNTS.length + 1);
                for (int j = 0; j < NODE_COUNTS.length; j++) {
                    biomes.add(new ForestBiome());
                }

                VoronoiEdge.assignTiles(edges, tiles, WORLD_SIZE);
                VoronoiEdge.assignNeighbours(edges);

                try {
                    BiomeGenerator biomeGenerator =
                            new BiomeGenerator(worldGenNodes, edges, random, NODE_COUNTS, biomes, LAKE_COUNT,
                                    LAKE_SIZES, RIVER_COUNT, RIVER_WIDTH, 0);
                    biomeGenerator.generateBiomes();
                } catch (NotEnoughPointsException | DeadEndGenerationException e) {
                    continue;
                }

                biomeLists.add(biomes);

                // Get the biome for each node by checking the biome of one if it's
                // tiles
                HashMap<WorldGenNode, AbstractBiome> nodesBiomes = new HashMap<>();
                for (WorldGenNode node : worldGenNodes) {
                    nodesBiomes.put(node, node.getTiles().get(0).getBiome());
                }
                nodesBiomesList.add(nodesBiomes);

                break;
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        biomeLists = null;
        worldGenNodesList = null;
        nodesBiomesList = null;
    }

    @Test
    public void lakeNotInOceanOrOtherLakeTest() {
        for (HashMap<WorldGenNode, AbstractBiome> nodesBiomes : nodesBiomesList) {
            for (WorldGenNode node : nodesBiomes.keySet()) {
                if (nodesBiomes.get(node).getBiomeName().equals("lake")) {
                    for (WorldGenNode neighbour : node.getNeighbours()) {
                        assertNotEquals("ocean", nodesBiomes.get(neighbour).getBiomeName());
                        assertFalse(nodesBiomes.get(neighbour).getBiomeName().equals("lake")
                                && nodesBiomes.get(neighbour) != nodesBiomes.get(node));
                    }
                }
            }
        }
    }

    @Test
    public void parentBiomeTest() {
        for (List<AbstractBiome> biomes : biomeLists) {
            for (AbstractBiome biome : biomes) {
                if (biome.getBiomeName().equals("lake")) {
                    assertNotNull(biome.getParentBiome());
                } else if (biome.getBiomeName().equals("river")) {
                    assertNotNull(biome.getParentBiome());
                    assertEquals("lake", biome.getParentBiome().getBiomeName());
                }
            }
        }
    }

    @Test
    public void noWaterOnOriginTile() {
        for (Tile tile : originTiles) {
            assertFalse(tile.getBiome().getBiomeName().equals("lake")
                    || tile.getBiome().getBiomeName().equals("river"));
        }
    }

    @Test
    public void correctNumberTest() {
        for (List<AbstractBiome> biomes : biomeLists) {
            int noLakes = 0;
            int noRivers = 0;
            for (AbstractBiome biome : biomes) {
                if (biome.getBiomeName().equals("lake")) {
                    noLakes++;
                }
                if (biome.getBiomeName().equals("river")) {
                    noRivers++;
                }
            }
            assertEquals(LAKE_COUNT, noLakes);
            assertEquals(RIVER_COUNT, noRivers);
        }
    }

    // Adapted from AbstractWorld.generateNeighbours().
    private static void generateTileNeighbours(List<Tile> tiles) {
        //multiply coords by 2 to remove floats
        Map<Integer, Map<Integer, Tile>> tileMap = new HashMap<>();
        Map<Integer, Tile> columnMap;
        for (Tile tile : tiles) {
            columnMap = tileMap.getOrDefault((int) tile.getCol() * 2, new HashMap<>());
            columnMap.put((int) (tile.getRow() * 2), tile);
            tileMap.put((int) (tile.getCol() * 2), columnMap);
        }

        for (Tile tile : tiles) {
            int col = (int) (tile.getCol() * 2);
            int row = (int) (tile.getRow() * 2);

            //West
            if (tileMap.containsKey(col - 2)) {
                //North West
                if (tileMap.get(col - 2).containsKey(row + 1)) {
                    tile.addNeighbour(Tile.NORTH_WEST, tileMap.get(col - 2).get(row + 1));
                }

                //South West
                if (tileMap.get(col - 2).containsKey(row - 1)) {
                    tile.addNeighbour(Tile.SOUTH_WEST, tileMap.get(col - 2).get(row - 1));
                }
            }

            //Central
            if (tileMap.containsKey(col)) {
                //North
                if (tileMap.get(col).containsKey(row + 2)) {
                    tile.addNeighbour(Tile.NORTH, tileMap.get(col).get(row + 2));
                }

                //South
                if (tileMap.get(col).containsKey(row - 2)) {
                    tile.addNeighbour(Tile.SOUTH, tileMap.get(col).get(row - 2));
                }
            }

            //East
            if (tileMap.containsKey(col + 2)) {
                //North East
                if (tileMap.get(col + 2).containsKey(row + 1)) {
                    tile.addNeighbour(Tile.NORTH_EAST, tileMap.get(col + 2).get(row + 1));
                }

                //South East
                if (tileMap.get(col + 2).containsKey(row - 1)) {
                    tile.addNeighbour(Tile.SOUTH_EAST, tileMap.get(col + 2).get(row - 1));
                }
            }
        }
    }
}