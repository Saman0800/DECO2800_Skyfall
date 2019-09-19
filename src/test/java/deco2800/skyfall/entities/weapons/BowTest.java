package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

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
        assert(bow.getDurability() == 10);
    }

    @Test
    public void getAttackRateTest() {
        assert (bow.getAttackRate() == 3);
    }

    @Test
    public void getDamageTest() {
        assert(bow.getDamage() == 4);
    }

    @Test
    public void getNameTest() {
        assert (bow.getName().equals("bow"));
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("range:bow", bow.toString());
    }
}
