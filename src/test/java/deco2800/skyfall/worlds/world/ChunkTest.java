package deco2800.skyfall.worlds.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.Tile;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(
        { WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class, GameManager.class })
public class ChunkTest {
    private World world;

    @Before
    public void setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then(
                (Answer<Chunk>) invocation -> {
                    Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                                            invocation.getArgumentAt(1, Integer.class),
                                            invocation.getArgumentAt(2, Integer.class));
                    chunk.generateEntities();
                    return chunk;
                });

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        MainCharacter.resetInstance();
        WorldBuilder builder = new WorldBuilder();
        WorldDirector.constructTestWorld(builder, 0);
        MainCharacter mainCharacter = MainCharacter.getInstance(0, 0, 0.05f, "Main Piece", 10);
        builder.addEntity(mainCharacter);
        world = builder.getWorld();

        GameManager gm = mock(GameManager.class);
        when(gm.getWorld()).thenReturn(world);

        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gm);
    }

    @After
    public void tearDown() {
        world = null;
    }

    @Test
    public void testDynamicLoading() {
        HashSet<Pair<Integer, Integer>> expected = new HashSet<>();
        expected.add(new Pair<>(0, 0));

        assertEquals(expected, world.getLoadedChunks().keySet());

        world.onTick(0);

        expected.clear();
        for (int y = -4; y <= 3; y++) {
            for (int x = -4; x <= 3; x++) {
                expected.add(new Pair<>(x, y));
            }
        }
        assertEquals(expected, world.getLoadedChunks().keySet());

        MainCharacter.getInstance().setPosition(-55, 0);
        world.onTick(0);

        expected.clear();
        for (int y = -4; y <= 3; y++) {
            for (int x = -9; x <= -3; x++) {
                expected.add(new Pair<>(x, y));
            }
        }
        assertEquals(expected, world.getLoadedChunks().keySet());
    }

    @Test
    public void testInterChunkNeighbours() {
        world.onTick(0);

        Tile tile1 = world.getTile(0, 0);
        Tile tile2 = world.getTile(-1, 0.5f);

        assertNotEquals(Chunk.getChunkForCoordinates(tile1.getCol(), tile1.getRow()),
                        Chunk.getChunkForCoordinates(tile2.getCol(), tile2.getRow()));
        assertEquals(tile2, tile1.getNeighbour(Tile.NORTH_WEST));
        assertEquals(tile1, tile2.getNeighbour(Tile.SOUTH_EAST));
    }

    @Test
    public void testTilesWithinChunkBounds() {
        world.onTick(0);

        for (Chunk chunk : world.getLoadedChunks().values()) {
            for (Tile tile : chunk.getTiles()) {
                assertEquals(new Pair<>(chunk.getX(), chunk.getY()),
                             Chunk.getChunkForCoordinates(tile.getCol(), tile.getRow()));
            }
        }
    }

    @Test
    public void testEntitiesWithinChunkBounds() {
        world.onTick(0);

        for (Chunk chunk : world.getLoadedChunks().values()) {
            for (AbstractEntity entity : chunk.getEntities()) {
                assertEquals(new Pair<>(chunk.getX(), chunk.getY()),
                             Chunk.getChunkForCoordinates(entity.getCol(), entity.getRow()));
            }
        }
    }

    @Test
    public void testChunksMapCoordinatesMatch() {
        world.onTick(0);

        for (Map.Entry<Pair<Integer, Integer>, Chunk> entry : world.getLoadedChunks().entrySet()) {
            assertEquals(entry.getKey(), new Pair<>(entry.getValue().getX(), entry.getValue().getY()));
        }
    }
}