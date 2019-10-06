package deco2800.skyfall.entities;

import deco2800.skyfall.util.HexVector;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Projectile test suite.
 */
public class ProjectileTest  {

    /**
     * Create a new Projectile.
     */
    private Projectile projectile = new Projectile(new HexVector(),"slash",
            "projectileTest",1,1,1,1,1);


    /**
     * Test the correct texture is set on the projectile.
     */
    @Test
    public void testProjectileSet() {
        assertThat("", projectile.getTexture(), is(equalTo("slash")));
    }

    /**
     * Test the projectile damage is correctly set.
     */
    @Test
    public void testProjectileDamage() {
        assertEquals(projectile.getDamage(),1);
    }

    /**
     * Test the projectile range is correctly set.
     */
    @Test
    public void testProjectileRange() {
        assertEquals(projectile.getRange(),1);
    }

    /**
     * Test the position of the projectile is correct.
     */
    @Test
    public void testProjectilePosition() {
        System.out.println(projectile.getCol());
        System.out.println(projectile.getRow());

        assertThat("", projectile.getCol(), is(equalTo(0.29289323f)));
        assertThat("", projectile.getRow(), is(equalTo(0.29289323f)));
    }

    /**
     * Test the onTick method of the projectile.
     */
    @Test
    public void testOnTick() {
        assertThat("", projectile.ticksAliveFor, is(equalTo(0L)));
        projectile.onTick(0);
        assertThat("", projectile.ticksAliveFor, is(equalTo(1L)));
        projectile.onTick(99999);
        assertThat("", projectile.ticksAliveFor, is(equalTo(2L)));
        projectile.onTick(-1);
        assertThat("", projectile.ticksAliveFor, is(equalTo(3L)));

    }

}
