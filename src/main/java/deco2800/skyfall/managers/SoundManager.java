package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    /* Initialize a map to store all sound effects */
    private static Map<String, Sound> soundMap = new HashMap<>();

    /* Initialize a map to store all looped sound effects */
    private static Map<String, Sound> soundLoops = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);

//    public void playSound(String soundName) {
//        Sound sound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + soundName));
//        sound.play(1);
//    }

    /**
     * Initialize SoundManager by adding different sounds in a map
     */
    public SoundManager() {
        LOGGER.info("soundManager song list");

        try {
            soundMap.put("people_walk_normal", Gdx.audio.newSound
                    (Gdx.files.internal("resources/sounds/" + "pick up.wav")));
            soundMap.put("spider", Gdx.audio.newSound
                    (Gdx.files.internal("resources/sounds/" + "spider.wav")));
            soundMap.put("robot", Gdx.audio.newSound
                    (Gdx.files.internal("resources/sounds/" + "robot.wav")));
            soundMap.put("sword", Gdx.audio.newSound
                    (Gdx.files.internal("resources/sounds/" + "sword.wav")));
        } catch(Exception e) {
            LOGGER.error("no song be found");
        }
    }

    /**
     * Check whether this sound is sored in the map
     * @param soundName
     * @return true if it does, otherwise, false.
     */
    public boolean soundInMap(String soundName){
        return soundMap.containsKey(soundName);
    }

    /**
     * Plays a given sound if it exists in the HashMap.
     * Returns true if sound is played.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is played
     */
    public static boolean playSound(String soundName) {
        if (soundMap.containsKey(soundName)) {
            Sound sound = soundMap.get(soundName);
            sound.play(6);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loops a given sound if it exists in soundMap.
     * Returns true if sound is looped.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is looped
     */
    public static void loopSound(String soundName){
        if (soundMap.containsKey(soundName)) {
            Sound sound = soundMap.get(soundName);
            sound.loop(6);
            //Add to the sounds which are being looped
            soundLoops.put(soundName, soundMap.get(soundName));
        } else {
            LOGGER.info("There does not exist a {} sound", soundName);
        }
    }

    /**
     * Stop the sound.
     * @param soundName Sound identifier/key
     * @return
     */
    public static boolean stopSound(String soundName) {
        if (soundMap.containsKey(soundName)) {
            Sound sound = soundMap.get(soundName);
            soundLoops.remove(soundName);
            sound.stop();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Pauses a given sound if it exists in soundLoops.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is paused
     */
    public static boolean pauseSound(String soundName) {
        if (soundLoops.containsKey(soundName)) {
            //Access the originally placed sound
            Sound sound = soundMap.get(soundName);
            sound.pause();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Resumes a given sound if it exists in soundLoops.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is resumed
     */
    public static boolean resumeSound(String soundName) {
        if (soundLoops.containsKey(soundName)) {
            //Access the originally placed sound
            Sound sound = soundMap.get(soundName);
            sound.resume();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether the sound is in the loop
     * @param soundName
     * @return true if it does, otherwise return false.
     */
    public  boolean checkLoop(String soundName){
        return soundMap.containsKey(soundName);
    }

    /**
     * Return the selected sound for corresponding action
     */
    public Sound getTheSound(String soundName){
        return soundMap.get(soundName);
    }

    /**
     * Returns the map of sounds
     * @return soundMap<Sound, String>
     */
    public Map<String, Sound> getSoundMap() {
        return Collections.unmodifiableMap(soundMap);

    }

//    /**
//     * Play the sound
//     * @param soundName
//     */
//    public void playTheSound(String soundName) {
//        Sound sound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + soundName));
//        sound.play(1);
//    }
//
//    /**
//     * Pause the sound
//     * @param soundName
//     */
//    public void pauseTheSound(String soundName){
//        Sound sound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + soundName));
//        sound.stop();
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
