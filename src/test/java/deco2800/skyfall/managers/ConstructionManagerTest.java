package deco2800.skyfall.managers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.entities.structures.WallBuilding;
import deco2800.skyfall.worlds.TestWorld;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.RocketWorld;

import java.util.TreeMap;

//Add all tests related to the construction manager
public class ConstructionManagerTest {
    private GameManager gm;
    private ConstructionManager cmgr;

    @Before
    public void setup() {
        this.gm = GameManager.get();
        this.cmgr = new ConstructionManager();
        gm.setWorld(new TestWorld(1));
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
        AbstractWorld world = null;
        Tile tile = null;
        AbstractBuilding building = null;
        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
        Assert.assertFalse(this.cmgr.verifyBiome(tile));
        Assert.assertFalse(this.cmgr.verifyEntity(world, tile));
        Assert.assertFalse(this.cmgr.isTilesBuildable(world, building));
    }

    @Test
    public void emptyTerrainTest() {
        String terrain = "";
        boolean bool = false;
        Tile tile = new Tile(1,1);

        tile.setTexture(terrain);
        this.cmgr.updateTerrainMap(terrain, bool);

        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
    }

    @Test
    public void existTerrainTest() {
        String terrain = "River";
        boolean bool = false;
        Tile tile = new Tile(1,1);

        tile.setTexture(terrain);
        this.cmgr.updateTerrainMap(terrain, bool);

        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
    }

    @Test
    public void verifyBoimeTest() {
        Tile tile = new Tile(1,1);
        tile.setIsBuildable(false);
        Assert.assertFalse(this.cmgr.verifyBiome(tile));
        tile.setIsBuildable(true);
        Assert.assertTrue(this.cmgr.verifyBiome(tile));
    }

    @Test
    public void verifyEntityTest() {
        AbstractWorld world = new RocketWorld(1, 30,5, new int[] {20,10,10}, 0, 0 );
        Tile tile = world.getTile(10,10);
        if (world.getEntities().size() == 0) {
            Assert.assertTrue(this.cmgr.verifyEntity(world, tile));
        }
    }

    @After
    public void cleanup() {
        this.cmgr = null;
    }
}
