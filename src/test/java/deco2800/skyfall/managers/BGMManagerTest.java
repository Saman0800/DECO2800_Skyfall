package deco2800.skyfall.managers;

import org.junit.Assert;
import org.junit.Test;

import javax.sound.sampled.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BGMManagerTest {

    String file = "resources/sounds/Forest Day.wav";

    BGMManager bgmManager = new BGMManager(); // Don't think this is necessary, can simply call BGMManager.x

    @Test
    public void exceptionTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            bgmManager.initClip("resources/sounds/09-running-in-the-90-s.mp3");
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
            assertEquals(bgmManager.getClip().isRunning(), true);
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
            assertEquals(bgmManager.getClip().isRunning(), false);
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
            assertEquals(bgmManager.getClip().isRunning(), true);
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
            assertEquals(bgmManager.getClip().getLongFramePosition(), 0);

            // Test that current clip is reset to the start
            assertEquals(bgmManager.getClip().getLongFramePosition(),0);
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
            BGMManager.mute();
            assertEquals(bgmManager.muteVol.getValue(), true);

            // Test that sound is muted
            assertEquals(bgmManager.muteVol.getValue(),true);
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
            assertEquals(bgmManager.muteVol.getValue(), false);

            // Test that sound is unmuted
            assertEquals(bgmManager.muteVol.getValue(),false);
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
    public void stopBGMTest() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            BGMManager.initClip(file);
            BGMManager.play();
            BGMManager.stop();
            assertFalse(BGMManager.getClip().isRunning());
        } catch (Exception e) {
            // Exception caught
        }
    }
}