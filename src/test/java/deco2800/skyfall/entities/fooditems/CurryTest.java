package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurryTest {

    private Curry curry;
    @Before
    public void setUp() throws Exception {
        curry = new Curry(new Tile(null, 0, 0),false);
    }

    @After
    public void tearDown() throws Exception {
        curry = null;
    }

    @Test
    public void getName() {
        assertEquals(curry.getName(), "curry");
    }

    @Test
    public void getTexture() {
        assertEquals(curry.getTexture(), "curry");
    }

    @Test
    public void getHealthValue() {
        assertEquals(curry.getHealthValue(), 8);
    }

    @Test
    public void setHealthValue() {
        curry.setHealthValue(2);
        assertEquals(curry.getHealthValue(), 2);
    }

    @Test
    public void isCarryable() {
        assertTrue(curry.isCarryable());
    }

    @Test
    public void isEquippable() {
        assertTrue(curry.isEquippable());
    }

    @Test
    public void isObstructed() {
        assertFalse(curry.isObstructed());
    }
}