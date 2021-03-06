package deco2800.skyfall.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, MainCharacter.class, WorldBuilder.class, WorldDirector.class, DatabaseManager.class,
                  DataBaseConnector.class })
public class StaticEntityTest {
    private World w;

    private PhysicsManager physics;

    @Mock
    private GameManager mockGM;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = PowerMockito.mock(DataBaseConnector.class);
        PowerMockito.when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then(
                (Answer<Chunk>) invocation -> {
                    Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                                            invocation.getArgumentAt(1, Integer.class),
                                            invocation.getArgumentAt(2, Integer.class));
                    chunk.generateEntities();
                    return chunk;
                });

        DatabaseManager manager = PowerMockito.mock(DatabaseManager.class);
        PowerMockito.when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        PowerMockito.when(DatabaseManager.get()).thenReturn(manager);

        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder, 0);
        w = worldBuilder.getWorld();

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

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
        Tile tile1 = new Tile(null, 0.0f, 0.0f);
        tileMap.add(tile1);
        System.out.println("World " + w);
        for (Chunk chunk : w.getLoadedChunks().values()) {
            chunk.getEntities().clear();
        }
        for (Tile tile : tileMap) {
            w.addTile(tile);
        }

        StaticEntity rock1 = new StaticEntity(tile1, 2, "rock", true);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(rock1.equals(rock1));
        assertTrue(rock1.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(rock1.getRenderOrder(), 2);
        assertEquals(rock1.getCol(), 0.0f, 0.001f);
        assertEquals(rock1.getRow(), 0.0f, 0.001f);
        assertTrue(rock1.isObstructed());
        assertEquals(rock1.getObjectName(), "staticEntityID");
    }

    @Test
    public void SetPropertiesRowColConstructor() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        Tile tile1 = new Tile(null, 0.0f, 0.0f);
        tileMap.add(tile1);
        for (Chunk chunk : w.getLoadedChunks().values()) {
            chunk.getEntities().clear();
        }
        for (Tile tile : tileMap) {
            w.addTile(tile);
        }

        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "rock");
        StaticEntity rock1 = new StaticEntity(0.0f, 0.0f, 2, texture);

        w.addEntity(rock1);

        // Check that the various properties of this static entity have been
        // set correctly
        assertTrue(rock1.equals(rock1));
        assertTrue(rock1.getTextures().equals(texture));
        assertTrue(rock1.getPosition().equals(new HexVector(0.0f, 0.0f)));
        assertEquals(rock1.getRenderOrder(), 2);
        assertEquals(rock1.getCol(), 0.0f, 0.0f);
        assertEquals(rock1.getRow(), 0.0f, 0.0f);
        assertTrue(rock1.isObstructed());
        assertEquals(rock1.getObjectName(), "staticEntityID");

        Set<HexVector> childPos = new HashSet<HexVector>();
        childPos.add(new HexVector(0.0f, 0.0f));

        assertEquals(childPos, rock1.getChildrenPositions());
    }

    @Test
    public void PlaceDownTest() {
        Tile tile1 = w.getTile(0, 0);
        Tile tile2 = w.getTile(0, 1);
        Tile tile3 = w.getTile(1, -0.5f);
        Tile tile4 = w.getTile(1, 0.5f);

        StaticEntity rock1 = new StaticEntity(tile1, 2, "rock", true);

        w.addEntity(rock1);
        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "rock");
        texture.put(new HexVector(1.0f, -0.5f), "rock");
        w.addEntity(new StaticEntity(0.0f, 1.0f, 2, texture));

        // Check that the static entity has been placed down on the tile
        assertTrue("Tile has had a static entity placed on it with the tile construct"
                + " thus the tile should have a parent.", tile1.hasParent());
        assertTrue("Tile has had a static entity placed on it with the tile construct"
                + " thus the tile should be obstructed", tile1.isObstructed());

        // Likewise for a static entity that occupies more than one tile
        assertTrue("Tile has had static entity placed on it. Thus the tile should be obstructed.",
                tile2.isObstructed());
        assertTrue("Tile has had static entity placed on it. Thus the tile should be obstructed.",
                tile4.isObstructed());

        assertFalse("Nothing placed on tile yet, should not have parent.", tile3.hasParent());
        assertFalse("Nothing placed on tile yet, should not be obstructed.", tile3.isObstructed());

        // Now that we have made a duplicate rock on tile 3 we expect that tile
        // 3 would have a parent and is obstructed.
        rock1.newInstance(tile3);
        assertTrue("New instance of static item not found.", tile3.hasParent());
        assertTrue("New instance of static item not found.", tile3.isObstructed());
    }

    @Test
    public void NewInstanceTest() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile(null, 0.0f, 0.0f);
        Tile tile2 = new Tile(null, 0.0f, 1.0f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        for (Chunk chunk : w.getLoadedChunks().values()) {
            chunk.getEntities().clear();
        }
        for (Tile tile : tileMap) {
            w.addTile(tile);
        }

        StaticEntity rock1 = new StaticEntity(tile1, 2, "rock", true);
        StaticEntity rock2 = rock1.newInstance(tile2);

        assertTrue(tile2.isObstructed());
        assertFalse(rock1.equals(rock2));
    }
}
