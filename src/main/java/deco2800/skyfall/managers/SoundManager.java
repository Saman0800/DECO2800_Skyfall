package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class SoundManager extends AbstractManager {

    /* Initialize a map to store all sound effects */
    private static Map<String, Sound> soundMap = new HashMap<>();

    /* Initialize a map to store all looped sound effects */
    private static Map<String, Sound> soundLoops = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);

    /**
     * Initialize SoundManager by adding different sounds in a map
     */
    public SoundManager() {
        LOGGER.info("soundManager song list");

        try {
            String PATH = "resources/sounds/";
            soundMap.put("people_walk_normal", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick up.wav")));
            soundMap.put("spider", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spider.wav")));
            soundMap.put("robot", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot.wav")));
            soundMap.put("sword", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "sword.wav")));
            soundMap.put("stoneWalk", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_walk.wav")));
            soundMap.put("stoneDie", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_die.wav")));
            soundMap.put("collectStone", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "collect-stone.wav")));
            soundMap.put("menu", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "menu.wav")));
            soundMap.put("robot_movement", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot movement.wav")));
            soundMap.put("robot_walk", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot walk.wav")));
            soundMap.put("first_attack", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "fist attack.wav")));
            soundMap.put("bow_and_arrow_attack", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "bow and arrow attack.wav")));
            soundMap.put("axe_attack", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "axe attack.wav")));
            soundMap.put("player_hurt", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "be_hit.wav")));
            soundMap.put("player_died", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "died.wav")));
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
            sound.play(1);
            return true;
        } else {
            LOGGER.info("There does not exist a {} sound", soundName);
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
            sound.loop(1);
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
            LOGGER.info("There does not exist a {} sound", soundName);
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
            LOGGER.info("There does not exist a {} sound", soundName);
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
            LOGGER.info("There does not exist a {} sound", soundName);
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

}
