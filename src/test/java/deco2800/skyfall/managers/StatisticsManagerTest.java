package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.resources.items.Stone;

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

    @Before
    /**
     * Set up all test variables
     */
    public void setUp() {
        testCharacter1 = new MainCharacter(4, 4,
                0.5f, "Side Piece", 10);
        testManager = new StatisticsManager(this.testCharacter1);
        testEnemy1 = new Enemy(1,1,"SpiderAnimation.png",
                1,100,1);
        testEnemy2 = new Enemy(2,2,"2800enemy.png",
                2,200,2);
        testEnemy3 = new Enemy(3,3,"EnemyStone.png",
                3,300,3);
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
     * Sets all test variables to null after testing
     */
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
        this.testManager.getCharacter().pickUpWeapon(sword);
        this.testManager.getCharacter().pickUpWeapon(sword);
        this.testManager.getCharacter().pickUpWeapon(sword);
        this.testManager.getCharacter().pickUpWeapon(spear);
        this.testManager.getCharacter().pickUpWeapon(spear);
        this.testManager.getCharacter().pickUpWeapon(bow);
        this.testManager.getCharacter().pickUpWeapon(bow);
        this.testManager.getCharacter().pickUpWeapon(axe);
        this.testManager.getCharacter().pickUpWeapon(axe);
        this.testManager.getCharacter().pickUpWeapon(axe);
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

    @Test
    /**
     * Test that deaths causes loss of level correctly
     */
    public void loseLevelTest() {
        this.testManager.getCharacter().changeLevel(5);

        Assert.assertEquals(testManager.getLevel(), 6);
        Assert.assertEquals(testManager.getHealth(), 60);

        this.testManager.getCharacter().changeHealth(-60);
        this.testManager.getCharacter().changeHealth(-60);
        this.testManager.getCharacter().changeHealth(-60);
        this.testManager.getCharacter().changeHealth(-60);
        this.testManager.getCharacter().changeHealth(-60);

        Assert.assertEquals(testManager.getDeaths(), 5);

        testManager.loseExperience();
        testManager.loseLevel();

        Assert.assertEquals(testManager.getLevel(), 5);

        Assert.assertEquals(testManager.getHealth(), 50);

    }

    @Test
    /**
     * Test that killed enemies are recorded correctly
     */
    public void killsTest() {
        this.recordKills();

        Assert.assertEquals(testManager.getAmountKilled(testEnemy1), 3);
        Assert.assertEquals(testManager.getAmountKilled(testEnemy2), 4);
        Assert.assertEquals(testManager.getAmountKilled(testEnemy3), 3);
        Assert.assertEquals(testManager.getKills(), 10);

        Assert.assertEquals(testManager.getMoney(), 0);
    }

    @Test
    /**
     * Test that experience is changing correctly due to different events
     */
    public void experienceLevelTest() {
        Assert.assertEquals(testManager.getExperienceCap(), 20);
        Assert.assertEquals(testManager.getLevel(), 1);
        Assert.assertEquals(testManager.getExperience(), 0);

        this.pickUpWeapons();
        this.recordKills();
        this.pickUpInventory();

        testManager.gainExperience();

//        Assert.assertEquals(testManager.getExperience(), 30);

        testManager.levelUp();

//        Assert.assertEquals(testManager.getLevel(), 2);
//        Assert.assertEquals(testManager.getExperience(), 10);
//        Assert.assertEquals(testManager.getExperienceCap(), 40);
//
//        Assert.assertEquals(testManager.getHealth(), 20);
    }
}
