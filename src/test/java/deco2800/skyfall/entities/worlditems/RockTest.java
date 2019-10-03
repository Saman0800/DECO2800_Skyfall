package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, PlayerPeon.class, WorldBuilder.class, WorldDirector.class, DatabaseManager.class,
        DataBaseConnector.class })
public class RockTest {
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
        WorldDirector.constructTestWorld(worldBuilder);
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

    @Test
    public void TestConstruction() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestRock rock1 = new ForestRock(tile1, true);

        // Make sure our tile is non-null
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertEquals(rock1, rock1);
        assertEquals(rock1.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(rock1.getRenderOrder(), 2);
        assertEquals(rock1.getCol(), 0.0f, 0.0f);
        assertEquals(rock1.getRow(), 0.0f, 0.0f);
        assertTrue(rock1.isObstructed());
        assertEquals(rock1.getObjectName(), "rock");
    }

    @Test
    public void TestAddedFunctions() {
        Tile tile1 = w.getTile(0.0f, 0.0f);
        Tile tile2 = w.getTile(0.0f, 1.0f);
        Tile tile3 = w.getTile(1.0f, -0.5f);
        Tile tile4 = w.getTile(1.0f, 0.5f);

        ForestRock rock1 = new ForestRock(tile1, true);

        // Check that the health interface is working as expected
        rock1.setHealth(5);
        assertEquals("Unexpected health value for ForestRock.", rock1.getHealth(), 5);

        ForestRock rock2 = rock1.newInstance(tile2);

        assertNotEquals(rock1, rock2);

        // check various properties of this new rock
        assertEquals(rock2.getPosition(), new HexVector(0.0f, 1.0f));
        assertEquals(rock2.getRenderOrder(), 2);
        assertEquals(rock2.getCol(), 0.0f, 0.001f);
        assertEquals(rock2.getRow(), 1.0f, 0.001f);
        assertTrue(rock2.isObstructed());
        String rockObjectName = "rock";
        assertEquals("ForestRock id was " + rock2.getObjectName() + " but expected " + rockObjectName, rockObjectName,
                rock2.getObjectName());

        // Check that the Overwritten newInstance method is working as expected
        // Check that the static entity has been placed down on the tile
        assertTrue("Tile has had a rock placed on it with the tile construct thus the tile should have a parent.",
                tile2.hasParent());
        assertTrue("Tile has had a rock placed on it with the tile construct thus the tile should be obstructed",
                tile2.isObstructed());
        // Check that no other tiles have static instances
        assertFalse("Unexpected rock placement.", tile3.hasParent());
        assertFalse("Unexpected rock placement.", tile3.isObstructed());
        assertFalse("Unexpected rock placement.", tile4.hasParent());
        assertFalse("Unexpected rock placement.", tile4.isObstructed());
    }

    @Test
    public void TestGetandSet() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestRock rock1 = new ForestRock(tile1, true);

        rock1.setHealth(3);
        assertEquals(3, rock1.getHealth());
    }
}