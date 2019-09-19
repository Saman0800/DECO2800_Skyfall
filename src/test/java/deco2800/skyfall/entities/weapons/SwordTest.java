package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class SwordTest {

    private Sword sword;

    @Before
    public void setUp() {
        sword = new Sword(new Tile(null, 0, 0), false);
    }

    @After
    public void tearDown() {
        sword = null;
    }

    @Test
    public void getDurabilityTest() {
        Assert.assertEquals(6, sword.getDurability());
    }

    @Test
    public void changeDurabilityTest() {
        Assert.assertTrue(sword.isUsable());
        for (int i = 0; i < 6; i++) {
            sword.decreaseDurability();
        }
        Assert.assertFalse(sword.isUsable());
    }

    @Test
    public void getAttackRateTest() {
        Assert.assertEquals(5, sword.getAttackRate());
    }

    @Test
    public void getDamageTest() {
        Assert.assertEquals(3, sword.getDamage());
    }

    @Test
    public void getName() {
        Assert.assertEquals("sword", sword.getName());
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("melee:sword", sword.toString());
    }
}
