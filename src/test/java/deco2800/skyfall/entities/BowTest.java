package deco2800.skyfall.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BowTest {

  private Bow bow = new Bow();
  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void getDurability() {
    assert(bow.getDurability().equals(10));
  }

  @Test
  public void getAttackRate() {
    assert (bow.getAttackRate().equals(3));
  }
  @Test
  public void getDamage() {
    assert(bow.getDamage().equals(4));
  }

  @Test
  public void getName() {
    assert (bow.getName().equals("bow"));
  }
}