package deco2800.skyfall.entities;

import org.junit.Before;
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
    private Projectile projectile = new Projectile("slash",
            "projectileTest",1,1,1,1);


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
     * Test the position of the projectile is correct.
     */
    @Test
    public void testProjectilePosition() {
        assertThat("", projectile.getCol(), is(equalTo(1f)));
        assertThat("", projectile.getRow(), is(equalTo(1f)));
    }

}
