package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

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
        assert(axe.getDurability() == 10);
    }

    @Test
    public void getAttackRateTest() {
        assert (axe.getAttackRate() == 4);
    }

    @Test
    public void getDamageTest() {
        assert(axe.getDamage() == 4);
    }

    @Test
    public void getNameTest() {
        assert (axe.getName().equals("axe"));
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("melee:axe", axe.toString());
    }
}
