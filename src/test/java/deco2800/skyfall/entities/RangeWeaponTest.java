package deco2800.skyfall.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangeWeaponTest {

  private Bow bow = new Bow();
  @Before
  public void setUp() throws Exception {}

  @Test
  public void getWeaponType() {
    assertEquals("range", bow.getWeaponType().toString());
  }

  @Test
  public void getDamageType() {
    assertEquals("splash", bow.getDamageType().toString());
  }
}