package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);

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
            textureMap.put("background", new Texture("resources/actualbackground.jpg"));
            textureMap.put("spacman_ded", new Texture("resources/spacman_ded.png"));
            textureMap.put("spacman_blue", new Texture("resources/spacman_blue.png"));
            textureMap.put("bowman", new Texture("resources/bowman.png"));
            textureMap.put("dialogue_text_background", new Texture("resources/dialogue_text_background.png"));

            textureMap.put("tornado_placeholder", new Texture("resources/spells/tornado_3.png"));
            textureMap.put("shield_placeholder", new Texture("resources/spells/shield_3.png"));
            textureMap.put("flame_wall_placeholder", new Texture("resources/spells/fire_3.png"));
            textureMap.put("range_test", new Texture("resources/projectile.png"));
            textureMap.put("melee_test", new Texture("resources/punch.png"));
            textureMap.put("camel", new Texture("resources/camel/camel-face(no-player).png"));
            textureMap.put("camel_character", new Texture("resources/camel/camel-withplayer1(left).png"));
            textureMap.put("horse_images", new Texture("resources/horse_images/horse-left-walk1.png"));
            textureMap.put("horse_character", new Texture("resources/horse_images/horse-left-walk-with-player1.png"));

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

            textureMap.put("gold", new Texture("resources/gold.png"));

            textureMap.put("whitebear", new Texture("resources/whitebear.png"));
            textureMap.put("icewhitebear", new Texture("resources/icewhitebear.png"));
            textureMap.put("lizardHome", new Texture("resources/lizard/home.png"));
            textureMap.put("lizard", new Texture("resources/lizard/lizard.png"));
            textureMap.put("lizardE", new Texture("resources/lizard/lizardE.png"));
            textureMap.put("lizardN", new Texture("resources/lizard/lizardN.png"));
            textureMap.put("lizardS", new Texture("resources/lizard/lizardS.png"));


            /*
            // New Enemies (Scout, Heavy, Abductor)
            textureMap.put("enemyScout_Move_N", new Texture("resources/Enemies/Abductor/enemy3_B.png"));
            textureMap.put("enemyScout_Move_NW", new Texture("resources/Enemies/Abductor/enemy3_BL.png"));
            textureMap.put("enemyScout_Move_NE", new Texture("resources/Enemies/Abductor/enemy3_NE.png"));
            textureMap.put("enemyScout_Move_S", new Texture("resources/Enemies/Abductor/enemy3_F.png"));
            textureMap.put("enemyScout_Move_SW", new Texture("resources/Enemies/Abductor/enemy3_FL.png"));
            textureMap.put("enemyScout_Move_SE", new Texture("resources/Enemies/Abductor/enemy3_FR.png"));
            textureMap.put("enemyScout_Move_W", new Texture("resources/Enemies/Abductor/enemy3_L.png"));
            textureMap.put("enemyScout_Move_E", new Texture("resources/Enemies/Abductor/enemy3_R.png"));
            */

            textureMap.put("chestClosed", new Texture("resources/chest_icon.png"));
            textureMap.put("blueprintShop", new Texture("resources/shopkeeper.png"));

            textureMap.put("petTiger", new Texture("resources/tiger_front1.png"));

            textureMap.put("grass_tuff", new Texture("resources/world_details/grass1.png"));

            textureMap.put("woodcube", new Texture("resources/woodcube.png"));

            textureMap.put("selection", new Texture("resources/blue_selection.png"));
            textureMap.put("path", new Texture("resources/yellow_selection.png"));

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

            textureMap.put("DRock1", new Texture("resources/world_details/DRock1.png"));
            textureMap.put("DRock2", new Texture("resources/world_details/DRock2.png"));
            textureMap.put("DRock3", new Texture("resources/world_details/DRock3.png"));

            textureMap.put("DRibs", new Texture("resources/world_details/DRibs.png"));
            textureMap.put("DSkull", new Texture("resources/world_details/DSkull.png"));
            textureMap.put("DBush1", new Texture("resources/world_details/DBush1.png"));

            textureMap.put("MSnow1", new Texture("resources/world_details/MSnow1.png"));
            textureMap.put("MSnow2", new Texture("resources/world_details/MSnow2.png"));
            textureMap.put("MSnow3", new Texture("resources/world_details/MSnow3.png"));

            textureMap.put("MBush1", new Texture("resources/world_details/MBush1.png"));
            textureMap.put("MBush2", new Texture("resources/world_details/MBush2.png"));
            textureMap.put("MBush3", new Texture("resources/world_details/MBush3.png"));

            textureMap.put("sBush1", new Texture("resources/world_details/sBush1.png"));
            textureMap.put("sBush2", new Texture("resources/world_details/sBush2.png"));
            textureMap.put("sBush3", new Texture("resources/world_details/sBush3.png"));

            textureMap.put("tikitorch", new Texture("resources/world_details/flame.gif"));

            textureMap.put("leaves1", new Texture("resources/world_details/leaves1.png"));

            textureMap.put("mound1", new Texture("resources/world_details/mound1.png"));

            textureMap.put("trunk1", new Texture("resources/world_details/trunk1.png"));

            textureMap.put("sRock1", new Texture("resources/world_details/sRock1.png"));
            textureMap.put("sRock2", new Texture("resources/world_details/sRock2.png"));
            textureMap.put("sRock3", new Texture("resources/world_details/sRock3.png"));

            textureMap.put("sTree1", new Texture("resources/world_details/sTree1.png"));
            textureMap.put("sTree2", new Texture("resources/world_details/sTree2.png"));
            textureMap.put("sTree3", new Texture("resources/world_details/sTree3.png"));

            textureMap.put("vBush1", new Texture("resources/world_details/vBush1.png"));
            textureMap.put("vBush2", new Texture("resources/world_details/vBush2.png"));
            textureMap.put("vBush3", new Texture("resources/world_details/vBush3.png"));

            textureMap.put("vRock1", new Texture("resources/world_details/vRock1.png"));
            textureMap.put("vRock2", new Texture("resources/world_details/vRock2.png"));
            textureMap.put("vRock3", new Texture("resources/world_details/vRock3.png"));

            textureMap.put("vTree1", new Texture("resources/world_details/vTree1.png"));
            textureMap.put("vTree2", new Texture("resources/world_details/vTree2.png"));
            textureMap.put("vTree3", new Texture("resources/world_details/vTree3.png"));

            textureMap.put("sword_tex", new Texture("resources/weapons/sword.png"));
            textureMap.put("sword", new Texture("resources/weapons/sword.png"));
            // Weapons pick-up
            textureMap.put("sword_tex", new Texture("resources/weapons/sword" +
                    ".png"));
            textureMap.put("axe_tex", new Texture("resources/weapons/axe.png"));
            textureMap.put("bow_tex", new Texture("resources/weapons/bow.png"));
            textureMap.put("spear_tex", new Texture("resources/weapons/spear.png"));

            // Weapons attack
            textureMap.put("sword_attack", new Texture("resources/weapons" +
                    "/DesertSword.png"));
            textureMap.put("axe_attack", new Texture("resources/weapons" +
                    "/axe.png"));
            textureMap.put("bow_attack", new Texture("resources/weapons" +
                    "/ArrowEast.png"));
            textureMap.put("spear_attack", new Texture("resources/weapons" +
                    "/spear.png"));

            // Weapons display
            textureMap.put("sword_display_inv", new Texture("resources" +
                    "/weapons" +
                    "/RustySword.png"));
            textureMap.put("axe_display_inv", new Texture("resources/weapons" +
                    "/axe.png"));
            textureMap.put("bow_display_inv", new Texture("resources/weapons" +
                    "/bow2.png"));
            textureMap.put("spear_display_inv", new Texture("resources/weapons" +
                    "/spear2.png"));

            textureMap.put("pop up screen", new Texture("resources/pop_up_screen_background.png"));
            textureMap.put("game menu bar", new Texture("resources/pop_up_screen_title_background.png"));
            textureMap.put("game_over_temp_bg", new Texture("resources/game_over_background.png"));
            textureMap.put("game over retry temp", new Texture("resources/game_over_retry.png"));
            textureMap.put("game over home temp", new Texture("resources/game_over_home_button.png"));

            textureMap.put("pause", new Texture("resources/pause_icon.png"));
            textureMap.put("settings", new Texture("resources/settings.png"));
            textureMap.put("info", new Texture("resources/information.png"));
            textureMap.put("select-character", new Texture("resources/character_selection.png"));

            textureMap.put("resume", new Texture("resources/resume.png"));
            textureMap.put("goHome", new Texture("resources/home.png"));
            textureMap.put("reset", new Texture("resources/reset_game.png"));

            textureMap.put("build", new Texture("resources/build_icon.png"));

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

            // Clock/Season graphics
            textureMap.put("dawn", new Texture("resources/clock/dawn_clock.png"));
            textureMap.put("dusk", new Texture("resources/clock/dusk_clock.png"));
            textureMap.put("day", new Texture("resources/clock/day_clock.png"));
            textureMap.put("night", new Texture("resources/clock/night_clock.png"));
            textureMap.put("summer", new Texture("resources/clock/summer.png"));
            textureMap.put("winter", new Texture("resources/clock/winter.png"));
            textureMap.put("spring", new Texture("resources/clock/spring.png"));
            textureMap.put("autumn", new Texture("resources/clock/autumn.png"));

            textureMap.put("rain", new Texture("resources/weather/rain.png")); // weather
            textureMap.put("snow", new Texture("resources/weather/snow.png")); // weather

            textureMap.put("mana_bar", new Texture("resources/mana_bar.png"));
            textureMap.put("mana_bar_inner", new Texture("resources/mana_bar_inner.png"));

            textureMap.put("inventory_banner", new Texture("resources/inventory_banner.png"));
            textureMap.put("goldPiece5", new Texture("resources/goldPieces/goldPieceFive.png"));
            textureMap.put("goldPiece10", new Texture("resources/goldPieces/goldPieceTen.png"));
            textureMap.put("goldPiece50", new Texture("resources/goldPieces/goldPieceFifty.png"));
            textureMap.put("goldPiece100", new Texture("resources/goldPieces/goldPieceHundred.png"));
            textureMap.put("goldPouch", new Texture("resources/goldPieces/goldPouch.png"));
            textureMap.put("goldBanner", new Texture("resources/goldPieces/goldBanner.png"));
            textureMap.put("menu_panel", new Texture("resources/menu_panel.png"));
            textureMap.put("exit", new Texture("resources/exit.png"));
            textureMap.put("exitButton", new Texture("resources/exit_button.png"));
            textureMap.put("inv_button", new Texture("resources/inv_button.png"));
            textureMap.put("Sand", new Texture("resources/temp_sand.png"));
            textureMap.put("Select", new Texture("resources/item_selected.png"));
            textureMap.put("quick_access_panel", new Texture("quick_access_panel.png"));
            textureMap.put("drop", new Texture("resources/temp_drop_button.png"));
            textureMap.put("drop inactive", new Texture("resources/temp_drop_button_inactive.png"));
            textureMap.put("equip", new Texture("resources/temp_equip_button.png"));
            textureMap.put("equip inactive", new Texture("resources/temp_equip_button_inactive.png"));
            textureMap.put("addqa", new Texture("resources/temp_addqa_button.png"));
            textureMap.put("addqa inactive", new Texture("resources/temp_addqa_button_inactive.png"));
            textureMap.put("takeall", new Texture("resources/takeall.png"));
            textureMap.put("selected", new Texture("resources/items_icons/selected.png"));
            textureMap.put("item_background", new Texture("resources/items_icons/item_background.png"));
            textureMap.put("checked", new Texture("resources/checked.png"));
            textureMap.put("unchecked", new Texture("resources/unchecked.png"));

            // Inventory Item Icons
            textureMap.put("Stone_inv", new Texture("resources/items_icons/stone.png"));
            textureMap.put("Wood_inv", new Texture("resources/items_icons/wood.png"));
            textureMap.put("Vine_inv", new Texture("resources/items_icons/vine.png"));
            textureMap.put("Metal_inv", new Texture("resources/items_icons/metal.png"));
            textureMap.put("Pick Axe_inv", new Texture("resources/items_icons/pickaxe.png"));
            textureMap.put("Hatchet_inv", new Texture("resources/items_icons/hatchet.png"));
            textureMap.put("Aloe_Vera_inv", new Texture("resources/items_icons/aloevera.png"));
            textureMap.put("Apple_inv", new Texture("resources/items_icons/apple.png"));
            textureMap.put("Berry_inv", new Texture("resources/items_icons/berry.png"));
            textureMap.put("PoisonousMushroom_inv", new Texture("resources/items_icons/poisonous_mushroom.png"));
            textureMap.put("Hatchet", new Texture("resources/items_icons/hatchet.png"));
            textureMap.put("Pick Axe", new Texture("resources/items_icons/pickaxe.png"));

            textureMap.put("Sand_inv", new Texture("resources/temp_sand.png"));

            // Inventory items
            textureMap.put("Apple", new Texture("resources/inventory/apple-1.png"));
            textureMap.put("Aloe_Vera", new Texture("resources/inventory/apple-1.png"));
            textureMap.put("Berry", new Texture("resources/inventory/201567400471_.pic_thumb.png"));

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
            textureMap.put("MainCharacter_Dead_E_Still", new Texture("resources/Main_Character_Dead_E_3.png"));

            // Spells
            textureMap.put("spells_fire_Anim", new Texture("resources/spells/fire_spritesheet.png"));
            textureMap.put("spells_shield_Anim", new Texture("resources/spells/shield_spritesheet.png"));
            textureMap.put("spells_shield_Still", new Texture("resources/spells/shield_3.png"));
            textureMap.put("spells_tornado_Anim", new Texture("resources/spells/tornado_spritesheet.png"));

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

            // Old Enemy
            textureMap.put("robot", new Texture("resources/enemyOld/robot.png"));
            textureMap.put("spider", new Texture("resources/enemyOld/spider.png"));
            textureMap.put("flower", new Texture("resources/enemyOld/flower.png"));
            textureMap.put("enemyStone", new Texture("resources/enemyOld/enemyStone.png"));
            textureMap.put("flowerDead", new Texture("resources/enemyOld/flowerDead.png"));
            textureMap.put("treemanDead", new Texture("resources/enemyOld/TreemanDead.png"));
            textureMap.put("enemyTreeman", new Texture("resources/enemyOld/Treeman.png"));
            textureMap.put("stoneRS", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/stoneRS.png"));
            textureMap.put("stoneRSE", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/stoneRSE.png"));
            textureMap.put("stoneRSW", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/stoneRSW.png"));
            textureMap.put("stoneRNE", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/stoneRNE.png"));
            textureMap.put("stoneRNW", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/stoneRNW.png"));
            textureMap.put("stoneRN", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/stoneRN.png"));
            textureMap.put("stoneDead", new Texture("resources/enemyOld/EnemyAnimationPacked/stoneUnderAttacking/Dead.png"));

            System.out.println("ALL TEXTURES LOADED SUCCESSFULLY");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used for testing only.
     *
     * @param test is the function being tested.
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
            // TODO fix the issue where tiles are not getting added to lakes correctly,
            // Temporary fix is just to assign tiles without a texture the lake texture so
            // that the
            // issue isn't as noticable
            return textureMap.get("lake1.1");
        }

    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     *
     * @return If texture is available or not.
     */
    @SuppressWarnings("WeakerAccess")
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
     * @return Texture of the given animation frame.
     */
    private Texture getTextureFromAnimation(String id, AnimationManager animationManager) {
        String id1 = id.replaceAll("__ANIMATION_", "");
        String[] split = id1.split(":");
        Texture texture = animationManager.getKeyFrameFromAnimation(split[0], Integer.valueOf(split[1]));
        if (texture == null) {
            // System.out.println("getTextureFromAnimation did not find texture");
            return null;
        }

        textureMap.put(id, texture);
        return texture;
    }
}
