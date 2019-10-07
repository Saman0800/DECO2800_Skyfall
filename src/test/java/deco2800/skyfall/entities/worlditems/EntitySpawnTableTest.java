package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
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
import static org.mockito.Mockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, WorldBuilder.class, WorldDirector.class, DatabaseManager.class,
        DataBaseConnector.class })
public class EntitySpawnTableTest {

    private World testWorld = null;
    private NoiseGenerator dummyNoiseGen;

    private PhysicsManager physics;

    // size of test world
    final int worldSize = 100;

    // create a mock game manager for successful .getWorld()
    @Mock
    private GameManager mockGM;

    AbstractBiome biome;

    EntitySpawnRule spawnRuleTest;
    EntitySpawnRule spawnRulePerlinTest;

    Tile centre;
    Tile tile2;
    Tile tile3;
    Tile tile4;
    Tile tile5;
    Tile tile6;
    Tile tile7;

    AbstractRock dummyRock;

    @Before
    public void createTestEnvironment() throws Exception {

        centre = new Tile(null, 0.0f, 0.0f);
        tile2 = new Tile(null, 0.0f, 1.0f);
        tile3 = new Tile(null, 0.0f, -1.0f);
        tile4 = new Tile(null, 0.5f, 0.5f);
        tile5 = new Tile(null, -0.5f, 0.5f);
        tile6 = new Tile(null, 0.5f, -0.5f);
        tile7 = new Tile(null, -0.5f, -0.5f);

        centre.addNeighbour(Tile.NORTH, tile2);
        centre.addNeighbour(Tile.SOUTH, tile3);
        centre.addNeighbour(Tile.NORTH_EAST, tile4);
        centre.addNeighbour(Tile.NORTH_WEST, tile6);
        centre.addNeighbour(Tile.SOUTH_EAST, tile5);
        centre.addNeighbour(Tile.SOUTH_WEST, tile7);

        dummyRock = new ForestRock(centre, true);

        spawnRuleTest = new EntitySpawnRule(tile -> null, 10, 1);

        SpawnControl dummyControl = mock(SpawnControl.class);
        when(dummyControl.probabilityMap(any(Double.class))).thenReturn(1d);

        spawnRulePerlinTest = new EntitySpawnRule(tile -> null, 10, true, dummyControl);

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
        testWorld = mock(World.class);

        dummyNoiseGen = mock(NoiseGenerator.class);

        doAnswer((invocation) -> {
            centre.setObstructed(true);
            return null;
        }).when(testWorld).addEntity(any(AbstractEntity.class));

        when(testWorld.getStaticEntityNoise()).thenReturn(dummyNoiseGen);
        when(dummyNoiseGen.getOctavedPerlinValue(any(Double.class), any(Double.class))).thenReturn(0.5d);

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        // required for proper EntitySpawnTable.placeEntity
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(testWorld);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);
    }

    @Ignore
    @Test
    public void testNormalizeStaticEntityNoise() {
        assertEquals(1 - 1 / (1 + Math.exp(23 * (0.1 - 0.5))), EntitySpawnTable.normalizeStaticEntityNoise(0.1), 0.001);
    }

    @Ignore
    @Test
    public void testPlaceUniform() {
        EntitySpawnTable.placeUniform(tile -> dummyRock, spawnRuleTest, centre, testWorld);
        assertTrue("Entity not placed on centre tile", centre.isObstructed());
        centre.setObstructed(false);
    }

    @Ignore
    @Test
    public void testPlacePerlin() {
        EntitySpawnTable.placeUniform(tile -> dummyRock, spawnRulePerlinTest, centre, testWorld);
        assertTrue("Entity not placed on centre tile", centre.isObstructed());
        centre.setObstructed(false);
    }

    // tests the place method
    @Test
    @Ignore
    public void testPlaceEntity() {
        Tile tile1 = new Tile(null, 0.0f, 0.0f);
        Tile tile2 = new Tile(null, 0.0f, 1.0f);
        ForestRock rock = new ForestRock(tile2, true);

        // check tile has no rock
        assertFalse(tile1.hasParent());

        // place
        // EntitySpawnTable.placeEntity(rock, tile1);

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
            // EntitySpawnTable.spawnEntity(tileToPlace -> new ForestRock(tileToPlace,
            // true),
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
    // ForestRock rock = new ForestRock();
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
