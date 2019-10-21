package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.MainCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test enemy.
 */
public class EnemyTest {

    // An instance of a main character
    private MainCharacter testCharacter;

    // Enemies
    private Enemy testEnemy;
    private Enemy testDummyEnemy;

    // Strings
    private String biomeName = "Forest";

    /**
     * Set up for enemy tests.
     */
    @Before
    public void setUp() {
        // Set up main character
        MainCharacter.resetInstance();
        testCharacter = MainCharacter.getInstance();

        // Set up enemy, need the longer constructor to test toString(), equals() and hashcode()
        testEnemy = new Enemy(30f, 30f, Enemy.EnemyType.HEAVY,
            0.06f, biomeName, "enemyTexture");
        testEnemy.setHealth(10);
        testEnemy.setMainCharacter(testCharacter);

        testDummyEnemy = new Enemy(0f, 0f, Enemy.EnemyType.SCOUT,
                0.06f, biomeName, "dummyTexture");
    }

    @Test
    public void setterAndGetterTests() {

        testEnemy.setHealth(10);
        Assert.assertEquals(10, testEnemy.getHealth());

        testEnemy.setBiome("testBiome");
        Assert.assertEquals("testBiome", testEnemy.getBiome());

        testEnemy.setMainCharacter(testCharacter);
        Assert.assertEquals(testCharacter, testEnemy.getMainCharacter());

        testEnemy.setHurt(true);
        Assert.assertTrue(testEnemy.getHurt());

        Assert.assertTrue(testEnemy.canDealDamage());
        Assert.assertArrayEquals(new int[0], testEnemy.getResistanceAttributes());

        // Test movementDirections, will test other directions later
        Assert.assertEquals(Direction.NORTH, testEnemy.movementDirection(2f));
    }

    /**
     * Test enemy's ability to be hurt.
     * Related methods include:
     *  > takeDamage()
     *  > checkIfHurtEnded()
     */
    @Test
    public void enemyHurtTest() {
        testEnemy.takeDamage(3);
        // set hurt time to 0.
        Assert.assertEquals(0, testEnemy.getHurtTime());
        // set enemy's "isHurt" status to true.
        Assert.assertTrue(testEnemy.getHurt());
        // reduce enemy's health
        Assert.assertEquals(7, testEnemy.getHealth());
        // If hurt equals TRUE,
        // and updateAnimation() is called in onTick(),
        // AnimationRole changed from NULL to HURT.
        testEnemy.updateAnimation();
        Assert.assertEquals(AnimationRole.HURT, testEnemy.getCurrentState());

        // when hurt time is less than 340 (less than 2 seconds in the game)...
        testEnemy.checkIfHurtEnded();
        Assert.assertEquals(20, testEnemy.getHurtTime());
        Assert.assertTrue(testEnemy.getHurt());
        // After hurt animations (around 2 seconds)...
        testEnemy.setHurtTime(360);
        testEnemy.checkIfHurtEnded();
        // reset hurt time to 0.
        Assert.assertEquals(0, testEnemy.getHurtTime());
        // enemy recovered
        Assert.assertFalse(testEnemy.getHurt());
    }

    @Test
    public void enemyAttackTest() {

        testEnemy.getMainCharacter().setHurt(false);
        testEnemy.getMainCharacter().setRecovering(false);
        testEnemy.getMainCharacter().setDead(false);
        testCharacter = new MainCharacter(30f, 30f, 0.05f, "Main Piece", 50);
        testEnemy.setMainCharacter(testCharacter);
        testEnemy.setStrength(3);
        testEnemy.setChasingSpeed(3f);
        testEnemy.setAttackRange(100);
        testEnemy.attackAction();
        Assert.assertEquals(AnimationRole.ATTACK, testEnemy.getCurrentState());
        Assert.assertTrue(testEnemy.getMainCharacter().isHurt());
        Assert.assertEquals(47, testEnemy.getMainCharacter().getHealth());
        Assert.assertEquals(3f, testEnemy.getChasingSpeed(), 0);
        // reset hurt status
        testEnemy.setHurtTime(400);
        testEnemy.checkIfHurtEnded();
        // reset enemy keeps moving
        testEnemy.randomMoveAction();

        // if player is recovering, enemy cannot attack
        testEnemy.getMainCharacter().setHurt(false);
        testEnemy.getMainCharacter().setRecovering(true);
        testEnemy.getMainCharacter().setDead(false);
        testEnemy.attackAction();
        Assert.assertEquals(AnimationRole.MOVE, testEnemy.getCurrentState());
        Assert.assertFalse(testEnemy.getMainCharacter().isHurt());

        testEnemy.getMainCharacter().setHurt(true);
        testEnemy.getMainCharacter().setRecovering(false);
        testEnemy.getMainCharacter().setDead(false);
        testEnemy.attackAction();
        Assert.assertEquals(AnimationRole.MOVE, testEnemy.getCurrentState());
        Assert.assertTrue(testEnemy.getMainCharacter().isHurt());
        testEnemy.setHurtTime(400);
        testEnemy.checkIfHurtEnded();

        testEnemy.getMainCharacter().setHurt(false);
        testEnemy.getMainCharacter().setRecovering(false);
        testEnemy.getMainCharacter().setDead(true);
        testEnemy.attackAction();
        Assert.assertEquals(AnimationRole.ATTACK, testEnemy.getCurrentState());
        Assert.assertTrue(testEnemy.getMainCharacter().isHurt());
        testCharacter = new MainCharacter(0f, 0f, 0.05f, "Main Piece", 50);
        testEnemy.setMainCharacter(testCharacter);
        testEnemy.randomMoveAction();

        testEnemy.getMainCharacter().setHurt(false);
        testEnemy.getMainCharacter().setRecovering(false);
        testEnemy.getMainCharacter().setDead(false);
        testEnemy.attackAction();
        Assert.assertEquals(AnimationRole.MOVE, testEnemy.getCurrentState());
    }

