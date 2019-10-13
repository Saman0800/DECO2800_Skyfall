package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.enemies.Scout;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class })
public class WorldBuilderTest {

    private WorldBuilder builder;

    @Before
    public void setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then(
                (Answer<Chunk>) invocation -> {
                    Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                                            invocation.getArgumentAt(1, Integer.class),
                                            invocation.getArgumentAt(2, Integer.class));
                    chunk.generateEntities();
                    return chunk;
                });

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        builder = new WorldBuilder();
        builder.setNodeSpacing(2);
        builder.setWorldSize(20);
        builder.setSeed(0);
    }

    @Test
    public void TestSetting() {
        builder.addBiome(new ForestBiome(new Random(0)), 20);
        builder.addLake(1);
        builder.setRiverSize(2);
        builder.addRiver();
        builder.addEntity(new Scout(-4f, -1f, 0.1f, "Forest"));
        builder.setStaticEntities(true);
        builder.setBeachSize(1);


        //Testing the builder methods
        World world = builder.getWorld();
        // Load some chunks.
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                world.getChunk(x, y);
            }
        }

        assertEquals(1, world.worldParameters.getNumOfLakes());
        assertEquals(1, world.worldParameters.getLakeSizesArray().length);

        assertEquals(2, world.worldParameters.getNodeSpacing());
        assertEquals(20, world.worldParameters.getWorldSize());

        assertEquals(1, world.worldParameters.getBeachWidth(), 1e-10);

        assertTrue("There should be at least 7 biomes", world.getBiomes().size() >= 7);
        assertTrue(world.getBiomes().get(0) instanceof ForestBiome);

        assertEquals(2, world.worldParameters.getRiverWidth(), 1e-10);


        assertEquals(0, world.getSeed());

        int enemyEntities = (int) world.getEntities().stream().filter(Enemy.class::isInstance).count();
        int staticEntities = (int) world.getEntities().stream().filter(StaticEntity.class::isInstance).count();

        assertTrue(staticEntities > 0);
        assertEquals(1, enemyEntities);
    }


    @Test
    public void TestTutorialWorld() {
        builder.setType("tutorial");
        World world = builder.getWorld();
        assertTrue(world instanceof TutorialWorld);
    }


}
