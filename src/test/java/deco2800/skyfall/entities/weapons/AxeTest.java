package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AxeTest {

    private Axe axe;

    @Before
    public void setUp() {
        axe = new Axe(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() {
        axe = null;
    }

    @Test
    public void getDurabilityTest() {
        Assert.assertEquals(10, axe.getDurability());
    }

    @Test
    public void changeDurabilityTest() {
        Assert.assertTrue(axe.isUsable());
        for (int i = 0; i < 10; i++) {
            axe.decreaseDurability();
        }
        System.out.println(axe.getDurability());
        Assert.assertFalse(axe.isUsable());
    }

    @Test
    public void getAttackRateTest() {
        Assert.assertEquals(4, axe.getAttackRate());
    }

    @Test
    public void getDamageTest() {
        Assert.assertEquals(4, axe.getDamage());
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("axe", axe.getName());
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("melee:axe", axe.toString());
    }
}
