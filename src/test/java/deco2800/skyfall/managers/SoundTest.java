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
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            SoundManager.backgroundGameMusic("resources/sounds/09-running-in-the-90-s.mp3");
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void playBGMTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(SoundManager.getClip().isRunning(), true);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void pauseTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.pause();
            assertEquals(SoundManager.getClip().isRunning(), false);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void resumeTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.pause();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.resume();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(SoundManager.getClip().isRunning(), true);
            //assertEquals(SoundManager.getClip().getFramePosition(), 84737);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void resetClipTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.pause();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.resetClip();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.backgroundGameMusic(file);
            assertEquals(SoundManager.getClip().getLongFramePosition(), 0);

            // Test that current clip is reset to the start
            assertEquals(SoundManager.getClip().getLongFramePosition(),0);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void muteTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.mute();
            assertEquals(SoundManager.muteVol.getValue(), true);

            // Test that sound is muted
            assertEquals(SoundManager.muteVol.getValue(),true);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void unmuteTest() {
        try {
            SoundManager.backgroundGameMusic(file);
            SoundManager.play();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.mute();
            TimeUnit.SECONDS.sleep(1);
            SoundManager.unmute();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(SoundManager.muteVol.getValue(), false);

            // Test that sound is unmuted
            assertEquals(SoundManager.muteVol.getValue(),false);
        } catch (Exception e) {
            //exception caught
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