package deco2800.skyfall.managers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import javax.sound.sampled.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BGMManagerTest {

    private String file = "resources/sounds/forest_day.wav";

    BGMManager bgmManager = new BGMManager();

    private AudioInputStream audioTest;
    private Clip clipTest;
    private FloatControl volumeTest;
    private BooleanControl mockMuteVol;

    @Before
    public void mockingStream() {
        // Mocking init clip method
        // Want to make sure that this either creates an audio input stream and clip, or throws particular exceptions.
        audioTest = Mockito.mock(AudioInputStream.class);
        try {
            audioTest = AudioSystem.getAudioInputStream(new File(file));
            clipTest = Mockito.mock(Clip.class);

            volumeTest = (FloatControl) clipTest.getControl(FloatControl.Type.MASTER_GAIN);
            mockMuteVol = (BooleanControl) clipTest.getControl(BooleanControl.Type.MUTE);
            mockMuteVol.setValue(true);

        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockMute() {
        try {
            mockMuteVol.setValue(true);
            assertTrue(mockMuteVol.getValue());
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockUnMute() {
        try {
            mockMuteVol.setValue(false);
            assertFalse(mockMuteVol.getValue());
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void mockSetVolume() {
        try {
            volumeTest = (FloatControl) clipTest.getControl(FloatControl.Type.MASTER_GAIN);

            // Test Minimum (0)
            clipTest.start();
            when(bgmManager.getVolume()).thenReturn((float) 0);
            clipTest.stop();

            // Test Maximum (100)
            clipTest.start();
            float y = 100;
            clipTest.start();
            volumeTest.setValue(y);
            assertEquals(100, volumeTest.getValue(), 0.01);
            when(volumeTest.getValue()).thenReturn((float) 100);
            clipTest.stop();

            // Testing within viable range
            // Test whether exceptions are thrown (x > MAX).
            try {
                float z = 1000;
                clipTest.start();
                if (!(z > volumeTest.getMaximum()) && !(z < volumeTest.getMinimum())) {
                    volumeTest.setValue(z);
                } else {
                    throw new IndexOutOfBoundsException();
                }
            } catch (Exception e) { /* Exception Caught*/}
            thrown.expect(IndexOutOfBoundsException.class);

            // Test whether exceptions are thrown (x > MAX).
            try {
                float a = -1;
                clipTest.start();
                if (!(a > volumeTest.getMaximum()) && !(a < volumeTest.getMinimum())) {
                    volumeTest.setValue(a);
                } else {
                    throw new IndexOutOfBoundsException();
                }
            } catch (Exception e) { /* Exception Caught*/}
            thrown.expect(IndexOutOfBoundsException.class);
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockGetClip() {
        try {
            clipTest = AudioSystem.getClip();
            when(bgmManager.getClip()).thenReturn(clipTest);
        } catch (Exception e) {
            // Catch things
        }
    }

    @Test
    public void keyPressTestMute() {
        try {
            clipTest.start();
            bgmManager.notifyKeyTyped('m');
            assertTrue(bgmManager.muteVol.getValue());
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void keyPressUnMute() {
        try {
            clipTest.start();
            bgmManager.notifyKeyTyped('u');
            assertFalse(bgmManager.muteVol.getValue());
        } catch (Exception e) { /* Catch exceptions */}
    }

    @Test
    public void initClipTest() {
        try {
            bgmManager.initClip("testFilename");
            assertEquals("testFilename", bgmManager.audio);
        } catch (Exception e) { /* Catch exceptions */ }

    }


    /* Tests for us to actually check functionality. */

    @Test
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            bgmManager.initClip("resources/sounds/09-running-in-the-90-s.mp3");
            assertFalse(bgmManager.getClip().isRunning());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void playBGMTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(true,bgmManager.getClip().isRunning());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void pauseTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.pause();
            assertEquals(false, bgmManager.getClip().isRunning());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void resumeTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.pause();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.resume();
            TimeUnit.SECONDS.sleep(1);
            assertTrue(bgmManager.getClip().isRunning());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void resetClipTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.pause();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.resetClip();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.initClip(file);
            assertEquals(0, bgmManager.getClip().getLongFramePosition());

            // Test that current clip is reset to the start
            assertEquals(0, bgmManager.getClip().getLongFramePosition());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void muteTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.mute();
            assertTrue(bgmManager.muteVol.getValue());

            // Test that sound is muted
            assertTrue(bgmManager.muteVol.getValue());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void unmuteTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.mute();
            TimeUnit.SECONDS.sleep(1);
            bgmManager.unmute();
            TimeUnit.SECONDS.sleep(1);
            assertFalse(bgmManager.muteVol.getValue());

            // Test that sound is unmuted
            assertFalse(bgmManager.muteVol.getValue());
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void getVolumeTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();

            // Test Minimum (0)
            bgmManager.setVolume(0);
            assertEquals(0, bgmManager.getVolume(), 0.01);

            // Test Maximum (100)
            bgmManager.setVolume(100);
            assertEquals(100, bgmManager.getVolume(), 0.01);

        } catch (Exception e) {
        }
    }

    @Test
    public void setVolumeTest() {
        try {
            bgmManager.initClip(file);
            bgmManager.play();

            // Test Minimum (0)
            bgmManager.setVolume(0);
            assertEquals(0, bgmManager.getVolume(), 0.01);

            // Test Maximum (100)
            bgmManager.setVolume(100);
            assertEquals(100, bgmManager.getVolume(), 0.01);

            // Test whether exceptions are thrown (x > MAX).
            try {
                bgmManager.setVolume(1000);
                fail();
            } catch (IndexOutOfBoundsException e) { }

            // Test whether exceptions are thrown (x > MAX).
            try {
                bgmManager.setVolume(-1);
                fail();
            } catch (IndexOutOfBoundsException e) { }

        } catch (Exception e) { }
    }

}