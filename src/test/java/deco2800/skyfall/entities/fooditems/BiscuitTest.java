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

    @Test
    public void getName() {
        assertEquals(biscuit.getName(), "biscuit");
    }

    @Test
    public void getTexture() {
        assertEquals(biscuit.getTexture(), "biscuit");
    }

    @Test
    public void getHealthValue() {
        assertEquals(biscuit.getHealthValue(), 6);
    }

    @Test
    public void setHealthValue() {
        biscuit.setHealthValue(3);
        assertEquals(biscuit.getHealthValue(), 3);
    }

    @Test
    public void isCarryable() {
        assertTrue(biscuit.isCarryable());
    }

    @Test
    public void isEquippable() {
        assertTrue(biscuit.isEquippable());
    }

    @Test
    public void isObstructed() {
        assertFalse(biscuit.isObstructed());
    }
}