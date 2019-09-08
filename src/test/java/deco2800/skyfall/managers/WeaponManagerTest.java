package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import org.junit.*;

public class WeaponManagerTest {
    // MainCharacter being used for testing
    private MainCharacter testCharacter;

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
        testCharacter = new MainCharacter(0f, 0f,
                0.05f, "Main Piece", 10);
        sword = new Weapon("sword", "melee",
                "slash", 3, 5, 6);
        spear = new Weapon("spear", "range",
                "splash", 5, 4, 7);
        bow = new Weapon("bow", "range",
                "splash", 4, 3, 10);
        axe = new Weapon("axe", "melee",
                "slash", 4, 4, 10);
    }

    @After
    /**
     * Sets up all variables to be null after testing
     */
    public void tearDown() {
        testCharacter = null;
        sword = null;
        spear = null;
        bow = null;
        axe = null;
    }

    @Ignore
    @Test
    /**
     * Tests basic pickup works
     */
    public void pickUpTest() {
        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                0);

        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(sword);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                2);

        testCharacter.pickUpWeapon(spear);
        testCharacter.pickUpWeapon(spear);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                4);

        testCharacter.pickUpWeapon(bow);
        testCharacter.pickUpWeapon(bow);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                6);

        testCharacter.pickUpWeapon(axe);
        testCharacter.pickUpWeapon(axe);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                8);

        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(sword), 2);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(spear), 2);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(bow), 2);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(axe), 2);
    }

    @Ignore
    @Test
    /**
     * Test that weapon dropping works
     */
    public void dropTest() {
        System.out.println(testCharacter.getWeapons().toString());

        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                0);

        testCharacter.pickUpWeapon(sword);
        testCharacter.dropWeapon(sword);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(sword), 0);

        testCharacter.dropWeapon(spear);
        testCharacter.pickUpWeapon(spear);
        testCharacter.pickUpWeapon(spear);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(spear), 2);

        testCharacter.pickUpWeapon(bow);
        testCharacter.pickUpWeapon(bow);
        testCharacter.pickUpWeapon(bow);
        testCharacter.pickUpWeapon(bow);
        testCharacter.getWeaponManager().dropWeapons(bow, 2);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(bow), 2);

        testCharacter.pickUpWeapon(axe);
        testCharacter.pickUpWeapon(axe);
        testCharacter.getWeaponManager().dropWeapons(spear, 3);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(axe), 2);

        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                6);
    }

    @Ignore
    @Test
    /**
     * Equip weapon testing
     */
    public void equipTest() {
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 0);

        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(sword);

        System.out.println(testCharacter.getWeapons().toString());

        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(sword), 2);

        testCharacter.equipWeapon(sword);
        Assert.assertTrue(testCharacter.getWeaponManager().isEquipped(sword));
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 1);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(sword), 1);

        testCharacter.equipWeapon(spear);
        Assert.assertFalse(testCharacter.getWeaponManager().isEquipped(spear));
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 1);
        testCharacter.pickUpWeapon(spear);
        testCharacter.pickUpWeapon(spear);
        testCharacter.equipWeapon(spear);
        testCharacter.equipWeapon(spear);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 2);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(spear), 1);

        testCharacter.unequipWeapon(bow);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 2);
        testCharacter.pickUpWeapon(bow);
        testCharacter.equipWeapon(bow);
        Assert.assertFalse(testCharacter.getWeaponManager().isEquipped(bow));
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 2);
        Assert.assertEquals(testCharacter.getWeaponManager()
                .getWeaponAmount(bow), 1);

        testCharacter.pickUpWeapon(axe);
        testCharacter.equipWeapon(axe);
        Assert.assertFalse(testCharacter.getWeaponManager().isEquipped(axe));
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped()
                , 2);
        testCharacter.unequipWeapon(spear);
        testCharacter.unequipWeapon(sword);
        testCharacter.equipWeapon(axe);
        Assert.assertFalse(testCharacter.getWeaponManager().isEquipped(spear));
        Assert.assertFalse(testCharacter.getWeaponManager().isEquipped(sword));
        Assert.assertTrue(testCharacter.getWeaponManager().isEquipped(axe));

        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                5);
    }

    @Test
    /**
     * Test return methods for weapons map, toString and equipped list are
     * doing the right thing
     */
    public void returnTest() {
        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(spear);
        testCharacter.pickUpWeapon(spear);
        testCharacter.pickUpWeapon(bow);
        testCharacter.pickUpWeapon(bow);
        testCharacter.pickUpWeapon(axe);
        testCharacter.pickUpWeapon(axe);
        Assert.assertEquals(testCharacter.getWeapons().size(), 4);

        testCharacter.equipWeapon(sword);
        testCharacter.equipWeapon(spear);
        testCharacter.equipWeapon(bow);
        testCharacter.equipWeapon(axe);
        Assert.assertEquals(testCharacter.getEquipped().size(), 2);

        Assert.assertEquals(testCharacter.getWeaponManager().toString().length(),
                ("Weapons: {sword=1, spear=1, bow=1, axe=2}" +
                        "\nEquipped: sword, spear.").length());
    }
}
