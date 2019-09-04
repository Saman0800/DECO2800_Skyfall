package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwjgl.Sys;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class})
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
    private Hatchet testHatchet;
    private Hatchet testHatchet2;
    private PickAxe testPickaxe;
    private World w =null;
    @Mock
    private GameManager mockGM;

    private PhysicsManager physics;

    // A hashmap for testing player's animations
    private HashMap testMap = new HashMap();

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

        testHatchet = new Hatchet();
        testHatchet2 = new Hatchet();

        testTile = new Tile(0f,0f);
        testTree = new Tree(testTile,true);
        testRock = new Rock(testTile,true);

        inventoryManager = GameManager.get().getManagerFromInstance(InventoryManager.class);

        WorldBuilder builder = new WorldBuilder();
        WorldDirector.constructTestWorld(builder);
        w = builder.getWorld();

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);


        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);
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
        Assert.assertEquals(testCharacter.getHealth(), 15);
        Assert.assertEquals(testCharacter.getDeaths(), 1);
    }

    @Test
    /**
     * Test main character is interacting correctly with basic weapon action
     */
    public void weaponTest() {
        testCharacter.pickUpWeapon(sword);
        testCharacter.pickUpWeapon(spear);
        testCharacter.dropWeapon(axe);
        testCharacter.dropWeapon(sword);
        testCharacter.pickUpWeapon(bow);

        testCharacter.weaponEffect(sword);
        testCharacter.weaponEffect(spear);
        testCharacter.weaponEffect(axe);
        Assert.assertEquals(testCharacter.getHealth(), 2);
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
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), inventoryManager.getAmount("Stone"));
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Wood"), inventoryManager.getAmount("Wood"));
        Stone stone = new Stone();
        testCharacter.pickUpInventory(stone);
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), inventoryManager.getAmount("Stone"));
        testCharacter.dropInventory("Stone");
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), inventoryManager.getAmount("Stone"));
        pickUpInventoryMultiple(stone, 500);
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), inventoryManager.getAmount("Stone"));
        /* Had to change inventory method inventoryDropMultiple
            -   if(amount == num)
            to:
            -   if(amount.equals(num)
            for this to work
        */
        testCharacter.getInventoryManager()
                .inventoryDropMultiple("Stone",inventoryManager.getAmount("Stone"));
        Assert.assertEquals((int)testCharacter.getInventoryManager()
                .getAmount("Stone"), inventoryManager.getAmount("Stone"));
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
        // Reduce health by input damage test
        testCharacter.hurt(3);
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
        testCharacter.hurt(10);

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
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 105);

        testCharacter.addGold(g5, count);
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(2));
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 110);

        // create a new gold piece with a value of 50
        GoldPiece g50 = new GoldPiece(50);

        testCharacter.addGold(g50, count);
        // ensure the gold piece is added to the pouch
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(50));
        // ensure the gold piece is only added once
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(1));

        // ensure that the pouch total value is correct
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 160);

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
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 280);
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(4));
        Assert.assertTrue(testCharacter.getGoldPouch().get(10).equals(1));
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(3));

        //remove a piece of gold from the pouch
        testCharacter.removeGold(g5);

        // ensure that the necessary adjustments have been made
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 275);
        Assert.assertTrue(testCharacter.getGoldPouch().get(5).equals(3));
        Assert.assertTrue(testCharacter.getGoldPouch().get(10).equals(1));
        Assert.assertTrue(testCharacter.getGoldPouch().get(50).equals(3));

        //remove a piece of gold from the pouch which is the last piece
        testCharacter.removeGold(g10);

        // ensure that the necessary adjustments have been made
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 265);
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
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue() == 280);

    }

    @Test
    public void useHatchetTest(){

        mockGM.setWorld(w);
        w.addEntity(testCharacter);
        w.addEntity(testTree);
        testCharacter.setCol(1f);
        testCharacter.setRow(1f);
        testTree.setCol(1f);
        testTree.setRow(1f);
        int currentWood = testCharacter.getInventoryManager().getAmount("Wood");
        testCharacter.useHatchet();
        Assert.assertEquals(currentWood+1,testCharacter.getInventoryManager().getAmount("Wood"));
    }

    @Test
    public void usePickAxeTest() {

        mockGM.setWorld(w);
        w.addEntity(testCharacter);
        w.addEntity(testRock);
        testCharacter.setCol(1f);
        testCharacter.setRow(1f);
        testRock.setCol(1f);
        testRock.setRow(1f);
        int currentStone = testCharacter.getInventoryManager().getAmount("Stone");
        testCharacter.usePickAxe();
        Assert.assertEquals(currentStone+1,testCharacter.getInventoryManager().getAmount("Stone"));

    }

    @Test
    public void createItemTest() {

        testCharacter.getBlueprintsLearned().add("Hatchet");
        int i;

        for (i = 0; i < 25; i++) {
            testCharacter.getInventoryManager().inventoryAdd(new Wood());
            testCharacter.getInventoryManager().inventoryAdd(new Stone());
        }

        int currentHatchetAmount = testCharacter.getInventoryManager().getAmount("Hatchet");
        testCharacter.createItem(testHatchet2);

        Assert.assertEquals(currentHatchetAmount, testCharacter.getInventoryManager().getAmount("Hatchet"));

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
