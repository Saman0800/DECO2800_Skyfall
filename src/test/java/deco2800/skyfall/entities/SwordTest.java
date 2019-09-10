package deco2800.skyfall.entities;

import org.junit.*;

public class SwordTest {

    private Weapon sword;

    @Before
    public void setUp() {
        sword = new Weapon("sword", "melee",
              "slash", 3, 5, 6);
    }

    @After
    public void tearDown() {
        sword = null;
    }

    @Test
    public void getDurabilityTest() {
        assert(sword.getDurability() == 6);
    }

    @Test
    public void getAttackRateTest() {
        assert (sword.getAttackRate() == 5);
    }
    @Test
    public void getDamageTest() {
        assert(sword.getDamage() == 3);
    }

    @Test
    public void getName() {
        assert (sword.getName().equals("sword"));
    }


    @Test
    public void toStringTest() {
        Assert.assertEquals(sword.toString(), "sword is a melee weapon " +
              "which can be used to help the Main Character" +
              " defeat enemies. It has deals 3 slash damages each time it is" +
              " used. It also has an attack rate " +
              "of: 5 and a durability of: 6 before it become useless. "
              + "sword" + "is carryable, but exchangeable.");
    }
}