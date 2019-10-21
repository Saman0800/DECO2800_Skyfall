package deco2800.skyfall.buildings;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuildingFactoryTest {

    private GameManager gm;
    private BuildingFactory factory;

    @Before
    public void setup() {
        this.gm = GameManager.get();
        WorldBuilder wb = new WorldBuilder();
        WorldDirector.constructTestWorld(wb, 0);
        this.gm.setWorld(wb.getWorld());
        this.factory = new BuildingFactory();
    }

    @Test
    public void getCountTest() {
        Assert.assertEquals(11, factory.getCount());
    }

    @Test
    public void createCabinTest() {
        BuildingEntity cabin = factory.createCabin(0f, 2f);
        Assert.assertEquals(1f, cabin.getWidth(), 0.0);
        Assert.assertEquals(1f, cabin.getLength(), 0.0);
        Assert.assertEquals(0f, cabin.getCol(), 0.0);
        //Assert.assertEquals(2f, cabin.getRow(), 0.0);
        Assert.assertEquals("Cabin", cabin.getObjectName().substring(0, 5));
    }

    @Test
    public void createStorageUnitTest() {
        //BuildingEntity storage = factory.createStorageUnit(3f, 1f);
        //Assert.assertEquals(2f, storage.getWidth(), 0.0);
        //Assert.assertEquals(2f, storage.getLength(), 0.0);
        //Assert.assertEquals(3f, storage.getCol(), 0.0);
        //Assert.assertEquals(1f, storage.getRow(), 0.0);
        //Assert.assertEquals("StorageUnit", storage.getObjectName().substring(0, 11));
    }

    @Test
    public void createTownCentreBuildingTest() {
        //BuildingEntity town = factory.createTownCentreBuilding(2f, 1f);
        //Assert.assertEquals(3f, town.getWidth(), 0.0);
        //Assert.assertEquals(3f, town.getLength(), 0.0);
        //Assert.assertEquals(2f, town.getCol(), 0.0);
        //Assert.assertEquals(1f, town.getRow(), 0.0);
        //Assert.assertEquals("TownCentre", town.getObjectName().substring(0, 10));
    }

    @Test
    public void createFenceTest() {
        BuildingEntity fence = factory.createFenceBuilding(3f, 2f);
        Assert.assertEquals(1f, fence.getWidth(), 0.0);
        Assert.assertEquals(1f, fence.getLength(), 0.0);
        Assert.assertEquals(3f, fence.getCol(), 0.0);
        Assert.assertEquals(2f, fence.getRow(), 0.0);
        Assert.assertEquals("Fence", fence.getObjectName().substring(0, 5));
    }

    @After
    public void cleanup() {
        this.gm = null;
        this.factory = null;
    }
}
