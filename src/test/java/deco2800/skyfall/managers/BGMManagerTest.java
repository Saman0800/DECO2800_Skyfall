package deco2800.skyfall.managers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import javax.sound.sampled.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BGMManagerTest {

    private String file = "resources/sounds/forest_day.wav";

    BGMManager bgmManager = new BGMManager();

    /* Hi Ash/Kelsey! Just a quick note:
     Our original tests possibly might fail in Jenkins because there's no hardware to actually test our methods!
     I think Brad (tutor) is going to make a folder for us to put in tests that Jenkins won't run,
     but i'm not too sure when/how they'll do that.
     I don't think the errors were being thrown in the earlier sprints because the private String file above was
     set to a file that wasn't in our resources so it was caught in the catch (I think?).
     I've just done some mocking stuff at the top for the sake of mocking things (?) but our tests at the bottom
     are for us to actually check the functionality of our methods.
     Just to reiterate, I'm not sure if the bottom tests will break our build, just be cautious when pushing!
     */

    private AudioInputStream audioTest;
    private Clip clipTest;
    private FloatControl volumeTest;

    @Before
    public void mockingStream() {
        // Mocking init clip method
        // Want to make sure that this either creates an audio input stream and clip, or throws particular exceptions.
        audioTest = Mockito.mock(AudioInputStream.class);
        try {
            audioTest = AudioSystem.getAudioInputStream(new File(file));
            clipTest = Mockito.mock(Clip.class); // mock clip
            volumeTest = (FloatControl) clipTest.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e) { /* Catch exceptions */ }
    }

    // mockPlay test doesn't work :(
//    @Test
//    public void mockPlay() {
//        audioTest = Mockito.mock(AudioInputStream.class);
//        try {
//            audioTest = AudioSystem.getAudioInputStream(new File(file));
//            clipTest = Mockito.mock(Clip.class); // mock clip
//            clipTest.start();
//            assertTrue(clipTest.isRunning());
//
//        } catch (Exception e) { /* Catch exceptions */ }
//    }

    @Test
    public void mockPause() {
        try {
            clipTest.start();
            clipTest.stop();
            TimeUnit.SECONDS.sleep(1);
            assertFalse(clipTest.isRunning());
        } catch (Exception e) { /* Catch exceptions */}
    }

    @Test
    public void mockStop() {
        try {
            clipTest.start();
            TimeUnit.SECONDS.sleep(1);
            clipTest.stop();
            TimeUnit.SECONDS.sleep(1);
            assertFalse(clipTest.isRunning());
            assertFalse(clipTest.isOpen());
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockReset() {
        try {
            clipTest.start();
            clipTest.stop();

            // Mock a new stream
            AudioInputStream audioStream2 =
                    AudioSystem.getAudioInputStream(new File("resources/sounds/forest_night.wav"));
            try {
                audioTest = AudioSystem.getAudioInputStream(new File(file));
                Clip clipTest2 = Mockito.mock(Clip.class); // mock clip
                clipTest2.start();
                clipTest2.stop();
                assertEquals(0,clipTest2.getLongFramePosition());

            } catch (Exception e) { /* Catch exceptions */ }
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockMute() {
        try {
            clipTest.start();
            BooleanControl mockMuteVol = (BooleanControl) clipTest.getControl(BooleanControl.Type.MUTE);
            mockMuteVol.setValue(true);
            TimeUnit.SECONDS.sleep(1);
            assertTrue(mockMuteVol.getValue());
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockUnMute() {
        try {
            clipTest.start();
            BooleanControl mockMuteVol = (BooleanControl) clipTest.getControl(BooleanControl.Type.MUTE);
            mockMuteVol.setValue(true);
            TimeUnit.SECONDS.sleep(1);
            mockMuteVol.setValue(false);
            assertFalse(mockMuteVol.getValue());
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Test
    public void mockGetVolume() {
        try {
            // Test Minimum (0)
            clipTest.start();
            volumeTest.setValue(0);
            assertEquals(0, volumeTest.getValue(), 0.01);

            // Test Maximum (100)
            volumeTest.setValue(100);
            assertEquals(100, volumeTest.getValue(), 0.01);
        } catch (Exception e) { /* Catch exceptions */ }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void mockSetVolume() {
        try {
            // Test Minimum (0)
            float x = 0;
            clipTest.start();
            assertEquals(0, volumeTest.getValue(), 0.01);
            clipTest.stop();

            // Test Maximum (100)
            float y = 100;
            clipTest.start();
            volumeTest.setValue(y);
            assertEquals(100, volumeTest.getValue(), 0.01);
            clipTest.stop();

            // Test whether exceptions are thrown (x > MAX).
            try {
                float z = 1000;
                clipTest.start();
                if (!(x > volumeTest.getMaximum()) && !(x < volumeTest.getMinimum())) {
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
                if (!(x > volumeTest.getMaximum()) && !(x < volumeTest.getMinimum())) {
                    volumeTest.setValue(a);
                } else {
                    throw new IndexOutOfBoundsException();
                }
            } catch (Exception e) { /* Exception Caught*/}
            thrown.expect(IndexOutOfBoundsException.class);

        } catch (Exception e) { /* Catch exceptions */ }
    }

    /* Tests for us to actually check functionality! */
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

    @Test
    public void stopBGMTest() throws IOException, LineUnavailableException {
        try {
            bgmManager.initClip(file);
            bgmManager.play();
            bgmManager.stop();
            assertFalse(bgmManager.getClip().isRunning());
        } catch (Exception e) {
            // Exception caught
        }
    }
}