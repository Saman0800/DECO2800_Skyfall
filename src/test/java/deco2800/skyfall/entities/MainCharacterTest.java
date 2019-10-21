package deco2800.skyfall.entities;

import static deco2800.skyfall.buildings.BuildingType.CABIN;
import static deco2800.skyfall.buildings.BuildingType.CASTLE;
import static deco2800.skyfall.buildings.BuildingType.SAFEHOUSE;
import static deco2800.skyfall.buildings.BuildingType.TOWNCENTRE;
import static deco2800.skyfall.buildings.BuildingType.WATCHTOWER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.badlogic.gdx.Input;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.entities.enemies.Scout;
import deco2800.skyfall.entities.spells.Spell;
import deco2800.skyfall.entities.spells.SpellType;
import deco2800.skyfall.entities.vehicle.Bike;
import deco2800.skyfall.entities.vehicle.SandCar;
import deco2800.skyfall.entities.weapons.Bow;
import deco2800.skyfall.entities.weapons.EmptyItem;
import deco2800.skyfall.entities.weapons.Spear;
import deco2800.skyfall.entities.weapons.Sword;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.AloeVera;
import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.Berry;
import deco2800.skyfall.resources.items.Hatchet;
import deco2800.skyfall.resources.items.Metal;
import deco2800.skyfall.resources.items.PickAxe;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.Wood;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class,
    GameManager.class})
public class MainCharacterTest {

    private MainCharacter testCharacter;

    private Tile testTile;
    private GoldPiece testGoldPiece;
    private InventoryManager inventoryManager;
    private World w = null;
    private List<BuildingType> craftedBuildingsTest;


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

        inventoryManager = GameManager.getManagerFromInstance(InventoryManager.class);
        // Reset the inventory contents.
        inventoryManager.setContents(new HashMap<>(new InventoryManager().getContents()));

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
     * Sets up all variables to be null after testing
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
        assertTrue(testCharacter.isTexChanging());
        testCharacter.setHurt(true);
        assertTrue(testCharacter.isHurt());
        testCharacter.setHurt(true);
        assertTrue(testCharacter.isHurt());

        testCharacter.changeTexture("mainCharacter");
        assertEquals("mainCharacter", testCharacter.getTexture());

        Assert.assertEquals(testCharacter.getName(),
            "Main Piece");
        testCharacter.setName("Side Piece");
        Assert.assertEquals(testCharacter.getName(), "Side Piece");

