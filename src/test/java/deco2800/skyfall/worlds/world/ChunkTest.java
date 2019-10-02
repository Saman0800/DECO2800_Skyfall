package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import org.javatuples.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(
        { WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class, GameManager.class })
public class ChunkTest {
    private static World world;

    @BeforeClass
    public static void setup() throws Exception {
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
        WorldDirector.constructTestWorld(builder);
        MainCharacter mainCharacter = MainCharacter.getInstance(0, 0, 0.05f, "Main Piece", 10);
        builder.addEntity(mainCharacter);
        world = builder.getWorld();

        GameManager gm = mock(GameManager.class);
        when(gm.getWorld()).thenReturn(world);

        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gm);
    }

    @AfterClass
    public static void tearDown() {
        world = null;
    }

    @Test
    public void testDynamicLoading() {
        HashSet<Pair<Integer, Integer>> expected = new HashSet<>();
        expected.add(new Pair<>(0, 0));

        assertEquals(expected, world.getLoadedChunks().keySet());

        world.onTick(0);

        expected.clear();
        for (int y = -6; y <= 6; y++) {
            for (int x = -6; x <= 5; x++) {
                expected.add(new Pair<>(x, y));
            }
        }
        assertEquals(expected, world.getLoadedChunks().keySet());

        MainCharacter.getInstance().setPosition(-55, 0);
        world.onTick(0);

        expected.clear();
        for (int y = -6; y <= 6; y++) {
            for (int x = -12; x <= 0; x++) {
                expected.add(new Pair<>(x, y));
            }
        }
        assertEquals(expected, world.getLoadedChunks().keySet());
    }
}