package deco2800.skyfall.entities.spells;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;

import com.badlogic.gdx.Input;
import deco2800.skyfall.entities.MainCharacter;
import org.junit.Before;
import org.junit.Test;

public class SpellCasterTest {

    SpellCaster spellCaster;
    MainCharacter mainCharacterMock;

    @Before
    public void setup() {

        mainCharacterMock = mock(MainCharacter.class);
        spellCaster = new SpellCaster(mainCharacterMock);

    }

    @Test
    public void testOnKeyPressed() {
        assertThat("",spellCaster.index, is(equalTo(0)));

        spellCaster.onKeyPressed(Input.Keys.UP);
        assertThat("",spellCaster.index, is(equalTo(1)));
        assertThat("",spellCaster.keyLog[0], is(equalTo(Input.Keys.UP)));

        spellCaster.onKeyPressed(Input.Keys.DOWN);
        assertThat("",spellCaster.index, is(equalTo(2)));
        assertThat("",spellCaster.keyLog[1], is(equalTo(Input.Keys.DOWN)));

        spellCaster.onKeyPressed(Input.Keys.UP);
        assertThat("",spellCaster.index, is(equalTo(3)));
        assertThat("",spellCaster.keyLog[2], is(equalTo(Input.Keys.UP)));

        spellCaster.onKeyPressed(Input.Keys.DOWN);
        assertThat("",spellCaster.index, is(equalTo(4)));
        assertThat("",spellCaster.keyLog[3], is(equalTo(Input.Keys.DOWN)));

        //Sequence complete, should reset keylog.
        spellCaster.onKeyPressed(Input.Keys.LEFT);
        assertThat("",spellCaster.index, is(equalTo(0)));
        assertThat("",spellCaster.keyLog[0], is(equalTo(0)));

        //Verify flame wall spell was cast.
        verify(mainCharacterMock, times(1)).selectSpell(SpellType.FLAME_WALL);

        //Start sequence again.
        spellCaster.onKeyPressed(Input.Keys.LEFT);
        assertThat("",spellCaster.index, is(equalTo(1)));
        assertThat("",spellCaster.keyLog[0], is(equalTo(Input.Keys.LEFT)));

        //No sequence matches, should reset.
        spellCaster.onKeyPressed(Input.Keys.F);
        assertThat("",spellCaster.index, is(equalTo(0)));
        assertThat("",spellCaster.keyLog[0], is(equalTo(0)));

    }


}
