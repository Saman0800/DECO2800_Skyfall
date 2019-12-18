package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BiscuitTest {

    private Biscuit biscuit;
    @Before
    public void setUp() throws Exception {
        biscuit = new Biscuit(new Tile(null, 0, 0),false);
    }

    @After
    public void tearDown() throws Exception {
        biscuit = null;
    }

    /**
     * test get name return biscuit
     */
    @Test
    public void getName() {
        assertEquals(biscuit.getName(), "biscuit");
    }

    /**
     * test get texture return biscuit
     */
    @Test
    public void getTexture() {
        assertEquals(biscuit.getTexture(), "biscuit");
    }

    /**
     * test health value equal to 6
     */
    @Test
    public void getHealthValue() {
        assertEquals(biscuit.getHealthValue(), 6);
    }

    /**
     * test set health value
     */
    @Test
    public void setHealthValue() {
        biscuit.setHealthValue(3);
        assertEquals(biscuit.getHealthValue(), 3);
    }

    /**
     * test if this item can be carried
     */
    @Test
    public void isCarryable() {
        assertTrue(biscuit.isCarryable());
    }

    /**
     * test if this item can be equipped
     */
    @Test
    public void isEquippable() {
        assertTrue(biscuit.isEquippable());
    }

    /**
     * test that if this item is obstructed
     */
    @Test
    public void isObstructed() {
        assertFalse(biscuit.isObstructed());
    }
}