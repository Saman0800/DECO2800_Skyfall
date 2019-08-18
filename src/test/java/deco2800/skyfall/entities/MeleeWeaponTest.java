package deco2800.skyfall.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeleeWeaponTest {

  private Axe axe = new Axe();

  @Before
  public void setUp() throws Exception {}

  @Test
  public void getWeaponType() {
    assert (axe.getWeaponType().equals("melee"));
  }

  @Test
  public void getDamageType() {
    assert (axe.getDamageType().equals("slash"));
  }
}