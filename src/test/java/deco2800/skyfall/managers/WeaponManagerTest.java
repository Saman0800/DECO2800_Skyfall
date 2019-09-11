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

    /**
     * Private helper method for the test character to pick up weapons
     */
    private void pickUpWeapons() {
        testCharacter.pickUpInventory(sword);
        testCharacter.pickUpInventory(sword);
        testCharacter.pickUpInventory(spear);
        testCharacter.pickUpInventory(spear);
        testCharacter.pickUpInventory(bow);
        testCharacter.pickUpInventory(bow);
        testCharacter.pickUpInventory(axe);
        testCharacter.pickUpInventory(axe);
    }

    /**
     * Private helper method for the test character to drop weapons
     */
    private void dropWeapons() {
        testCharacter.getInventoryManager().inventoryDropMultiple("sword", 2);
        testCharacter.dropInventory("spear");
        testCharacter.dropInventory("bow");
        testCharacter.dropInventory("axe");
    }

    @Ignore
    @Test
    /**
     * Tests basic pickup works
     */
    public void pickUpTest() {
        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 6);

        this.pickUpWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 14);

    }

    @Ignore
    @Test
    /**
     * Test that weapon dropping works
     */
    public void dropTest() {
        this.pickUpWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 14);

        this.dropWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 9);

        testCharacter.dropInventory("sword");

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 9);
    }

    @Ignore
    @Test
    /**
     * Test return methods for weapons map, toString and equipped list are
     * doing the right thing
     */
    public void returnTest() {
        this.pickUpWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getInventoryAmounts().size(), 8);
    }
}
