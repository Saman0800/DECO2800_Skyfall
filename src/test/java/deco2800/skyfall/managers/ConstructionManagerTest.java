package deco2800.skyfall.managers;

import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.entities.structures.WallBuilding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import deco2800.skyfall.managers.ConstructionManager;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.AbstractEntity;

import java.util.TreeMap;

//Add all tests related to the construction manager
public class ConstructionManagerTest {
    private ConstructionManager cmgr;

    @Before
    public void setup() {
        this.cmgr = new ConstructionManager();
    }

    @Test
    public void testInvCheckPositive(){
        AbstractBuilding building = new WallBuilding(1,1);
        TreeMap<String, Integer> buildingCost = new TreeMap<>();
        buildingCost.put("Stone", 2);
        buildingCost.put("Wood", 2);
        building.setCost(buildingCost);

        InventoryManager inventoryManager = new InventoryManager();
        Boolean result = cmgr.invCheck(building, inventoryManager);

        Assert.assertTrue(result);
    }

    @Test
    public void testInvCheckNegative(){
        AbstractBuilding building = new WallBuilding(1,1);
        TreeMap<String, Integer> buildingCost = new TreeMap<>();
        buildingCost.put("Stone", 3);
        buildingCost.put("Wood", 2);
        building.setCost(buildingCost);

        InventoryManager inventoryManager = new InventoryManager();
        Boolean result = cmgr.invCheck(building, inventoryManager);

        Assert.assertFalse(result);
    }

    @Test
    public void testInvRemove(){
        AbstractBuilding building = new WallBuilding(1,1);
        TreeMap<String, Integer> buildingCost = new TreeMap<>();
        buildingCost.put("Stone", 2);
        buildingCost.put("Wood", 2);
        building.setCost(buildingCost);

        InventoryManager inventoryManager = new InventoryManager();
        cmgr.invRemove(building, inventoryManager);

        Assert.assertEquals(0, inventoryManager.getAmount("Stone"));
        Assert.assertEquals(0, inventoryManager.getAmount("Wood"));
    }

    @Test
    public void verifyNullTest() {
        AbstractWorld world = null;
        Tile tile = null;
        AbstractBuilding building = null;
        Assert.assertEquals(false, this.cmgr.verifyTerrain(tile));
        Assert.assertEquals(false, this.cmgr.verifyBiome(tile));
        Assert.assertEquals(false, this.cmgr.verifyEntity(world, tile));
        Assert.assertEquals(false, this.cmgr.isTilesBuildable(world, building));
    }

    @After
    public void cleanup() {
        this.cmgr = null;
    }
}
