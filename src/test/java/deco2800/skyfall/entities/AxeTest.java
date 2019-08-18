package deco2800.skyfall.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AxeTest {

  private Axe axe = new Axe();
  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void getDurability() {
    assert(axe.getDurability().equals(10));
  }

  @Test
  public void getAttackRate() {
    assert (axe.getAttackRate().equals(4));
  }
  @Test
  public void getDamage() {
    assert(axe.getDamage().equals(4));
  }

  @Test
  public void getName() {
    assert (axe.getName().equals("axe"));
  }
}