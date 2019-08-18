package deco2800.skyfall.managers;

import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SoundEffectTest {
    SoundManager sound = new SoundManager();

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



}
