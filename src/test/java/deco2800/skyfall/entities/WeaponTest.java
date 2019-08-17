package deco2800.skyfall.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeaponTest {

    private Weapon weapon;

    @Before
    public void setUp() throws Exception {
        weapon = new Weapon("Axe","Melee","Single",1f,1f,100f);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameTest() {
        assertEquals("Axe", weapon.getName());
    }

    @Test
    public void getWeaponTypeTest() {
        assertEquals("Melee", weapon.getWeaponType());
    }

    @Test
    public void getDamageTypeTest() {
        assertEquals("Single", weapon.getDamageType());
    }

    @Test
    public void getDamageTest() {
        assertEquals(1f, weapon.getDamage());
    }

    @Test
    public void getAttackRateTest() {
        assertEquals(1f, weapon.getAttackRate());
    }

    @Test
    public void getDurabilityTest() {
        assertEquals(100f, weapon.getDurability());
    }
}