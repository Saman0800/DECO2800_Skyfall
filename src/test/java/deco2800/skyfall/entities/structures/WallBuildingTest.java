package deco2800.skyfall.entities.structures;

import deco2800.skyfall.managers.ConstructionManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PhysicsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

public class WallBuildingTest {

    WallBuilding testWall;

    private GameManager mockGM;
    private PhysicsManager physics;
    private ConstructionManager con;

    @Before
    public void setUp() throws Exception {
        testWall = new WallBuilding(1f, 1f);

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        physics = new PhysicsManager();
        con = new ConstructionManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);
        when(mockGM.getManager(ConstructionManager.class)).thenReturn(con);
    }

    @After
    public void tearDown() throws Exception {
        testWall = null;
    }

    @Test
    public void getMaxHealth() {
        assertEquals(testWall.getMaxHealth(), 5);
    }

    @Test
    public void getCurrentHealth() {
        assertEquals(testWall.getCurrentHealth(), 5);
    }

    @Test
    public void setHealth() {
        testWall.setHealth(10);
        assertEquals(testWall.getMaxHealth(), 10);
    }

    @Test
    public void setCurrentHealth() {
        testWall.setCurrentHealth(4);
        assertEquals(testWall.getCurrentHealth(), 4);
    }

    @Test
    public void takeDamage() {
        testWall.takeDamage(3);
        assertEquals(testWall.getCurrentHealth(), 2);
        testWall.takeDamage(4);
        assertEquals(testWall.getCurrentHealth(), 0);
    }

    @Test
    public void onTick() {
    }
}