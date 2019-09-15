package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.entities.weapons.Sword;
import deco2800.skyfall.worlds.Tile;
import org.junit.*;

public class SwordTest {

  private Sword sword;

  @Before
  public void setUp() {
    sword = new Sword(new Tile(0, 0), false);
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
    Assert.assertEquals(sword.toString(), "" + "melee" + ":" + "sword");
  }
}
