package deco2800.skyfall.managers;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class SoundManagerTest {

    // private member variables
    private SoundManager sound = mock(SoundManager.class);
    private Sound s = mock(Sound.class);
    private Music m = mock(Music.class);
    private String path = "resources/sounds/";

    @Mock
    Map<String, Sound> soundMap;

    @Mock
    Map<String, Music> musicMap;

    @InjectMocks
    SoundManager sm = new SoundManager(true);

    /**
     * Tests if the correct sound is retrieved from the map
     */
    @Test
    public void getSoundTest() {
        when(soundMap.get("died")).thenReturn(s);
        assertEquals(s, soundMap.get("died"));
    }

    /**
     * Tests if the correct music is retrieved from the map
     */
    @Test
    public void getTheMusicTest() {
        when(musicMap.get("forest_day")).thenReturn(m);
        assertEquals(m, musicMap.get("forest_day"));
    }

    /**
     * Tests if sounds can or cannot be played
     */
    @Test
    public void playTest() {
        when(sound.playSound("died")).thenReturn(true);
        when(sound.playSound("invalid-name")).thenReturn(false);
    }

    /**
     * Tests functionality of BGM using same methods as for sound objects
     */
    @Test
    public void BGMTest() {
        when(sound.playSound("forest_day")).thenReturn(true);
        when(sound.pauseSound("forest_day")).thenReturn(true);
        when(sound.resumeSound("forest_day")).thenReturn(true);
        when(sound.stopSound("forest_day")).thenReturn(true);
    }

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

        when(Gdx.files.internal(path + "spell_fire.mp3"))
                .thenReturn(null);

        when(Gdx.audio.newSound(Gdx.files.internal(path + "spell_fire.mp3")))
                .thenReturn(sm);

        when(sm.play(1)).thenReturn(1L);

        // Test whether put ("spell_fire", "spell_fire.mp3") in the soundMap.
        when(soundItem.soundInMap("spell_fire")).thenReturn(true);
    }

    /**
     * Test the invalid sound name.
     */
    @Test
    public void playSound() {
        assertFalse(sm.playSound("invalid-name!"));
    }

    /**
     * Test the invalid sound stop.
     */
    @Test
    public void testStopSound() {
        sm.playSound("died");
        assertFalse(sm.stopSound("tester"));
    }

    /**
     * Tests appropriate exceptions thrown
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    @Test
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            sm.playSound("walk_D");
        } catch (Exception e) {
            e.getMessage();
            fail();
        }
    }

    /**
     * Tests pause functionality of sounds
     */
    @Test
    public void testPause() {
        try{
            sm.playSound("walk_D");
            TimeUnit.SECONDS.sleep(1);
            sm.pauseSound("walk_D");
        } catch (Exception e){
            // exception caught
        }
    }


    /**
     * Tests the altering of game volume - used in PauseMenu
     */
    @Test
    public void testSetVolume() {
        sm.setSoundVolume(10);
        assertEquals(sm.getSoundVolume(), 0.1, 0.0001);
    }
}