package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class RangeWeaponTest {

    private Bow bow;
    private Spear spear;

    @Before
    public void setUp() {
        bow = new Bow(new Tile(null, 0, 0), false);
        spear = new Spear(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() {
        bow = null;
        spear = null;
    }

    @Test
    public void getWeaponType() {
        Assert.assertEquals(bow.getWeaponType(), bow.getSubtype());
        Assert.assertEquals(spear.getWeaponType(), spear.getSubtype());

        Assert.assertEquals("range", bow.getWeaponType());
        Assert.assertEquals("range", spear.getWeaponType());
    }

    @Test
    public void getDamageType() {
        Assert.assertEquals(spear.getDamageType(), bow.getDamageType());
        Assert.assertEquals("splash", spear.getDamageType());
    }
}
