package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.GoldPiece;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.util.Collider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class MainCharacterTest {

    private GoldPiece goldpiece;
    private MainCharacter testCharacter;
    private Weapon sword;
    private Weapon spear;
    private Weapon bow;
    private Weapon axe;
    private Tree testTree;
    private Rock testRock;
    private Tile testTile;
    private InventoryManager inventoryManager;

    @Before
    /**
     * Sets up all variables to be used for testing
     */
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

        testTile = new Tile(0f,0f);
        testTree = new Tree(testTile,true);
        testRock = new Rock(testTile,true);

        inventoryManager = GameManager.get().getManagerFromInstance(InventoryManager.class);
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


    //LEAVE COMMENTED! As discussed on Gitlab ticket #197, after fixing an issue with the MainCharacter inventory this
    //causes issues with gradle that need to be fixed.
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
        Assert.assertEquals(0,testCharacter.getItemSlotSelected());
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

    @Test
    public void addGoldTest(){
        // create a new gold piece with a value of 5
        GoldPiece g5 = new GoldPiece(5);
        Integer count = 1;

        // adding one gold piece at a time
        testCharacter.addGold(g5, count);
        // ensure the gold piece is added to the pouch
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(5));
        // ensure the gold piece is only added once
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(1));
        // ensure that total pouch value has been calculated correctly
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(105));

        testCharacter.addGold(g5, count);
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(2));
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(110));

        // create a new gold piece with a value of 50
        GoldPiece g50 = new GoldPiece(50);

        testCharacter.addGold(g50, count);
        // ensure the gold piece is added to the pouch
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(50));
        // ensure the gold piece is only added once
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(1));

        // ensure that the pouch total value is correct
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(160));

    }

    @Test
    public void removeGoldTest(){
        // create a new gold pieces
        GoldPiece g5 = new GoldPiece(5);
        GoldPiece g10 = new GoldPiece(10);
        GoldPiece g50 = new GoldPiece(50);

        // add the respective gold pieces to the pouch
        testCharacter.addGold(g5, 4);
        testCharacter.addGold(g10, 1);
        testCharacter.addGold(g50, 3);

        // ensure all the pieces have been added
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(280));
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(4));
        Assert.assertTrue(testCharacter.getGoldPouch().get(10).equals(1));
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(3));

        //remove a piece of gold from the pouch
        testCharacter.removeGold(g5);

        // ensure that the necessary adjustments have been made
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(275));
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(3));
        Assert.assertTrue(testCharacter.getGoldPouch().get(10).equals(1));
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(3));

        //remove a piece of gold from the pouch which is the last piece
        testCharacter.removeGold(g10);

        // ensure that the necessary adjustments have been made
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(265));
        Assert.assertFalse(testCharacter.getGoldPouch().containsKey(10));

    }

    @Test
    public void getGoldPouchTest(){
        // create a new gold pieces
        GoldPiece g5 = new GoldPiece(5);
        GoldPiece g10 = new GoldPiece(10);
        GoldPiece g50 = new GoldPiece(50);

        // add the respective gold pieces to the pouch
        testCharacter.addGold(g5, 3);
        testCharacter.addGold(g10, 1);
        testCharacter.addGold(g50, 2);

        // ensure all the pieces have been added
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(5));
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(10));
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(50));
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(3));
        Assert.assertTrue(testCharacter.getGoldPouch().get(10).equals(1));
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(2));

    }

    @Test
    public void getGoldPouchTotalValueTest(){
        // create a new gold pieces
        GoldPiece g5 = new GoldPiece(5);
        GoldPiece g10 = new GoldPiece(10);
        GoldPiece g50 = new GoldPiece(50);

        // add the respective gold pieces to the pouch
        testCharacter.addGold(g5, 4);
        testCharacter.addGold(g10, 1);
        testCharacter.addGold(g50, 3);

        // ensure all the pieces have been added
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(280));

    }

    @Test
    public void useHatchetTest(){



    }

    @After
    public void cleanup() {
        testCharacter = null;
        sword = null;
        spear = null;
        bow = null;
        axe = null;
    }

    @Test
    public void testCollision() {
        WoodCube woodCube = new WoodCube(50,50);
        Collider c1 = woodCube.getCollider();
        Collider c2 = testCharacter.getCollider();
        if (c1.overlaps(c2)) {
            System.out.println("COLLISION");
        } else {
            System.out.println("NO COLLISION");
        }
    }
}
