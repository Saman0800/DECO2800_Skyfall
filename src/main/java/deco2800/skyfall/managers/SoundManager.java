package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

    /* Initialize a map to store all music */
    private static Map<String, Music> musicMap = new HashMap<>();

    /* String containing the current music playing */
    private static String playing;

    /* Class logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);

    /* Boolean to state whether the sound manager is paused or not */
    private static boolean paused = false;

    /**
     * Initialize SoundManager by adding different sounds in a map
     */
    public SoundManager() {
        LOGGER.info("soundManager song list");

        try {
            String PATH = "resources/sounds/";
            soundMap.put("be_hit", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "be_hit.mp3")));
            soundMap.put("bow_desert", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "bow_desert.mp3")));
            soundMap.put("bow_standard", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "bow_standard.mp3")));
            soundMap.put("bow_volcano", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "bow_volcano.mp3")));
            soundMap.put("coins", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "coins.mp3")));
            soundMap.put("collect_stone", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "collect_stone.mp3")));
            soundMap.put("cut_tree", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "cut_tree.mp3")));
            soundMap.put("died", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "died.mp3")));
            soundMap.put("door_close", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "door_close.mp3")));
            soundMap.put("door_open", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "door_open.mp3")));
            soundMap.put("dragon_fly", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "dragon_fly.mp3")));
            soundMap.put("draw_arrow", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "draw_arrow.mp3")));
            soundMap.put("eat_aloe_vera", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "eat_aloe_vera.mp3")));
            soundMap.put("eat_apple", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "eat_apple.mp3")));
            soundMap.put("eat_berry", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "eat_berry.mp3")));
            soundMap.put("eat_mushroom", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "eat_mushroom.mp3")));
            soundMap.put("fist_attack", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "fist_attack.mp3")));
            soundMap.put("flower_death", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "flower_death.mp3")));
            soundMap.put("flower_open", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "flower_open.mp3")));
            soundMap.put("get_money", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "get_money.mp3")));
            soundMap.put("horse_run", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "horse_run.mp3")));
            soundMap.put("menu", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "menu.mp3")));
            soundMap.put("pick_up_axe_and_hatchet", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up_axe_and_hatchet.mp3")));
            soundMap.put("pick_up_metal", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up_metal.mp3")));
            soundMap.put("pick_up_sand", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up_sand.mp3")));
            soundMap.put("pick_up_stone", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up_stone.mp3")));
            soundMap.put("pick_up_vine", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up_vine.mp3")));
            soundMap.put("pick_up_wood", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up_wood.mp3")));
            soundMap.put("pick_up", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "pick_up.mp3")));
            soundMap.put("robot_death", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot_death.mp3")));
            soundMap.put("robot_movement", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot_movement.mp3")));
            soundMap.put("robot_walk", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot_walk.mp3")));
            soundMap.put("robot", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "robot.mp3")));
            soundMap.put("shield", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "shield.mp3")));
            soundMap.put("spear", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spear.mp3")));
            soundMap.put("spell_fire", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spell_fire.mp3")));
            soundMap.put("spell_firewall", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spell_firewall.mp3")));
            soundMap.put("spell_tornado", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spell_tornado.mp3")));
            soundMap.put("spider_death", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spider_death.mp3")));
            soundMap.put("spider", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "spider.mp3")));
            soundMap.put("stone_attack", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_attack.mp3")));
            soundMap.put("stone_death", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_death.mp3")));
            soundMap.put("stone_die", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_die.mp3")));
            soundMap.put("stone_move", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_move.mp3")));
            soundMap.put("stone_walk", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "stone_walk.mp3")));
            soundMap.put("sword_desert", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "sword_desert.mp3")));
            soundMap.put("sword_fire", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "sword_fire.mp3")));
            soundMap.put("sword_standard", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "sword_standard.mp3")));
            soundMap.put("walk_D", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "walk_D.mp3")));
            soundMap.put("walk_N", Gdx.audio.newSound
                    (Gdx.files.internal(PATH + "walk_N.mp3")));

            // make into for-loop for nicer code :)
            musicMap.put("forest_day", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "forest_day.mp3")));
            musicMap.put("forest_night", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "forest_night.mp3")));
            musicMap.put("beach_day", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "beach_day.mp3")));
            musicMap.put("beach_night", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "beach_night.mp3")));
            musicMap.put("swamp_day", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "swamp_day.mp3")));
            musicMap.put("swamp_night", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "swamp_night.mp3")));
            musicMap.put("desert_day", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "desert_day.mp3")));
            musicMap.put("desert_night", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "desert_night.mp3")));
            musicMap.put("snowy_mountains_day", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "snowy_mountains_day.mp3")));
            musicMap.put("snowy_mountains_night", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "snowy_mountains_night.mp3")));
            musicMap.put("volcanic_mountains_day", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "volcanic_mountains_day.mp3")));
            musicMap.put("volcanic_mountains_night", Gdx.audio.newMusic
                    (Gdx.files.internal(PATH + "volcanic_mountains_night.mp3")));

        } catch (Exception e) {
            LOGGER.error("no song be found");
        }
    }

    /**
     * Check whether this sound is sored in the map
     *
     * @param soundName Name/Key of the sound in the soundMap.
     * @return true if it does, otherwise, false.
     */
    public boolean soundInMap(String soundName) {
        return soundMap.containsKey(soundName);
    }

    public boolean musicInMap(String musicName) {
        return musicMap.containsKey(musicName);
    }

    /**
     * Plays a given sound if it exists in the HashMap. Returns true if sound is
     * played.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is played
     */
    public static boolean playSound(String soundName) {
        if (!paused) {
            if (soundMap.containsKey(soundName)) {
                Sound sound = soundMap.get(soundName);
                sound.play(1);
                return true;
            } else if (musicMap.containsKey(soundName)) {
                Music music = musicMap.get(soundName);
                music.play();
                playing = soundName;
                return true;
            } else {
                // LOGGER.info("There does not exist a {} sound", soundName);
                return false;
            }
        }
        return false;
    }

    /**
     * Loops a given sound if it exists in soundMap. Returns true if sound is
     * looped.
     *
     * @param soundName Sound identifier/key
     */
    public static void loopSound(String soundName) {
        if (!paused) {
            if (soundMap.containsKey(soundName)) {
                Sound sound = soundMap.get(soundName);
                sound.loop(1);
                // Add to the sounds which are being looped
                soundLoops.put(soundName, soundMap.get(soundName));
            } else if (musicMap.containsKey(soundName)) {
                Music music = musicMap.get(soundName);
                music.play();
                playing = soundName;
                music.setLooping(true);
            } else {
                // LOGGER.info("There does not exist a {} sound", soundName);
            }
        }
    }

    /**
     * Stop the sound.
     *
     * @param soundName Sound identifier/key
     * @return true if the sound was successfully stopped, false otherwise
     */
    public static boolean stopSound(String soundName) {
        if (soundMap.containsKey(soundName)) {
            Sound sound = soundMap.get(soundName);
            soundLoops.remove(soundName);
            sound.stop();
            return true;
        } else if (musicMap.containsKey(soundName)) {
            Music music = musicMap.get(soundName);
            music.setLooping(false);
            music.stop();
            return true;
        } else {
            // LOGGER.info("There does not exist a {} sound", soundName);
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
            // Access the originally placed sound
            Sound sound = soundMap.get(soundName);
            sound.pause();
            return true;
        } else if (musicMap.containsKey(soundName)) {
            Music music = musicMap.get(soundName);
            music.pause();
            return true;
        } else {
            // LOGGER.info("There does not exist a {} sound", soundName);
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
        if (!paused) {
            if (soundLoops.containsKey(soundName)) {
                // Access the originally placed sound
                Sound sound = soundMap.get(soundName);
                sound.resume();
                return true;
            } else if (musicMap.containsKey(soundName)) {
                Music music = musicMap.get(soundName);
                music.play();
                return true;
            } else {
                // LOGGER.info("There does not exist a {} sound", soundName);
                return false;
            }
        }
        return false;
    }

    /**
     * Check whether the sound is in the loop
     *
     * @param soundName name of the sound in soundMap.
     * @return true if it does, otherwise return false.
     */
    public boolean checkLoop(String soundName) {
        return soundMap.containsKey(soundName)
                || musicMap.containsKey(soundName);
    }

    /**
     * Return the selected sound for corresponding action
     */
    public Sound getTheSound(String soundName) {
        return soundMap.get(soundName);
    }

    public Music getTheMusic(String musicName) {
        return musicMap.get(musicName);
    }

    /**
     * Returns the map of sounds
     *
     * @return soundMap<Sound, String>
     */
    public Map<String, Sound> getSoundMap() {
        return Collections.unmodifiableMap(soundMap);
    }

    public Map<String, Music> getMusicMap() {
        return Collections.unmodifiableMap(musicMap);
    }

    public static void setPaused(boolean paused) {
        SoundManager.paused = paused;
    }

    public static String getPlaying() {
        return playing;
    }

    public static void dispose(String soundName) {
        if (soundLoops.containsKey(soundName)) {
            Sound sound = soundMap.get(soundName);
            sound.dispose();
            soundLoops.remove(soundName);
            soundMap.remove(soundName);
        } else if (musicMap.containsKey(soundName)) {
            Music music = musicMap.get(soundName);
            music.dispose();
            musicMap.remove(soundName);
        }
    }



}
