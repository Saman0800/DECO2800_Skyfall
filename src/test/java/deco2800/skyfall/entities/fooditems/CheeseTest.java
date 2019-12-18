package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheeseTest {

    private Cheese cheese;

    @Before
    public void setUp() throws Exception {
        cheese = new Cheese(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() throws Exception {
        cheese = null;
    }

    @Test
    public void getName() {
        assertEquals(cheese.getName(), "cheese");
    }

    @Test
    public void getTexture() {
        assertEquals(cheese.getTexture(), "cheese");
    }

    @Test
    public void getHealthValue() {
        assertEquals(cheese.getHealthValue(), 7);
    }

    @Test
    public void setHealthValue() {
        cheese.setHealthValue(3);
        assertEquals(cheese.getHealthValue(), 3);
    }

    @Test
    public void isCarryable() {
        assertTrue(cheese.isCarryable());
    }

    @Test
    public void isEquippable() {
        assertTrue(cheese.isEquippable());
    }

    @Test
    public void isObstructed() {
        assertFalse(cheese.isObstructed());
    }
}