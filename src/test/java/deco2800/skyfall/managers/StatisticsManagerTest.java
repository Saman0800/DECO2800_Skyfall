package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.enemies.AbstractEnemy;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.enemies.Treeman;
import deco2800.skyfall.entities.weapons.*;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.worlds.Tile;

import org.junit.*;

import java.lang.reflect.Field;

public class StatisticsManagerTest {

    // MainCharacter used for testing
    private MainCharacter testCharacter1;

    // StatisticsManager used for testing
    private StatisticsManager testManager;

    // Enemies used for testing
    private AbstractEnemy testEnemy1;
    private AbstractEnemy testEnemy2;
    private AbstractEnemy testEnemy3;

    // Weapons being used for testing
    private Weapon sword;
    private Weapon spear;
    private Weapon bow;
    private Weapon axe;

    @Before
    /**
     * Set up all test variables
     */
    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        testCharacter1 = MainCharacter.getInstance(4, 4, 0.5f, "Side Piece", 60);
        // Reset the level, heath and deaths.
        testCharacter1.changeLevel(1 - testCharacter1.getLevel());
        testCharacter1.changeHealth(10 - testCharacter1.getHealth());
        // There is no other way to modify this.
        Field deathsField = Peon.class.getDeclaredField("deaths");
        deathsField.setAccessible(true);
        deathsField.setInt(testCharacter1, 0);

        testManager = new StatisticsManager(this.testCharacter1);
        testEnemy1 = new Treeman(1,1, testCharacter1);
        testEnemy2 = new Treeman(2,2, testCharacter1);
        testEnemy3 = new Treeman(3,3, testCharacter1);
        sword = new Sword(new Tile(null, 0, 0), false);
        spear = new Spear(new Tile(null, 0, 0), false);
        bow = new Bow(new Tile(null, 0, 0), false);
        axe = new Axe(new Tile(null, 0, 0), false);
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

        Assert.assertEquals(testManager.getMoney(), 100);
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
        testManager.levelUp();
    }
}
