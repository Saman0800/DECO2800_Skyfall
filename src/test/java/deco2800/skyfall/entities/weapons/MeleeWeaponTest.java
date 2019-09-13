package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class MeleeWeaponTest {

    private Axe axe;
    private Sword sword;

    @Before
    public void setUp() {
        axe = new Axe(new Tile(0, 0), false);
        sword = new Sword(new Tile(0, 0), false);
    }

    @After
    public void tearDown() {
        axe = null;
        sword = null;
    }

    @Test
    public void getWeaponType() {
        Assert.assertEquals(axe.getWeaponType(), axe.getSubtype());
        Assert.assertEquals(sword.getWeaponType(), sword.getSubtype());

        Assert.assertEquals(axe.getWeaponType(), "melee");
        Assert.assertEquals(sword.getWeaponType(), "melee");
    }

    @Test
    public void getDamageType() {
        Assert.assertEquals(axe.getDamageType(), sword.getDamageType());
        Assert.assertEquals(axe.getDamageType(), "slash");
    }
}