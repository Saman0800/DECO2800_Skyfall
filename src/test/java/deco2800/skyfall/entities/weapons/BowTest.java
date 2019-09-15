package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class BowTest {

  private Bow bow;

  @Before
  public void setUp() {
    bow = new Bow(new Tile(0, 0), false);
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
    Assert.assertEquals(bow.toString(), "bow is a range weapon " +
            "which can be used to help the Main Character" +
            " defeat enemies. It has deals 4 splash damages each time it is" +
            " used. It also has an attack rate " +
            "of: 3 and a durability of: 10 before it become useless. "
            + "bow" + "is carryable, but exchangeable.");
  }
}
