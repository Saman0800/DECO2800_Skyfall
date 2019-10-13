package deco2800.skyfall.entities;

import com.badlogic.gdx.Input;
import deco2800.skyfall.entities.enemies.Scout;
import deco2800.skyfall.entities.weapons.EmptyItem;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;

import org.mockito.Mock;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class,
        GameManager.class })
public class MainCharacterTest {

    private MainCharacter testCharacter;
    private Tile testTile;
    private GoldPiece testGoldPiece;
    private InventoryManager inventoryManager;
    private World w = null;

    @Mock
    private GameManager mockGM;

    private PhysicsManager physics;

    // A hashmap for testing player's animations
    private HashMap testMap = new HashMap();

    @Before
    /**
     * Sets up all variables to be used for testing
     */
    public void setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then((Answer<Chunk>) invocation -> {
            Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                    invocation.getArgumentAt(1, Integer.class), invocation.getArgumentAt(2, Integer.class));
            chunk.generateEntities();
            return chunk;
        });

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        MainCharacter.resetInstance();
        testCharacter = MainCharacter.getInstance();

        testTile = new Tile(null, 0f, 0f);

        testGoldPiece = new GoldPiece(5);

        inventoryManager = GameManager.get().getManagerFromInstance(InventoryManager.class);

        WorldBuilder builder = new WorldBuilder();
        WorldDirector.constructTestWorld(builder, 0);
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
        // testCharacter = null;
    }

    /**
     * Test getters and setters from Peon super Character class
     */
    @Test
    public void setterGetterTest() {
        testCharacter.setTexChanging(true);
        Assert.assertTrue(testCharacter.isTexChanging());
        testCharacter.setHurt(true);
        Assert.assertTrue(testCharacter.isHurt());
        testCharacter.setHurt(true);
        Assert.assertTrue(testCharacter.isHurt());

        testCharacter.changeTexture("mainCharacter");
        assertEquals("mainCharacter", testCharacter.getTexture());

        Assert.assertEquals(testCharacter.getName(),
        "Main Piece"); testCharacter.setName("Side Piece");
        Assert.assertEquals(testCharacter.getName(), "Side Piece");

        Assert.assertFalse(testCharacter.isDead());
        Assert.assertEquals(testCharacter.getHealth(), 50);
        testCharacter.changeHealth(5);
        Assert.assertEquals(testCharacter.getHealth(), 55);
        testCharacter.changeHealth(-55);
        Assert.assertEquals(testCharacter.getHealth(), 0);
        Assert.assertEquals(testCharacter.getDeaths(), 1);
        Assert.assertTrue(testCharacter.isDead());

        testCharacter.changeHealth(50);
        Assert.assertEquals(testCharacter.getHealth(), 50);
        testCharacter.changeHealth(-2);
        Assert.assertEquals(testCharacter.getHealth(), 48);
        testCharacter.changeHealth(-48);
        Assert.assertEquals(testCharacter.getHealth(), 0);
        Assert.assertEquals(testCharacter.getDeaths(), 2);
    }

    /**
     * Test level changing of main character
     */
    @Test
    public void levelTest() {
        testCharacter.changeLevel(4);
        Assert.assertEquals(5, testCharacter.getLevel());

        testCharacter.changeLevel(-5);
        Assert.assertEquals(5, testCharacter.getLevel());

        testCharacter.changeLevel(-4);
        Assert.assertEquals(1, testCharacter.getLevel());
    }

    /**
     * Private helper method used for inventory testing
     */
    private void pickUpInventoryMultiple(Item item, int amount) {
        for (int i = 0; i < amount; i++) {
            testCharacter.pickUpInventory(item);
        }
    }

    // LEAVE COMMENTED! As discussed on Gitlab ticket #197, after fixing an issue
    // with the MainCharacter inventory this
    // causes issues with gradle that need to be fixed.
    @Test
    /**
     * Test main character is interacting correctly with basic inventory action
     */
    public void inventoryTest() {
        Assert.assertEquals((int) testCharacter.getInventoryManager().getAmount("Stone"),
                inventoryManager.getAmount("Stone"));
        Assert.assertEquals((int) testCharacter.getInventoryManager().getAmount("Wood"),
                inventoryManager.getAmount("Wood"));
        Stone stone = new Stone();
        testCharacter.pickUpInventory(stone);
        Assert.assertEquals((int) testCharacter.getInventoryManager().getAmount("Stone"),
                inventoryManager.getAmount("Stone"));
        testCharacter.dropInventory("Stone");
        Assert.assertEquals((int) testCharacter.getInventoryManager().getAmount("Stone"),
                inventoryManager.getAmount("Stone"));
        pickUpInventoryMultiple(stone, 500);
        Assert.assertEquals((int) testCharacter.getInventoryManager().getAmount("Stone"),
                inventoryManager.getAmount("Stone"));
        /*
         * Had to change inventory method inventoryDropMultiple - if(amount == num) to:
         * - if(amount.equals(num) for this to work
         */
        testCharacter.getInventoryManager().dropMultiple("Stone", inventoryManager.getAmount("Stone"));
        Assert.assertEquals((int) testCharacter.getInventoryManager().getAmount("Stone"),
                inventoryManager.getAmount("Stone"));
    }

    /**
     * Test that the item properly switches.
     */
    @Test
    public void switchItemTest() {
        Assert.assertEquals(1, testCharacter.getItemSlotSelected());
        testCharacter.switchItem(9);
        Assert.assertEquals(2, testCharacter.getItemSlotSelected());
        testCharacter.switchItem(10);
        Assert.assertEquals(3, testCharacter.getItemSlotSelected());
        testCharacter.switchItem(8);
        Assert.assertEquals(1, testCharacter.getItemSlotSelected());
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
        // testCharacter.getAnimationName(AnimationRole.MOVE_EAST);
    }

    /**
     * Test playerHurt effect
     */
    @Test
    public void playerHurtTest() {
        // Set isHurt to true.
        testCharacter.playerHurt(3);
        Assert.assertTrue(testCharacter.isHurt());
        // Health decreases
        Assert.assertEquals(47, testCharacter.getHealth());
        // set current animation to hurt
        Assert.assertEquals(AnimationRole.HURT, testCharacter.getCurrentState());
        // set hurt time and recover time to 0.
        Assert.assertEquals(0, testCharacter.getHurtTime());
        Assert.assertEquals(0, testCharacter.getRecoverTime());

        // test checkIfHurtEnded()
        testCharacter.checkIfHurtEnded();
        // hurt time increases by 20.
        Assert.assertEquals(20, testCharacter.getHurtTime());
        // after hurt animation finished (around 2 seconds),
        // finish hurting, start recovering.
        testCharacter.setHurtTime(500);
        testCharacter.checkIfHurtEnded();
        // set animation status to "not hurt" and is recovering.
        Assert.assertFalse(testCharacter.isHurt());
        Assert.assertTrue(testCharacter.isRecovering());
        // reset hurt time.
        Assert.assertEquals(0, testCharacter.getHurtTime());
    }

    /**
     * Test recover effect
     */
    @Test
    public void recoverTest() {
        // Set the health status of player from hurt back to normal
        // so that the effect (e.g. sprite flashing) will disappear
        // after recovering.
        testCharacter.checkIfRecovered();
        testCharacter.checkIfRecovered();
        // recover time increased by 20.
        Assert.assertEquals(20, testCharacter.getRecoverTime());
        // main character unable to be touched by other objects.
        Assert.assertFalse(testCharacter.getCollidable());

        // After recovered (around 3 seconds)...
        testCharacter.setRecoverTime(3000);
        testCharacter.checkIfRecovered();
        // reset recover time.
        Assert.assertEquals(0, testCharacter.getRecoverTime());
        // main character able to be touched by other objects again.
        Assert.assertTrue(testCharacter.getCollidable());
        // set animation/sprite status to "not recovering".
        Assert.assertFalse(testCharacter.isRecovering());
    }

    /**
     * Test kill effect and method
     */
    @Test
    public void killTest() {
        // call kill() when character's health is 0.
        testCharacter.playerHurt(100);
        // set animation status to DEAD.
        Assert.assertEquals(AnimationRole.DEAD, testCharacter.getCurrentState());
        // reset dead time to 0.
        Assert.assertEquals(0, testCharacter.getDeadTime());
        // main character's number of death increased by 1.
        Assert.assertEquals(1, testCharacter.getDeaths());
    }

    /**
     * Test whether the animation role is updated when
     * method is called.
     */
    @Test
    public void updateAnimationTest() {

        // test hurt animation state
        testCharacter.playerHurt(2);
        // testCharacter.updateAnimation();
        assertEquals(AnimationRole.HURT, testCharacter.getCurrentState());
        testCharacter.setHurt(false);
    }

    /**
     * Test key code
     */
    @Test
    public void notifyKeyDownTest() {
        GameManager.setPaused(false);
        testCharacter.notifyKeyDown(Input.Keys.W);
        assertEquals(1, testCharacter.getYInput());
        testCharacter.notifyKeyDown(Input.Keys.A);
        assertEquals(-1, testCharacter.getXInput());
        testCharacter.notifyKeyDown(Input.Keys.S);
        testCharacter.notifyKeyDown(Input.Keys.S);
        assertEquals(-1, testCharacter.getYInput());
        testCharacter.notifyKeyDown(Input.Keys.D);
        testCharacter.notifyKeyDown(Input.Keys.D);
        assertEquals(1, testCharacter.getXInput());
        testCharacter.notifyKeyDown(Input.Keys.SHIFT_LEFT);
        assertTrue(testCharacter.getIsSprinting());
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
    public void addGoldTest() {
        // create a new gold piece with a value of 5
        GoldPiece g5 = new GoldPiece(5);
        Integer count = 1;

        // adding one gold piece at a time
        testCharacter.addGold(g5, count);
        // ensure the gold piece is added to the pouch
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(5));
        // ensure the gold piece is only added once
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(5), 1);
        // ensure that total pouch value has been calculated correctly
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(5));

        testCharacter.addGold(g5, count);
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(5), 2);
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(10));

        // create a new gold piece with a value of 50
        GoldPiece g50 = new GoldPiece(50);

        testCharacter.addGold(g50, count);
        // ensure the gold piece is added to the pouch
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(50));
        // ensure the gold piece is only added once
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(50), 1);

        // ensure that the pouch total value is correct
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(60));

    }

    @Test
    public void removeGoldTest() {

        // create a new gold pieces
        GoldPiece g5 = new GoldPiece(5);
        GoldPiece g10 = new GoldPiece(10);
        GoldPiece g50 = new GoldPiece(50);

        //remove a piece of gold from the pouch which is not there
        // (should do nothing)
        testCharacter.removeGold(5);

        // add the respective gold pieces to the pouch
        testCharacter.addGold(g5, 4);
        testCharacter.addGold(g10, 1);
        testCharacter.addGold(g50, 3);

        // ensure all the pieces have been added
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(180));
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(5), 4);
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(10), 1);
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(50), 3);

        // remove a piece of gold from the pouch
        testCharacter.removeGold(5);



        // ensure that the necessary adjustments have been made
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(175));
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(5), 3);
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(10), 1);
        Assert.assertEquals((int)testCharacter.getGoldPouch().get(50), 3);

        // remove a piece of gold from the pouch which is the last piece
        testCharacter.removeGold(10);

        // ensure that the necessary adjustments have been made
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(165));
        Assert.assertFalse(testCharacter.getGoldPouch().containsKey(10));


    }

    @Test
    public void getGoldPouchTest() {
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
        Assert.assertEquals(3, (int) testCharacter.getGoldPouch().get(5));
        Assert.assertEquals(1, (int) testCharacter.getGoldPouch().get(10));
        Assert.assertEquals(2, (int) testCharacter.getGoldPouch().get(50));

    }

    @Test
    public void getGoldPouchTotalValueTest() {
        // create a new gold pieces
        GoldPiece g5 = new GoldPiece(5);
        GoldPiece g10 = new GoldPiece(10);
        GoldPiece g50 = new GoldPiece(50);

        // add the respective gold pieces to the pouch
        testCharacter.addGold(g5, 4);
        testCharacter.addGold(g10, 1);
        testCharacter.addGold(g50, 3);

        // ensure all the pieces have been added
        Assert.assertTrue(testCharacter.getGoldPouchTotalValue().equals(180));
    }

    @Test
    public void equippedItemTest() {
        Item toEquip = testCharacter.getInventoryManager().drop("Wood");
        testCharacter.setEquippedItem(toEquip);

        Assert.assertEquals(testCharacter.getEquippedItem().toString(),
                new EmptyItem().toString());
        Assert.assertEquals(testCharacter.displayEquippedItem(), "No item equipped.");
    }

    // These methods no longer exist so tests are commented out
    // @Test
    // public void useHatchetTest() {
    //
    // mockGM.setWorld(w);
    // w.addEntity(testCharacter);
    // w.addEntity(testTree);
    // testCharacter.setCol(1f);
    // testCharacter.setRow(1f);
    // testTree.setCol(1f);
    // testTree.setRow(1f);
    // int currentWood = testCharacter.getInventoryManager().getAmount("Wood");
    // testCharacter.useHatchet();
    // Assert.assertEquals(currentWood + 1,
    // testCharacter.getInventoryManager().getAmount("Wood"));
    // }
    //
    // @Test
    // public void usePickAxeTest() {
    //
    // mockGM.setWorld(w);
    // w.addEntity(testCharacter);
    // w.addEntity(testRock);
    // testCharacter.setCol(1f);
    // testCharacter.setRow(1f);
    // testRock.setCol(1f);
    // testRock.setRow(1f);
    // int currentStone = testCharacter.getInventoryManager().getAmount("Stone");
    // testCharacter.usePickAxe();
    // Assert.assertEquals(currentStone + 1,
    // testCharacter.getInventoryManager().getAmount("Stone"));
    //
    // }

    /**
     * Tests to ensure that the closest gold piece is added to the gold pouch
     * Tested here instead of World to allow for ease of testing.
     */
    @Test
    public void addClosestGoldPieceTest() {
        mockGM.setWorld(w);
        w.addEntity(testCharacter);
        w.addEntity(testGoldPiece);
        testCharacter.setCol(1f);
        testCharacter.setRow(1f);
        testGoldPiece.setCol(1f);
        testGoldPiece.setRow(1f);
        testCharacter.addClosestGoldPiece();
        Assert.assertTrue(testCharacter.getGoldPouch().containsKey(5));
        Assert.assertEquals(5, (int) testCharacter.getGoldPouchTotalValue());

    }

    @Test
    public void createItemTest() {
        // int i;
        // testCharacter.getBlueprintsLearned().add("Hatchet");
        //
        // for (i = 0; i < 25; i++) {
        // testCharacter.getInventoryManager().inventoryAdd(new Wood());
        // testCharacter.getInventoryManager().inventoryAdd(new Stone());
        // testCharacter.getInventoryManager().inventoryAdd(new Metal());
        // }
        //
        // int currentHatchetAmount =
        // testCharacter.getInventoryManager().getAmount("Hatchet");
        // testCharacter.createItem(new Hatchet());
        // Assert.assertEquals(currentHatchetAmount+1,
        // testCharacter.getInventoryManager().getAmount("Hatchet"));
    }

    @Test
    public void manaTest() {
        Assert.assertEquals(this.testCharacter.getMana(), 100);
        this.testCharacter.setMana(50);
        Assert.assertEquals(this.testCharacter.getMana(), 50);
        this.testCharacter.setMana(-1);
        Assert.assertEquals(this.testCharacter.getMana(), -1);
        this.testCharacter.setMana(0);
        Assert.assertEquals(this.testCharacter.getMana(), 0);

    }

    @Test
    public void testGetItemSlot() {
        Assert.assertEquals(this.testCharacter.getItemSlotSelected(), 1);
        this.testCharacter.switchItem(9);
        Assert.assertEquals(this.testCharacter.getItemSlotSelected(), 2);
        this.testCharacter.switchItem(1);
        Assert.assertEquals(this.testCharacter.getItemSlotSelected(), 2);
        this.testCharacter.switchItem(10);
        Assert.assertEquals(this.testCharacter.getItemSlotSelected(), 3);

    }

    @Test
    public void healthItemTest() {
        // Create items that give you health
        Aloe_Vera alo = new Aloe_Vera();
        Apple apple = new Apple();
        Berry berry = new Berry();

        testCharacter.changeHealth(-8);

        int currentHealth = testCharacter.getHealth();

        // Check that health increases by 2
        testCharacter.pickUpInventory(alo);
        testCharacter.setEquippedItem(alo);
        testCharacter.useEquipped();
        Assert.assertEquals(currentHealth + 2, testCharacter.getHealth());

        // Check that health increases by 4
        testCharacter.changeHealth(-2);
        testCharacter.setEquippedItem(apple);
        testCharacter.useEquipped();
        Assert.assertEquals(currentHealth + 4, testCharacter.getHealth());

        // Check that health increases by 6
        testCharacter.changeHealth(-4);
        testCharacter.setEquippedItem(berry);
        testCharacter.useEquipped();
        Assert.assertEquals(currentHealth + 6, testCharacter.getHealth());
    }

    @Test
    /**
     * Checks to see if the player is actually colliding with entities
     */
    public void MainCharacterCollisionTest() {
        GameManager gm = GameManager.get();
        World world = mock(World.class);
        gm.setWorld(world);

        world.addEntity(testCharacter);

        testCharacter.setPosition(123.f, 234.f, 0);

        HexVector old_pos = new HexVector(testCharacter.getPosition().getRow(), testCharacter.getPosition().getCol());

        world.addEntity(new Scout(old_pos.getRow() + 0.1f, old_pos.getCol() + 0.1f,
                0.1f, "Forest"));

        for (int i = 0; i < 100; ++i) {
            world.onTick(100);
        }

        assertFalse(testCharacter.getPosition().getRow() == old_pos.getRow()
                && testCharacter.getPosition().getCol() == old_pos.getCol());
    }


    /**
     * Test the mana restoration system works.
     */
    @Test
    public void testRestoreMana() {

        Assert.assertEquals(this.testCharacter.mana, 100);
        Assert.assertEquals(this.testCharacter.manaCD, 0);

        testCharacter.mana = 0;
        testCharacter.manaCD = testCharacter.totalManaCooldown;

        Assert.assertEquals(this.testCharacter.mana, 0);
        Assert.assertEquals(this.testCharacter.manaCD, testCharacter.totalManaCooldown);

        //Ensure 1 mana was added and manaCD was reset.
        testCharacter.restoreMana();
        Assert.assertEquals(this.testCharacter.mana, 1);
        Assert.assertEquals(this.testCharacter.manaCD, 0);
    }

    /**
     * Test the getHeath() method works.
     */
    @Test
    public void getHealthTest() {
        assertEquals(50, testCharacter.getHealth());

        testCharacter.changeHealth(-2);

        assertEquals(48, testCharacter.getHealth());
    }

    /**
     * Test the setDead() method works.
     */
    @Test
    public void setDeadTest() {
        testCharacter.setDead(false);
        assertFalse(testCharacter.isDead());

        testCharacter.changeHealth(-50);

        assertTrue(testCharacter.isDead());

        assertEquals(0, testCharacter.getHealth());
    }

    /**
     * Test the setDead() method works.
     */
    @Test
    public void getDeathsTest() {
        testCharacter.changeHealth(-50);
        assertTrue(testCharacter.isDead());

        assertEquals(1, testCharacter.getDeaths());

    }
