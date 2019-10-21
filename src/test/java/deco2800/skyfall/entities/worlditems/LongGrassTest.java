package deco2800.skyfall.entities.worlditems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.util.Random;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, MainCharacter.class, WorldBuilder.class, WorldDirector.class, DatabaseManager.class,
                  DataBaseConnector.class })
public class LongGrassTest {
    private World w = null;

    private PhysicsManager physics;

    @Mock
    private GameManager mockGM;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
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

        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder, 0);
        w = worldBuilder.getWorld();
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

        // mocked imput manager
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);
    }

    @Ignore
    @Test
    public void TestConstruction() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestShrub longGrass1 = new ForestShrub(tile1, true);

        // Make sure our tile is non-null
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertEquals(longGrass1, longGrass1);
        assertEquals(longGrass1.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(longGrass1.getRenderOrder(), 2);
        assertEquals(longGrass1.getCol(), 0.0f, 0.0f);
        assertEquals(longGrass1.getRow(), 0.0f, 0.0f);
        assertTrue(longGrass1.isObstructed());
        assertEquals(longGrass1.getObjectName(), "forest_shrub");
    }

    @Ignore
    @Test
    public void TestAddedFunctions() {
        Tile tile1 = w.getTile(0.0f, 0.0f);
        Tile tile2 = w.getTile(0.0f, 1.0f);

        ForestShrub longGrass1 = new ForestShrub(tile1, true);
        longGrass1.newInstance(tile2);

        // Check that the Overwritten newInstance method is working as expected
        // Check that the static entity has been placed down on the tile
        assertTrue("Tile has had a rock placed on it with the tile construct thus the tile should have a parent.",
                tile2.hasParent());
        assertTrue("Tile has had a rock placed on it with the tile construct thus the tile should be obstructed",
                tile2.isObstructed());
    }
}