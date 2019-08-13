package deco2800.skyfall.managers;

import org.junit.Test;

import javax.sound.sampled.*;
import java.io.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SoundTest {

    String file ="resources/sounds/Forest Day.wav";

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
            assertEquals(SoundManager.muteVol.getValue(),true);
        } catch (Exception e) {
            //exception caught
        }
    }
}
