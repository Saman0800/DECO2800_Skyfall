package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ShieldTest {

    Shield shield;

    @Before
    public void setup() throws Exception {
        shield = new Shield(new HexVector(), "shield_placeholder",
                "shield", 0f, 0f,
                20,
                0.1f,
                10);
    }

    @Test
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