/*
    *//**
     * Test the removeAllGold() method works.
     *//*
    @Test
    public void removeAllGoldTest() {
        assertEquals(0, testCharacter.getGoldPouchTotalValue());

        testCharacter.removeAllGold();

        assertEquals(0, testCharacter.getGoldPouchTotalValue());
    }

    *//**
     * Test the removeAllGold() method works.
     *//*
    @Test
    public void playerAddGoldTest() {
        assertEquals(0, testCharacter.getGoldPouchTotalValue());

        testCharacter.removeAllGold();

        assertEquals(0, testCharacter.getGoldPouchTotalValue());
    }*/

    /**
     * Test the isRecovering() method works.
     */
    @Test
    public void isRecoveringTest() {
        testCharacter.playerHurt(2);

        assertFalse(testCharacter.isRecovering());
    }

    /**
     * Test the setRecovering() method works.
     */
    @Test
    public void setRecoveringTest() {
        testCharacter.playerHurt(2);
        assertFalse(testCharacter.isRecovering());

        testCharacter.setRecovering(false);
        assertFalse(testCharacter.isRecovering());

        testCharacter.setRecovering(true);
        assertTrue(testCharacter.isRecovering());
    }

    /**
     * Test the pop up methods work.
     */
    @Test
    public void popUpTest() {
        GameMenuManager gameMenuManager = new GameMenuManager();
        testCharacter.setUpGUI();

        gameMenuManager.hideOpened();
        gameMenuManager.setPopUp("gameOverTable");

        assertEquals(testCharacter, gameMenuManager.getMainCharacter());
        assertEquals(gameMenuManager.getPopUp("gameOverTable"), gameMenuManager.getCurrentPopUp());
    }

    @After
    public void cleanup() {
        testCharacter = null;
    }
}
