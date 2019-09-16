package deco2800.skyfall.worlds.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import deco2800.skyfall.entities.worlditems.Rock;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class WorldParametersTest {
    WorldParameters worldParameters;
    @Before
    public void setup(){
         worldParameters = new WorldParameters();
         worldParameters.setBiomes(new ArrayList<>());
         worldParameters.setEntities(new ArrayList<>());
         worldParameters.setBiomeSizes(new ArrayList<>());
    }

    @Test
    public void testSeed(){
        worldParameters.setSeed(0);
        assertEquals(0, worldParameters.getSeed());
    }

    @Test
    public void testWoldSize(){
        worldParameters.setWorldSize(10);
        assertEquals(10, worldParameters.getWorldSize());
    }

    @Test
    public void testNodeSpacing(){
        worldParameters.setNodeSpacing(10);
        assertEquals(10, worldParameters.getNodeSpacing());
    }

    @Test
    public void testBiomeSizesArray(){
        worldParameters.addBiomeSize(5);
        worldParameters.addBiomeSize(7);
        assertEquals((Integer) 5,worldParameters.getBiomeSizes().get(0));
        assertEquals((Integer) 7,worldParameters.getBiomeSizes().get(1));
        assertEquals(5, worldParameters.getBiomeSizesArray()[0]);
        assertEquals(7, worldParameters.getBiomeSizesArray()[1]);
    }

    @Test
    public void testNumOfLakes(){
        worldParameters.setNumOfLakes(3);
        assertEquals(3, worldParameters.getNumOfLakes());
    }

    @Test
    public void testBiomes(){
        worldParameters.addBiome(new ForestBiome(new Random()));
        assertTrue(worldParameters.getBiomes().get(0) instanceof ForestBiome);
    }

    @Test
    public void testNoOfRivers(){
        worldParameters.setNoRivers(5);
        assertEquals(5, worldParameters.getNoRivers());
    }

    @Test
    public void testNoRiverWidth(){
        worldParameters.setRiverWidth(8);
        assertEquals(8.0, worldParameters.getRiverWidth(),0.00001);
    }

    @Test
    public void testEntities(){
        worldParameters.addEntity(new Rock());
        assertTrue(worldParameters.getEntities().get(0) instanceof Rock);
    }
}