    @Test
    public void setValues() {
        float row = 30f;
        float col = 30f;
        float scale = 0.06f;
        Enemy test = new Enemy(row, col, Enemy.EnemyType.HEAVY,
                scale, biomeName, "enemyTexture");
        float scaling = 1.5f;
        int health = 10;
        int damage = 5;
        float range = 1.5f;
        float wSpeed = 2.0f;
        float cSpeed = 3.0f;
        test.setValues(scaling, health, damage, range, wSpeed, cSpeed);
        Assert.assertEquals(scaling * health, test.getMaxHealth(), 0.01);
        Assert.assertEquals(scaling * health, test.getHealth(), 0.01);
        Assert.assertEquals((int) (scaling * damage), test.getStrength(), 0.01);
        Assert.assertEquals(scaling * range, test.getAttackRange(), 0.01);
        Assert.assertEquals(scaling * wSpeed, test.getWalkingSpeed(), 0.01);
        Assert.assertEquals(scaling * cSpeed, test.getChasingSpeed(), 0.01);

        scale = (int) scale + 1;

        Heavy heavy1 = new Heavy(row, col, scale, biomeName);
        Heavy heavy2 = new Heavy(row, col, scale);
        Assert.assertEquals(heavy1.getRow(), row, 0.01);
        Assert.assertEquals(heavy1.getCol(), col, 0.01);
        Assert.assertEquals(heavy1.getScale(), scale, 0.01);
        Assert.assertEquals(heavy2.getRow(), row, 0.01);
        Assert.assertEquals(heavy2.getCol(), col, 0.01);
        Assert.assertEquals(heavy2.getScale(), scale, 0.01);

        Medium medium1 = new Medium(row, col, scale, biomeName);
        Medium medium2 = new Medium(row, col, scale);
        Assert.assertEquals(medium1.getRow(), row, 0.01);
        Assert.assertEquals(medium1.getCol(), col, 0.01);
        Assert.assertEquals(medium1.getScale(), scale, 0.01);
        Assert.assertEquals(medium2.getRow(), row, 0.01);
        Assert.assertEquals(medium2.getCol(), col, 0.01);
        Assert.assertEquals(medium2.getScale(), scale, 0.01);

        Scout scout1 = new Scout(row, col, scale, biomeName);
        Scout scout2 = new Scout(row, col, scale);
        Assert.assertEquals(scout1.getRow(), row, 0.01);
        Assert.assertEquals(scout1.getCol(), col, 0.01);
        Assert.assertEquals(scout1.getScale(), scale, 0.01);
        Assert.assertEquals(scout2.getRow(), row, 0.01);
        Assert.assertEquals(scout2.getCol(), col, 0.01);
        Assert.assertEquals(scout2.getScale(), scale, 0.01);
    }

    /**
     * Originally intended to test updateAnimation(),
     * but since it requires a set up of AnimationManager,
     * there will only be setter getter tests for now.
     */
    @Test
    public void setAndGetEnemyAnimationTest() {
        // If enemy dies, set animation state to DEAD.
        testEnemy.setCurrentState(AnimationRole.DEAD);
        Assert.assertEquals(AnimationRole.DEAD, testEnemy.getCurrentState());

        // If enemy hurts, set animation state to HURT.
        testEnemy.setCurrentState(AnimationRole.HURT);
        Assert.assertEquals(AnimationRole.HURT, testEnemy.getCurrentState());

        // If enemy moves, set animation state to MOVE.
        testEnemy.setCurrentState(AnimationRole.MOVE);
        Assert.assertEquals(AnimationRole.MOVE, testEnemy.getCurrentState());

        // If enemy attacks, set animation state to ATTACK.
        testEnemy.setCurrentState(AnimationRole.HURT);
        Assert.assertEquals(AnimationRole.HURT, testEnemy.getCurrentState());
    }

    /**
     * Test whether the sound files for the enemies are correct.
     */
    @Test
    public void setAndGetSoundTest() {
        testEnemy.configureSounds();
        Assert.assertEquals("pick up", testEnemy.getChaseSound());
        Assert.assertEquals("fist_attack", testEnemy.getAttackSound());
        Assert.assertEquals("died", testEnemy.getDeadSound());
    }

    @Test
    public void testToString() {
        String testString = "HEAVY at (30, 30) Forest biome";
        Assert.assertEquals(testString, testEnemy.toString());
    }

    @Test
    public void testEquals() {
        // Not equal due to different hashcode.
        Assert.assertFalse(testEnemy.equals(testDummyEnemy));

        // Not equal due to different instance
        Assert.assertFalse(testEnemy.equals(testCharacter));

        // Equals due to same instance and same hashcode
        testDummyEnemy =  new Enemy(30f, 30f, Enemy.EnemyType.HEAVY,
                0.06f, biomeName, "enemyTexture");
        Assert.assertTrue(testEnemy.equals(testDummyEnemy));
    }
}