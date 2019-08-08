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

import java.util.*;
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
public class StaticEntityTest {
    private TestWorld w = null;

    @Mock
    private GameManager mockGM;

    @Before
    public void Setup() {
        w = new TestWorld();

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
    public void SetPropertiesTileConstructor() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        Tile tile1 = new Tile("grass_1_0", 0.0f, 0.0f);
        tileMap.add(tile1);
        w.setTileMap(tileMap);

        StaticEntity rock1 = new StaticEntity(tile1, 2, "rock", true);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(rock1.equals(rock1));
        assertTrue(rock1.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(rock1.getRenderOrder(), 2);
        assertEquals(rock1.getCol(), 0.0f, 0.001f);
        assertEquals(rock1.getRow(), 0.0f, 0.001f);
        assertTrue(rock1.getObstructed());
        assertEquals(rock1.getObjectName(), "staticEntityID");
    }

    @Test
    public void SetPropertiesRowColConstructor() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile("grass_1_0", 0.0f, 0.0f);
        Tile tile2 = new Tile("grass_1_0", 0.0f, 1.0f);
        Tile tile3 = new Tile("grass_1_0", 1.0f, -0.5f);
        Tile tile4 = new Tile("grass_1_0", 1.0f, 0.5f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        tileMap.add(new Tile("grass_1_0", -1.0f, 0.5f));
        tileMap.add(new Tile("grass_1_0", -1.0f, -0.5f));
        tileMap.add(new Tile("grass_1_0", 0.0f, -1.f));
        w.setTileMap(tileMap);

        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "tree");
        texture.put(new HexVector(1.0f, -0.5f), "tree");

        StaticEntity tree1 = new StaticEntity(0.0f, 1.0f, 2, texture);
        w.addEntity(tree1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(tree1.equals(tree1));
        assertTrue(tree1.getPosition().equals(new HexVector(0.0f, 1.0f)));
        assertEquals(tree1.getRenderOrder(), 2);
        assertEquals(tree1.getCol(), 0.0f, 0.0f);
        assertEquals(tree1.getRow(), 1.0f, 0.0f);
        assertTrue(tree1.getObstructed());
        assertEquals(tree1.getObjectName(), "staticEntityID");
        assertEquals(tree1.getTextures(), Collections.unmodifiableMap(texture));
    }

    @Test
    public void PlaceDownTestTileConstructor() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile("grass_1_0", 0.0f, 0.0f);
        Tile tile2 = new Tile("grass_1_0", 0.0f, 1.0f);
        Tile tile3 = new Tile("grass_1_0", 1.0f, -0.5f);
        Tile tile4 = new Tile("grass_1_0", 1.0f, 0.5f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        tileMap.add(new Tile("grass_1_0", -1.0f, 0.5f));
        tileMap.add(new Tile("grass_1_0", -1.0f, -0.5f));
        tileMap.add(new Tile("grass_1_0", 0.0f, -1.f));
        w.setTileMap(tileMap);

        // Just check that the tiles have indeed been placed into the world
        Tile tileGet1 = w.getTile(0.0f, 0.0f);
        assertNotNull(tileGet1);

        StaticEntity rock1 = new StaticEntity(tile1, 2, "rock", true);

        w.addEntity(rock1);

        // Check that the static entity has been placed down on the tile
        assertTrue("Tile has had a static entity placed on it with the tile construct"
                + " thus the tile should have a parent.", tile1.hasParent());
        assertTrue("Tile has had a static entity placed on it with the tile construct"
                + " thus the tile should be obstructed", tile1.isObstructed());

        assertFalse("Nothing placed on tile yet, should not have parent.", tile3.hasParent());
        assertFalse("Nothing placed on tile yet, should not be obstructed.", tile3.isObstructed());

        // Now that we have made a duplicate rock on tile 3 we expect that tile
        // 3 would have a parent and is obstructed.
        StaticEntity rock2 = rock1.newInstance(tile3);
        assertTrue(rock2.getPosition().equals(new HexVector(1.0f, -0.5f)));
        assertEquals(rock2.getRenderOrder(), 2);
        assertEquals(rock2.getCol(), 1.0f, 0.001f);
        assertEquals(rock2.getRow(), -0.5f, 0.001f);
        assertTrue(rock2.getObstructed());
        assertEquals(rock2.getObjectName(), "staticEntityID");

        assertTrue("New instance of static item not found.", tile3.hasParent());
        assertTrue("New instance of static item not found.", tile3.isObstructed());
    }

    @Test
    public void PlaceDownTestRowColConstructor() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile("grass_1_0", 0.0f, 0.0f);
        Tile tile2 = new Tile("grass_1_0", 0.0f, 1.0f);
        Tile tile3 = new Tile("grass_1_0", 1.0f, -0.5f);
        Tile tile4 = new Tile("grass_1_0", 1.0f, 0.5f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        tileMap.add(new Tile("grass_1_0", -1.0f, 0.5f));
        tileMap.add(new Tile("grass_1_0", -1.0f, -0.5f));
        tileMap.add(new Tile("grass_1_0", 0.0f, -1.f));
        w.setTileMap(tileMap);

        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "tree");
        texture.put(new HexVector(1.0f, -0.5f), "tree");

        StaticEntity tree1 = new StaticEntity(0.0f, 1.0f, 2, texture);
        w.addEntity(tree1);

        // Likewise for a static entity that occupies more than one tile
        assertTrue("Tile has had static entity placed on it. Thus the tile should be obstructed.",
                tile2.isObstructed());
        assertTrue("Tile has had static entity placed on it. Thus the tile should be obstructed.",
                tile4.isObstructed());

        StaticEntity tree2 = tree1.newInstance(0.0f, 0.0f);
        // StaticEntity tree2 = new StaticEntity(0.0f, 0.0f, 2, texture);
        assertTrue("Tile has had static entity placed on it. Thus the tile should be obstructed.",
                tile1.isObstructed());
        assertTrue("Tile has had static entity placed on it. Thus the tile should be obstructed.",
                tile3.isObstructed());
        // Check various properties of the duplicated tree
        assertTrue(tree2.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(tree2.getRenderOrder(), 2);
        assertEquals(tree2.getCol(), 0.0f, 0.0f);
        assertEquals(tree2.getRow(), 0.0f, 0.0f);
        assertTrue(tree2.getObstructed());
        assertEquals(tree2.getObjectName(), "staticEntityID");
        assertEquals(tree2.getTextures(), Collections.unmodifiableMap(texture));
    }
}
