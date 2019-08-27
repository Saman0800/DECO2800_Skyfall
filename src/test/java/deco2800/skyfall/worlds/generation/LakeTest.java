package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.biomes.*;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Not;

import java.util.*;

import static org.junit.Assert.*;

// TODO delete this test class and add the tests into BiomeGeneratorTest
public class LakeTest {

    private static final int NODE_SPACING = 2;
    private static final int WORLD_SIZE = 15;
    private static final int[] NODE_COUNTS = { 15, 15, 15 };
    private static final int LAKE_SIZE = 2;
    private static final int LAKE_COUNT = 2;
    private List<AbstractBiome> biomes;
    private List<WorldGenNode> nodes;
    private List<Tile> tiles;
    private HashMap<WorldGenNode, AbstractBiome> nodeBiomes;

    @Before
    public void setUp() throws Exception {
        Random random = new Random(0);
        biomes = new ArrayList<>(NODE_COUNTS.length + 1);
        nodes = new ArrayList<>();
        tiles = new ArrayList<>();
        while (true) {
            int nodeCount = Math.round((float) WORLD_SIZE * WORLD_SIZE * 4 / NODE_SPACING / NODE_SPACING);
            biomes.clear();
            nodes.clear();
            tiles.clear();
            for (int q = -WORLD_SIZE; q <= WORLD_SIZE; q++) {
                for (int r = -WORLD_SIZE; r <= WORLD_SIZE; r++) {
                    float oddCol = (q % 2 != 0 ? 0.5f : 0);

                    Tile tile = new Tile(q, r + oddCol);
                    tiles.add(tile);
                }
            }

            generateTileNeighbours(tiles);

            for (int i = 0; i < nodeCount; i++) {
                float x = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
                float y = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
                nodes.add(new WorldGenNode(x, y));
            }
            try {
                WorldGenNode.calculateVertices(nodes, WORLD_SIZE);
                WorldGenNode.lloydRelaxation(nodes, 2, WORLD_SIZE);
            } catch (WorldGenException e) {
                continue;
            }


            biomes.add(new ForestBiome());
            biomes.add(new DesertBiome());
            biomes.add(new MountainBiome());
            biomes.add(new OceanBiome());

            try {
                WorldGenNode.assignTiles(nodes, tiles, random, WORLD_SIZE);
                WorldGenNode.removeZeroTileNodes(nodes, WORLD_SIZE);
                WorldGenNode.assignNeighbours(nodes);
            } catch (WorldGenException e) {
                continue;
            }


            try {
                BiomeGenerator.generateBiomes(nodes, random, NODE_COUNTS, biomes, LAKE_COUNT, LAKE_SIZE);
            } catch (DeadEndGenerationException e) {
                continue;
            }

            // Get the biome for each node by checking the biome of one if it's
            // tiles
            nodeBiomes = new HashMap<>();
            for (WorldGenNode node : nodes) {
                nodeBiomes.put(node, node.getTiles().get(0).getBiome());
            }
            break;
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void notInOceanOrOtherLakeTest() {
        for (WorldGenNode node : nodeBiomes.keySet()) {
            if (nodeBiomes.get(node).getBiomeName().equals("lake")) {
                for (WorldGenNode neighbour : node.getNeighbours()) {
                    assertNotEquals("ocean", nodeBiomes.get(neighbour).getBiomeName());
                    assertFalse(nodeBiomes.get(neighbour).getBiomeName().equals("lake")
                            && nodeBiomes.get(neighbour) != nodeBiomes.get(node));
                }
            }
        }
    }

    @Test
    public void parentBiomeTest() {
        for (WorldGenNode node : nodes) {
            AbstractBiome biome = nodeBiomes.get(node);
            if (biome.getBiomeName().equals("lake")) {
                assertNotNull(biome.getParentBiome());
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
