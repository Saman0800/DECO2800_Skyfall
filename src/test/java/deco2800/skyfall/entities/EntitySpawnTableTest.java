package deco2800.skyfall.entities;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.TestWorld;

import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class EntitySpawnTableTest {

    private TestWorld testWorld = null;

    // size of test world
    final int worldSize = 100;

    // create a mock game manager for successful .getWorld()
    @Mock
    private GameManager mockGM;

    AbstractBiome biome;

    @Before
    public void createTestEnvironment() {
        testWorld = new TestWorld(0);

        biome = new ForestBiome();

        // create tile map, add tiles and push to testWorld
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();

        for (int i = 0; i < worldSize; i++) {
            Tile tile = new Tile(1.0f * i, 0.0f);
            tileMap.add(tile);
            biome.addTile(tile);
        }

        testWorld.setTileMap(tileMap);

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        // required for proper EntitySpawnTable.placeEntity
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(testWorld);
    }

    // tests the place method
    @Test
    public void testPlaceEntity() {
        Tile tile = new Tile(0.0f, 0.0f);
        Rock rock = new Rock();

        // check tile has no rock
        assertTrue(!tile.hasParent());

        // place
        EntitySpawnTable.placeEntity(rock, tile);

        // has the rock
        assertTrue(tile.hasParent());

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
    public void testDirectPlace() {
        // check if construction was valid
        assertEquals(worldSize, testWorld.getTileMap().size());

        // count before spawning
        assertEquals(0, countWorldEntities());

        // check basic spawnEntities
        final double chance = 0.9;
        Rock rock = new Rock();
        EntitySpawnTable.spawnEntities(rock, chance, testWorld);

        // count after spawning
        assertTrue(countWorldEntities() > 0);
    }
}
