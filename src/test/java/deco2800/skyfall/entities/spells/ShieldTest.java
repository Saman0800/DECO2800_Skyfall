package deco2800.skyfall.entities.spells;

import com.badlogic.gdx.physics.box2d.World;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, AbstractEntity.class, World.class })
public class ShieldTest {

    Shield shield;

    @Before
    public void setup() {
        GameManager gmReal = GameManager.get();

        GameManager gm = mock(GameManager.class);
        when(gm.getWorld()).thenReturn(null);
        when(gm.getManager(PhysicsManager.class)).then(
                (Answer<PhysicsManager>) invocation -> gmReal.getManager(PhysicsManager.class));

        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gm);

        shield = new Shield(new HexVector(), "shield_placeholder",
                "shield", 0f, 0f,
                20,
                0.1f,
                10);
    }

    @Test
    @Ignore // FIXME I cannot get this to work correctly with the mocking.
    public void testOnTick() {

        //Ensure the shield is moving with the character.
        assertThat("", shield.getPosition(), is(equalTo(new HexVector(0f,0f))));

        //Create mock MainCharacter object that can be moved.
        MainCharacter mc = mock(MainCharacter.class);
        shield.mc = mc;

        when(mc.getCol()).thenReturn(1f);
        when(mc.getRow()).thenReturn(1f);

        shield.onTick(0);
        assertThat("", shield.getPosition(), is(equalTo(new HexVector(1f,1f))));

        when(mc.getCol()).thenReturn(-1f);
        when(mc.getRow()).thenReturn(-1f);

        shield.onTick(0);
        assertThat("", shield.getPosition(), is(equalTo(new HexVector(-1,-1))));

        when(mc.getCol()).thenReturn(200f);
        when(mc.getRow()).thenReturn(-300f);

        shield.onTick(0);
        assertThat("", shield.getPosition(), is(equalTo(new HexVector(200f,-300f))));

    }
}
