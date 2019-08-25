package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import deco2800.skyfall.resources.items.Stone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class MainCharacterTest {

    private MainCharacter testCharacter;
    private Weapon sword;
    private Weapon spear;
    private Weapon bow;
    private Weapon axe;

    // MainCharacter being used for testing
    @Before
    public void setup() {
        testCharacter = new MainCharacter(0f, 0f,
                0.05f, "Main Piece", 10);

        // Weapons being used for testing
        sword = new Weapon("sword", "melee",
                "slash", 3, 5, 6);
        spear = new Weapon("spear", "range",
                "splash", 5, 4, 7);
        bow = new Weapon("bow", "range",
                "splash", 4, 3, 10);
        axe = new Weapon("axe", "melee",
                "slash", 4, 4, 10);
    }
    @Test
    /**
     * Test getters and setters from Peon super Character class
     */
    public void setterGetterTest() {
        Assert.assertEquals(testCharacter.getName(), "Main Piece");
        testCharacter.setName("Side Piece");
        Assert.assertEquals(testCharacter.getName(), "Side Piece");

        Assert.assertFalse(testCharacter.isDead());
        Assert.assertEquals(testCharacter.getHealth(), 10);
        testCharacter.changeHealth(5);
        Assert.assertEquals(testCharacter.getHealth(), 15);
        testCharacter.changeHealth(-20);
        Assert.assertEquals(testCharacter.getHealth(), 0);
        Assert.assertTrue(testCharacter.isDead());
    }

    @Test
    /**
     * Test main character is interacting correctly with basic weapon action
     */
    public void weaponTest() {
        Assert.assertEquals(testCharacter.getWeapons().size(), 0);
        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(spear);
        Assert.assertEquals(testCharacter.getWeapons().size(), 2);
        testCharacter.dropWeapon(axe);
        testCharacter.dropWeapon(sword);
        testCharacter.pickUpWeapon(bow);
        Assert.assertEquals(testCharacter.getWeapons().size(), 2);

        testCharacter.weaponEffect(sword);
        testCharacter.weaponEffect(spear);
        testCharacter.weaponEffect(axe);
        Assert.assertEquals(testCharacter.getHealth(), 0);
        Assert.assertTrue(testCharacter.isDead());
    }

    @Test
    /**
     * Test level changing of main character
     */
    public void levelTest() {
        testCharacter.changeLevel(4);
        Assert.assertEquals(testCharacter.getLevel(), 5);

        testCharacter.changeLevel(-5);
        Assert.assertEquals(testCharacter.getLevel(), 5);

        testCharacter.changeLevel(-4);
        Assert.assertEquals(testCharacter.getLevel(), 1);
    }

    /**
     * Private helper method used for inventory testting
     */
    private void pickUpInventoryMultiple(Item item, int amount) {
        for(int i = 0; i < amount; i++) {
            testCharacter.pickUpInventory(item);
        }
    }

    @Test
    /**
     * Test main character is interacting correctly with basic inventory action
     */
    public void inventoryTest() {
        Assert.assertEquals((int)testCharacter.inventories
                .getAmount("Stone"), 2);
        Assert.assertEquals((int)testCharacter.inventories
                .getAmount("Wood"), 2);
        Stone stone = new Stone();
        testCharacter.pickUpInventory(stone);
        Assert.assertEquals((int)testCharacter.inventories
                .getAmount("Stone"), 3);
        testCharacter.dropInventory("Stone");
        Assert.assertEquals((int)testCharacter.inventories
                .getAmount("Stone"), 2);
        pickUpInventoryMultiple(stone, 500);
        Assert.assertEquals((int)testCharacter.inventories
                .getAmount("Stone"), 502);
        /* Had to change inventory method inventoryDropMultiple
            -   if(amount == num)
            to:
            -   if(amount.equals(num)
            for this to work
        */
        testCharacter.inventories
                .inventoryDropMultiple("Stone",502);
        Assert.assertEquals((int)testCharacter.inventories
                .getAmount("Stone"), 0);
    }

    @Test
    /**
     * Test main character is interacting correctly with basic food action
     */
    public void foodTest() {
        Assert.assertEquals(100,testCharacter.getFoodLevel());

        Apple apple = new Apple();
        testCharacter.pickUpInventory(apple);
        testCharacter.eatFood(new Apple());
        Assert.assertEquals(100, testCharacter.getFoodLevel());

        testCharacter.pickUpInventory(new PoisonousMushroom());
        testCharacter.eatFood(new PoisonousMushroom());
        Assert.assertEquals(80, testCharacter.getFoodLevel());

        testCharacter.pickUpInventory(new PoisonousMushroom());
        testCharacter.eatFood(new PoisonousMushroom());
        Assert.assertEquals(60, testCharacter.getFoodLevel());

        for (int i = 0; i < 10; i++) {
            testCharacter.pickUpInventory(new PoisonousMushroom());
            testCharacter.eatFood(new PoisonousMushroom());
        }

        Assert.assertEquals(0, testCharacter.getFoodLevel());
        Assert.assertTrue(testCharacter.isStarving());
    }

    //TODO: change these tests as Animation System Changes
    //These test abstract entity methods
    /**
     * Tests movingAnimation
     */
    @Test
    public void setMovingAnimationTest() {
        //testCharacter.setMovingAnimation(AnimationRole.MOVE_NORTH);
        //Assert.assertEquals(AnimationRole.MOVE_NORTH, testCharacter.getMovingAnimation());

        //testCharacter.setMovingAnimation(AnimationRole.NULL);
        //Assert.assertEquals(AnimationRole.NULL, testCharacter.getMovingAnimation());

    }

    /**
     * Set and get Animations
     */
    @Test
    public void setAndGetAnimationTest() {
        //testCharacter.addAnimations(AnimationRole.MOVE_EAST, "right");
        //testCharacter.getAnimationName(AnimationRole.MOVE_EAST);
    }

    @After
    public void cleanup() {
        testCharacter = null;
        sword = null;
        spear = null;
        bow = null;
        axe = null;
    }
}