package deco2800.skyfall.entities;

import com.badlogic.gdx.Game;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class })
/**
 * Projectile test suite.
 */
public class ProjectileTest  {

    /**
     * Create a new Projectile.
     */
    private Projectile projectile = new Projectile(new HexVector(),"slash",
            "projectileTest",new HexVector(1,1),1,1,1, 40);


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
        assertThat("", projectile.getRange(), is(equalTo(1f)));

    }

    /**
     * Test the position of the projectile is correct.
     */
    @Test
    public void testProjectilePosition() {
        assertThat("", projectile.getCol(), is(equalTo(1f)));
        assertThat("", projectile.getRow(), is(equalTo(1f)));
    }

    @Test
    public void testGetTextureName() {
        assertThat("",projectile.getTextureName(),is(equalTo("slash")));
    }

    /**
     * Test destroying the Projectile.
     */
    @Test
    public void testDestroy() {
        GameManager gmReal = GameManager.get();
        //mockStatic(GameManager.class);
        GameManager gm = mock(GameManager.class);
        mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(gm);
        World mockWorld = mock(World.class);
        when(gm.getWorld()).thenReturn(mockWorld);

        projectile.destroy();
        assertThat("",projectile.beenDestroyed,is(equalTo(true)));
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

    /**
     * Test the rangeof the projectile.
     */
    @Test
    public void testRange() {
        projectile.range = 10.f;
        for (int i  = 0; i < 11; ++i) {
            projectile.onTick(0);
        }

        assertThat("", projectile.position.length() < 12.f ,is(equalTo(true)));
    }

}
