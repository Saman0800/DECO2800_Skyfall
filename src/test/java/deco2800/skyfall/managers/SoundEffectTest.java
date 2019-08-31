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

    @Test
    public void mockTestSound() {
        Gdx.audio = mock(Audio.class);
        Gdx.files = mock(Files.class);
        Sound sm = mock(Sound.class);
        SoundManager soundItem = mock(SoundManager.class);

        when(Gdx.files.internal("resources/sounds/" + "COLLECT-STONE.wav")).thenReturn(null);

        when(Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + "collect-stone.wav")))
                .thenReturn(sm);

        when(sm.play(1)).thenReturn(1L);

        // Test whether put ("stoneWalk", "stone_walk.wav") in the soundMap.
        when(soundItem.soundInMap("stoneWalk")).thenReturn(true);

        // Check whether "stoneWalk" is the key of the stored entry ("stoneWalk", "stone_walk.wav")
        when(soundItem.getTheSound("stoneWalk")).thenReturn(sm);

        SoundManager s = new SoundManager();
        s.playSound("stoneWalk");
    }

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
    public void testPause() {
        try{
            SoundManager.playSound("people_walk_normal");
            TimeUnit.SECONDS.sleep(1);
            SoundManager.pauseSound("people_walk_normal");
            assertEquals(SoundManager.playSound("people_walk_normal"), false);
        } catch (Exception e){
            // exception caught
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
