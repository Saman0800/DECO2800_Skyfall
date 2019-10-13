package deco2800.skyfall.buildings;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import deco2800.skyfall.util.WorldUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.util.Collider;

/**
 *
 */
public class BuildingEntityTest {

    private World world;
    private float tileSize;

    @Before
    public void setup() {
        world = mock(World.class);
        GameManager.get().setWorld(world);
        tileSize = 100f;
    }

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
    public void setBody() {
    }

    @Test
    public void setFixture() {
    }

    @Test
    public void setColRenderLength() {
    }

    @Test
    public void setRowRenderLength() {
    }

    @Test
    public void setCollidable() {
    }

    @Test
    public void getCollidable() {
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
    public void setPosition() {
    }

    @Test
    public void setHeight() {
    }

    @Test
    public void setPosition1() {
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
    public void getModulatingColor() {
    }

    @Test
    public void setModulatingColor() {
    }

    @Test
    public void setModulatingColor1() {
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
    public void between() {
    }

    @Test
    public void takeDamage() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        building.takeDamage(3);
        assertEquals(building.getHealth(), -3);
    }

    @Test
    public void dealDamage() {
    }

    @Test
    public void canDealDamage() {
    }

    @Test
    public void getDamage() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        int result = building.getDamage();
        assertEquals(result, 0);

        building = new BuildingEntity(0, 0, 2, BuildingType.CASTLE);
        result = building.getDamage();
        assertEquals(result, 1);

        building = new BuildingEntity(0, 0, 2, BuildingType.TOWNCENTRE);
        result = building.getDamage();
        assertEquals(result, 2);

        building = new BuildingEntity(0, 0, 2, BuildingType.FENCE);
        result = building.getDamage();
        assertEquals(result, 0);
        building = new BuildingEntity(0, 0, 2, BuildingType.SAFEHOUSE);
        result = building.getDamage();
        assertEquals(result, 1);
        building = new BuildingEntity(0, 0, 2, BuildingType.WATCHTOWER);
        result = building.getDamage();
        assertEquals(result, 2);
        building = new BuildingEntity(0, 0, 2, BuildingType.STORAGE_UNIT);
        result = building.getDamage();
        assertEquals(result, 0);


    }

    @Test
    public void getResistanceAttributes() {
    }

    @Test
    public void getHealth() {
    }

    @Test
    public void setHealth() {
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
    public void setBuildingLevel() {
    }

    @Test
    public void getBuildingLevel() {
    }

    @Test
    public void updateHealth() {
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
    public void cabinInteract() {
    }

    @Test
    public void fenceInteract() {
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
    public void getInventoryManager() {
    }

    @Test
    public void setInventoryManager() {
    }

    @Test
    public void addInventory() {
    }

    @Test
    public void quickAccessRemove() {
    }

    @Test
    public void fireProjectile() {
    }
}
