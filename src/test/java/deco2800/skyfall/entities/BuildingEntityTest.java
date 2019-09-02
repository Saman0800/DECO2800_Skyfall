package deco2800.skyfall.entities;

import deco2800.skyfall.buildings.BuildingEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void HealthTest() {
        //setting the initial health and getting it
        testbuilding.setInitialHealth(10);
        assertEquals(testbuilding.getInitialHealth(), 10);

        // detracting from the health
        testbuilding.updateHealth(-4);
        assertEquals(testbuilding.getCurrentHealth(), 6);

        // adding to the health
        testbuilding.updateHealth(2);
        assertEquals(testbuilding.getCurrentHealth(), 8);

        // doing nothing to the health
        testbuilding.updateHealth(0);
        assertEquals(testbuilding.getCurrentHealth(), 8);
    }

    @Test
    public void lengthTest() {
        //testing with random value
        testbuilding.setLength(2);
        assertEquals(testbuilding.getLength(), 2);
        // testing with a zero value
        testbuilding.setLength(0);
        assertEquals(testbuilding.getLength(), 2);
    }

    @Test
    public void widthTest() {
        //testing with random value
        testbuilding.setWidth(4);
        assertEquals(testbuilding.getWidth(), 4);
        // testing with a zero value
        testbuilding.setWidth(0);
        assertEquals(testbuilding.getWidth(), 4);
    }

    @Test
    public void upgradableTest() {
        // testing the constructor
        assertEquals(testbuilding.isUpgradable(), false);
        // changing to be upgradable
        testbuilding.setUpgradable(true);
        assertEquals(testbuilding.isUpgradable(), true);

    }


    @After
    public void tearDown() throws Exception {
        testbuilding = null;
    }







}
