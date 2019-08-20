package deco2800.skyfall.entities.structures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TownCentreBuildingTest {

    TownCentreBuilding townTest;

    @Before
    public void setUp() throws Exception {
        townTest = new TownCentreBuilding(0f, 0f);
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