package deco2800.skyfall.entities;

import org.junit.*;

public class SpearTest {

  private Weapon spear;

  @Before
  public void setUp() {
    spear = new Weapon("spear", "range",
            "splash", 5, 4, 7);
  }

  @After
  public void tearDown() {
    spear = null;
  }

  @Test
  public void getDurabilityTest() {
    assert(spear.getDurability() == 7);
  }

  @Test
  public void getAttackRateTest() {
    assert (spear.getAttackRate() == 4);
  }

  @Test
  public void getDamageTest() {
    assert(spear.getDamage() == 5);
  }

  @Test
  public void getNameTest() {
    assert (spear.getName().equals("spear"));
  }

  @Test
  public void toStringTest() {
    Assert.assertEquals(spear.toString(), "spear is a range weapon " +
            "which can be used to help the Main Character" +
            " defeat enemies. It has deals 5 splash damages each time it is" +
            " used. It also has an attack rate " +
            "of: 4 and a durability of: 7 before it become useless. "
            + "spear" + "is carryable, but exchangeable.");
  }
}
