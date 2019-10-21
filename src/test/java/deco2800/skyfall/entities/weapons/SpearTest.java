package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpearTest {

    private Spear spear;

    @Before
    public void setUp() {
        spear = new Spear(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() {
        spear = null;
    }

    @Test
    public void getDurabilityTest() {
        Assert.assertEquals(7, spear.getDurability());
    }

    @Test
    public void changeDurabilityTest() {
        Assert.assertTrue(spear.isUsable());
        for (int i = 0; i < 7; i++) {
            spear.decreaseDurability();
        }
        Assert.assertFalse(spear.isUsable());
    }

    @Test
    public void getAttackRateTest() {
        Assert.assertEquals(4, spear.getAttackRate());
    }

    @Test
    public void getDamageTest() {
        Assert.assertEquals(5, spear.getDamage());
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("spear", spear.getName());
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("range:spear", spear.toString());
    }

    @Test
    public void getRequiredMetalTest() {
        Assert.assertEquals(20, spear.getRequiredMetal());
    }

    @Test
    public void getRequiredWoodTest() {
        Assert.assertEquals(20, spear.getRequiredWood());
    }

    @Test
    public void getRequiredStoneTest() {
        Assert.assertEquals(20, spear.getRequiredStone());
    }
}
