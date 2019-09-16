package deco2800.skyfall.buildings;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;

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

    @Before
    public void setUp() throws Exception {
        buildingEntity = new BuildingEntity(0, 0, 20, BuildingType.WATCHTOWER);
    }

    @After
    public void tearDown() throws Exception {
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