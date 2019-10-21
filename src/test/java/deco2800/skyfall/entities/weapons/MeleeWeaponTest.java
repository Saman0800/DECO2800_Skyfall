package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MeleeWeaponTest {

    private Axe axe;
    private Sword sword;

    @Before
    public void setUp() {
        axe = new Axe(new Tile(null, 0, 0), false);
        sword = new Sword(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() {
        axe = null;
        sword = null;
    }

    @Test
    public void getWeaponTypeTest() {
        Assert.assertEquals(axe.getWeaponType(), axe.getSubtype());
        Assert.assertEquals(sword.getWeaponType(), sword.getSubtype());

        Assert.assertEquals("melee", axe.getWeaponType());
        Assert.assertEquals("melee", sword.getWeaponType());
    }

    @Test
    public void getDamageTypeTest() {
        Assert.assertEquals(axe.getDamageType(), sword.getDamageType());
        Assert.assertEquals("slash", axe.getDamageType());
    }

    @Test
    public void itemMethodTest() {
        Assert.assertEquals(axe.isCarryable(), sword.isCarryable());
        Assert.assertTrue(axe.isCarryable());
        Assert.assertTrue(sword.isCarryable());

        Assert.assertEquals(axe.isExchangeable(), sword.isExchangeable());
        Assert.assertFalse(axe.isExchangeable());
        Assert.assertFalse(sword.isExchangeable());
    }
}
