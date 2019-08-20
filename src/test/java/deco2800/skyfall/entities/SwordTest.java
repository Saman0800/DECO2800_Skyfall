package deco2800.skyfall.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SwordTest {

  private Sword sword = new Sword();
  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void getDurability() {
    assert(sword.getDurability().equals(6));
  }

  @Test
  public void getAttackRate() {
    assert (sword.getAttackRate().equals(5));
  }
  @Test
  public void getDamage() {
    assert(sword.getDamage().equals(3));
  }

  @Test
  public void getName() {
    assert (sword.getName().equals("sword"));
  }
}