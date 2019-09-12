package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;
import org.junit.*;

public class RangeWeaponTest {

    private Bow bow;
    private Spear spear;

    @Before
    public void setUp() throws Exception {
        bow = new Bow(new Tile(0, 0), "bow_tex", false);
        spear = new Spear(new Tile(0, 0), "spear_tex", false);
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

        Assert.assertEquals(bow.getWeaponType(), "range");
        Assert.assertEquals(spear.getWeaponType(), "range");
    }

    @Test
    public void getDamageType() {
        Assert.assertEquals(spear.getDamageType(), bow.getDamageType());
        Assert.assertEquals(spear.getDamageType(), "splash");
    }
}