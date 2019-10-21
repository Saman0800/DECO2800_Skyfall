package deco2800.skyfall.buildings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.world.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
}