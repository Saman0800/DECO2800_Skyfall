package deco2800.skyfall.managers;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.worlds.Tile;

import java.util.TreeMap;

//Add all tests related to the construction manager
public class ConstructionManagerTest {
    private GameManager gm;
    private ConstructionManager cmgr;
    private WorldBuilder wb;

    @Before
    public void setup() {
        this.gm = GameManager.get();
        this.cmgr = new ConstructionManager();
        wb = new WorldBuilder();
        WorldDirector.constructTestWorld(wb);
        gm.setWorld(wb.getWorld());

    }

    @Test
    public void testInvCheckPositive() {

        BuildingFactory factory = new BuildingFactory();
        BuildingEntity cabin = factory.createCabin(1,1);

        InventoryManager inventoryManager = new InventoryManager();
        Boolean result = cmgr.invCheck(cabin, inventoryManager);

        Assert.assertTrue(result);
    }

    @Test
    public void testInvCheckNegative() {
        BuildingFactory factory = new BuildingFactory();
        BuildingEntity cabin = factory.createCabin(1,1);

        TreeMap<String, Integer> buildingCost = new TreeMap<>();
        buildingCost.put("Stone", 3);
        buildingCost.put("Wood", 2);
        //building.setCost(buildingCost);

        InventoryManager inventoryManager = new InventoryManager();
        //Boolean result = cmgr.invCheck(building, inventoryManager);

        //Assert.assertFalse(result);
    }

    @Test
    public void testInvRemove() {
        //AbstractBuilding building = new WallBuilding(1, 1);
        TreeMap<String, Integer> buildingCost = new TreeMap<>();
        buildingCost.put("Stone", 2);
        buildingCost.put("Wood", 2);
        //building.setCost(buildingCost);

        InventoryManager inventoryManager = new InventoryManager();
        //cmgr.invRemove(building, inventoryManager);

        Assert.assertEquals(0, inventoryManager.getAmount("Stone"));
        Assert.assertEquals(0, inventoryManager.getAmount("Wood"));
    }

    /**
    @Test
    public void testMergeBuildingPositive() {

        BuildingFactory factory = new BuildingFactory();

        arraylist
        AbstractBuilding[] buildings = {
                factory.createCabin(1,1);
        }
        InventoryManager inventoryManager = new InventoryManager();
        Boolean result = cmgr.mergeBuilding(buildings, inventoryManager);

        Assert.assertEquals(true, result);
    }
     */

    @Test
    public void testMergeBuildingNegative() {
        AbstractBuilding[] buildings = {
            //    new WallBuilding(1, 1),
            //    new TownCentreBuilding(3, 5)
        };
        InventoryManager inventoryManager = new InventoryManager();
        Boolean result = cmgr.mergeBuilding(buildings, inventoryManager);

        Assert.assertEquals(false, result);
    }

    @Test
    public void updateTerrainTest() {
        String terrain;
        boolean bool;

        terrain = "River";
        bool = false;
        Assert.assertTrue(this.cmgr.updateTerrainMap(terrain, bool));

        terrain = null;
        bool = true;
        Assert.assertFalse(this.cmgr.updateTerrainMap(terrain, bool));
    }

    @Test
    public void verifyNullTest() {
        World world = null;
        Tile tile = null;
        BuildingEntity building = null;
        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
        Assert.assertFalse(this.cmgr.verifyBiome(tile));
        Assert.assertFalse(this.cmgr.verifyEntity(world, tile));
        Assert.assertFalse(this.cmgr.isTilesBuildable(world, building));
    }

    @Test
    public void emptyTerrainTest() {
        String terrain = "";
        boolean bool = false;
        Tile tile = new Tile(1, 1);

        tile.setTexture(terrain);
        this.cmgr.updateTerrainMap(terrain, bool);

        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
    }

    @Test
    public void existTerrainTest() {
        String terrain = "River";
        boolean bool = false;
        Tile tile = new Tile(1, 1);

        tile.setTexture(terrain);
        this.cmgr.updateTerrainMap(terrain, bool);

        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
    }

    @Test
    public void verifyBoimeTest() {
        Tile tile = new Tile(1, 1);
        tile.setIsBuildable(false);
        Assert.assertFalse(this.cmgr.verifyBiome(tile));
        tile.setIsBuildable(true);
        Assert.assertTrue(this.cmgr.verifyBiome(tile));
    }

//    @Test
//    public void verifyEntityTest() {
//        World world = new WorldBuilder().getWorld();
//        Tile tile = world.getTile(10, 10);
//        if (world.getEntities().size() == 0) {
//            Assert.assertTrue(this.cmgr.verifyEntity(world, tile));
//        }
//    }

    @After
    public void cleanup() {
        this.cmgr = null;
    }
}
