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
    /**
     * test get name return drumstick
     */
    @Test
    public void getName() {
        assertEquals(drumstick.getName(), "drumstick");
    }
    /**
     * test get texture return drumstick
     */
    @Test
    public void getTexture() {
        assertEquals(drumstick.getTexture(), "drumstick");
    }
    /**
     * test health value equal to 9
     */
    @Test
    public void getHealthValue() {
        assertEquals(drumstick.getHealthValue(), 9);
    }
    /**
     * test set health value
     */
    @Test
    public void setHealthValue() {
        drumstick.setHealthValue(10);
        assertEquals(drumstick.getHealthValue(), 10);
    }

    /**
     * test that this item can be set with different texture
     */
    @Test
    public void setTexture() {
        drumstick.setTexture("cherry");
        assertEquals(drumstick.getTexture(), "cherry");
    }
    /**
     * test if this item can be carried
     */
    @Test
    public void isCarryable() {
        assertTrue(drumstick.isCarryable());
    }
    /**
     * test if this item can be equipped
     */
    @Test
    public void isEquippable() {
        assertTrue(drumstick.isEquippable());
    }
    /**
     * test that if this item is obstructed
     */
    @Test
    public void isObstructed() {
        assertFalse(drumstick.isObstructed());
    }
}