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

    @Test
    public void getName() {
        assertEquals(cherry.getName(), "cherry");
    }

    @Test
    public void getTexture() {
        assertEquals(cherry.getTexture(), "cherry");
    }

    @Test
    public void getHealthValue() {
        assertEquals(cherry.getHealthValue(), 10);
    }

    @Test
    public void setHealthValue() {
        cherry.setHealthValue(5);
        assertEquals(cherry.getHealthValue(), 5);
    }

    @Test
    public void isCarryable() {
        assertTrue(cherry.isCarryable());
    }

    @Test
    public void isEquippable() {
        assertTrue(cherry.isEquippable());
    }

    @Test
    public void isObstructed() {
        assertFalse(cherry.isObstructed());
    }
}