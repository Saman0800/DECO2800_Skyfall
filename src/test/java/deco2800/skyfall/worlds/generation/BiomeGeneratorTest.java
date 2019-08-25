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

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BiomeGeneratorTest {
    private static final int TEST_COUNT = 5;
    private static final int[] NODE_COUNTS = { 10, 10, 10, 5, 5 };

    private static final int WORLD_SIZE = 80;
    private static final int NODE_SPACING = 5;

    private static ArrayList<ArrayList<ArrayList<WorldGenNode>>> biomeNodesList;
    private static ArrayList<ArrayList<AbstractBiome>> biomeLists;

    @BeforeClass
    public static void setup() {
        Random random = new Random();

        biomeLists = new ArrayList<>(TEST_COUNT);
        biomeNodesList = new ArrayList<>(TEST_COUNT);

        for (int i = 0; i < TEST_COUNT; i++) {
            outer: while (true) {
                ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();

                int nodeCount = Math.round((float) WORLD_SIZE * WORLD_SIZE * 4 / NODE_SPACING * NODE_SPACING);

                for (int k = 0; k < nodeCount; k++) {
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
                    }
                }

                generateTileNeighbours(tiles);

                // Creates and stores tiles at the exact locations of nodes, ensuring that every node has a tile in it
                // by which to identify its biome.
                HashSet<Tile> nodePointTiles = new HashSet<>();
                for (WorldGenNode node : worldGenNodes) {
                    Tile tile = new Tile((float) node.getX(), (float) node.getY());
                    nodePointTiles.add(tile);
                    tiles.add(tile);
                }

                try {
                    WorldGenNode.assignTiles(worldGenNodes, tiles, random, NODE_SPACING);
                    WorldGenNode.removeZeroTileNodes(worldGenNodes, WORLD_SIZE);
                    WorldGenNode.assignNeighbours(worldGenNodes);
                } catch (WorldGenException e) {
                    continue;
                }

                ArrayList<AbstractBiome> biomes = new ArrayList<>(NODE_COUNTS.length + 1);
                for (int j = 0; j < NODE_COUNTS.length; j++) {
                    biomes.add(new ForestBiome());
                }
                biomes.add(new OceanBiome());

                try {
                    BiomeGenerator.generateBiomes(worldGenNodes, random, NODE_COUNTS, biomes, 0, 0);
                } catch (NotEnoughPointsException e) {
                    continue;
                }

                // Determine which nodes are in which biomes by checking a single tile inside each node and getting its
                // biome.
                ArrayList<ArrayList<WorldGenNode>> biomeNodes = new ArrayList<>();
                for (int j = 0; j < NODE_COUNTS.length + 1; j++) {
                    biomeNodes.add(new ArrayList<>());
                }
                for (WorldGenNode node : worldGenNodes) {
                    List<Tile> nodeTiles = node.getTiles();
                    // This should basically never happen, but it's probably possible for the imprecision of floating-
                    // point numbers to cause no tiles to be in a node, despite the existance of nodePointTiles. Restart if
                    // this happens.
                    if (nodeTiles.size() == 0) {
                        continue outer;
                    }
                    // Add the node to the corresponding biome node list.
                    biomeNodes.get(biomes.indexOf(nodeTiles.get(0).getBiome())).add(node);
                    nodeTiles.removeIf(nodePointTiles::contains);
                }

                for (AbstractBiome biome : biomes) {
                    biome.getTiles().removeIf(nodePointTiles::contains);
                }

                biomeLists.add(biomes);
                biomeNodesList.add(biomeNodes);

                break;
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        biomeLists = null;
        biomeNodesList = null;
    }

    @Test
    @Ignore("This test almost always passes, but can fail due to issue #99.")
    public void testTileContiguity() {
        for (ArrayList<AbstractBiome> biomes : biomeLists) {
            for (AbstractBiome biome : biomes) {
                HashSet<Tile> tilesToFind = new HashSet<>(biome.getTiles());

                ArrayDeque<Tile> borderTiles = new ArrayDeque<>();

                Tile startTile = biome.getTiles().get(0);
                tilesToFind.remove(startTile);
                borderTiles.add(startTile);

                while (!borderTiles.isEmpty()) {
                    Tile nextTile = borderTiles.remove();
                    for (int direction = 0; direction < 6; direction++) {
                        Tile neighbour = nextTile.getNeighbour(direction);
                        if (tilesToFind.contains(neighbour)) {
                            tilesToFind.remove(neighbour);
                            borderTiles.add(neighbour);
                        }
                    }
                }

                assertEquals(0, tilesToFind.size());
            }
        }
    }

    @Test
    public void testNodeContiguity() {
        for (ArrayList<ArrayList<WorldGenNode>> nodesList : biomeNodesList) {
            for (ArrayList<WorldGenNode> nodeList : nodesList) {
                HashSet<WorldGenNode> nodesToFind = new HashSet<>(nodeList);

                ArrayDeque<WorldGenNode> borderNodes = new ArrayDeque<>();

                WorldGenNode startNode = nodeList.get(0);
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

                assertEquals(0, nodesToFind.size());
            }
        }
    }

    @Test
    public void testBiomeNodeCounts() {
        for (ArrayList<ArrayList<WorldGenNode>> biomeNodes : biomeNodesList) {
            for (int i = 0; i < NODE_COUNTS.length; i++) {
                // There may be more nodes in the resulting biomes than in NODE_COUNTS because biomes may be expanded to
                // fill gaps.
                assertTrue(String.format("Expected node count (%d) must be less than or equal to actual (%d)",
                                         NODE_COUNTS[i], biomeNodes.get(i).size()),
                           NODE_COUNTS[i] <= biomeNodes.get(i).size());
            }
        }
    }

    @Test
    public void testOceanOnBorders() {
        for (ArrayList<ArrayList<WorldGenNode>> biomeNodes : biomeNodesList) {
            for (int i = 0; i < biomeNodes.size() - 1; i++) {
                ArrayList<WorldGenNode> nodes = biomeNodes.get(i);
                assertTrue("Non-ocean biomes cannot contain border nodes",
                           nodes.stream().noneMatch(WorldGenNode::isBorderNode));
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