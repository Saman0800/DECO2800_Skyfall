package deco2800.skyfall.entities;

import org.junit.*;
import static org.junit.Assert.*;

public class WeaponTest {

    private Weapon weapon;

    @Before
    public void setUp() throws Exception {
        weapon = new Weapon("Axe","Melee","Single", 1,1,100);
    }

    @After
    public void tearDown() throws Exception {
        weapon = null;
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
        assertEquals(1, weapon.getDamage());
    }

    @Test
    public void getAttackRateTest() {
        assertEquals(1, weapon.getAttackRate());
    }

    @Test
    public void getDurabilityTest() {
        assertEquals(100, weapon.getDurability());
    }
}