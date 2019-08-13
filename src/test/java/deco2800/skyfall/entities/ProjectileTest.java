package deco2800.skyfall.entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProjectileTest  {

    private Projectile projectile = new Projectile("slash","projectileTest",1,1,1);

    @Test
    public void testProjectileSet() {
        Projectile projectile = new Projectile("slash","projectileTest",1,1,1);
        assertThat("", projectile.getTexture(), is(equalTo("slash")));
    }

}
