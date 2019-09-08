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

public class TownCentreBuildingTest {

    TownCentreBuilding townTest;

    private GameManager mockGM;
    private PhysicsManager physics;
    private ConstructionManager con;

    @Before
    public void setUp() throws Exception {
        townTest = new TownCentreBuilding(0f, 0f);

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        physics = new PhysicsManager();
        con = new ConstructionManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);
        when(mockGM.getManager(ConstructionManager.class)).thenReturn(con);
    }

    @After
    public void tearDown() throws Exception {
        townTest = null;
    }

    @Test
    public void getMaxHealth() {
        assertEquals(townTest.getMaxHealth(), 80);
    }

    @Test
    public void getCurrentHealth() {
        assertEquals(townTest.getCurrentHealth(), 80);
    }

    @Test
    public void setHealth() {
        townTest.setHealth(75);
        assertEquals(townTest.getMaxHealth(), 75);
    }

    @Test
    public void setCurrentHealth() {
        townTest.setCurrentHealth(75);
        assertEquals(townTest.getCurrentHealth(), 75);
    }

    @Test
    public void takeDamage() {
        townTest.takeDamage(15);
        assertEquals(townTest.getCurrentHealth(), 65);
        townTest.takeDamage(100);
        assertEquals(townTest.getCurrentHealth(), 0);
    }
}