        Assert.assertFalse(testCharacter.isDead());
        Assert.assertEquals(testCharacter.getHealth(), 50);
        testCharacter.changeHealth(5);
        Assert.assertEquals(testCharacter.getHealth(), 55);
        testCharacter.changeHealth(-55);
        Assert.assertEquals(testCharacter.getHealth(), 0);
        Assert.assertEquals(testCharacter.getDeaths(), 1);
        assertTrue(testCharacter.isDead());

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
    /**
     * Test main character is interacting correctly with basic inventory action
     */
    @Test
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
        assertTrue(testCharacter.isHurt());
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
        assertTrue(testCharacter.isRecovering());
        // reset hurt time.
        Assert.assertEquals(0, testCharacter.getHurtTime());
    }


    @Test
    @Ignore
    public void testFireProjectile() {
        Sword mockSword = mock(Sword.class);
        when(mockSword.getName()).thenReturn("sword");
        Projectile projectile = mock(Projectile.class);

        testCharacter.equippedItem = mockSword;
        testCharacter.defaultProjectile = projectile;
        testCharacter.attack(new HexVector(0, 0));

        //Ensure the projectile has been added.
        assertTrue(GameManager.get().getWorld().getEntities().contains(projectile));

        //Test other branch.
        testCharacter.spellSelected = SpellType.FLAME_WALL;
        testCharacter.attack(new HexVector(0, 0));

        assertTrue(GameManager.get().getWorld().getEntities().stream().anyMatch(e -> e instanceof Spell));
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
        assertTrue(testCharacter.getCollidable());
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
     * Test whether the animation role is updated when method is called.
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
     * Test the notifyKeyDown and notifyKeyUp methods for handling player movement
     */
    @Test
    public void movementKeyBoardTest() {
        int currentXInput = 0;
        int currentYInput = 0;
        GameManager.setPaused(false);

        // Test press W
        testCharacter.notifyKeyDown(Input.Keys.W);
        currentYInput += 1;
        assertEquals(currentYInput, testCharacter.getYInput());

        // Test release W
        testCharacter.notifyKeyUp(Input.Keys.W);
        currentYInput -= 1;
        assertEquals(currentYInput, testCharacter.getYInput());

        // Test press S
        testCharacter.notifyKeyDown(Input.Keys.S);
        currentYInput -= 1;
        assertEquals(currentYInput, testCharacter.getYInput());

        // Test release S
        testCharacter.notifyKeyUp(Input.Keys.S);
        currentYInput += 1;
        assertEquals(currentYInput, testCharacter.getYInput());

        // Test press D
        testCharacter.notifyKeyDown(Input.Keys.D);
        currentXInput += 1;
        assertEquals(currentXInput, testCharacter.getXInput());

        // Test release D
        testCharacter.notifyKeyUp(Input.Keys.D);
        currentXInput -= 1;
        assertEquals(currentXInput, testCharacter.getXInput());

        // Test press A
        testCharacter.notifyKeyDown(Input.Keys.A);
        currentXInput -= 1;
        assertEquals(currentXInput, testCharacter.getXInput());

        // Test release A
        testCharacter.notifyKeyUp(Input.Keys.A);
        currentXInput += 1;
        assertEquals(currentXInput, testCharacter.getXInput());

        testCharacter.notifyKeyDown(Input.Keys.SHIFT_LEFT);
        assertTrue(testCharacter.getIsSprinting());
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
    public void addGoldTest() {
        // create a new gold piece with a value of 5
        GoldPiece g5 = new GoldPiece(5);
        Integer count = 1;

        // adding one gold piece at a time
        testCharacter.addGold(g5, count);
        // ensure the gold piece is added to the pouch
        assertTrue(testCharacter.getGoldPouch().containsKey(5));
        // ensure the gold piece is only added once
        Assert.assertEquals((int) testCharacter.getGoldPouch().get(5), 1);
        // ensure that total pouch value has been calculated correctly
        assertEquals(5, (int) testCharacter.getGoldPouchTotalValue());

        testCharacter.addGold(g5, count);
        Assert.assertEquals((int) testCharacter.getGoldPouch().get(5), 2);
        assertEquals(10, (int) testCharacter.getGoldPouchTotalValue());

        // create a new gold piece with a value of 50
        GoldPiece g50 = new GoldPiece(50);

        testCharacter.addGold(g50, count);
        // ensure the gold piece is added to the pouch
        assertTrue(testCharacter.getGoldPouch().containsKey(50));
        // ensure the gold piece is only added once
        Assert.assertEquals((int) testCharacter.getGoldPouch().get(50), 1);

        // ensure that the pouch total value is correct
        assertEquals(60, (int) testCharacter.getGoldPouchTotalValue());

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
        assertEquals(180, (int) testCharacter.getGoldPouchTotalValue());

        // remove a piece of gold from the pouch
        testCharacter.removeGold(5);

        // ensure that the necessary adjustments have been made
        assertEquals(175, (int) testCharacter.getGoldPouchTotalValue());

        // remove a piece of gold from the pouch which is the last piece
        testCharacter.removeGold(10);

        // ensure that the necessary adjustments have been made
        assertEquals(165, (int) testCharacter.getGoldPouchTotalValue());


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
        assertTrue(testCharacter.getGoldPouch().containsKey(5));
        assertTrue(testCharacter.getGoldPouch().containsKey(10));
        assertTrue(testCharacter.getGoldPouch().containsKey(50));
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
        assertEquals(180, (int) testCharacter.getGoldPouchTotalValue());
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
     * Tests to ensure that the closest gold piece is added to the gold pouch Tested here instead of World to allow for
     * ease of testing.
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
        assertTrue(testCharacter.getGoldPouch().containsKey(5));
        Assert.assertEquals(5, (int) testCharacter.getGoldPouchTotalValue());

    }

    @Test
    public void checkRequiredResourcesTest() {
        mockGM.setWorld(w);
        w.addEntity(testCharacter);
        testCharacter.setCol(1f);
        testCharacter.setRow(1f);

        Assert.assertFalse(testCharacter.checkRequiredResources(new Bow()));

        for (int i = 0; i < 100; i++) {
            testCharacter.getInventoryManager().add(new Wood());
            testCharacter.getInventoryManager().add(new Stone());
            testCharacter.getInventoryManager().add(new Metal());
        }

        assertTrue(testCharacter.checkRequiredResources(new Bow()));
    }

    @Test
    public void createItemTest() {

        mockGM.setWorld(w);
        w.addEntity(testCharacter);
        testCharacter.setCol(1f);
        testCharacter.setRow(1f);

        int m;

        for (m = 0; m < 1000; m++) {
            testCharacter.getInventoryManager().add(new Wood());
            testCharacter.getInventoryManager().add(new Stone());
            testCharacter.getInventoryManager().add(new Metal());
        }

        testCharacter.createItem(new Hatchet());
        testCharacter.createItem(new PickAxe());
        testCharacter.createItem(new Sword());
        testCharacter.createItem(new Spear());
        testCharacter.createItem(new Bow());
        testCharacter.createItem(BuildingType.CABIN);
        testCharacter.createItem(BuildingType.TOWNCENTRE);
        testCharacter.createItem(BuildingType.SAFEHOUSE);
        testCharacter.createItem(BuildingType.WATCHTOWER);
        testCharacter.createItem(BuildingType.CASTLE);


        assertTrue(testCharacter.getCraftedBuildings().contains(CABIN));
        assertTrue(testCharacter.getCraftedBuildings().contains(TOWNCENTRE));
        assertTrue(testCharacter.getCraftedBuildings().contains(SAFEHOUSE));
        assertTrue(testCharacter.getCraftedBuildings().contains(WATCHTOWER));
        assertTrue(testCharacter.getCraftedBuildings().contains(CASTLE));

        Assert.assertEquals(2,
            testCharacter.getInventoryManager().getAmount("Hatchet"));

        Assert.assertEquals(2,
            testCharacter.getInventoryManager().getAmount("Pick Axe"));

        Assert.assertEquals(1,
            testCharacter.getInventoryManager().getAmount("sword"));

        Assert.assertEquals(1,
            testCharacter.getInventoryManager().getAmount("spear"));

        Assert.assertEquals(1,
            testCharacter.getInventoryManager().getAmount("bow"));

        System.out.println(testCharacter.getInventoryManager().toString());

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
        AloeVera alo = new AloeVera();
        Apple apple = new Apple();
        Berry berry = new Berry();

        testCharacter.getInventoryManager().add(alo);
        testCharacter.getInventoryManager().add(apple);
        testCharacter.getInventoryManager().add(berry);

        testCharacter.changeHealth(-8);

        int currentHealth = testCharacter.getHealth();

        // Check that health increases by 2
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

    //Test the removeAllGold() method works.

    @Test
    public void removeAllGoldTest() {
        assertTrue(testCharacter.getGoldPouchTotalValue().equals(0));

        testCharacter.removeAllGold();

        assertTrue(testCharacter.getGoldPouchTotalValue().equals(0));
    }

    /**
     * Test the removeAllGold() method works.
     */
    @Test
    public void playerAddGoldTest() {
        assertTrue(testCharacter.getGoldPouchTotalValue().equals(0));

        testCharacter.removeAllGold();

        assertTrue(testCharacter.getGoldPouchTotalValue().equals(0));
    }

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

    /**
     * Test notifyKeyDown method Except the movement code as it is tested seperately
     */
    @Test
    public void notifyKeyDownTest() {
        testCharacter.notifyKeyDown(Input.Keys.Z);
        assertEquals(SpellType.FLAME_WALL, testCharacter.spellSelected);

        testCharacter.notifyKeyDown(Input.Keys.X);
        assertEquals(SpellType.SHIELD, testCharacter.spellSelected);

        testCharacter.notifyKeyDown(Input.Keys.C);
        assertEquals(SpellType.TORNADO, testCharacter.spellSelected);
    }

    /**
     * Tests the processMovement method for main character and subsequently called methods
     * Rapidly changing the player direction should trigger the preventSliding method allowing
     * it to be tested
     * <p>
     * When process each movement direction 30 iterations are used so that movement can be processed
     * for 1 second
     * <p>
     * The time step setting for processing movement are the same as in the actual game
     *
     * During testing +0.1 needs to be added to the max speed due to rounding/numerical errors
     *
     * 2 is used for all deltas to account for numerical/rounding errors
     */
    @Test
    public void processMovementTest() {
        List<Float> velHistory;

        // Stationary
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertEquals(0, velHistory.get(0), 2);
        assertEquals(0, velHistory.get(1), 2);
        assertEquals(0, velHistory.get(2), 2);
        assertEquals("East", testCharacter.getPlayerDirectionCardinal());

        // North-West movement
        testCharacter.notifyKeyDown(Input.Keys.W);
        testCharacter.notifyKeyDown(Input.Keys.A);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertTrue(velHistory.get(0) < 0);
        assertTrue(velHistory.get(1) > 0);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("North-West", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.W);
        testCharacter.notifyKeyUp(Input.Keys.A);

        // North movement
        testCharacter.notifyKeyDown(Input.Keys.W);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertEquals(0, velHistory.get(0), 2);
        assertTrue(velHistory.get(1) > 0);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("North", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.W);

        // South-West movement
        testCharacter.notifyKeyDown(Input.Keys.S);
        testCharacter.notifyKeyDown(Input.Keys.A);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertTrue(velHistory.get(0) < 0);
        assertTrue(velHistory.get(1) < 0);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("South-West", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.S);
        testCharacter.notifyKeyUp(Input.Keys.A);

        // East movement
        testCharacter.notifyKeyDown(Input.Keys.D);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertTrue(velHistory.get(0) > 0);
        assertEquals(0, velHistory.get(1), 2);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("East", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.D);

        // North-East movement
        testCharacter.notifyKeyDown(Input.Keys.W);
        testCharacter.notifyKeyDown(Input.Keys.D);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertTrue(velHistory.get(0) > 0);
        assertTrue(velHistory.get(1) > 0);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("North-East", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.W);
        testCharacter.notifyKeyUp(Input.Keys.D);

        // South movement
        testCharacter.notifyKeyDown(Input.Keys.S);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertEquals(0, velHistory.get(0), 2);
        assertTrue(velHistory.get(1) < 0);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("South", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.S);

        // South-East movement
        testCharacter.notifyKeyDown(Input.Keys.S);
        testCharacter.notifyKeyDown(Input.Keys.D);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertTrue(velHistory.get(0) > 0);
        assertTrue(velHistory.get(1) < 0);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("South-East", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.S);
        testCharacter.notifyKeyUp(Input.Keys.D);

        // West movement
        testCharacter.notifyKeyDown(Input.Keys.A);
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertTrue(velHistory.get(0) < 0);
        assertEquals(0, velHistory.get(1), 10);
        assertTrue(velHistory.get(2) > 0 && velHistory.get(2) <= testCharacter.getMaxSpeed() + 0.1);
        assertEquals("West", testCharacter.getPlayerDirectionCardinal());
        testCharacter.notifyKeyUp(Input.Keys.A);

        // Stationary
        for (int i = 0; i < 30; ++i) {
            testCharacter.processMovement();
            testCharacter.getBody().getWorld().step(1 / 30f, 6, 2);
        }
        velHistory = testCharacter.getVelocity();
        assertEquals(0, velHistory.get(0), 2);
        assertEquals(0, velHistory.get(1), 2);
        assertEquals(0, velHistory.get(2), 2);
    }

    @After
    public void cleanup() {
        testCharacter = null;
    }

    @Test
    public void enterVehicleTest() {
        testCharacter.enterVehicle("Camel");
        assertEquals(0.1f, testCharacter.getAcceleration(), 0.000001);
        assertEquals(0.8f, testCharacter.getMaxSpeed(), 0.000001);
        testCharacter.enterVehicle("Dragon");
        assertEquals(0.125f, testCharacter.getAcceleration(), 0.000001);
        assertEquals(1f, testCharacter.getMaxSpeed(), 0.000001);
        testCharacter.enterVehicle("Boat");
        assertEquals(0.01f, testCharacter.getAcceleration(), 0.000001);
        assertEquals(0.5f, testCharacter.getMaxSpeed(), 0.000001);
        testCharacter.enterVehicle("Invalid");
        assertEquals(0.03f, testCharacter.getAcceleration(), 0.00001);
        assertEquals(0.6f, testCharacter.getMaxSpeed(), 0.00001);
    }

    @Test
    public void vehicleToUseTest() {
        testCharacter.setOnVehicle(true);
        testCharacter.setVehicleType("bike");
        testCharacter.vehicleToUse();
        assertFalse(testCharacter.isOnVehicle());
        assertEquals(25.f, testCharacter.getAcceleration(), 0.0001);
        assertEquals(13f, testCharacter.getMaxSpeed(), 0.0001);

        testCharacter.setOnVehicle(true);
        testCharacter.setVehicleType("sand_car");
        testCharacter.vehicleToUse();
        assertFalse(testCharacter.isOnVehicle());
        assertEquals(25.f, testCharacter.getAcceleration(), 0.0001);
        assertEquals(13f, testCharacter.getMaxSpeed(), 0.0001);
    }

    @Test
    public void notOnVehicleTest() {
        List<AbstractEntity> entities = new ArrayList<>();
        entities.add(new Bike(0, 0, testCharacter));
        World world = mock(World.class);
        when(mockGM.getWorld()).thenReturn(world);
        world.addEntity(testCharacter);

        when(world.getEntities()).thenReturn(entities);

        testCharacter.notOnVehicle();
        assertTrue(testCharacter.isOnVehicle());
        assertEquals(AnimationRole.NULL, testCharacter.getCurrentState());
        assertEquals(10.f, testCharacter.getAcceleration(), 0.00001);
        assertEquals(5.f, testCharacter.getMaxSpeed(), 0.00001);
        assertEquals("bike", testCharacter.getVehicleType());



        entities = new ArrayList<>();
        entities.add(new SandCar(0, 0, testCharacter));
        world = mock(World.class);
        when(mockGM.getWorld()).thenReturn(world);
        world.addEntity(testCharacter);

        when(world.getEntities()).thenReturn(entities);

        testCharacter.notOnVehicle();
        assertTrue(testCharacter.isOnVehicle());
        assertEquals(AnimationRole.NULL, testCharacter.getCurrentState());
        assertEquals(10.f, testCharacter.getAcceleration(), 0.00001);
        assertEquals(5.f, testCharacter.getMaxSpeed(), 0.00001);
        assertEquals("sand_car", testCharacter.getVehicleType());
    }

    @Test
    public void getTileTest() {
        assertEquals(new HexVector(0, 0), testCharacter.getTile(0, 0).getCoordinates());
        assertEquals(new HexVector(0, 0), testCharacter.getTile(0.4f, 0.4f).getCoordinates());
        assertEquals(new HexVector(1, 0.5f), testCharacter.getTile(0.6f, 0.4f).getCoordinates());
        assertEquals(new HexVector(1, 1.5f), testCharacter.getTile(0.6f, 0.6f).getCoordinates());
    }
}
