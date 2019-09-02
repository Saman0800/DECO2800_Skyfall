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

import java.lang.reflect.Field;
import java.rmi.activation.UnknownGroupException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BiomeGeneratorTest {
    private static final int TEST_COUNT = 5;
    private static final int[] NODE_COUNTS = { 10, 10, 10, 5, 5 };

    private static final int WORLD_SIZE = 80;
    private static final int NODE_SPACING = 5;

    // private static final int[] LAKE_SIZES = { 2, 2 };
    // private static final int LAKE_COUNT = 2;
    private static final int[] LAKE_SIZES = { 2, 2 };
    private static final int LAKE_COUNT = 2;

    // Rivers split biomes and break contiguity so they must be disabled for these tests.
    private static final int RIVER_WIDTH = 0;
    private static final int RIVER_COUNT = 0;

    private static final int BEACH_WIDTH = 1;

    private static ArrayList<ArrayList<ArrayList<WorldGenNode>>> biomeNodesList;
    private static ArrayList<ArrayList<AbstractBiome>> biomeLists;
    private static ArrayList<ArrayList<WorldGenNode>> worldGenNodesList;
    private static ArrayList<HashMap<WorldGenNode, AbstractBiome>> nodesBiomesList;
    private static List<Tile> originTiles;

    @BeforeClass
    public static void setup() {
        Random random = new Random(1);

        biomeNodesList = new ArrayList<>(TEST_COUNT);
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

                HashMap<WorldGenNode, AbstractBiome> nodesBiomes = null;
                try {
                    BiomeGenerator biomeGenerator =
                            new BiomeGenerator(worldGenNodes, edges, random, NODE_COUNTS, biomes, LAKE_COUNT,
                                               LAKE_SIZES, RIVER_COUNT, RIVER_WIDTH, BEACH_WIDTH);
                    biomeGenerator.generateBiomes();

                    try {
                        // There is no other way to get the node-biome data.

                        Field biomesField = biomeGenerator.getClass().getDeclaredField("biomes");
                        biomesField.setAccessible(true);
                        ArrayList<Object> biomesInProgress = (ArrayList<Object>) biomesField.get(biomeGenerator);

                        Field nodesBiomesField = biomeGenerator.getClass().getDeclaredField("nodesBiomes");
                        nodesBiomesField.setAccessible(true);
                        HashMap<WorldGenNode, Object> map =
                                (HashMap<WorldGenNode, Object>) nodesBiomesField.get(biomeGenerator);

                        nodesBiomes = new HashMap<>();
                        for (Map.Entry<WorldGenNode, Object> entry : map.entrySet()) {
                            nodesBiomes.put(entry.getKey(), biomes.get(biomesInProgress.indexOf(entry.getValue())));
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NotEnoughPointsException | DeadEndGenerationException e) {
                    continue;
                }

                // Determine which nodes are in which biomes by checking a single tile inside each node and getting its
                // biome.
                ArrayList<ArrayList<WorldGenNode>> biomeNodes = new ArrayList<>(biomes.size());
                for (int j = 0; j < biomes.size(); j++) {
                    biomeNodes.add(new ArrayList<>());
                }

                for (WorldGenNode node : worldGenNodes) {
                    biomeNodes.get(biomes.indexOf(node.getTiles().get(0).getBiome())).add(node);
                }

                biomeNodesList.add(biomeNodes);
                biomeLists.add(biomes);
                worldGenNodesList.add(worldGenNodes);
                nodesBiomesList.add(nodesBiomes);

                break;
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        biomeNodesList = null;
        biomeLists = null;
        worldGenNodesList = null;
        nodesBiomesList = null;
    }

    @Test
    public void testTileContiguity() {
        for (ArrayList<AbstractBiome> biomes : biomeLists) {
            for (AbstractBiome biome : biomes) {
                ArrayList<Tile> descendantTiles =
                        biome.getDescendantBiomes().stream().flatMap(descendant -> descendant.getTiles().stream())
                                .collect(Collectors.toCollection(ArrayList::new));
                HashSet<Tile> tilesToFind = new HashSet<>(descendantTiles);

                ArrayDeque<Tile> borderTiles = new ArrayDeque<>();

                Tile startTile = descendantTiles.get(0);
                tilesToFind.remove(startTile);
                borderTiles.add(startTile);

                while (!borderTiles.isEmpty()) {
                    Tile nextTile = borderTiles.remove();
                    for (Tile neighbour : nextTile.getNeighbours().values()) {
                        if (tilesToFind.contains(neighbour)) {
                            tilesToFind.remove(neighbour);
                            borderTiles.add(neighbour);
                        }
                    }
                }

                assertTrue(tilesToFind.isEmpty());
            }
        }
    }

    @Test
    public void testNodeContiguity() {
        for (int i = 0; i < TEST_COUNT; i++) {
            ArrayList<AbstractBiome> biomes = biomeLists.get(i);
            ArrayList<WorldGenNode> nodes = worldGenNodesList.get(i);
            HashMap<WorldGenNode, AbstractBiome> nodesBiomes = nodesBiomesList.get(i);

            for (AbstractBiome biome : biomes) {
                HashSet<WorldGenNode> nodesToFind =
                        nodes.stream().filter(node -> nodesBiomes.get(node).isDescendedFrom(biome)).collect(
                                Collectors.toCollection(HashSet::new));

                // TODO Fix empty biomes (is this even an issue?).
                if (nodesToFind.isEmpty()) {
                    continue;
                }

                ArrayDeque<WorldGenNode> borderNodes = new ArrayDeque<>();

                WorldGenNode startNode = nodesToFind.iterator().next();
                nodesToFind.remove(startNode);
                borderNodes.add(startNode);

                while (!borderNodes.isEmpty()) {
                    WorldGenNode nextNode = borderNodes.remove();
                    for (WorldGenNode neighbour : nextNode.getNeighbours()) {
                        if (nodesToFind.contains(neighbour)) {
                            nodesToFind.remove(neighbour);
                            borderNodes.add(neighbour);
                        }
                    }
                }

                assertEquals(biome.getBiomeName(), 0, nodesToFind.size());
            }
        }
    }

    @Test
    @Ignore // This test is no longer correct since lakes can take nodes from other biomes when they are generated.
    public void testBiomeNodeCounts() {
        for (int i = 0; i < TEST_COUNT; i++) {
            ArrayList<AbstractBiome> biomes = biomeLists.get(i);
            ArrayList<WorldGenNode> nodes = worldGenNodesList.get(i);
            HashMap<WorldGenNode, AbstractBiome> nodesBiomes = nodesBiomesList.get(i);
            for (int j = 0; j < NODE_COUNTS.length; j++) {
                AbstractBiome biome = biomes.get(j);
                long nodeCount = nodes.stream().filter(node -> nodesBiomes.get(node).isDescendedFrom(biome)).count();
                assertTrue(String.format("Expected node count (%d) must be less than or equal to actual (%d)",
                                         NODE_COUNTS[i], nodeCount),
                           NODE_COUNTS[i] <= nodeCount);
            }
        }
    }

    @Test
    public void testOceanOnBorders() {
        for (int i = 0; i < TEST_COUNT; i++) {
            ArrayList<WorldGenNode> worldGenNodes = worldGenNodesList.get(i);
            HashMap<WorldGenNode, AbstractBiome> nodesBiomes = nodesBiomesList.get(i);

            for (WorldGenNode node : worldGenNodes) {
                if (node.isBorderNode()) {
                    assertEquals("ocean", nodesBiomes.get(node).getBiomeName());
                }
            }
        }
    }

    @Test
    public void testBeachIsOnCoast() {
        for (ArrayList<AbstractBiome> biomes : biomeLists) {
            for (AbstractBiome biome : biomes) {
                if (biome.getBiomeName().equals("beach")) {
                    assertTrue(biome.getTiles().stream().anyMatch(tile -> tile.getNeighbours().values().stream()
                            .anyMatch(neighbour -> neighbour.getBiome().getBiomeName().equals("ocean"))));
                }
            }
        }
    }

    @Test
    public void testBeachHasParent() {
        for (ArrayList<AbstractBiome> biomes : biomeLists) {
            for (AbstractBiome biome : biomes) {
                if (biome.getBiomeName().equals("beach")) {
                    assertNotNull(biome.getParentBiome());
                }
            }
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