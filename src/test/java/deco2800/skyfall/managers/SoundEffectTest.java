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
            SoundManager.backgroundGameMusic("resources/sounds/09-running-in-the-90-s.mp3");
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void playSoundEffect(){
        try {
            sound.playSound("menu");
            TimeUnit.SECONDS.sleep(1);
            assertEquals(sound.soundInMap("menu"), true);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void playSoundEffectFOrPause(){
        try {
            sound.playTheSound("09-running-in-the-90-s.mp3");
            TimeUnit.SECONDS.sleep(1);
            sound.pauseTheSound("09-running-in-the-90-s.mp3");
            //assertEquals(SoundManager.getClip().isRunning(), false);
        } catch (Exception e) {
            //exception caught
        }
    }
}
