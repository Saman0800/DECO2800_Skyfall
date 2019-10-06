package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.weapons.*;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.worlds.Tile;

import org.junit.*;

public class StatisticsManagerTest {

    // MainCharacter used for testing
    private MainCharacter testCharacter1;

    // StatisticsManager used for testing
    private StatisticsManager testManager;

    // Enemies used for testing
    private Enemy testEnemy1;
    private Enemy testEnemy2;
    private Enemy testEnemy3;

    // Weapons being used for testing
    private Weapon sword;
    private Weapon spear;
    private Weapon bow;
    private Weapon axe;

    /**
     * Set up all test variables
     */
    @Before
    public void setUp() {
        MainCharacter.resetInstance();
        testCharacter1 = MainCharacter.getInstance(4, 4, 0.5f, "Side Piece", 50);

        testManager = new StatisticsManager(this.testCharacter1);
        testEnemy1 = new Enemy(1,1);
        testEnemy2 = new Enemy(2,2);
        testEnemy3 = new Enemy(3,3);
        sword = new Sword(new Tile(null, 0, 0), false);
        spear = new Spear(new Tile(null, 0, 0), false);
        bow = new Bow(new Tile(null, 0, 0), false);
        axe = new Axe(new Tile(null, 0, 0), false);
    }

    /**
     * Sets all test variables to null after testing
     */
    @After
    public void tearDown() {
        testCharacter1 = null;
        testManager = null;
        testEnemy1 = null;
        testEnemy2 = null;
        testEnemy3 = null;
        sword = null;
        spear = null;
        bow = null;
        axe = null;
    }

    /**
     * Private helper method to be called to record killed enemies
     */
    private void recordKills() {
        testManager.recordKill(testEnemy1);
        testManager.recordKill(testEnemy1);
        testManager.recordKill(testEnemy1);
        testManager.recordKill(testEnemy2);
        testManager.recordKill(testEnemy2);
        testManager.recordKill(testEnemy2);
        testManager.recordKill(testEnemy2);
        testManager.recordKill(testEnemy3);
        testManager.recordKill(testEnemy3);
        testManager.recordKill(testEnemy3);
    }

    /**
     * Private helper method to be called to record picked up weapons
     */
    private void pickUpWeapons() {
        this.testManager.getCharacter().pickUpInventory(sword);
        this.testManager.getCharacter().pickUpInventory(sword);
        this.testManager.getCharacter().pickUpInventory(sword);
        this.testManager.getCharacter().pickUpInventory(spear);
        this.testManager.getCharacter().pickUpInventory(spear);
        this.testManager.getCharacter().pickUpInventory(bow);
        this.testManager.getCharacter().pickUpInventory(bow);
        this.testManager.getCharacter().pickUpInventory(axe);
        this.testManager.getCharacter().pickUpInventory(axe);
        this.testManager.getCharacter().pickUpInventory(axe);
    }

    /**
     * Private helper method to be called to record picked up inventories
     */
    private void pickUpInventory() {
        this.testManager.getCharacter().pickUpInventory(new Aloe_Vera());
        this.testManager.getCharacter().pickUpInventory(new Apple());
        this.testManager.getCharacter().pickUpInventory(new Berry());
        this.testManager.getCharacter().pickUpInventory(new Metal());
        this.testManager.getCharacter().pickUpInventory(new Metal());
        this.testManager.getCharacter().pickUpInventory(new Sand());
        this.testManager.getCharacter().pickUpInventory(new Stone());
        this.testManager.getCharacter().pickUpInventory(new Vine());
        this.testManager.getCharacter().pickUpInventory(new Wood());
        this.testManager.getCharacter().pickUpInventory(new Aloe_Vera());
    }

    /**
     * Test that deaths causes loss of level correctly
     */
    @Test
    public void loseLevelTest() {
        this.testManager.getCharacter().changeLevel(5);

        Assert.assertEquals(6, testManager.getLevel());
        Assert.assertEquals(50, testManager.getHealth());


        this.testManager.getCharacter().changeHealth(-50);
        Assert.assertTrue(testManager.getCharacter().isDead());

        this.testManager.getCharacter().changeHealth(-50);
        this.testManager.getCharacter().changeHealth(-50);
        this.testManager.getCharacter().changeHealth(-50);
        this.testManager.getCharacter().changeHealth(-50);

        Assert.assertEquals(5, testManager.getDeaths());

        testManager.loseExperience();
        testManager.loseLevel();

        Assert.assertEquals(5, testManager.getLevel());
        Assert.assertEquals(0, testManager.getHealth());

    }

    /**
     * Test that killed enemies are recorded correctly
     */
    @Test
    public void killsTest() {
        this.recordKills();

        Assert.assertEquals(3, testManager.getAmountKilled(testEnemy1));
        Assert.assertEquals(4, testManager.getAmountKilled(testEnemy2));
        Assert.assertEquals(3, testManager.getAmountKilled(testEnemy3));
        Assert.assertEquals(10, testManager.getKills());

        Assert.assertEquals(100, testManager.getMoney());
    }

    /**
     * Test that experience is changing correctly due to different events
     */
    @Test
    public void experienceLevelTest() {
        Assert.assertEquals(20, testManager.getExperienceCap());
        Assert.assertEquals(1, testManager.getLevel());
        Assert.assertEquals(0, testManager.getExperience());

        this.pickUpWeapons();
        this.recordKills();
        this.pickUpInventory();

        testManager.gainExperience();
        testManager.levelUp();
    }
}
