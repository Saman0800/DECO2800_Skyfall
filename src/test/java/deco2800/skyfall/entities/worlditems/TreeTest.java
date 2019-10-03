package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.entities.WoodCube;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class TreeTest {
    private World w = null;

    private PhysicsManager physics;

    @Mock
    private GameManager mockGM;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = PowerMockito.mock(DataBaseConnector.class);
        PowerMockito.when(connector.loadChunk(any(World.class), anyInt(), anyInt()))
                .then((Answer<Chunk>) invocation -> {
                    Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                            invocation.getArgumentAt(1, Integer.class), invocation.getArgumentAt(2, Integer.class));
                    chunk.generateEntities();
                    return chunk;
                });

        DatabaseManager manager = PowerMockito.mock(DatabaseManager.class);
        PowerMockito.when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.get()).thenReturn(manager);

        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        w = worldBuilder.getWorld();

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);

        // mocked input manager
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);
    }

    @Test
    public void TestConstruction() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestTree tree1 = new ForestTree(tile1, true);

        // Make sure our tile is non-null
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertEquals(tree1, tree1);
        assertNotEquals(null, tree1);
        assertEquals(tree1.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(tree1.getRenderOrder(), 5);
        assertEquals(tree1.getCol(), 0.0f, 0.0f);
        assertEquals(tree1.getRow(), 0.0f, 0.0f);
        assertTrue(tree1.isObstructed());
        // NOTE: may change in future
        assertEquals(tree1.getObjectName(), "staticEntityID");
    }

    @Test
    public void TestnewInstance() {
        Tile tile1 = w.getTile(0.0f, 0.0f);
        Tile tile2 = w.getTile(0.0f, 1.0f);
        Tile tile3 = w.getTile(1.0f, -0.5f);
        Tile tile4 = w.getTile(1.0f, 0.5f);

        ForestTree tree1 = new ForestTree(tile1, true);
        ForestTree tree2 = tree1.newInstance(tile2);

        assertNotEquals("Duplicated tree on the same position", tree1, tree2);

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
    public void TestHarvest() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestTree tree1 = new ForestTree(tile1, true);

        List<AbstractEntity> drops = tree1.harvest(tile1);

        assertTrue("Unexpected drop size.", 0 <= drops.size());
        assertTrue("Unexpected drop size.", 15 >= drops.size());

        // Check that the drops are instance of Woodblocks
        if (0 < drops.size()) {
            AbstractEntity dropItem = drops.get(0);
            assertTrue("Incorrect instance type for tree drop", dropItem instanceof WoodCube);
        }
    }

    @Test
    public void TestHashCode() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestTree tree1 = new ForestTree(tile1, true);

        // Calculate the expected hash code
        float result = 1;
        final float prime = 31;
        result = (result + tree1.getCol()) * prime;
        result = (result + tree1.getRow()) * prime;
        result = (result + tree1.getHeight()) * prime;

        assertEquals("Unexpected hashcode for tree.", tree1.hashCode(), result, 0.0);
    }

    @Test
    public void TestWoodAmount() {
        Tile tile1 = w.getTile(0.0f, 0.0f);
        Tile tile2 = w.getTile(0.0f, 0.5f);

        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "tree");

        ForestTree tree1 = new ForestTree(tile1, true);
        ForestTree tree2 = new ForestTree(0.0f, 0.5f, 2, texture);

        assertEquals(15, tree1.getWoodAmount());

        assertEquals(15, tree2.getWoodAmount());
        tree2.decreaseWoodAmount();
        assertEquals(14, tree2.getWoodAmount());
    }
}