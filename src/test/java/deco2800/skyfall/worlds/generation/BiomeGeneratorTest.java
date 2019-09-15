package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.OceanBiome;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.rmi.activation.UnknownGroupException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class})
public class BiomeGeneratorTest {
    private static final int TEST_COUNT = 5;

    private static List<World> worlds;

    @BeforeClass
    public static void setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withNoArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then(
                (Answer<Chunk>) invocation -> new Chunk(invocation.getArgumentAt(0, World.class),
                                                        invocation.getArgumentAt(1, Integer.class),
                                                        invocation.getArgumentAt(2, Integer.class)));

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        worlds = new ArrayList<>();

        for (int i = 0; i < TEST_COUNT; i++) {
            WorldBuilder builder = new WorldBuilder();
            WorldDirector.constructNBiomeSinglePlayerWorld(builder, 3);
            worlds.add(builder.getWorld());
        }
    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void testTileContiguity() {
        for (World world : worlds) {
            for (AbstractBiome biome : world.getBiomes()) {
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
        for (World world : worlds) {
            for (AbstractBiome biome : world.getBiomes()) {
                HashSet<WorldGenNode> nodesToFind = world.getWorldGenNodes().stream()
                        .filter(node -> node.getBiome().isDescendedFrom(biome))
                        .collect(Collectors.toCollection(HashSet::new));

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
        for (World world : worlds) {
            for (int i = 0; i < world.getWorldParameters().getBiomeSizes().size(); i++) {
                AbstractBiome biome = world.getBiomes().get(i);
                long nodeCount = world.getWorldGenNodes().stream().filter(node -> node.getBiome().isDescendedFrom(biome)).count();
                int expectedNodeCount = world.getWorldParameters().getBiomeSizes().get(i);
                assertTrue(String.format("Expected node count (%d) must be less than or equal to actual (%d)",
                                         expectedNodeCount, nodeCount), expectedNodeCount <= nodeCount);
            }
        }
    }

    @Test
    public void testOceanOnBorders() {
        for (World world : worlds) {
            for (WorldGenNode node : world.getWorldGenNodes()) {
                if (node.isBorderNode()) {
                    assertEquals("ocean", node.getBiome().getBiomeName());
                }
            }
        }
    }

    @Test
    public void testBeachIsOnCoast() {
        for (World world : worlds) {
            for (AbstractBiome biome : world.getBiomes()) {
                if (biome.getBiomeName().equals("beach")) {
                    assertTrue(biome.getTiles().stream().anyMatch(tile -> tile.getNeighbours().values().stream()
                            .anyMatch(neighbour -> neighbour.getBiome().getBiomeName().equals("ocean"))));
                }
            }
        }
    }

    @Test
    public void testBeachHasParent() {
        for (World world : worlds) {
            for (AbstractBiome biome : world.getBiomes()) {
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