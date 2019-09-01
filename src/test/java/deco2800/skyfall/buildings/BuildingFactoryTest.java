package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.structures.BuildingType;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.TestWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class BuildingFactoryTest {

    private GameManager gm;
    private BuildingFactory factory;

    @Before
    public void setup() {
        this.gm = GameManager.get();
        this.gm.setWorld(new TestWorld(1));
        this.factory = new BuildingFactory();
    }

    @Test
    public void getCountTest() {
        Assert.assertEquals(BuildingType.values().length, factory.getCount());
    }

    @Test
    public void createHouseTest() {
        BuildingEntity house = factory.createHouse(0f, 2f);
        Assert.assertEquals(2f, house.getWidth(), 0.0);
        Assert.assertEquals(2f, house.getHeight(), 0.0);
        Assert.assertEquals(0f, house.getCol(), 0.0);
        Assert.assertEquals(2f, house.getRow(), 0.0);
        Assert.assertEquals("House", house.getObjectName().substring(0, 5));
    }

    @Test
    public void createStorageUnitTest() {
        BuildingEntity storage = factory.createHouse(3f, 1f);
        Assert.assertEquals(2f, storage.getWidth(), 0.0);
        Assert.assertEquals(2f, storage.getHeight(), 0.0);
        Assert.assertEquals(3f, storage.getCol(), 0.0);
        Assert.assertEquals(1f, storage.getRow(), 0.0);
        Assert.assertEquals("StorageUnit", storage.getObjectName().substring(0, 11));
    }

    @Test
    public void buildingIdTest() {
        BuildingEntity house = factory.createHouse(0f, 2f);
        BuildingEntity storage = factory.createHouse(3f, 1f);
        BuildingEntity house2 = factory.createHouse(4f, 2f);
        Assert.assertEquals(house.getEntityID(), 0);
        Assert.assertEquals(storage.getEntityID(), 1);
        Assert.assertEquals(house2.getEntityID(), 2);
    }

    @After
    public void cleanup() {
        this.gm = null;
        this.factory = null;
    }
}
