package deco2800.skyfall.managers;

import org.junit.Test;
import org.mockito.internal.matchers.Null;

import javax.sound.sampled.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SoundTest {

    String file = "resources/sounds/Forest Day.wav";

    @Test
    public void exceptionTest() {
        try {
            // Test adding an incorrect file type to audio stream that throws exception
            SoundManager.backgroundGameMusic("resources/sounds/09-running-in-the-90-s.mp3");
        } catch (Exception e) {
            // Exception caught
            fail();
        }
    }

    @Test
    public void playBGMTest()  {
        try {
            // Add correct file type
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.MILLISECONDS.sleep(100);

            // Test that clip is playing added file
            assertEquals(SoundManager.getClip().isRunning(), true);
        } catch (Exception e) {
        }
    }

    @Test
    public void pauseTest() {
        try {
            // Add and play clip, then pause
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.pause();

            // Test that clip is paused
            assertEquals(SoundManager.getClip().isRunning(), false);
        } catch (Exception e) {
        }
    }

    @Test
    public void resumeTest() {
        try {
            // Pause and resume clip
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.pause();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.resume();
            TimeUnit.MILLISECONDS.sleep(100);

            // Test that clip is resumed
            assertEquals(SoundManager.getClip().isRunning(), true);
        } catch (Exception e) {
        }
    }

    @Test
    public void resetClipTest() {
        try {

            // Play, pause and then reset given clip
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.pause();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.resetClip();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.backgroundGameMusic(file);
            assertEquals(SoundManager.getClip().getLongFramePosition(), 0);

            // Test that current clip is reset to the start
            assertEquals(SoundManager.getClip().getLongFramePosition(),0);
        } catch (Exception e) {
        }
    }

    @Test
    public void muteTest() {
        try {

            // Play and then mute given clip
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            SoundManager.mute();
            assertEquals(SoundManager.muteVol.getValue(), true);

            // Test that sound is muted
            assertEquals(SoundManager.muteVol.getValue(),true);
        } catch (Exception e) {
        }
    }

    @Test
    public void unmuteTest() {
        try {

            // Play, mute and then unmute given clip
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            SoundManager.mute();
            TimeUnit.MILLISECONDS.sleep(100);
            SoundManager.unmute();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(SoundManager.muteVol.getValue(), false);

            // Test that sound is unmuted
            assertEquals(SoundManager.muteVol.getValue(),false);
        } catch (Exception e) {
        }
    }

    @Test
    public void getVolumeTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();

            // Test Minimum (0)
            SoundManager.setVolume(0);
            assertEquals(0, SoundManager.getVolume(), 0.01);

            // Test Maximum (100)
            SoundManager.setVolume(100);
            assertEquals(100, SoundManager.getVolume(), 0.01);

        } catch (Exception e) {
        }
    }

    @Test
    public void setVolumeTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();

            // Test Minimum (0)
            SoundManager.setVolume(0);
            assertEquals(0, SoundManager.getVolume(), 0.01);

            // Test Maximum (100)
            SoundManager.setVolume(100);
            assertEquals(100, SoundManager.getVolume(), 0.01);

            // Test whether exceptions are thrown (x > MAX).
            try {
                SoundManager.setVolume(1000);
                fail();
            } catch (IndexOutOfBoundsException e) { }

            // Test whether exceptions are thrown (x > MAX).
            try {
                SoundManager.setVolume(-1);
                fail();
            } catch (IndexOutOfBoundsException e) { }

        } catch (Exception e) { }
    }

    @Test
    public void stopTest(){
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            SoundManager.stop();
            assertEquals(SoundManager.getClip().isRunning(), false);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) { }
    }
}