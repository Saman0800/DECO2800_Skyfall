package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

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
    public void getWeaponType() {
        Assert.assertEquals(axe.getWeaponType(), axe.getSubtype());
        Assert.assertEquals(sword.getWeaponType(), sword.getSubtype());

        Assert.assertEquals("melee", axe.getWeaponType());
        Assert.assertEquals("melee", sword.getWeaponType());
    }

    @Test
    public void getDamageType() {
        Assert.assertEquals(axe.getDamageType(), sword.getDamageType());
        Assert.assertEquals("slash", axe.getDamageType());
    }
}
