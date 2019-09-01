package deco2800.skyfall.entities;

import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.worlds.TestWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, DatabaseManager.class, PlayerPeon.class })
public class RockTest {
    private TestWorld w = null;

    @Mock
    private GameManager mockGM;

    @Before
    public void Setup() {
        w = new TestWorld(0);

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);

        // mocked imput manager
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);
    }

    @Test
    public void TestConstruction() {
        // Populate the world with tiles
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        Tile tile1 = new Tile(0.0f, 0.0f);
        tileMap.add(tile1);
        w.setTileMap(tileMap);

        Rock rock1 = new Rock(tile1, true);

        // Make sure our tile is non-null
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(rock1.equals(rock1));
        assertTrue(rock1.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(rock1.getRenderOrder(), 2);
        assertEquals(rock1.getCol(), 0.0f, 0.0f);
        assertEquals(rock1.getRow(), 0.0f, 0.0f);
        assertTrue(rock1.isObstructed());
        assertEquals(rock1.getObjectName(), "rock");
    }

    @Test
    public void TestAddedFunctions() {
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

        Rock rock1 = new Rock(tile1, true);

        // Check that the health interface is working as expected
        rock1.setHealth(5);
        assertEquals("Unexpected health value for Rock.", rock1.getHealth(), 5);

        Rock rock2 = rock1.newInstance(tile2);
        // check various properties of this new rock
        assertTrue(rock2.getPosition().equals(new HexVector(0.0f, 1.0f)));
        assertEquals(rock2.getRenderOrder(), 2);
        assertEquals(rock2.getCol(), 0.0f, 0.001f);
        assertEquals(rock2.getRow(), 1.0f, 0.001f);
        assertTrue(rock2.getObstructed());
        String rockObjectName = "rock";
        assertEquals("Rock id was " + rock2.getObjectName() + " but expected " + rockObjectName, rockObjectName,
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
}