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
    public void takeDamage() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        building.takeDamage(3);
        assertEquals(building.getHealth(), -3);
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
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        assertEquals(building.getHealth(), 0);
    }

    @Test
    public void setHealth() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        building.setHealth(5);
        assertEquals(building.getHealth(), 5);
    }

    @Test
    public void setCollider() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        building.setCollider();
        assertEquals(building.getCollider().getX(), 0.0, 0);
    }

    @Test
    public void getCollider() {
        BuildingEntity building = new BuildingEntity(0, 0, 2, BuildingType.CABIN);
        assertEquals(building.getCollider().getX(), 0.0, 0);
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