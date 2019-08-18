package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundManager extends AbstractManager {

    /* Audio input stream */
    private static AudioInputStream audioInputStream;

    /* Clip that will be played */
    private static Clip clip;

    /* File to be played */
    private static String audio;

    /* Current position of clip */
    private static Long currentPosition;

    /* Volume of the clip */
    private static FloatControl volume;

    /* Boolean mute control */
    public static BooleanControl muteVol;

//    public void playSound(String soundName) {
//        Sound sound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + soundName));
//        sound.play(1);
//    }

    /**
     * Initialises the Audio Input Stream and allows clip to loop continuously.
     * @param file is the name of the audio intended to be played.
     */
    public static void backgroundGameMusic(String file) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        try {
            //Set current audio to equal file
            audio = file;

            //Create the audio system input
            audioInputStream = AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());

            //Initiate clip source
            clip = AudioSystem.getClip();

            //Open and loop the clip until told otherwise
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e) {
            System.out.println("Cannot play sound from given file.");
            e.printStackTrace();
        }

    }

    /**
     * Plays the clip.
     */
    public static void play()  {
        //Play the clip
        clip.start();
    }

    /**
     * Pauses the clip.
     */
    public static void pause() {
        //Get paused position
        currentPosition = clip.getMicrosecondPosition();

        //Stop the clip
        clip.stop();
    }

    /**
     * Resumes the clip.
     */
    public static void resume() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        //Close current clip and reset
        clip.close();
        resetClip();
        clip.setMicrosecondPosition(currentPosition);
        play();
    }

    /**
     * Stops the clip.
     */
    public static void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        //Reset clip
        currentPosition = 0L;
        clip.stop();
        clip.close();
    }

    /**
     * Resets the clip to start time.
     */
    public static void resetClip() throws UnsupportedAudioFileException, IOException,
    LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Returns the current clip.
     */
    public static Clip getClip()  {
        return clip;
    }

    /**
     * Mutes the clip.
     */
    public static void mute () {
        //Set mute value to true
        muteVol = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
        muteVol.setValue(true);
    }

    /**
     * Unmutes the clip.
     */
    public static void unmute () {
        //Set mute value to true
        muteVol = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
        muteVol.setValue(false);
    }

    /**
     * Gets the current volume of the clip.
     * @return float of current volume.
     */
    public static float getVolume() {
        // Get the current volume of the clip.
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return volume.getValue();
    }

    /**
     * Sets the volume of the clip.
     * @param x is the intended float value to set the clip to.
     */
    public static void setVolume(float x) throws IndexOutOfBoundsException,
            NullPointerException, IllegalArgumentException {
        // Checks whether the given value is valid.
        try {
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if (!(x > volume.getMaximum()) && !(x < volume.getMinimum())) {
                volume.setValue(x);
            } else {
                System.out.println("Value is too high/low. Pls don't.");
                throw new IndexOutOfBoundsException();
            }
        } catch (NullPointerException | IllegalArgumentException ex) { }
    }
}
