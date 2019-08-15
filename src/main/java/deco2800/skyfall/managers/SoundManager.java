package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import javax.sound.sampled.*;

public class SoundManager extends AbstractManager {

    //Audio input stream
    private static AudioInputStream audioInputStream;

    //Clip that will be played
    private static Clip clip;

    //File to be played
    private static String audio;

    //Current position of clip
    private static Long currentPosition;

    //Volume of the clip
    private static FloatControl volume;

    //Boolean mute control
    public static BooleanControl muteVol;

    private Music song;

    private static Map<String, Sound> soundMap = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);

    private static Map<String, Sound> soundLoops = new HashMap<>();


    public SoundManager() {
        LOGGER.info("soundManager song list");
        song = null;
        try {
            soundMap.put("menu", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + "menu.wav")));
            soundMap.put("pick_up", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + "pick_up.wav")));
            soundMap.put("people_walk_normal", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + "People Walk-Normal")));
        } catch(Exception e) {
            LOGGER.error("no song be found");
        }
    }

    public boolean soundInMap(String soundName){
        return soundMap.containsKey(soundName);
    }

    public static boolean playSound(String soundName) {
        if (soundMap.containsKey(soundName)) {
            Sound sound = soundMap.get(soundName);
            sound.play(1);
            return true;
        } else {
            return false;
        }
    }

    public static void loopSound(String soundName){
        if (soundMap.containsKey(soundName)){
            Sound sound = soundMap.get(soundName);
            sound.play(1);
            soundLoops.put(soundName, soundMap.get(soundName));
        } else {
            LOGGER.error("Can't loop the sound", soundName);
        }
    }

    public static boolean checkLoop(String soundName){
        return soundMap.containsKey(soundName);
    }

    /**
     * Initialises the Audio Input Stream
     *
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
     * Play the clip
     */
    public static void play()  {
        //Play the clip
        clip.start();
    }

    /**
     * Pause the clip
     */
    public static void pause() {
        //Get paused position
        currentPosition = clip.getMicrosecondPosition();

        //Stop the clip
        clip.stop();
    }

    /**
     * Resumes the clip
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
     * Stop the clip
     */
    public static void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        //Reset clip
        currentPosition = 0L;
        clip.stop();
        clip.close();
    }

    /**
     * Resets the clip to start time
     */
    public static void resetClip() throws UnsupportedAudioFileException, IOException,
    LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Returns the current clip
     */
    public static Clip getClip()  {
        return clip;
    }

    /**
     * Mutes the clip
     */
    public static void mute () {
        //Set mute value to true
        muteVol = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
        muteVol.setValue(true);
    }
}
