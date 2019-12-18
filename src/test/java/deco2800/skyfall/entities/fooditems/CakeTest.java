package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CakeTest {

    private Cake cake;
    @Before
    public void setUp() throws Exception {
        cake = new Cake(new Tile(null, 0, 0),false);
    }

    @After
    public void tearDown() throws Exception {
        cake = null;
    }
    /**
     * test get name return cake
     */
    @Test
    public void getName() {
        assertEquals(cake.getName(), "cake");
    }
    /**
     * test get texture return cake
     */
    @Test
    public void getTexture() {
        assertEquals(cake.getTexture(), "cake");
    }
    /**
     * test health value equal to 11
     */
    @Test
    public void getHealthValue() {
        assertEquals(cake.getHealthValue(), 11);
    }
    /**
     * test set health value
     */
    @Test
    public void setHealthValue() {
         cake.setHealthValue(20);
         assertEquals(cake.getHealthValue(), 20);
    }
    /**
     * test if this item can be carried
     */
    @Test
    public void isCarryable() {
         assertTrue(cake.isCarryable());
    }
    /**
     * test if this item can be equipped
     */
    @Test
    public void isEquippable() {
        assertTrue(cake.isEquippable());
    }
    /**
     * test that if this item is obstructed
     */
    @Test
    public void isObstructed() {
        assertFalse(cake.isObstructed());
    }
}