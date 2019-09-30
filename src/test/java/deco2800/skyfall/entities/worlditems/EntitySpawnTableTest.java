package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Before;
import org.junit.Ignore;
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
@PrepareForTest({ GameManager.class, WorldBuilder.class, WorldDirector.class, DatabaseManager.class,
        DataBaseConnector.class })
public class EntitySpawnTableTest {

    private World testWorld = null;

    private PhysicsManager physics;

    // size of test world
    final int worldSize = 100;

    // create a mock game manager for successful .getWorld()
    @Mock
    private GameManager mockGM;

    AbstractBiome biome;

    @Before
    public void createTestEnvironment() throws Exception {
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
        testWorld = worldBuilder.getWorld();

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        // required for proper EntitySpawnTable.placeEntity
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(testWorld);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);
    }

    // tests the place method
    @Test
    public void testPlaceEntity() {
        Tile tile1 = new Tile(null, 0.0f, 0.0f);
        Tile tile2 = new Tile(null, 0.0f, 1.0f);
        Rock rock = new Rock(tile2, true);

        // check tile has no rock
        assertFalse(tile1.hasParent());

        // place
        EntitySpawnTable.placeEntity(rock, tile1);

        // has the rock
        assertTrue(tile1.hasParent());

    }

    // simple method to count number of static entities on world
    private int countWorldEntities() {
        int count = 0;
        for (Tile tile : testWorld.getTileMap()) {
            if (tile.hasParent()) {
                count++;
            }
        }
        return count;
    }

    @Test
    @Ignore
    public void testDirectPlace() {
        // Generate a chunk.
        testWorld.getChunk(0, 0);

        // check if construction was valid
        assertEquals(worldSize, testWorld.getTileMap().size());

        // count before spawning
        assertEquals(0, countWorldEntities());

        // check basic spawnEntities
        final double chance = 0.95;
        for (Tile tile : testWorld.getTileMap()) {
            // EntitySpawnTable.spawnEntity(tileToPlace -> new Rock(tileToPlace, true),
            // chance, testWorld, tile);
        }

        // count after spawning
        assertTrue(countWorldEntities() > 0);
    }

    // @Test
    // @Ignore // This is no longer a valid test, since the minimum and maximum
    // values are no longer guaranteed.
    // public void maxMinPlacementTest() {
    // WorldBuilder worldBuilder = new WorldBuilder();
    // WorldDirector.constructTestWorld(worldBuilder);
    // World newWorld = worldBuilder.getWorld();
    //
    // // create tile map, add tiles and push to testWorld
    // CopyOnWriteArrayList<Tile> newTileMap = new CopyOnWriteArrayList<>();
    //
    // for (int i = 0; i < worldSize; i++) {
    // Tile tile = new Tile(null, 1.0f * i, 0.0f);
    // newTileMap.add(tile);
    // biome.addTile(tile);
    // }
    //
    // newWorld.setTileMap(newTileMap);
    //
    // EntitySpawnRule newRule = new EntitySpawnRule(2, 4, null, true);
    // newRule.setChance(1.0);
    //
    // Rock rock = new Rock();
    // EntitySpawnTable.spawnEntity(rock, newRule, newWorld);
    //
    // int count = 0;
    // for (Tile tile : newWorld.getTileMap()) {
    // if (tile.hasParent()) {
    // count++;
    // }
    // }
    //
    // assertTrue("Count was " + count, count <= 4);
    // }
}
