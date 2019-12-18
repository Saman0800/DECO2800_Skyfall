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
    /**
     * test get name return cheese
     */
    @Test
    public void getName() {
        assertEquals(cheese.getName(), "cheese");
    }
    /**
     * test get texture return cheese
     */
    @Test
    public void getTexture() {
        assertEquals(cheese.getTexture(), "cheese");
    }
    /**
     * test health value equal to 7
     */
    @Test
    public void getHealthValue() {
        assertEquals(cheese.getHealthValue(), 7);
    }
    /**
     * test set health value
     */
    @Test
    public void setHealthValue() {
        cheese.setHealthValue(3);
        assertEquals(cheese.getHealthValue(), 3);
    }
    /**
     * test if this item can be carried
     */
    @Test
    public void isCarryable() {
        assertTrue(cheese.isCarryable());
    }
    /**
     * test if this item can be equipped
     */
    @Test
    public void isEquippable() {
        assertTrue(cheese.isEquippable());
    }
    /**
     * test that if this item is obstructed
     */
    @Test
    public void isObstructed() {
        assertFalse(cheese.isObstructed());
    }
}