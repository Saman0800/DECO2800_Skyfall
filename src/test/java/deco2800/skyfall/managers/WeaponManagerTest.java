package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.weapons.*;
import deco2800.skyfall.worlds.Tile;

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
        sword = new Sword(new Tile(0, 0), true);
        spear = new Spear(new Tile(0, 0), true);
        bow = new Bow(new Tile(0, 0), true);
        axe = new Axe(new Tile(0, 0), true);
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


    @Test
    /**
     * Tests basic pickup works
     */
    public void pickUpTest() {
        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 7);

        this.pickUpWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 15);
    }


    @Test
    /**
     * Test that weapon dropping works
     */
    public void dropTest() {
        this.pickUpWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 15);

        this.dropWeapons();

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 10);

        testCharacter.dropInventory("sword");

        Assert.assertEquals(testCharacter.getInventoryManager().getTotalAmount(), 10);
    }


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
