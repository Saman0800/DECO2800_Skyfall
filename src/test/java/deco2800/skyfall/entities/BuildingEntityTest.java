package deco2800.skyfall.entities;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.entities.structures.Building;
import deco2800.skyfall.entities.structures.TownCentreBuilding;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;


public class BuildingEntityTest {
    BuildingEntity testbuilding;

    @Before
    public void setUp() throws Exception {
        testbuilding = new BuildingEntity(1,1,1);
    }


    @Test
    public void buildTime() {
        testbuilding.setBuildTime(1);
        assertEquals(testbuilding.getBuildTime(), 1);
    }

    @Test
    public void resourceTest() {
        // testing with no resources
        testbuilding.addBuildCost("", 0);
        Map<String, Integer> expectedCost1 = new TreeMap();
        assertEquals(testbuilding.getCost(), expectedCost1);

        // testing with one resource added
        testbuilding.addBuildCost("tree", 1);
        Map<String, Integer> expectedCost2 = new TreeMap();
        expectedCost2.put("tree", 1);
        assertEquals(testbuilding.getCost(), expectedCost2);
    }

    @Test
    public void texturesTest() {
        // testing with one resource added
        testbuilding.addTexture(null, "");
        Map<String, String> expectedTextures1 = new TreeMap();
        assertEquals(testbuilding.getTextures(), expectedTextures1);

        // testing with one resource added
        testbuilding.addTexture("House", "house1");
        Map<String, String> expectedTextures2 = new TreeMap();
        expectedTextures2.put("House", "house1");
        assertEquals(testbuilding.getTextures(), expectedTextures2);
    }


    @After
    public void tearDown() throws Exception {
        testbuilding = null;
    }







}
