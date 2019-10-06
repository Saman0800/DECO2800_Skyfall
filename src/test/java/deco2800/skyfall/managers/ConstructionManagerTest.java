package deco2800.skyfall.managers;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingFactory;

import deco2800.skyfall.managers.database.DataBaseConnector;

import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.Wood;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Random;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

//Add all tests related to the construction manager
@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class })
public class ConstructionManagerTest {
    private GameManager gm;
    private ConstructionManager cmgr;
    private WorldBuilder wb;

    @Before
    public void setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then(
                (Answer<Chunk>) invocation -> new Chunk(invocation.getArgumentAt(0, World.class),
                                                        invocation.getArgumentAt(1, Integer.class),
                                                        invocation.getArgumentAt(2, Integer.class)));

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        this.gm = GameManager.get();
        this.cmgr = new ConstructionManager();
        wb = new WorldBuilder();
        WorldDirector.constructTestWorld(wb, 0);
        gm.setWorld(wb.getWorld());
    }

    @Test
    public void testPlacement() {

        BuildingFactory factory = new BuildingFactory();
        float x = 1f;
        float y = 1f;
        //MainCharacter character = new MainCharacter( 1f, 1f, 1f, "character", 1);

        //character.notifyTouchDown( 1, 1, 1,);


    }


    @Test
    public void testInvCheckPositive() {
        InventoryManager inventoryManager = new InventoryManager();
        BuildingFactory factory = new BuildingFactory();

        Stone stone1 = new Stone();
        inventoryManager.add(stone1);
        Stone stone2 = new Stone();
        inventoryManager.add(stone2);
        Wood wood1 = new Wood();
        inventoryManager.add(wood1);
        Wood wood2 = new Wood();
        inventoryManager.add(wood2);
        Wood wood3 = new Wood();
        inventoryManager.add(wood3);
        Wood wood4 = new Wood();
        inventoryManager.add(wood4);
        Wood wood5 = new Wood();
        inventoryManager.add(wood5);

        cmgr.setBuilding(0);

        BuildingEntity cabin = factory.createCabin(1,1);
        boolean result = cmgr.invCheck(inventoryManager);

        Assert.assertTrue(result);
    }

    @Test
    public void testInvCheckNegative() {
        BuildingFactory factory = new BuildingFactory();
        BuildingEntity cabin = factory.createCabin(1,1);

        HashMap<String, Integer> buildingCost = new HashMap<>();
        buildingCost.put("Wood", 7);
        buildingCost.put("Stone",4);

        InventoryManager inventoryManager = new InventoryManager();
        cmgr.setBuilding(4);

        //boolean result = cmgr.invCheck(inventoryManager);

        //Assert.assertFalse(result);
    }


    @Test
    public void testInvRemove() {

        InventoryManager inventoryManager = GameManager.getManagerFromInstance(InventoryManager.class);

        Stone stone1 = new Stone();
        inventoryManager.add(stone1);
        Stone stone2 = new Stone();
        inventoryManager.add(stone2);
        Wood wood1 = new Wood();
        inventoryManager.add(wood1);
        Wood wood2 = new Wood();
        inventoryManager.add(wood2);
        Wood wood3 = new Wood();
        inventoryManager.add(wood3);
        Wood wood4 = new Wood();
        inventoryManager.add(wood4);
        Wood wood5 = new Wood();
        inventoryManager.add(wood5);



        Assert.assertEquals(10.0, inventoryManager.getAmount("Wood"), 0.0);
        Assert.assertEquals(4.0, inventoryManager.getAmount("Stone"), 0.0);
        cmgr.setBuilding(0);



        BuildingFactory factory = new BuildingFactory();
        BuildingEntity cabin = factory.createCabin(1,1);
        cmgr.setBuildingToBePlaced(cabin);

        cmgr.build(gm.getWorld(),2,2);


        Assert.assertEquals(5.0, inventoryManager.getAmount("Wood"), 0.0);
        Assert.assertEquals(2.0, inventoryManager.getAmount("Stone"), 0.0);

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
        boolean result = cmgr.mergeBuilding(buildings, inventoryManager);

        Assert.assertEquals(true, result);
    }
     */

    @Test
    public void testMergeBuildingNegative() {
        BuildingEntity[] buildings = {
            //    new WallBuilding(1, 1),
            //    new TownCentreBuilding(3, 5)
        };
        InventoryManager inventoryManager = new InventoryManager();
        boolean result = cmgr.mergeBuilding(buildings, inventoryManager);

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
        Tile tile = new Tile(null, 1, 1);

        tile.setTexture(terrain);
        this.cmgr.updateTerrainMap(terrain, bool);

        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
    }

    @Test
    public void existTerrainTest() {
        String terrain = "River";
        boolean bool = false;
        Tile tile = new Tile(null, 1, 1);

        tile.setTexture(terrain);
        this.cmgr.updateTerrainMap(terrain, bool);

        Assert.assertFalse(this.cmgr.verifyTerrain(tile));
    }

    @Test
    public void verifyBoimeTest() {
        Tile tile = new Tile(null, 1, 1);
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
