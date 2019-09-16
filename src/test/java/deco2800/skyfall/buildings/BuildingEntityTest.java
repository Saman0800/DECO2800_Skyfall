package deco2800.skyfall.buildings;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import deco2800.skyfall.util.WorldUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.util.Collider;
import org.lwjgl.Sys;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.After;

import static org.junit.Assert.*;

public class BuildingEntityTest {

    private BuildingEntity buildingEntity;
    private World world;
    private float tileSize;
	
    @Before
    public void setUp() throws Exception {
		world = mock(World.class);
        GameManager.get().setWorld(world);
        tileSize = 100f;
        buildingEntity = new BuildingEntity(0, 0, 20, BuildingType.WATCHTOWER);
    }

    //@After
    //public void tearDown() throws Exception {
    //}


    @Test
    public void testNotNullCollider() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        Collider collider = building.getCollider();
        assertNotNull(collider);

        building = new BuildingEntity(-1, 3, 2, BuildingType.FENCE);
        collider = building.getCollider();
        assertNotNull(collider);
    }

    @Test
    public void testColliderPosition() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        Collider collider = building.getCollider();
        float[] cords = WorldUtil.colRowToWorldCords(building.getCol(), building.getRow());
        assertEquals(cords[0], collider.getX(), 0.0);
        assertEquals(cords[1], collider.getY(), 0.0);

        building = new BuildingEntity(-1, 3, 2, BuildingType.FENCE);
        collider = building.getCollider();
        cords = WorldUtil.colRowToWorldCords(building.getCol(), building.getRow());
        assertEquals(cords[0], collider.getX(), 0.0);
        assertEquals(cords[1], collider.getY(), 0.0);
    }

    @Test
    public void testColliderSizeBasedTile() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        Collider collider = building.getCollider();
        try {
            assertEquals(building.getLength() * tileSize, collider.getXLength(), 0.0);
            assertEquals(building.getWidth() * tileSize, collider.getYLength(), 0.0);
        } catch (Exception e) {
            System.err.println("ColiderSizeTest: It is not based on tile.");
        }

        building = new BuildingEntity(-1, 3, 2, BuildingType.FENCE);
        collider = building.getCollider();
        try {
            assertEquals(building.getLength() * tileSize, collider.getXLength(), 0.0);
            assertEquals(building.getWidth() * tileSize, collider.getYLength(), 0.0);
        } catch (Exception e) {
            System.out.println("ColiderSizeTest: It is not based on tile.");
        }
    }

    @After
    public void clean() {
        world = null;
    }
	
    @Test
    public void resetID() {
    }

    @Test
    public void getNextID() {
    }

    @Test
    public void getCol() {
    }

    @Test
    public void getRow() {
    }

    @Test
    public void getHeight() {
    }

    @Test
    public void setCol() {
    }

    @Test
    public void setRow() {
    }

    @Test
    public void setHeight() {
    }

    @Test
    public void setPosition() {
    }

    @Test
    public void getColRenderWidth() {
    }

    @Test
    public void getRowRenderWidth() {
    }

    @Test
    public void setRenderOrder() {
    }

    @Test
    public void getRenderOrder() {
    }

    @Test
    public void compareTo() {
    }

    @Test
    public void getColRenderLength() {
    }

    @Test
    public void getRowRenderLength() {
    }

    @Test
    public void getTexture() {
    }

    @Test
    public void setTexture() {
    }

    @Test
    public void equals1() {
    }

    @Test
    public void hashCode1() {
    }

    @Test
    public void distance() {
    }

    @Test
    public void getPosition() {
    }

    @Test
    public void setObjectName() {
    }

    @Test
    public void getObjectName() {
    }

    @Test
    public void getEntityID() {
    }

    @Test
    public void setEntityID() {
    }

    @Test
    public void dispose() {
    }

    @Test
    public void getToBeRun() {
    }

    @Test
    public void initialiseBox2D() {
    }

    @Test
    public void defineFixture() {
    }

    @Test
    public void defineFixture1() {
    }

    @Test
    public void changeCollideability() {
    }

    @Test
    public void handleCollision() {
    }

    @Test
    public void setGetToBeRunToNull() {
    }

    @Test
    public void getCurrentDirection() {
    }

    @Test
    public void getCurrentState() {
    }

    @Test
    public void setCurrentDirection() {
    }

    @Test
    public void setCurrentState() {
    }

    @Test
    public void addAnimations() {
    }

    @Test
    public void getDefaultTexture() {
    }

    @Test
    public void getScale() {
    }

    @Test
    public void getBody() {
    }

    @Test
    public void setCollider() {
    }

    @Test
    public void getCollider() {
    }

    @Test
    public void onTick() {
    }

    @Test
    public void placeBuilding() {
    }

    @Test
    public void removeBuilding() {
    }

    @Test
    public void getBuildingType() {
    }

    @Test
    public void setBuildTime() {
    }

    @Test
    public void getBuildTime() {
    }

    @Test
    public void addBuildCost() {
    }

    @Test
    public void getCost() {
    }

    @Test
    public void addTexture() {
    }

    @Test
    public void getTextures() {
    }

    @Test
    public void setInitialHealth() {
    }

    @Test
    public void getInitialHealth() {
    }

    @Test
    public void setLength() {
    }

    @Test
    public void getLength() {
    }

    @Test
    public void setWidth() {
    }

    @Test
    public void getWidth() {
    }

    @Test
    public void setUpgradable() {
    }

    @Test
    public void isUpgradable() {
    }

    @Test
    public void getCurrentHealth() {
    }

    @Test
    public void getRequiredWood() {
    }

    @Test
    public void getRequiredStone() {
    }

    @Test
    public void getRequiredMetal() {
    }

    @Test
    public void getAllRequirements() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void isBlueprintLearned() {
    }

    @Test
    public void toggleBlueprintLearned() {
    }

    @Test
    public void cabinInteract() {
        MainCharacter player = GameManager.getManagerFromInstance(GameMenuManager.class).getMainCharacter();
        assertEquals(10, player.getHealth());
        player.changeHealth(-5);
        assertEquals(5, player.getHealth());
        buildingEntity.cabinInteract();
        assertEquals(5, player.getHealth());
    }

    @Test
    public void fenceInteract() {
    }

    @Test
    public void castleInteract() {
    }

    @Test
    public void safehouseInteract() {
    }

    @Test
    public void towncentreInteract() {
    }

    @Test
    public void watchtowerInteract() {
    }

    @Test
    public void setBuildingLevel() {
    }

    @Test
    public void getBuildingLevel() {
    }

    @Test
    public void updateHealth() {
    }

    @Test
    public void getInventoryManager() {
        buildingEntity.setInventoryManager(new InventoryManager());
        String expected = "Inventory Contents {Pick Axe=1, Wood=2, Hatchet=1, Stone=2}";
        Assert.assertEquals(expected, buildingEntity.getInventoryManager().toString());
    }

    @Test
    public void setInventoryManager() {
        buildingEntity.setInventoryManager(new InventoryManager());
        String expected = "Inventory Contents {Pick Axe=1, Wood=2, Hatchet=1, Stone=2}";
        Assert.assertEquals(expected, buildingEntity.getInventoryManager().toString());
    }

    @Test
    public void addInvetory() {
        buildingEntity.setInventoryManager(new InventoryManager());
        Assert.assertEquals(6, buildingEntity.getInventoryManager().getTotalAmount());
        buildingEntity.AddInventory(new Stone());
        Assert.assertEquals(7, buildingEntity.getInventoryManager().getTotalAmount());
    }

    @Test
    public void quickAccessRemove() {

    }
}

