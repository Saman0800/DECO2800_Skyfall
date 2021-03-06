package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BowTest {

    private Bow bow;

    @Before
    public void setUp() {
        bow = new Bow(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() {
        bow = null;
    }

    @Test
    public void getDurabilityTest() {
        Assert.assertEquals(10, bow.getDurability());
    }

    @Test
    public void changeDurabilityTest() {
        Assert.assertTrue(bow.isUsable());
        for (int i = 0; i < 10; i++) {
            bow.decreaseDurability();
        }
        Assert.assertFalse(bow.isUsable());
    }

    @Test
    public void getAttackRateTest() {
        Assert.assertEquals(3, bow.getAttackRate());
    }

    @Test
    public void getDamageTest() {
        Assert.assertEquals(4, bow.getDamage());
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("bow", bow.getName());
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("range:bow", bow.toString());
    }

    @Test
    public void getRequiredMetalTest() {
        Assert.assertEquals(15, bow.getRequiredMetal());
    }

    @Test
    public void getRequiredWoodTest() {
        Assert.assertEquals(40, bow.getRequiredWood());
    }

    @Test
    public void getRequiredStoneTest() {
        Assert.assertEquals(20, bow.getRequiredStone());
    }

}
