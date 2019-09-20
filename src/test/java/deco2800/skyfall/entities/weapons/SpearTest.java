package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class SpearTest {

  private Spear spear;

  @Before
  public void setUp() {
    spear = new Spear(new Tile(null, 0, 0), false);
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
    Assert.assertEquals(spear.toString(), "" + "range" + ":" + "spear");
  }
}
