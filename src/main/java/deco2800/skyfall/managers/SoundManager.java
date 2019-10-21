package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the sounds for the game.
 */
public class SoundManager extends AbstractManager {

    /* Initialize a map to store all sound effects */
    private static Map<String, Sound> soundMap = new HashMap<>();

    /* Initialize a map to store all looped sound effects */
    private static Map<String, Sound> soundLoops = new HashMap<>();

    /* Initialize a map to store all music */
    private static Map<String, Music> musicMap = new HashMap<>();

    /* String containing the current music playing */
    private String playing;

    /* Class logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);

    /* Boolean to state whether the sound manager is paused or not */
    private boolean paused = false;

    /* Defines how quickly the music fades in/out */
    private static float fadeConstant = 0.1f;

    /* Sound effects volume for the game */
    private float gameSoundVolume = 1f;

    /* Music volume for the game */
    private float gameMusicVolume = 1f;


    /**
     * Initialize SoundManager by adding different sounds in a map
     */
    public SoundManager() {
        LOGGER.info("soundManager song list");

        try {
            String path = "resources/sounds/";
            soundMap.put("axe_standard", Gdx.audio.newSound
                    (Gdx.files.internal(path + "sword_standard.mp3")));
            soundMap.put("be_hit", Gdx.audio.newSound
                    (Gdx.files.internal(path + "be_hit.mp3")));
            soundMap.put("bow_desert", Gdx.audio.newSound
                    (Gdx.files.internal(path + "bow_desert.mp3")));
            soundMap.put("bow_standard", Gdx.audio.newSound
                    (Gdx.files.internal(path + "bow_standard.mp3")));
            soundMap.put("bow_volcano", Gdx.audio.newSound
                    (Gdx.files.internal(path + "bow_volcano.mp3")));
            soundMap.put("coins", Gdx.audio.newSound
                    (Gdx.files.internal(path + "coins.mp3")));
            soundMap.put("collect_stone", Gdx.audio.newSound
                    (Gdx.files.internal(path + "collect_stone.mp3")));
            soundMap.put("cut_tree", Gdx.audio.newSound
                    (Gdx.files.internal(path + "cut_tree.mp3")));
            soundMap.put("died", Gdx.audio.newSound
                    (Gdx.files.internal(path + "died.mp3")));
            soundMap.put("door_close", Gdx.audio.newSound
                    (Gdx.files.internal(path + "door_close.mp3")));
            soundMap.put("door_open", Gdx.audio.newSound
                    (Gdx.files.internal(path + "door_open.mp3")));
            soundMap.put("dragon_fly", Gdx.audio.newSound
                    (Gdx.files.internal(path + "dragon_fly.mp3")));
            soundMap.put("draw_arrow", Gdx.audio.newSound
                    (Gdx.files.internal(path + "draw_arrow.mp3")));
            soundMap.put("eat_aloe_vera", Gdx.audio.newSound
                    (Gdx.files.internal(path + "eat_aloe_vera.mp3")));
            soundMap.put("eat_apple", Gdx.audio.newSound
                    (Gdx.files.internal(path + "eat_apple.mp3")));
            soundMap.put("eat_berry", Gdx.audio.newSound
                    (Gdx.files.internal(path + "eat_berry.mp3")));
            soundMap.put("eat_mushroom", Gdx.audio.newSound
                    (Gdx.files.internal(path + "eat_mushroom.mp3")));
            soundMap.put("fist_attack", Gdx.audio.newSound
                    (Gdx.files.internal(path + "fist_attack.mp3")));
            soundMap.put("flower_death", Gdx.audio.newSound
                    (Gdx.files.internal(path + "flower_death.mp3")));
            soundMap.put("flower_open", Gdx.audio.newSound
                    (Gdx.files.internal(path + "flower_open.mp3")));
            soundMap.put("get_money", Gdx.audio.newSound
                    (Gdx.files.internal(path + "get_money.mp3")));
            soundMap.put("horse_run", Gdx.audio.newSound
                    (Gdx.files.internal(path + "horse_run.mp3")));
            soundMap.put("menu", Gdx.audio.newSound
                    (Gdx.files.internal(path + "menu.mp3")));
            soundMap.put("pick_up_axe_and_hatchet", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up_axe_and_hatchet.mp3")));
            soundMap.put("pick_up_metal", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up_metal.mp3")));
            soundMap.put("pick_up_sand", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up_sand.mp3")));
            soundMap.put("pick_up_stone", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up_stone.mp3")));
            soundMap.put("pick_up_vine", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up_vine.mp3")));
            soundMap.put("pick_up_wood", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up_wood.mp3")));
            soundMap.put("pick_up", Gdx.audio.newSound
                    (Gdx.files.internal(path + "pick_up.mp3")));
            soundMap.put("robot_death", Gdx.audio.newSound
                    (Gdx.files.internal(path + "robot_death.mp3")));
            soundMap.put("robot_movement", Gdx.audio.newSound
                    (Gdx.files.internal(path + "robot_movement.mp3")));
            soundMap.put("robot_walk", Gdx.audio.newSound
                    (Gdx.files.internal(path + "robot_walk.mp3")));
            soundMap.put("robot", Gdx.audio.newSound
                    (Gdx.files.internal(path + "robot.mp3")));
            soundMap.put("shield", Gdx.audio.newSound
                    (Gdx.files.internal(path + "shield.mp3")));
            soundMap.put("spear", Gdx.audio.newSound
                    (Gdx.files.internal(path + "spear.mp3")));
            soundMap.put("spell_fire", Gdx.audio.newSound
                    (Gdx.files.internal(path + "spell_fire.mp3")));
            soundMap.put("spell_firewall", Gdx.audio.newSound
                    (Gdx.files.internal(path + "spell_firewall.mp3")));
            soundMap.put("spell_tornado", Gdx.audio.newSound
                    (Gdx.files.internal(path + "spell_tornado.mp3")));
            soundMap.put("spider_death", Gdx.audio.newSound
                    (Gdx.files.internal(path + "spider_death.mp3")));
            soundMap.put("spider", Gdx.audio.newSound
                    (Gdx.files.internal(path + "spider.mp3")));
            soundMap.put("stone_attack", Gdx.audio.newSound
                    (Gdx.files.internal(path + "stone_attack.mp3")));
            soundMap.put("stone_death", Gdx.audio.newSound
                    (Gdx.files.internal(path + "stone_death.mp3")));
            soundMap.put("stone_die", Gdx.audio.newSound
                    (Gdx.files.internal(path + "stone_die.mp3")));
            soundMap.put("stone_move", Gdx.audio.newSound
                    (Gdx.files.internal(path + "stone_move.mp3")));
            soundMap.put("stone_walk", Gdx.audio.newSound
                    (Gdx.files.internal(path + "stone_walk.mp3")));
            soundMap.put("sword_desert", Gdx.audio.newSound
                    (Gdx.files.internal(path + "sword_desert.mp3")));
            soundMap.put("sword_fire", Gdx.audio.newSound
                    (Gdx.files.internal(path + "sword_fire.mp3")));
            soundMap.put("sword_standard", Gdx.audio.newSound
                    (Gdx.files.internal(path + "sword_standard.mp3")));
            soundMap.put("walk_D", Gdx.audio.newSound
                    (Gdx.files.internal(path + "walk_D.mp3")));
            soundMap.put("walk_N", Gdx.audio.newSound
                    (Gdx.files.internal(path + "walk_N.mp3")));
            soundMap.put("bike_animation", Gdx.audio.newSound
                    (Gdx.files.internal(path + "bike-movement.wav")));
            soundMap.put("sand_car_animation", Gdx.audio.newSound
                    (Gdx.files.internal(path + "movement-car.wav")));
            soundMap.put("running", Gdx.audio.newSound
                    (Gdx.files.internal(path + "run.wav")));

            musicMap.put("forest_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "forest_day.mp3")));
            musicMap.put("forest_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "forest_night.mp3")));
            musicMap.put("beach_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "beach_day.mp3")));
            musicMap.put("beach_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "beach_night.mp3")));
            musicMap.put("swamp_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "swamp_day.mp3")));
            musicMap.put("swamp_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "swamp_night.mp3")));
            musicMap.put("desert_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "desert_day.mp3")));
            musicMap.put("desert_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "desert_night.mp3")));
            musicMap.put("mountain_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "mountain_day.mp3")));
            musicMap.put("mountain_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "mountain_night.mp3")));
            musicMap.put("snowy_mountains_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "snowy_mountains_day.mp3")));
            musicMap.put("snowy_mountains_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "snowy_mountains_night.mp3")));
            musicMap.put("volcanic_mountains_day", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "volcanic_mountains_day.mp3")));
            musicMap.put("volcanic_mountains_night", Gdx.audio.newMusic
                    (Gdx.files.internal(path + "volcanic_mountains_night.mp3")));

        } catch (Exception e) {
            LOGGER.error("no song be found");
        }
    }

    /**
     * Constructor used for testing only
     *
     * @param test Whether or not the class is used for testing
     */
    public SoundManager(boolean test) {

    }

    /**
     * Check whether this sound is stored in the map
     *
     * @param soundName Name/Key of the sound in the soundMap.
     * @return true if it does, otherwise, false.
     */
    public boolean soundInMap(String soundName) {
        return soundMap.containsKey(soundName);
    }

    /**
     * Plays a given sound if it exists in the HashMap. Returns true if sound is
     * played.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is played
     */
    public boolean playSound(String soundName) {
        if (!paused) {
            if (soundMap.containsKey(soundName)) {
                Sound sound = soundMap.get(soundName);
                sound.play(gameSoundVolume);
                return true;
            } else if (musicMap.containsKey(soundName)) {
                Music music = musicMap.get(soundName);
                music.play();
                music.setVolume(gameMusicVolume);
                playing = soundName;
                return true;
            } else {
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
    public void loopSound(String soundName) {
        if (!paused) {
            if (soundMap.containsKey(soundName)) {
                Sound sound = soundMap.get(soundName);
                sound.loop(gameSoundVolume);
                // Add to the sounds which are being looped
                soundLoops.put(soundName, soundMap.get(soundName));
            } else if (musicMap.containsKey(soundName)) {
                Music music = musicMap.get(soundName);
                music.play();
                music.setVolume(gameMusicVolume);
                playing = soundName;
                music.setLooping(true);
            }
        }
    }

    /**
     * Fades in the music at a rate determined by fadeConstant.
     *
     * @param soundName the sound to be faded in
     */
    public void fadeInPlay(String soundName) {
        Music music = musicMap.get(soundName);
        music.play();
        music.setVolume(0f);
        playing = soundName;
        music.setLooping(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (music.getVolume() < gameMusicVolume) {
                    music.setVolume(music.getVolume() + fadeConstant);
                } else {
                    music.setVolume(gameMusicVolume);
                    this.cancel();
                }
            }
        }, 0.3f, 0.01f);
    }

    /**
     * Fades out the music at a rate determined by fadeConstant.
     *
     * @param soundName the sound to be faded out
     */
    public void fadeOutStop(String soundName) {
        Music music = musicMap.get(soundName);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (music.getVolume() >= fadeConstant) {
                    music.setVolume(music.getVolume() - fadeConstant);
                } else {
                    music.stop();
                    this.cancel();
                }
            }
        }, 0.3f, 0.01f);
    }

    /**
     * Stop the sound.
     *
     * @param soundName Sound identifier/key
     * @return true if the sound was successfully stopped, false otherwise
     */
    public boolean stopSound(String soundName) {
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
            return false;
        }
    }

    /**
     * Pauses a given sound if it exists in soundLoops.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is paused
     */
    public boolean pauseSound(String soundName) {
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
            return false;
        }
    }

    /**
     * Resumes a given sound if it exists in soundLoops.
     *
     * @param soundName Sound identifier/key
     * @return true if sound is resumed
     */
    public boolean resumeSound(String soundName) {
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
                return false;
            }
        }
        return false;
    }

    /**
     * Return the selected music
     */
    public Music getTheMusic(String musicName) {
        return musicMap.get(musicName);
    }

    /**
     * Sets the sound volume of the game
     *
     * @param volume the volume to be set to
     */
    public void setSoundVolume(float volume) {
        // needs to be between 0 and 1
        gameSoundVolume = volume / 100;
    }

    /**
     * Sets the music volume of the game
     *
     * @param volume the volume to be set to
     */
    public void setMusicVolume(float volume) {
        // needs to be between 0 and 1
        gameMusicVolume = volume / 100;
        updateVolume();
    }

    /**
     * Getter for the sound volume of the game
     *
     * @return the sound volume
     */
    public float getSoundVolume() {
        return gameSoundVolume;
    }

    /**
     * Updates the volume of the currently playing music (called by menu's
     * elsewhere)
     *
     */
    public void updateVolume() {
        Music bgm = getTheMusic(playing);
        bgm.setVolume(gameMusicVolume);
    }

}
