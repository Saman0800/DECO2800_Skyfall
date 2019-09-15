package deco2800.skyfall.managers;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;

public class BGMManager extends AbstractManager {

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

    // Logger for class to display messages
    private static final Logger LOGGER =
            LoggerFactory.getLogger(BGMManager.class);

    public static boolean paused = false;

    /**
     * Background music constructor
     */
    public BGMManager() {
        // Set parameters here
    }

    /**
     * Initialises the Audio Input Stream and allows clip to loop continuously.
     * @param file is the name of the audio intended to be played.
     */
    public static void initClip(String file) throws UnsupportedAudioFileException,
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
            LOGGER.warn("Cannot play sound from given file.");
        }

    }

    /**
     * Plays the clip.
     */
    public static void play()  {
        //Play the clip
            System.out.println("pause is false play()");
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
    public static void stop() {
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
     * Un-mutes the clip.
     */
    public static void unmute() {
        //Set mute value to false
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
    public static void setVolume(float x) {
        // Checks whether the given value is valid.
        try {
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if ((x <= volume.getMaximum()) && (x >= volume.getMinimum())) {
                volume.setValue(x);
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (NullPointerException | IllegalArgumentException ex) {
            LOGGER.warn(String.valueOf(ex));
        }
    }
}
