package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, AbstractEntity.class })
public class TornadoTest {

    private Tornado tornado;

    @Before
    public void setup() {
        GameMenuManager gmm = mock(GameMenuManager.class);
        when(gmm.getMainCharacter()).thenReturn(null);

        mockStatic(GameManager.class);
        when(GameManager.getManagerFromInstance(GameMenuManager.class)).thenReturn(gmm);
        when(GameManager.get()).thenCallRealMethod();

        tornado = new Tornado(new HexVector(), "tornado_placeholder",
                "spell", new HexVector(0f, 0f),
                20,
                0.1f,
                10);
    }

    /**
     * Test the tornado position.
     */
    @Test
    public void positionTest(){
        assertThat("", tornado.getCol(), is(equalTo(0f)));
        assertThat("", tornado.getRow(), is(equalTo(0f)));
    }

    @Test
    public void getManaTest() {
        assertThat("", tornado.getManaCost(), is(equalTo(10)));
    }

    @Test
    public void testOnTick() {
        //Test the inside branch.

        GameManager gm = GameManager.get();
        World world = mock(World.class);
        Enemy enemy = mock(Enemy.class);
        gm.setWorld(world);

        //Add a new list with mock enemy.
        List<AbstractEntity> list = new LinkedList<>();
        list.add(enemy);

        //Make gm and world return mocked objects.
        when (world.getEntities()).thenReturn(list);
        //Mock enemy position at the position of the projectile.
        when (enemy.getPosition()).thenReturn(new HexVector(0f,0f));

        tornado.onTick(0);
        //Reset cooldown.
        tornado.onTick(0);

        //Verify enemy took 20 damage twice.
        //I've already tested the takeDamage method in enemy test.
        verify(enemy, times(2)).takeDamage(20);
    }
}
