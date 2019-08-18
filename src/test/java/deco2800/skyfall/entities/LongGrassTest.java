package deco2800.skyfall.entities;

import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.worlds.TestWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.AbstractBiome;
import deco2800.skyfall.worlds.ForestBiome;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, DatabaseManager.class, PlayerPeon.class })
public class LongGrassTest {
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

        LongGrass longGrass1 = new LongGrass(tile1, true);

        // Make sure our tile is non-null
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(longGrass1.equals(longGrass1));
        assertTrue(longGrass1.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(longGrass1.getRenderOrder(), 2);
        assertEquals(longGrass1.getCol(), 0.0f, 0.0f);
        assertEquals(longGrass1.getRow(), 0.0f, 0.0f);
        assertTrue(longGrass1.isObstructed());
        assertEquals(longGrass1.getObjectName(), "long_grass");
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

        LongGrass longGrass1 = new LongGrass(tile1, true);
        longGrass1.newInstance(tile2);

        // Check that the Overwritten newInstance method is working as expected
        // Check that the static entity has been placed down on the tile
        assertTrue("Tile has had a rock placed on it with the tile construct thus the tile should have a parent.",
                tile2.hasParent());
        assertTrue("Tile has had a rock placed on it with the tile construct thus the tile should be obstructed",
                tile2.isObstructed());
    }
}