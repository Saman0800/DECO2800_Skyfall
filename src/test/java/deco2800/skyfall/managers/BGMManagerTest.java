package deco2800.skyfall.managers;

import org.junit.Test;

import javax.sound.sampled.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BGMManagerTest {

    String file = "resources/sounds/Forest Day.wav";

    @Test
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            BGMManager.BGMManager("resources/sounds/09-running-in-the-90-s.mp3");
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void playBGMTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(BGMManager.getClip().isRunning(), true);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void pauseTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.pause();
            assertEquals(BGMManager.getClip().isRunning(), false);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void resumeTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.pause();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.resume();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(BGMManager.getClip().isRunning(), true);
            //assertEquals(SoundManager.getClip().getFramePosition(), 84737);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void resetClipTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.pause();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.resetClip();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.BGMManager(file);
            assertEquals(BGMManager.getClip().getLongFramePosition(), 0);

            // Test that current clip is reset to the start
            assertEquals(BGMManager.getClip().getLongFramePosition(),0);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void muteTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.mute();
            assertEquals(BGMManager.muteVol.getValue(), true);

            // Test that sound is muted
            assertEquals(BGMManager.muteVol.getValue(),true);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void unmuteTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.mute();
            TimeUnit.SECONDS.sleep(1);
            BGMManager.unmute();
            TimeUnit.SECONDS.sleep(1);
            assertEquals(BGMManager.muteVol.getValue(), false);

            // Test that sound is unmuted
            assertEquals(BGMManager.muteVol.getValue(),false);
        } catch (Exception e) {
            //exception caught
        }
    }

    @Test
    public void getVolumeTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();

            // Test Minimum (0)
            BGMManager.setVolume(0);
            assertEquals(0, BGMManager.getVolume(), 0.01);

            // Test Maximum (100)
            BGMManager.setVolume(100);
            assertEquals(100, BGMManager.getVolume(), 0.01);

        } catch (Exception e) {
        }
    }

    @Test
    public void setVolumeTest() {
        try {
            BGMManager.BGMManager(file);
            BGMManager.play();

            // Test Minimum (0)
            BGMManager.setVolume(0);
            assertEquals(0, BGMManager.getVolume(), 0.01);

            // Test Maximum (100)
            BGMManager.setVolume(100);
            assertEquals(100, BGMManager.getVolume(), 0.01);

            // Test whether exceptions are thrown (x > MAX).
            try {
                BGMManager.setVolume(1000);
                fail();
            } catch (IndexOutOfBoundsException e) { }

            // Test whether exceptions are thrown (x > MAX).
            try {
                BGMManager.setVolume(-1);
                fail();
            } catch (IndexOutOfBoundsException e) { }

        } catch (Exception e) { }
    }

//    @Test
//    public void stopTest(){
//        try {
//            SoundManager.BGMManager(file);
//            SoundManager.play();
//            SoundManager.stop();
//            assertEquals(SoundManager.getClip().isRunning(), false); // assert false
//        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) { }
//    }
}