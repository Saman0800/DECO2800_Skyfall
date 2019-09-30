package deco2800.skyfall.entities;

import deco2800.skyfall.util.HexVector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Enemy class test suite.
 */
public class EnemyTest {

    //Enemy instance being used for testing.
    private Enemy enemy;

    /**
     * Create the enemy instance.
     * @throws Exception if new Enemy cannot be constructed.
     */
    @Before
    public void setUp() throws Exception {
        enemy = new Enemy(1,1,"enemy",1,100,1);
    }

    /**
     * Test the enemy position.
     */
    @Test
    public void testPosition() {
        assertThat("", enemy.getCol(), is(equalTo(1f)));
        assertThat("", enemy.getRow(), is(equalTo(1f)));
    }

    /**
     * Test that the health is set.
     */
    @Test
    public void testHealthSet() {
        assertEquals(enemy.getHealth(),100);
    }

    /**
     * Test that health can be set.
     */
    @Test
    public void testSetHealth() {
        assertEquals(enemy.getHealth(),100);
        enemy.setHealth(50);
        assertEquals(enemy.getHealth(),50);
        enemy.setHealth(-1);
        assertEquals(enemy.getHealth(),-1);
        enemy.setHealth(100);
        assertEquals(enemy.getHealth(),100);
    }

    /**
     * Test the armour is set.
     */
    @Test
    public void testArmour() {
        assertEquals(enemy.getArmour(),1);
    }

    /**
     * Test that the damage is set.
     */
    @Test
    public void testDamageSet() {
        assertEquals(enemy.getDamage(),1);
    }

    /**
     * Test that the damage can be dealt.
     */
    @Test
    public void testCanDealDamage() {
        assertTrue(enemy.canDealDamage());
    }

    /**
     * Test dealing damage with a mock enemy object.
     */
    @Test
    public void testDealDamage() {

        MainCharacter testMainCharcter = mock(MainCharacter.class);
        Projectile testProjectile = mock(Projectile.class);

        //Return true that the mock enemy can deal damage, and that it deals 1 damage.
        // when(testEnemy.canDealDamage()).thenReturn(true);
        when(testMainCharcter.getHealth()).thenReturn(1);

        doCallRealMethod().when(testMainCharcter).attack(new HexVector(1, 1));
        //testMainCharcter.attack(new HexVector(1, 1));
        testProjectile.handleCollision(enemy);

        //Ensure 1 damage has been dealt.
        // assertEquals(enemy.getHealth(),99);
    }

    /**
     * Test that the damage can be taken from the enemy's health.
     */
    @Test
    public void testTakeDamage() {
        enemy.takeDamage(1);
        assertEquals(enemy.getHealth(),99);
    }

    /**
     * Test the resistance attributes aren't initialized yet.
     */
    @Test
    public void testResistanceAttributes() {
        assertNull(enemy.getResistanceAttributes());
    }
    /**
     * Test the status indicators aren't initialized yet.
     */
    @Test
    public void testStatusIndicators() {
        assertNull(enemy.getStatusIndicators());
    }


}