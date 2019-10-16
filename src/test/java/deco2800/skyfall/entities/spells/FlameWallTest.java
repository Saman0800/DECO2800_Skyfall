package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class FlameWallTest {

    FlameWall flameWall;

    @Before
    public void setup() throws Exception {
        flameWall = new FlameWall(new HexVector(), "flame_wall",
                "spell", new HexVector(2f, 2f),
                1,
                0.1f,
                10);
    }

    /**
     * Test the tornado position.
     */
    @Test
    public void positionTest(){
        assertThat("", flameWall.getCol(), is(equalTo(2f)));
        assertThat("", flameWall.getRow(), is(equalTo(2f)));
    }

    @Test
    public void getManaTest() {
        assertThat("", flameWall.getManaCost(), is(equalTo(20)));
    }

    /**
     * Test the onTick method of the projectile.
     */
    @Test
    public void testOnTick() {
        Assert.assertThat("", flameWall.ticksSinceAttacked, is(equalTo(0)));
        flameWall.onTick(0);
        Assert.assertThat("", flameWall.ticksSinceAttacked, is(equalTo(1)));
        flameWall.onTick(99999);
        Assert.assertThat("", flameWall.ticksSinceAttacked, is(equalTo(2)));
        flameWall.onTick(-1);
        Assert.assertThat("", flameWall.ticksSinceAttacked, is(equalTo(3)));

        //Test the inside branch.

        GameManager gm = GameManager.get();
        World world = mock(World.class);
        Enemy enemy = mock(Enemy.class);
        gm.setWorld(world);

        //Add a new list with mock enemy.
        List list = new LinkedList<AbstractEntity>();
        list.add(enemy);

        //Make gm and world return mocked objects.
        when (world.getEntities()).thenReturn(list);
        //Mock enemy position at the position of the projectile.
        when (enemy.getPosition()).thenReturn(new HexVector(2f,2f));

        flameWall.ticksSinceAttacked = 100;
        flameWall.onTick(0);

        //Reset cooldown.
        flameWall.ticksSinceAttacked = 100;
        flameWall.onTick(0);

        //Verify enemy took 1 damage twice.
        //I've already tested the takeDamage method in enemy test.
        verify(enemy, times(2)).takeDamage(1);

    }
}
