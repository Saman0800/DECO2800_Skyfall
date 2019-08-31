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

    // MainCharacter being used for testing
    private MainCharacter testCharacter;

    @Before
    /**
     * Sets up all variables to be used for testing
     */
    public void setup() {
        testCharacter = new MainCharacter(0f, 0f,
                0.05f, "Main Piece", 10);
    }

    @After
    /**
     * Sets up all variables to be null after esting
     */
    public void tearDown() {
        testCharacter = null;
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
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), 2);
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Wood"), 2);
        Stone stone = new Stone();
        testCharacter.pickUpInventory(stone);
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), 3);
        testCharacter.dropInventory("Stone");
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), 2);
        pickUpInventoryMultiple(stone, 500);
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), 502);
        /* Had to change inventory method inventoryDropMultiple
            -   if(amount == num)
            to:
            -   if(amount.equals(num)
            for this to work
        */
        testCharacter.getInventoryManager()
                .inventoryDropMultiple("Stone",502);
        Assert.assertEquals((int)testCharacter.getInventoryManager()
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

    @Test
    /**
     * Test that the item properly switches.
     */
    public void switchItemTest() {
        Assert.assertEquals(1,testCharacter.getItemSlotSelected());
        testCharacter.switchItem(9);
        Assert.assertEquals(2,testCharacter.getItemSlotSelected());
        testCharacter.switchItem(10);
        Assert.assertEquals(3,testCharacter.getItemSlotSelected());
        testCharacter.switchItem(8);
        Assert.assertEquals(1,testCharacter.getItemSlotSelected());
    }


}
