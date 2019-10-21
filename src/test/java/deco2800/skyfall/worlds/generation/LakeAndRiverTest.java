package deco2800.skyfall.worlds.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class })
public class LakeAndRiverTest {
    private static final int TEST_COUNT = 5;

    private static List<World> worlds;

    @BeforeClass
    public static void setup() throws Exception {
        Random random = new Random(1);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then((Answer<Chunk>) invocation -> {
            Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                    invocation.getArgumentAt(1, Integer.class), invocation.getArgumentAt(2, Integer.class));
            chunk.generateEntities();
            return chunk;
        });

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        worlds = new ArrayList<>();

        for (int i = 0; i < TEST_COUNT; i++) {
            WorldBuilder builder = new WorldBuilder();
            WorldDirector.constructNBiomeSinglePlayerWorld(builder, 0, 3, false);
            builder.setNodeSpacing(5);
            builder.setWorldSize(60);
            worlds.add(builder.getWorld());
        }
    }

    @AfterClass
    public static void tearDown() {
        worlds = null;
    }

    @Test
    @Ignore // Sojourn ignore. Just ignoring this for now to run game.
    public void lakeNotInOceanOrOtherLakeTest() {
        for (World world : worlds) {
            for (WorldGenNode node : world.getWorldGenNodes()) {
                if (node.getBiome().getBiomeName().equals("lake")) {
                    for (WorldGenNode neighbour : node.getNeighbours()) {
                        assertNotEquals("ocean", neighbour.getBiome().getBiomeName());
                        assertFalse(neighbour.getBiome().getBiomeName().equals("lake")
                                && neighbour.getBiome() != node.getBiome());
                    }
                }
            }
        }
    }

    @Test
    public void parentBiomeTest() {
        for (World world : worlds) {
            for (AbstractBiome biome : world.getBiomes()) {
                if (biome.getBiomeName().equals("lake")) {
                    assertNotNull(biome.getParentBiome());
                }
            }
        }
    }

    @Test
    public void noWaterOnOriginTile() {
        for (World world : worlds) {
            Tile tile = world.getTile(0, 0);
            assertNotEquals("lake", tile.getBiome().getBiomeName());
            assertNotEquals("river", tile.getBiome().getBiomeName());
        }
    }

    @Test
    public void correctNumberTest() {
        // This is not calculated for rivers anymore as the number of biomes that
        // constitute a single river is now
        // random, and multiple rivers may be conjoined into one randomly as well.
        for (World world : worlds) {
            int noLakes = 0;
            for (AbstractBiome biome : world.getBiomes()) {
                if (biome.getBiomeName().equals("lake")) {
                    noLakes++;
                }
            }
            assertEquals(world.getWorldParameters().getNumOfLakes(), noLakes);
        }
    }

    @Test
    @Ignore
    public void riverTerminatingBiomeTest() {
        for (World world : worlds) {
            for (AbstractBiome biome : world.getBiomes()) {
                if (!biome.getBiomeName().equals("river")) {
                    continue;
                }
                List<AbstractBiome> adjacentWaterBodies = new ArrayList<>();
                for (Tile tile : biome.getTiles()) {
                    for (Tile neighbour : tile.getNeighbours().values()) {
                        boolean correctBiome = neighbour.getBiome().getBiomeName().equals("lake")
                                || neighbour.getBiome().getBiomeName().equals("ocean")
                                || (neighbour.getBiome().getBiomeName().equals("river")
                                        && neighbour.getBiome() != biome);
                        if (correctBiome && !adjacentWaterBodies.contains(biome)) {
                            adjacentWaterBodies.add(neighbour.getBiome());
                        }
                    }
                }
                assertTrue(adjacentWaterBodies.size() >= 2);
            }
        }
    }

    // Adapted from AbstractWorld.generateNeighbours().
    private static void generateTileNeighbours(List<Tile> tiles) {
        // multiply coords by 2 to remove floats
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
}