package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import deco2800.skyfall.resources.items.Stone;

import deco2800.skyfall.util.Vector2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class MainCharacterTest {

    // MainCharacter being used for testing
    private MainCharacter testCharacter;

    // A hashmap for testing player's animations
    private HashMap testMap = new HashMap();

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
        Assert.assertEquals(testCharacter.getHealth(), 10);
        Assert.assertEquals(testCharacter.getDeaths(), 1);
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
        Assert.assertEquals(testCharacter.getInventoryManager()
                .getAmount("Stone"), 2);
        Assert.assertEquals(testCharacter.getInventoryManager()
                .getAmount("Wood"), 2);
        Stone stone = new Stone();
        testCharacter.pickUpInventory(stone);
        Assert.assertEquals(testCharacter.getInventoryManager()
                .getAmount("Stone"), 3);
        testCharacter.dropInventory("Stone");
        Assert.assertEquals(testCharacter.getInventoryManager()
                .getAmount("Stone"), 2);
        pickUpInventoryMultiple(stone, 500);
        Assert.assertEquals(testCharacter.getInventoryManager()
                .getAmount("Stone"), 502);
        /* Had to change inventory method inventoryDropMultiple
            -   if(amount == num)
            to:
            -   if(amount.equals(num)
            for this to work
        */
        testCharacter.getInventoryManager()
                .inventoryDropMultiple("Stone",502);
        Assert.assertEquals(testCharacter.getInventoryManager()
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

    @Test
    public void directionCheck() {
        testCharacter.setCurrentDirection(Direction.EAST);
        Assert.assertEquals(testCharacter.getCurrentDirection(), Direction.EAST);

        testCharacter.setCurrentDirection(Direction.WEST);
        Assert.assertEquals(testCharacter.getCurrentDirection(), Direction.WEST);
    }

    @Test
    public void roleCheck() {
        testCharacter.setCurrentState(AnimationRole.MOVE);
        Assert.assertEquals(testCharacter.getCurrentState(), AnimationRole.MOVE);

        testCharacter.setCurrentState(AnimationRole.NULL);
        Assert.assertEquals(testCharacter.getCurrentState(), AnimationRole.NULL);
    }

    @Test
    public void setAndGetAnimationTest() {
        // testCharacter.addAnimations(AnimationRole.MOVE_EAST, "right");
        //testCharacter.getAnimationName(AnimationRole.MOVE_EAST);
    }

    /**
     * Test hurt effect
     */
    @Test
    public void hurtTest() {
        // testCharacter.direction = new Vector2(20, 0);
        // Set player's health back to 10.
        testCharacter.changeHealth(3);

        // Check if the hurt() is called.
        testCharacter.hurt(3);
        Assert.assertTrue(testCharacter.IsHurt());

        // Reduce health by input damage test
        testCharacter.changeHealth(-3);
        Assert.assertEquals(7, testCharacter.getHealth());

        // Character bounce back test
        // Assert.assertEquals(, testCharacter.getCol());

        // "Hurt" animation test
        AnimationLinker animationLinker = new AnimationLinker("MainCharacter_Hurt_E_Anim",
                AnimationRole.HURT, Direction.DEFAULT, false ,true);
        testMap.put(Direction.DEFAULT, animationLinker);
        testCharacter.addAnimations(AnimationRole.HURT, Direction.DEFAULT, animationLinker);
        Assert.assertEquals(testMap, testCharacter.animations.get(AnimationRole.HURT));
    }

    /**
     * Test recover effect
     */
    @Test
    public void recoverTest() {
        // Set the health status of player from hurt back to normal
        // so that the effect (e.g. sprite flashing in red) will disappear
        // after recovering.
        testCharacter.recover();
        Assert.assertFalse(testCharacter.IsHurt());
    }

    /**
     * Test kill effect
     */
    @Test
    public void killTest() {
        // Test if hurt() can trigger Peon.changeHealth() when
        // the damage taken can make player's health below 0.
        testCharacter.hurt(100);
        // player death + 1
        Assert.assertEquals(1, testCharacter.getDeaths());

        // "Kill" animation test
        AnimationLinker animationLinker = new AnimationLinker("MainCharacter_Dead_E_Anim",
                AnimationRole.DEAD, Direction.DEFAULT, false, true);
        testMap.put(Direction.DEFAULT, animationLinker);
        testCharacter.addAnimations(AnimationRole.DEAD, Direction.DEFAULT, animationLinker);
        Assert.assertEquals(testMap, testCharacter.animations.get(AnimationRole.DEAD));
    }

    public void movementAnimationsExist() {
        testCharacter.setCurrentState(AnimationRole.MOVE);
        testCharacter.setCurrentDirection(Direction.EAST);

        AnimationLinker al = testCharacter.getToBeRun();
        Assert.assertEquals(al.getAnimationName(), "MainCharacterE_Anim");
        Assert.assertEquals(al.getType(), AnimationRole.MOVE);
    }

    @Test
    public void directionTexturesExist() {
        testCharacter.setCurrentDirection(Direction.EAST);
        String s = testCharacter.getDefaultTexture();
        Assert.assertEquals(s, "__ANIMATION_MainCharacterE_Anim:0");

        testCharacter.setCurrentDirection(Direction.WEST);
        s = testCharacter.getDefaultTexture();
        Assert.assertEquals(s, "__ANIMATION_MainCharacterW_Anim:0");

        testCharacter.setCurrentDirection(Direction.NORTH);
        s = testCharacter.getDefaultTexture();
        Assert.assertEquals(s, "__ANIMATION_MainCharacterN_Anim:0");
    }
}
