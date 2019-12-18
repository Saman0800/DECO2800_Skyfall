package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrumstickTest {

    private Drumstick drumstick;

    @Before
    public void setUp() throws Exception {
        drumstick = new Drumstick(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() throws Exception {
        drumstick = null;
    }
    @Test
    public void getName() {
        assertEquals(drumstick.getName(), "drumstick");
    }

    @Test
    public void getTexture() {
        assertEquals(drumstick.getTexture(), "drumstick");
    }

    @Test
    public void getHealthValue() {
        assertEquals(drumstick.getHealthValue(), 9);
    }

    @Test
    public void setHealthValue() {
        drumstick.setHealthValue(10);
        assertEquals(drumstick.getHealthValue(), 10);
    }

    @Test
    public void setTexture() {
        drumstick.setTexture("cherry");
        assertEquals(drumstick.getTexture(), "cherry");
    }

    @Test
    public void isCarryable() {
        assertTrue(drumstick.isCarryable());
    }

    @Test
    public void isEquippable() {
        assertTrue(drumstick.isEquippable());
    }
    @Test
    public void isObstructed() {
        assertFalse(drumstick.isObstructed());
    }
}