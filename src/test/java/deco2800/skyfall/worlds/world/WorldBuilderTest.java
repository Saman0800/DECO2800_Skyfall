package deco2800.skyfall.worlds.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.entities.Robot;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class WorldBuilderTest {

    private WorldBuilder builder;

    @Before
    public void setup() {
        builder = new WorldBuilder();
        builder.setNodeSpacing(2);
        builder.setWorldSize(20);
        builder.setSeed(0);
    }


    @Test
    public void TestSetting() {
        builder.addBiome(new ForestBiome(), 20);
        builder.addLake(1);
        builder.setRiverSize(2);
        builder.addRiver();
        builder.addEntity(new Robot(0,0));
        builder.setStaticEntities(true);
        builder.setBeachSize(1);


        //Testing the builder methods
        World world = builder.getWorld();

        assertEquals(1, world.numOfLakes);
        assertEquals(1, world.lakeSizes.length);

        assertEquals(2, world.nodeSpacing);
        assertEquals(20, world.worldSize);

        assertEquals(1, world.beachWidth);

        assertEquals(5, world.getBiomes().size());
        assertTrue(world.getBiomes().get(0) instanceof ForestBiome);

        assertEquals(2, world.riverWidth);


        assertEquals(0, world.getSeed());

        int enemyEntities = (int) world.getEntities().stream().filter(entity -> entity instanceof EnemyEntity).count();
        int staticEntities = (int) world.getEntities().stream().filter(entity -> entity instanceof StaticEntity).count();

        assertTrue(staticEntities > 0);
        assertEquals(1, enemyEntities);


    }

    @Test
    public void TestServerWorld() {
        builder.setType("server");
        World world = builder.getWorld();
        assertTrue(world instanceof ServerWorld);
    }

    @Test
    public void TestTutorialWorld() {
        builder.setType("tutorial");
        World world = builder.getWorld();
        assertTrue(world instanceof TutorialWorld);
    }


    @Test
    public void TestTestWorld(){
        builder.setType("test");
        World world = builder.getWorld();
        assertTrue(world instanceof TestWorld);
    }


}
