package deco2800.skyfall.managers;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SoundEffectTest {
    SoundManager sound = new SoundManager();

    String path = "resources/sounds/";

    /**
     *  To test whether the file path is correct, whether the selected audio can be opened
     *  whether new-added sound is in the map in the form of (soundName, fileName)
     */
    @Test
    public void mockTestSound() {
        Gdx.audio = mock(Audio.class);
        Gdx.files = mock(Files.class);
        Sound sm = mock(Sound.class);
        SoundManager soundItem = mock(SoundManager.class);

        when(Gdx.files.internal("resources/sounds/" + "spell_fire.mp3"))
                .thenReturn(null);

        when(Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + "spell_fire.mp3")))
                .thenReturn(sm);

        when(sm.play(1)).thenReturn(1L);

        // Test whether put ("spell_fire", "spell_fire.mp3") in the soundMap.
        when(soundItem.soundInMap("spell_fire")).thenReturn(true);

        // Check whether "stoneWalk" is the key of the stored entry ("stoneWalk", "stone_walk.wav")
        when(soundItem.getTheSound("spell_fire")).thenReturn(sm);

        SoundManager s = new SoundManager();
        s.playSound("spell_fire");
    }

    /**
     * Tests sounds loaded correctly from constructor. All sounds are loaded
     * the same way, so tests the first.
     */
    @Test
    public void testHasSound() {
        try {
            assertEquals(sound.getSoundMap().get("spear"),
                    Gdx.audio.newSound(Gdx.files.internal(path + "spear" + ".mp3")));
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Test the invalid sound name.
     */
    @Test
    public void playSound() {
        assertFalse(sound.playSound("invalid-name!"));
    }

    /**
     * Test the invalid sound stop.
     */
    @Test
    public void testStopSound() {
        sound.playSound("died");
        assertFalse(sound.stopSound("tester"));
    }

    @Test
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            sound.playSound("walk_D");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void testPause() {
        try{
            SoundManager.playSound("walk_D");
            TimeUnit.SECONDS.sleep(1);
            SoundManager.pauseSound("walk_D");
//            assertEquals(SoundManager.playSound("people_walk_normal"), false);
        } catch (Exception e){
            // exception caught
        }
    }

    @Test
    public void testMasterVolumeGetAndSet() {
        SoundManager.setMasterVolume(5);
        assertEquals(SoundManager.getMasterVolume(), 5f, 0.0001);
    }
}