package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class AxeTest {

  private Axe axe;

  @Before
  public void setUp() {
    axe = new Axe(new Tile(0, 0), "axe_tex", false);
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
    Assert.assertEquals(axe.toString(), "axe is a melee weapon " +
            "which can be used to help the Main Character" +
            " defeat enemies. It has deals 4 slash damages each time it is" +
            " used. It also has an attack rate " +
            "of: 4 and a durability of: 10 before it become useless. "
            + "axe" + "is carryable, but exchangeable.");
  }
}
