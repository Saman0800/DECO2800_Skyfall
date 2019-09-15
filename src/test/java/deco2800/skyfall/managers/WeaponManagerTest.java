package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import org.junit.*;

public class WeaponManagerTest {
    // MainCharacter being used for testing
    private MainCharacter testCharacter;

    // WeaponManager being used for testing
    private WeaponManager testWeaponManager;

    //GameManager to be used for testing
    private WeaponManager actualWeaponManager;

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
        testCharacter = MainCharacter.getInstance(0f, 0f,
                0.05f, "Main Piece", 10);

        testWeaponManager = new WeaponManager();

        sword = new Weapon("sword", "melee",
                "slash", 3, 5, 6);
        spear = new Weapon("spear", "range",
                "splash", 5, 4, 7);
        bow = new Weapon("bow", "range",
                "splash", 4, 3, 10);
        axe = new Weapon("axe", "melee",
                "slash", 4, 4, 10);

        actualWeaponManager = GameManager.get().getManagerFromInstance(WeaponManager.class);
    }

    @After
    /**
     * Sets up all variables to be null after testing
     */
    public void tearDown() {
        testCharacter = null;
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
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 2);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(spear), 1);

        testWeaponManager.unequipWeapon(bow);
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 2);
        testWeaponManager.pickUpWeapon(bow);
        testWeaponManager.equipWeapon(bow);
        Assert.assertFalse(testWeaponManager.isEquipped(bow));
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 2);
        Assert.assertEquals(testWeaponManager.getWeaponAmount(bow), 1);

        testWeaponManager.pickUpWeapon(axe);
        testWeaponManager.equipWeapon(axe);
        Assert.assertFalse(testWeaponManager.isEquipped(axe));
        Assert.assertEquals(testWeaponManager.getNumEquipped(), 2);
        testWeaponManager.unequipWeapon(spear);
        testWeaponManager.unequipWeapon(sword);
        testWeaponManager.equipWeapon(axe);
        Assert.assertFalse(testWeaponManager.isEquipped(spear));
        Assert.assertFalse(testWeaponManager.isEquipped(sword));
        Assert.assertTrue(testWeaponManager.isEquipped(axe));

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
        Assert.assertEquals(testWeaponManager.getEquipped().size(), 2);

        Assert.assertEquals(testWeaponManager.toString().length(),
                ("Weapons: {sword=1, spear=1, bow=1, axe=2}" +
                        "\nEquipped: sword, spear.").length());
    }

    @Test
    /**
     * Test main character is interacting correctly with its weapon manager
     */
    public void characterWeaponTest() {
        Assert.assertEquals(testCharacter.getWeapons().size(), actualWeaponManager.getWeapons().size());
        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(spear);
        Assert.assertEquals(testCharacter.getWeapons().size(), actualWeaponManager.getWeapons().size());
        testCharacter.dropWeapon(axe);
        testCharacter.dropWeapon(sword);
        testCharacter.pickUpWeapon(bow);
        Assert.assertEquals(testCharacter.getWeapons().size(), actualWeaponManager.getWeapons().size());
        Assert.assertEquals(testCharacter.getWeaponManager().getNumWeapons(),
                actualWeaponManager.getNumWeapons());
        Assert.assertEquals(testCharacter.getWeaponManager()
                        .getWeaponAmount(sword), actualWeaponManager.getWeaponAmount(sword));
        Assert.assertEquals(testCharacter.getWeaponManager()
                        .getWeaponAmount(spear), actualWeaponManager.getWeaponAmount(spear));
        Assert.assertEquals(testCharacter.getWeaponManager()
                        .getWeaponAmount(axe), actualWeaponManager.getWeaponAmount(axe));
        Assert.assertEquals(testCharacter.getWeaponManager()
                        .getWeaponAmount(bow), actualWeaponManager.getWeaponAmount(bow));

        Assert.assertEquals(testCharacter.getEquipped().size(), 0);
        testCharacter.equipWeapon(spear);
        testCharacter.equipWeapon(axe);
        testCharacter.equipWeapon(bow);
        Assert.assertEquals(testCharacter.getEquipped().size(), 2);
        Assert.assertEquals(testCharacter.getWeaponManager().getNumEquipped(),
                2);
        Assert.assertTrue(testCharacter.getWeaponManager().isEquipped(spear));
        Assert.assertFalse(testCharacter.getWeaponManager().isEquipped(axe));
        Assert.assertTrue(testCharacter.getWeaponManager().isEquipped(bow));

        Assert.assertEquals(testCharacter.getHealth(), 10);
        testCharacter.weaponEffect(sword);
        testCharacter.weaponEffect(spear);
        Assert.assertEquals(testCharacter.getHealth(), 2);
        testCharacter.weaponEffect(axe);
        Assert.assertEquals(testCharacter.getHealth(), 2);
        Assert.assertEquals(testCharacter.getDeaths(), 1);
    }
}
