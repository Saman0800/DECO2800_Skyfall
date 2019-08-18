package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SoundEffectTest {
    SoundManager sound = new SoundManager();
    String path = "resources/sounds/";

    /**
     *  Tests sounds loaded correctly from constructor. All sounds are loaded
     *  the same way, so tests the first.
     */
    @Test
    public void testHasSound() {
        try {
            assertEquals(sound.getSoundMap().get("spider"),
                    Gdx.audio.newSound(Gdx.files.internal(path + "spider" + ".wav")));
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
        sound.playSound("spider");
        assertFalse(sound.stopSound("tester"));
    }

    @Test
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            sound.playSound("people_walk_normal");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    public void playSoundEffect() {
        try {
            sound.playSound("people_walk_normal");
            TimeUnit.SECONDS.sleep(1);
            assertEquals(sound.soundInMap("people_walk_normal"), false);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void stopSoundEffect() {
        try {
            sound.playSound("people_walk_normal");
            TimeUnit.SECONDS.sleep(1);
            assertEquals(sound.stopSound("people_walk_normal"), false);
        } catch (Exception e) {
            //exception caught
        }
    }
}
