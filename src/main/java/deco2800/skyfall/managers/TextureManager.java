package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Texture manager acts as a cache between the file system and the renderers.
 * This allows all textures to be read into memory at the start of the game
 * saving file reads from being completed during rendering.
 * <p>
 * With this in mind don't load textures you're not going to use. Textures that
 * are not used should probably (at some point) be removed from the list and
 * then read from disk when needed again using some type of reference counting
 *
 * @Author Tim Hadwen
 */
public class TextureManager extends AbstractManager {

    /**
     * The width of the tile to use then positioning the tile.
     */
    public static final int TILE_WIDTH = 320;

    /**
     * The height of the tile to use when positioning the tile.
     */
    public static final int TILE_HEIGHT = 278;

    // private final Logger log = LoggerFactory.getLogger(TextureManager.class);

    /**
     * A HashMap of all textures with string keys
     */
    private Map<String, Texture> textureMap = new HashMap<>();

    /**
     * Constructor Currently loads up all the textures but probably
     * shouldn't/doesn't need to.
     */
    public TextureManager() {
        try {
            textureMap.put("background", new Texture("resources/background.jpg"));
            textureMap.put("spacman_ded", new Texture("resources/spacman_ded.png"));
            textureMap.put("spacman_blue", new Texture("resources/spacman_blue.png"));
            textureMap.put("bowman", new Texture("resources/bowman.png"));
            textureMap.put("dialogue_text_background", new Texture("resources/dialogue_text_background.png"));
            textureMap.put("main_piece", new Texture("resources" +
                    "/Main_Character_F_Right.png"));
            textureMap.put("slash", new Texture("resources/slash_long.png"));

            //Tile textures
            textureMap.put("grass_0", new Texture("resources/tile_textures/grass_0.png"));
            textureMap.put("grass_1", new Texture("resources/tile_textures/grass_1.png"));
            textureMap.put("grass_2", new Texture("resources/tile_textures/grass_2.png"));
            textureMap.put("grass_3", new Texture("resources/tile_textures/grass_3.png"));
            textureMap.put("grass_4", new Texture("resources/tile_textures/grass_4.png"));
            textureMap.put("grass_5", new Texture("resources/tile_textures/grass_5.png"));
            textureMap.put("grass_6", new Texture("resources/tile_textures/grass_6.png"));
            textureMap.put("spider", new Texture("resources/spider.png"));
            textureMap.put("robot", new Texture("resources/robot.png"));

            //EnemyEntity robot
            textureMap.put("robotS", new Texture("resources/robotS.png"));
            textureMap.put("robotSW", new Texture("resources/robotSW.png"));
            textureMap.put("robotSE", new Texture("resources/robotSE.png"));
            textureMap.put("robotN", new Texture("resources/robotN.png"));
            textureMap.put("robotNE", new Texture("resources/robotNE.png"));
            textureMap.put("robotNW", new Texture("resources/robotNW.png"));


            textureMap.put("water_0", new Texture("resources/tile_textures/water_0.png"));
            textureMap.put("water_1", new Texture("resources/tile_textures/water_1.png"));
            textureMap.put("water_2", new Texture("resources/tile_textures/water_2.png"));
            textureMap.put("water_3", new Texture("resources/tile_textures/water_3.png"));
            textureMap.put("water_4", new Texture("resources/tile_textures/water_4.png"));
            textureMap.put("water_5", new Texture("resources/tile_textures/water_5.png"));
            textureMap.put("water_6", new Texture("resources/tile_textures/water_6.png"));

            textureMap.put("desert_0", new Texture("resources/tile_textures/desert_0.png"));
            textureMap.put("desert_1", new Texture("resources/tile_textures/desert_1.png"));
            textureMap.put("desert_2", new Texture("resources/tile_textures/desert_2.png"));
            textureMap.put("desert_3", new Texture("resources/tile_textures/desert_3.png"));

            textureMap.put("mountain_0", new Texture("resources/tile_textures/mountain_0.png"));
            textureMap.put("mountain_1", new Texture("resources/tile_textures/mountain_1.png"));
            textureMap.put("mountain_2", new Texture("resources/tile_textures/mountain_2.png"));
            textureMap.put("mountain_3", new Texture("resources/tile_textures/mountain_3.png"));
            textureMap.put("mountain_4", new Texture("resources/tile_textures/mountain_4.png"));
            textureMap.put("mountain_5", new Texture("resources/tile_textures/mountain_5.png"));
            textureMap.put("mountain_6", new Texture("resources/tile_textures/mountain_6.png"));
            textureMap.put("mountain_7", new Texture("resources/tile_textures/mountain_7.png"));
            textureMap.put("mountain_8", new Texture("resources/tile_textures/mountain_8.png"));

            //Tile textures that have undecided biome type
            textureMap.put("random_0", new Texture("resources/tile_textures/random_0.png"));
            textureMap.put("random_1", new Texture("resources/tile_textures/random_1.png"));



            textureMap.put("grass_tuff", new Texture("resources/world_details/grass1.png"));

            textureMap.put("woodcube", new Texture("resources/woodcube.png"));

            textureMap.put("selection", new Texture("resources/blue_selection.png"));
            textureMap.put("path", new Texture("resources/yellow_selection.png"));

            // Portrait of the tutorial AI, replace later with custom art
            textureMap.put("Karen", new Texture("resources/Karen(replace)" +
                    ".png"));

            textureMap.put("buildingB", new Texture("resources/building3x2.png"));

            textureMap.put("buildingA", new Texture("resources/buildingA.png"));
            textureMap.put("tree", new Texture("resources/tree.png"));

            textureMap.put("fenceN-S", new Texture("resources/fence N-S.png"));

            textureMap.put("fenceNE-SW", new Texture("resources/fence NE-SW.png"));
            textureMap.put("fenceNW-SE", new Texture("resources/fence NW-SE.png"));

            textureMap.put("fenceNE-S", new Texture("resources/fence NE-S.png"));
            textureMap.put("fenceNE-SE", new Texture("resources/fence NE-SE.png"));
            textureMap.put("fenceN-SE", new Texture("resources/fence N-SE.png"));
            textureMap.put("fenceN-SW", new Texture("resources/fence N-SW.png"));
            textureMap.put("fenceNW-NE", new Texture("resources/fence NW-NE.png"));
            textureMap.put("fenceSE-SW", new Texture("resources/fence SE-SW.png"));
            textureMap.put("fenceNW-S", new Texture("resources/fence NW-S.png"));
            textureMap.put("mario_right", new Texture("resources/mario_texture1.png"));
            textureMap.put("mario_left", new Texture("resources/mario_texture2.png"));

            textureMap.put("rock", new Texture("resources/rocks.png"));
            textureMap.put("rock1", new Texture("resources/world_details/rock1.png"));
            textureMap.put("rock2", new Texture("resources/world_details/rock2.png"));
            textureMap.put("rock3", new Texture("resources/world_details/rock3.png"));
            
            textureMap.put("pop up screen", new Texture("resources/pop_up_screen_background.png"));
            textureMap.put("game menu bar", new Texture("resources/pop_up_screen_title_background.png"));

            textureMap.put("pause", new Texture("resources/pause_icon.png"));
            textureMap.put("settings", new Texture("resources/settings.png"));
            textureMap.put("info", new Texture("resources/information.png"));
            textureMap.put("select-character", new Texture("resources/character_selection.png"));

            textureMap.put("resume", new Texture("resources/resume.png"));
            textureMap.put("goHome", new Texture("resources/home.png"));
            textureMap.put("reset", new Texture("resources/reset_game.png"));

            textureMap.put("radar", new Texture("resources/radar.png"));
            textureMap.put("build", new Texture("resources/build.png"));

            textureMap.put("house1", new Texture("resources/world_structures/house1.png"));
            textureMap.put("storage_unit", new Texture("resources/world_structures/storage_unit.png"));
            textureMap.put("town_centre", new Texture("resources/world_structures/town_centre.png"));
            textureMap.put("fence_bottom_left", new Texture("resources/world_structures/fence_bottom_left.png"));
            textureMap.put("fence_bottom_right", new Texture("resources/world_structures/fence_bottom_right.png"));
            textureMap.put("fence_left_left", new Texture("resources/world_structures/fence_left_left.png"));
            textureMap.put("fence_right_right", new Texture("resources/world_structures/fence_right_right.png"));
            textureMap.put("fence_top_left", new Texture("resources/world_structures/fence_top_left.png"));
            textureMap.put("fence_top_right", new Texture("resources/world_structures/fence_top_right.png"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a texture object for a given string id
     *
     * @param id Texture identifier
     * @return Texture for given id
     */
    public Texture getTexture(String id) {
        if (textureMap.containsKey(id)) {
            return textureMap.get(id);
        } else if (id.startsWith("__ANIMATION_")) {
            System.out.println("Getting animation texture");
            AnimationManager animationManager = GameManager.getManagerFromInstance(AnimationManager.class);
            Texture texture = this.getTextureFromAnimation(id, animationManager);

            if (texture != null) {
                return texture;
            } else {
                System.out.println("Texture animation could not be found");
                return textureMap.get("spacman_ded");
            }

        }else {
            //log.info("Texture map does not contain P{}, returning default texture.", id);
            return textureMap.get("spacman_ded");
        }

    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     * @return If texture is available or not.
     */
    public boolean hasTexture(String id) {
        return textureMap.containsKey(id);

    }

    /**
     * Saves a texture with a given id
     *
     * @param id       Texture id
     * @param filename Filename within the assets folder
     */
    public void saveTexture(String id, String filename) {
        if (!textureMap.containsKey(id)) {
            textureMap.put(id, new Texture(filename));
        }
    }


    private Texture getTextureFromAnimation(String id, AnimationManager animationManager) {
        String id1 = id.replaceAll("__ANIMATION_", "");
        String[] split = id1.split(":");
        System.out.println(split[0] + " " + split[1]);
        Texture texture = animationManager.
                getKeyFrameFromAnimation(split[0],
                        Integer.valueOf(split[1]));
        if (texture == null) {
            System.out.println("getTextureFromAnimation did not find texture");
            return null;
        }

        textureMap.put(id, texture);
        return texture;
    }

}
