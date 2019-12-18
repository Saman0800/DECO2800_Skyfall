package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheeryTest {

    private Cheery cherry;
    private MainCharacter mc;
    @Before
    public void setUp() throws Exception {
        cherry = new Cheery(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() throws Exception {
        cherry = null;
    }
    /**
     * test get name return cherry
     */
    @Test
    public void getName() {
        assertEquals(cherry.getName(), "cherry");
    }
    /**
     * test get texture return cherry
     */
    @Test
    public void getTexture() {
        assertEquals(cherry.getTexture(), "cherry");
    }
    /**
     * test health value equal to 10
     */
    @Test
    public void getHealthValue() {
        assertEquals(cherry.getHealthValue(), 10);
    }
    /**
     * test set health value
     */
    @Test
    public void setHealthValue() {
        cherry.setHealthValue(5);
        assertEquals(cherry.getHealthValue(), 5);
    }
    /**
     * test if this item can be carried
     */
    @Test
    public void isCarryable() {
        assertTrue(cherry.isCarryable());
    }
    /**
     * test if this item can be equipped
     */
    @Test
    public void isEquippable() {
        assertTrue(cherry.isEquippable());
    }
    /**
     * test that if this item is obstructed
     */
    @Test
    public void isObstructed() {
        assertFalse(cherry.isObstructed());
    }
}