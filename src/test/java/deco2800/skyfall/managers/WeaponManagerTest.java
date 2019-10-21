package deco2800.skyfall.managers;

import deco2800.skyfall.entities.weapons.Axe;
import deco2800.skyfall.entities.weapons.Bow;
import deco2800.skyfall.entities.weapons.Spear;
import deco2800.skyfall.entities.weapons.Sword;
import deco2800.skyfall.entities.weapons.Weapon;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeaponManagerTest {
    // WeaponManager being used for testing
    private WeaponManager testWeaponManager;

    // Weapons being used for testing
    private Weapon sword;
    private Weapon spear;
    private Weapon bow;
    private Weapon axe;

    @Before
    /**
     * Sets up all variables to be used for testing
     */
    public void setup() {
        testWeaponManager = new WeaponManager();
        sword = new Sword(new Tile(null, 0, 0), true);
        spear = new Spear(new Tile(null, 0, 0), true);
        bow = new Bow(new Tile(null, 0, 0), true);
        axe = new Axe(new Tile(null, 0, 0), true);
    }

    @After
    /**
     * Sets up all variables to be null after testing
     */
    public void tearDown() {
        testWeaponManager = null;
        sword = null;
        spear = null;
        bow = null;
        axe = null;
    }

    @Test
    /**
     * Tests basic pickup works
     */
    public void pickUpTest() {
        Assert.assertEquals(testWeaponManager.getNumWeapons(), 0);

        testWeaponManager.pickUpWeapon(sword);
        testWeaponManager.pickUpWeapon(sword);
        Assert.assertEquals(testWeaponManager.getNumWeapons(), 2);

        testWeaponManager.pickUpWeapon(spear);
        testWeaponManager.pickUpWeapon(spear);
        Assert.assertEquals(testWeaponManager.getNumWeapons(), 4);

        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.pickUpWeapon(bow);
        Assert.assertEquals(testWeaponManager.getNumWeapons(), 6);

        testWeaponManager.pickUpWeapon(axe);
        testWeaponManager.pickUpWeapon(axe);
        Assert.assertEquals(testWeaponManager.getNumWeapons(), 8);

        Assert.assertEquals(testWeaponManager.getWeaponAmount(sword), 2);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(spear), 2);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(bow), 2);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(axe), 2);
    }

    @Test
    /**
     * Test that weapon dropping works
     */
    public void dropTest() {
        testWeaponManager.pickUpWeapon(sword);
        testWeaponManager.dropWeapon(sword);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(sword), 0);

        testWeaponManager.dropWeapon(spear);
        testWeaponManager.pickUpWeapon(spear);
        testWeaponManager.pickUpWeapon(spear);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(spear), 2);

        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.dropWeapons(bow, 2);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(bow), 2);

        testWeaponManager.pickUpWeapon(axe);
        testWeaponManager.pickUpWeapon(axe);
        testWeaponManager.dropWeapons(spear, 3);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(axe), 2);

        Assert.assertEquals(testWeaponManager.getNumWeapons(), 6);
    }

    @Test
    /**
     * Equip weapon testing
     */
    public void equipTest() {
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 0);

        testWeaponManager.pickUpWeapon(sword);
        testWeaponManager.pickUpWeapon(sword);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(sword), 2);
        testWeaponManager.equipWeapon(sword);
        Assert.assertTrue(testWeaponManager.isEquipped(sword));
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 1);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(sword), 1);

        testWeaponManager.equipWeapon(spear);
        Assert.assertFalse(testWeaponManager.isEquipped(spear));
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 1);
        testWeaponManager.pickUpWeapon(spear);
        testWeaponManager.pickUpWeapon(spear);
        testWeaponManager.equipWeapon(spear);
        testWeaponManager.equipWeapon(spear);
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 1);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(spear), 2);

        testWeaponManager.unequipWeapon(bow);
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 1);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.equipWeapon(bow);
        Assert.assertFalse(testWeaponManager.isEquipped(bow));
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 1);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(bow), 1);

        testWeaponManager.pickUpWeapon(axe);
        testWeaponManager.equipWeapon(axe);
        Assert.assertFalse(testWeaponManager.isEquipped(axe));
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 1);
        testWeaponManager.unequipWeapon(spear);
        testWeaponManager.equipWeapon(axe);
        Assert.assertFalse(testWeaponManager.isEquipped(spear));
        Assert.assertFalse(testWeaponManager.isEquipped(axe));

        Assert.assertEquals(testWeaponManager.getNumWeapons(), 5);
    }

    @Test
    /**
     * Test return methods for weapons map, toString and equipped list are
     * doing the right thing
     */
    public void returnTest() {
        testWeaponManager.pickUpWeapon(sword);
        testWeaponManager.pickUpWeapon(sword);
        testWeaponManager.pickUpWeapon(spear);
        testWeaponManager.pickUpWeapon(spear);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.pickUpWeapon(axe);
        testWeaponManager.pickUpWeapon(axe);
        Assert.assertEquals(testWeaponManager.getWeapons().size(), 4);

        testWeaponManager.equipWeapon(sword);
        testWeaponManager.equipWeapon(spear);
        testWeaponManager.equipWeapon(bow);
        testWeaponManager.equipWeapon(axe);
        Assert.assertEquals(testWeaponManager.getEquipped().size(), 1);

        Assert.assertEquals(testWeaponManager.toString().length(),
                ("Weapons: {sword=1, spear=1, bow=1, axe=2}" +
                        "\nEquipped: sword.").length());
    }
}
