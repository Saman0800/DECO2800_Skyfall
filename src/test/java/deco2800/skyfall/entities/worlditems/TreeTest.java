package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.util.HexVector;

import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, DatabaseManager.class, PlayerPeon.class })
public class TreeTest {
    private World w = null;

    private PhysicsManager physics;

    @Mock
    private GameManager mockGM;

    @Before
    public void Setup() {

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
        // Populate the world with tiles
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        Tile tile1 = new Tile(0.0f, 0.0f);
        tileMap.add(tile1);
        w.setTileMap(tileMap);

        Tree tree1 = new Tree(tile1, true);

        // Make sure our tile is non-null
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(tree1.equals(tree1));
        assertFalse(tree1.equals(null));
        assertTrue(tree1.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(tree1.getRenderOrder(), 5);
        assertEquals(tree1.getCol(), 0.0f, 0.0f);
        assertEquals(tree1.getRow(), 0.0f, 0.0f);
        assertTrue(tree1.isObstructed());
        // NOTE: may change in future
        assertEquals(tree1.getObjectName(), "staticEntityID");
    }

    @Test
    public void TestnewInstance() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile(0.0f, 0.0f);
        Tile tile2 = new Tile(0.0f, 1.0f);
        Tile tile3 = new Tile(1.0f, -0.5f);
        Tile tile4 = new Tile(1.0f, 0.5f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        w.setTileMap(tileMap);

        Tree tree1 = new Tree(tile1, true);
        Tree tree2 = tree1.newInstance(tile2);

        assertFalse("Duplicated tree on the same position", tree1.equals(tree2));

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
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile(0.0f, 0.0f);
        Tile tile2 = new Tile(0.0f, 1.0f);
        Tile tile3 = new Tile(1.0f, -0.5f);
        Tile tile4 = new Tile(1.0f, 0.5f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        w.setTileMap(tileMap);

        Tree tree1 = new Tree(tile1, true);

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
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        Tile tile1 = new Tile(0.0f, 0.0f);
        tileMap.add(tile1);
        w.setTileMap(tileMap);

        Tree tree1 = new Tree(tile1, true);

        // Calculate the expected hash code
        float result = 1;
        final float prime = 31;
        result = (result + tree1.getCol()) * prime;
        result = (result + tree1.getRow()) * prime;
        result = (result + tree1.getHeight()) * prime;

        assertTrue("Unexpected hashcode for tree.", tree1.hashCode() == result);
    }

    @Test
    public void TestWoodAmount() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        Tile tile1 = new Tile(0.0f, 0.0f);
        Tile tile2 = new Tile(0.0f, 0.5f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        w.setTileMap(tileMap);

        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "tree");

        Tree tree1 = new Tree(tile1, true);
        Tree tree2 = new Tree(0.0f, 0.5f, 2, texture);

        assertEquals(15, tree1.getWoodAmount());

        assertEquals(15, tree2.getWoodAmount());
        tree2.decreaseWoodAmount();
        assertEquals(14, tree2.getWoodAmount());
    }
}