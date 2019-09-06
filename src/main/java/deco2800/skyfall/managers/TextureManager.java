package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.io.FileNotFoundException;
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

            textureMap.put("range_test", new Texture("resources/projectile.png"));
            textureMap.put("melee_test", new Texture("resources/punch.png"));

            // Tile textures
            // Goes through all the folders with tile_textures and adds the tile name to the
            // textures, it removes
            // the last 4 characters to get the name of the file.
            // Using this means that each tile texture should be given a unique name as
            // otherwise it will get
            // overridden in the texture hashmap .
            File[] files = new File("resources/tile_textures").listFiles();
            if (files == null) {
                throw new FileNotFoundException();
            }
            for (File direc : files) {
                if (direc.isDirectory()) {
                    for (File file : direc.listFiles()) {
                        if (file.getName().toLowerCase().endsWith(".png")) {
                            String path = String.format("resources/tile_textures/%s/%s", direc.getName(),
                                    file.getName());
                            textureMap.put(file.getName().substring(0, file.getName().length() - 4), new Texture(path));
                        }
                    }
                }

            }

            //EnemyEntity robot
            textureMap.put("flower", new Texture("resources/flower.png"));
            textureMap.put("enemyStone", new Texture("resources/enemyStone.png"));
            textureMap.put("spider", new Texture("resources/spider.png"));
            textureMap.put("robot", new Texture("resources/robot.png"));
            textureMap.put("stoneRS", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/stoneRS.png"));
            textureMap.put("stoneRSE", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/stoneRSE.png"));
            textureMap.put("stoneRSW", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/stoneRSW.png"));
            textureMap.put("stoneRNE", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/stoneRNE.png"));
            textureMap.put("stoneRNW", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/stoneRNW.png"));
            textureMap.put("stoneRN", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/stoneRN.png"));
            textureMap.put("stoneDead", new Texture("resources/EnemyAnimationPacked/stoneUnderAttacking/Dead.png"));

            textureMap.put("enemyTreeman", new Texture("resources/Treeman.png"));
            textureMap.put("treemanDead", new Texture("resources/TreemanDead.png"));

            textureMap.put("flowerDead", new Texture("resources/flowerDead.png"));

            textureMap.put("grass_tuff", new Texture("resources/world_details/grass1.png"));

            textureMap.put("woodcube", new Texture("resources/woodcube.png"));

            textureMap.put("selection", new Texture("resources/blue_selection.png"));
            textureMap.put("path", new Texture("resources/yellow_selection.png"));

            // Portrait of the tutorial AI, replace later with custom art
            textureMap.put("Karen", new Texture("resources/Karen(replace)" + ".png"));

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

            textureMap.put("rock", new Texture("resources/rocks.png"));
            textureMap.put("rock1", new Texture("resources/world_details/rock1.png"));
            textureMap.put("rock2", new Texture("resources/world_details/rock2.png"));
            textureMap.put("rock3", new Texture("resources/world_details/rock3.png"));

            textureMap.put("bush1", new Texture("resources/world_details/bush1.png"));
            textureMap.put("bush2", new Texture("resources/world_details/bush2.png"));
            textureMap.put("bush3", new Texture("resources/world_details/bush3.png"));

            textureMap.put("tree1", new Texture("resources/world_details/tree1.png"));
            textureMap.put("tree2", new Texture("resources/world_details/tree2.png"));
            textureMap.put("tree3", new Texture("resources/world_details/tree3.png"));

            textureMap.put("mushrooms1", new Texture("resources/world_details/mushrooms1.png"));
            textureMap.put("mushrooms2", new Texture("resources/world_details/mushrooms2.png"));

            textureMap.put("MTree1", new Texture("resources/world_details/MTree1.png"));
            textureMap.put("MTree2", new Texture("resources/world_details/MTree2.png"));
            textureMap.put("MTree3", new Texture("resources/world_details/MTree3.png"));

            textureMap.put("MRock1", new Texture("resources/world_details/MRock1.png"));
            textureMap.put("MRock2", new Texture("resources/world_details/MRock2.png"));
            textureMap.put("MRock3", new Texture("resources/world_details/MRock3.png"));

            textureMap.put("DCactus1", new Texture("resources/world_details/DCactus1.png"));
            textureMap.put("DCactus2", new Texture("resources/world_details/DCactus2.png"));
            textureMap.put("DCactus3", new Texture("resources/world_details/DCactus3.png"));
            textureMap.put("DCactus4", new Texture("resources/world_details/DCactus4.png"));

            textureMap.put("MSnow1", new Texture("resources/world_details/MSnow1.png"));
            textureMap.put("MSnow2", new Texture("resources/world_details/MSnow2.png"));
            textureMap.put("MSnow3", new Texture("resources/world_details/MSnow3.png"));

            textureMap.put("MBush1", new Texture("resources/world_details/MBush1.png"));
            textureMap.put("MBush2", new Texture("resources/world_details/MBush2.png"));
            textureMap.put("MBush3", new Texture("resources/world_details/MBush3.png"));

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

            textureMap.put("left_arrow", new Texture("resources/left_arrow.png"));
            textureMap.put("right_arrow", new Texture("resources/right_arrow.png"));
            textureMap.put("select", new Texture("resources/select_button.png"));

            textureMap.put("house1", new Texture("resources/world_structures/house1.png"));
            textureMap.put("storage_unit", new Texture("resources/world_structures/storage_unit.png"));
            textureMap.put("town_centre", new Texture("resources/world_structures/town_centre.png"));
            textureMap.put("fence_bottom_left", new Texture("resources/world_structures/fence_bottom_left.png"));
            textureMap.put("fence_bottom_right", new Texture("resources/world_structures/fence_bottom_right.png"));
            textureMap.put("fence_left_left", new Texture("resources/world_structures/fence_left_left.png"));
            textureMap.put("fence_right_right", new Texture("resources/world_structures/fence_right_right.png"));
            textureMap.put("fence_top_left", new Texture("resources/world_structures/fence_top_left.png"));
            textureMap.put("fence_top_right", new Texture("resources/world_structures/fence_top_right.png"));

            textureMap.put("big_circle", new Texture("resources/OrangeCircle.png"));
            textureMap.put("inner_circle", new Texture("resources/RedCircle.png"));

            textureMap.put("inventory_banner", new Texture("resources/inventory_banner.png"));
            textureMap.put("Stone", new Texture("resources/temp_stone.png"));
            textureMap.put("Wood", new Texture("resources/temp_wood.png"));
            textureMap.put("menu_panel", new Texture("resources/menu_panel.png"));
            textureMap.put("info_panel", new Texture("resources/info_panel.png"));
            textureMap.put("exit", new Texture("resources/exit.png"));
            textureMap.put("exitButton", new Texture("resources/exit_button.png"));
            textureMap.put("inv_button", new Texture("resources/inv_button.png"));
            textureMap.put("Vine", new Texture("resources/temp_vine.png"));
            textureMap.put("Sand", new Texture("resources/temp_sand.png"));
            textureMap.put("Metal", new Texture("resources/temp_metal.png"));
            textureMap.put("Pick Axe", new Texture("resources/temp_pickaxe.png"));
            textureMap.put("Hatchet", new Texture("resources/temp_hatchet.png"));
            textureMap.put("Select", new Texture("resources/item_selected.png"));
            textureMap.put("quick_access_panel", new Texture("quick_access_panel.png"));

            textureMap.put("MainCharacter", new Texture("resources/Main_Character_F_Right.png"));
            textureMap.put("MainCharacterN_Anim", new Texture("resources/Main_Character_Back_Anim.png"));
            textureMap.put("MainCharacterNE_Anim", new Texture("resources/Main_Character_B_Right_Anim.png"));
            textureMap.put("MainCharacterE_Anim", new Texture("resources/Main_Character_Right_Anim.png"));
            textureMap.put("MainCharacterSE_Anim", new Texture("resources/Main_Character_F_Right_Anim.png"));
            textureMap.put("MainCharacterS_Anim", new Texture("resources/Main_Character_Front_Anim.png"));
            textureMap.put("MainCharacterSW_Anim", new Texture("resources/Main_Character_F_Left_Anim.png"));
            textureMap.put("MainCharacterW_Anim", new Texture("resources/Main_Character_Left_Anim.png"));
            textureMap.put("MainCharacterNW_Anim", new Texture("resources/Main_Character_B_Left_Anim.png"));

            // Main character Attack animation
            textureMap.put("MainCharacter_Attack_E_Anim", new Texture("resources/Main_Character_Attack_E.png"));

            // Main character Hurt animation
            textureMap.put("MainCharacter_Hurt_E_Anim", new Texture("resources/Main_Character_Hurt_E.png"));

            // Main character Dead animation
            textureMap.put("MainCharacter_Dead_E_Anim", new Texture("resources/Main_Character_Dead_E.png"));

            System.out.println("ALL TEXTURES LOADED SUCCESSFULLY");

            textureMap.put("cabin_0", new Texture("resources/world_structures/cabin_0.png"));
            textureMap.put("cabin_90", new Texture("resources/world_structures/cabin_90.png"));
            textureMap.put("cabin_180", new Texture("resources/world_structures/cabin_180.png"));
            textureMap.put("cabin_270", new Texture("resources/world_structures/cabin_270.png"));
            textureMap.put("castle_0", new Texture("resources/world_structures/castle_0.png"));
            textureMap.put("castle_90", new Texture("resources/world_structures/castle_90.png"));
            textureMap.put("castle_180", new Texture("resources/world_structures/castle_180.png"));
            textureMap.put("castle_270", new Texture("resources/world_structures/castle_270.png"));
            textureMap.put("safe_house_0", new Texture("resources/world_structures/safe_house_0.png"));
            textureMap.put("safe_house_90", new Texture("resources/world_structures/safe_house_90.png"));
            textureMap.put("safe_house_180", new Texture("resources/world_structures/safe_house_180.png"));
            textureMap.put("safe_house_270", new Texture("resources/world_structures/safe_house_270.png"));
            textureMap.put("watchtower_0", new Texture("resources/world_structures/watchtower_0.png"));
            textureMap.put("watchtower_90", new Texture("resources/world_structures/watchtower_90.png"));
            textureMap.put("watchtower_180", new Texture("resources/world_structures/watchtower_180.png"));
            textureMap.put("watchtower_270", new Texture("resources/world_structures/watchtower_270.png"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used for testing only.
     * 
     * @param test
     */
    public TextureManager(boolean test) {

    }

    /**
     * Gets a texture object for a given string id, if the string id starts with
     * __ANIMATION it will try and retrieve a sprite from an animation.
     *
     * @param id Texture identifier
     *
     * @return Texture for given id
     */
    public Texture getTexture(String id) {
        if (textureMap.containsKey(id)) {
            return textureMap.get(id);
        } else if (id != null && id.startsWith("__ANIMATION_")) {
            // System.out.println("Getting animation texture");
            AnimationManager animationManager = GameManager.getManagerFromInstance(AnimationManager.class);
            Texture texture = this.getTextureFromAnimation(id, animationManager);

            if (texture != null) {
                return texture;
            } else {
                // System.out.println("Texture animation could not be found");
                return textureMap.get("spacman_ded");
            }

        } else {
            // log.info("Texture map does not contain P{}, returning default texture.", id);
            //TODO fix the issue where tiles are not getting added to lakes correctly,
            //Temporary fix is just to assign tiles without a texture the lake texture so that the
            //issue isn't as noticable
            return textureMap.get("lake_1");
        }

    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     *
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

    /**
     * Gets a texture from an animation.
     * 
     * @param id               The string id of the form
     *                         __ANIMATION_<animation_name>:<index>
     * @param animationManager The animation manager
     * @return
     */
    private Texture getTextureFromAnimation(String id, AnimationManager animationManager) {
        String id1 = id.replaceAll("__ANIMATION_", "");
        String[] split = id1.split(":");
        // System.out.println(split[0] + " " + split[1]);
        Texture texture = animationManager.getKeyFrameFromAnimation(split[0], Integer.valueOf(split[1]));
        if (texture == null) {
            // System.out.println("getTextureFromAnimation did not find texture");
            return null;
        }

        textureMap.put(id, texture);
        return texture;
    }

}
