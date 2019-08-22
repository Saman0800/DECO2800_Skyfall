package deco2800.skyfall.entities.structures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WallBuildingTest {

    WallBuilding testWall;

    @Before
    public void setUp() throws Exception {
        testWall = new WallBuilding(1f, 1f);
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