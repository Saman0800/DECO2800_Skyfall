package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the animations for the game.
 */
public class AnimationManager extends AbstractManager {

    private TextureManager textureManager;
    /**
     * Maps Animation Name to the Animation object
     */
    private Map<String, Animation<TextureRegion>> animationMap ;
    /**
     * For logging error msgs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationManager.class);

    /**
     * Constructor Currently loads up all the animations but probably
     * shouldn't/doesn't need to.
     */
    public AnimationManager() {
        textureManager = GameManager.getManagerFromInstance(TextureManager.class);
        animationMap = new HashMap<>();
        final float DEFAULT_FRAME_RATE  = 1f/4f;

        //These are simply test objects.
        this.generateAnimationObject("mario_right",
                "mario_right", 100, 138, DEFAULT_FRAME_RATE);
        this.generateAnimationObject("mario_left",
                "mario_left", 100, 138, DEFAULT_FRAME_RATE);

        // Add bike animation
        this.generateAnimationObject("bikeW","resources/Bike_Left_Animation/left.atlas",0.05f);
        this.generateAnimationObject("bikeE","resources/Bike_Right_Animation/right.atlas",0.05f);

        this.generateAnimationObject("whitebearNE","resources/whitebearright/whitebearright.atlas",0.05f);
        this.generateAnimationObject("whitebearN","resources/whitebearback/whitebearback.atlas",0.05f);
        this.generateAnimationObject("whitebearNW","resources/whitebearleft/whitebearleft.atlas",0.05f);
        this.generateAnimationObject("whitebearS","resources/whitebearfront/whitebearfront.atlas",0.05f);
        this.generateAnimationObject("whitebearSW","resources/whitebearleft/whitebearleft.atlas",0.05f);
        this.generateAnimationObject("whitebearSE","resources/whitebearright/whitebearright.atlas",0.05f);

        // Pets
        this.generateAnimationObject("tigerFront","resources/petTigerMovement/tigerMovementFront/tigerMovementFront.atlas",0.2f);

        // New Enemies
        this.generateAnimationObject("ScoutMoveN",
                "enemyScout_Move_N",
                208, 279, 0.2f);
        this.generateAnimationObject("ScoutMoveS",
                "enemyScout_Move_S",
                208, 278, 0.2f);
        this.generateAnimationObject("ScoutMoveE",
                "enemyScout_Move_E",
                177, 265, 0.2f);
        this.generateAnimationObject("ScoutMoveW",
                "enemyScout_Move_W",
                166, 265, 0.2f);
        this.generateAnimationObject("ScoutMoveNE",
                "enemyScout_Move_NE",
                179, 265, 0.2f);
        this.generateAnimationObject("ScoutMoveSE",
                "enemyScout_Move_SE",
                178, 255, 0.2f);
        this.generateAnimationObject("ScoutMoveSW",
                "enemyScout_Move_SW",
                184, 254, 0.2f);
        this.generateAnimationObject("ScoutMoveNW",
                "enemyScout_Move_NW",
                178, 265, 0.2f);

        this.generateAnimationObject("AbductorMoveN",
                "enemyAbductor_Move_N",
                151, 306, 0.2f);
        this.generateAnimationObject("AbductorMoveS",
                "enemyAbductor_Move_S",
                149, 306, 0.2f);
        this.generateAnimationObject("AbductorMoveE",
                "enemyAbductor_Move_E",
                150, 306, 0.2f);
        this.generateAnimationObject("AbductorMoveW",
                "enemyAbductor_Move_W",
                150, 307, 0.2f);
        this.generateAnimationObject("AbductorMoveNE",
                "enemyAbductor_Move_NE",
                148, 306, 0.2f);
        this.generateAnimationObject("AbductorMoveSE",
                "enemyAbductor_Move_SE",
                150, 305, 0.2f);
        this.generateAnimationObject("AbductorMoveSW",
                "enemyAbductor_Move_SW",
                149, 306, 0.2f);
        this.generateAnimationObject("AbductorMoveNW",
                "enemyAbductor_Move_NW",
                149, 306, 0.2f);

        this.generateAnimationObject("HeavyMoveN",
                "enemyHeavy_Move_N",
                224, 348, 0.13f);
        this.generateAnimationObject("HeavyMoveS",
                "enemyHeavy_Move_S",
                224, 349, 0.13f);
        this.generateAnimationObject("HeavyMoveE",
                "enemyHeavy_Move_E",
                222, 349, 0.13f);
        this.generateAnimationObject("HeavyMoveW",
                "enemyHeavy_Move_W",
                222, 350, 0.13f);
        this.generateAnimationObject("HeavyMoveNE",
                "enemyHeavy_Move_NE",
                222, 349, 0.13f);
        this.generateAnimationObject("HeavyMoveSE",
                "enemyHeavy_Move_SE",
                221, 349, 0.13f);
        this.generateAnimationObject("HeavyMoveSW",
                "enemyHeavy_Move_SW",
                221, 352, 0.13f);
        this.generateAnimationObject("HeavyMoveNW",
                "enemyHeavy_Move_NW",
                220, 353, 0.13f);

        this.generateAnimationObject("HeavyAttackN",
                "enemyHeavy_Attack_N",
                220, 341, 0.2f);
        this.generateAnimationObject("HeavyAttackS",
                "enemyHeavy_Attack_S",
                223, 341, 0.2f);
        this.generateAnimationObject("HeavyAttackE",
                "enemyHeavy_Attack_E",
                221, 342, 0.2f);
        this.generateAnimationObject("HeavyAttackW",
                "enemyHeavy_Attack_W",
                221, 342, 0.2f);
        this.generateAnimationObject("HeavyAttackNE",
                "enemyHeavy_Attack_NE",
                214, 341, 0.2f);
        this.generateAnimationObject("HeavyAttackSE",
                "enemyHeavy_Attack_SE",
                220, 348, 0.2f);
        this.generateAnimationObject("HeavyAttackSW",
                "enemyHeavy_Attack_SW",
                217, 347, 0.2f);
        this.generateAnimationObject("HeavyAttackNW",
                "enemyHeavy_Attack_NW",
                215, 341, 0.2f);

        this.generateAnimationObject("HeavyDamageN",
                "enemyHeavy_Damage_N",
                225, 341, 0.2f);
        this.generateAnimationObject("HeavyDamageS",
                "enemyHeavy_Damage_S",
                225, 341, 0.2f);
        this.generateAnimationObject("HeavyDamageE",
                "enemyHeavy_Damage_E",
                224, 342, 0.2f);
        this.generateAnimationObject("HeavyDamageW",
                "enemyHeavy_Damage_W",
                224, 342, 0.2f);
        this.generateAnimationObject("HeavyDamageNE",
                "enemyHeavy_Damage_NE",
                224, 341, 0.2f);
        this.generateAnimationObject("HeavyDamageSE",
                "enemyHeavy_Damage_SE",
                224, 341, 0.2f);
        this.generateAnimationObject("HeavyDamageSW",
                "enemyHeavy_Damage_SW",
                227, 347, 0.2f);
        this.generateAnimationObject("HeavyDamageNW",
                "enemyHeavy_Damage_NW",
                224, 341, 0.2f);
        this.generateAnimationObject("enemyDie",
                "enemyDie",
                369, 357, 0.4f);

        // Main Character
        // Walking animations
        this.generateAnimationObject("MainCharacterN_Anim",
                "MainCharacterN_Anim",
                729, 1134, 0.11f);

        this.generateAnimationObject("MainCharacterNE_Anim",
                "MainCharacterNE_Anim",
                740, 1143, 0.11f);

        this.generateAnimationObject("MainCharacterE_Anim",
                "MainCharacterE_Anim",
                714, 1125, 0.11f);

        this.generateAnimationObject("MainCharacterSE_Anim",
                "MainCharacterSE_Anim",
                729, 1128, 0.11f);

        this.generateAnimationObject("MainCharacterS_Anim",
                "MainCharacterS_Anim",
                729, 1134, 0.11f);

        this.generateAnimationObject("MainCharacterSW_Anim",
                "MainCharacterSW_Anim",
                729, 1129, 0.11f);

        this.generateAnimationObject("MainCharacterW_Anim",
                "MainCharacterW_Anim",
                714, 1125, 0.11f);

        this.generateAnimationObject("MainCharacterNW_Anim",
                "MainCharacterNW_Anim",
                743, 1147, 0.11f);
        // Attack animations
        this.generateAnimationObject("MainCharacter_Attack_E_Anim",
                "MainCharacter_Attack_E_Anim",
                731, 1130, 0.09f);
        this.generateAnimationObject("MainCharacter_Attack_N_Anim",
                "MainCharacter_Attack_N_Anim",
                771, 1135, 0.09f);
        this.generateAnimationObject("MainCharacter_Attack_W_Anim",
                "MainCharacter_Attack_W_Anim",
                809, 1125, 0.09f);
        // Hurt animations
        this.generateAnimationObject("MainCharacter_Hurt_E_Anim",
                "MainCharacter_Hurt_E_Anim",
                750, 1161, 0.15f);
        this.generateAnimationObject("MainCharacter_Hurt_W_Anim",
                "MainCharacter_Hurt_W_Anim",
                788, 1118, 0.15f);
        // Dead animations
        this.generateAnimationObject("MainCharacter_Dead_E_Anim",
                "MainCharacter_Dead_E_Anim",
                1020, 1167, 0.2f);
        this.generateAnimationObject("MainCharacter_Dead_L_Anim",
                "MainCharacter_Dead_L_Anim",
                1072, 1128, 0.2f);
        this.generateAnimationObject("MainCharacter_Dead_R_Anim",
                "MainCharacter_Dead_R_Anim",
                1032, 1123, 0.2f);
        this.generateAnimationObject("MainCharacter_Dead_W_Anim",
                "MainCharacter_Dead_W_Anim",
                1020, 1115, 0.2f);
        this.generateAnimationObject("MainCharacter_Dead_E_Still",
                "MainCharacter_Dead_E_Still",
                1216, 1293, 50f);

        this.generateAnimationObject("Spells_Fire_Anim",
                "spells_fire_Anim",
                184, 278, 0.12f);

        this.generateAnimationObject("Spells_Shield_Anim",
                "spells_shield_Anim",
                638, 515, 0.12f);
        this.generateAnimationObject("Spells_Shield_Still",
                "spells_shield_Still",
                629, 515, 50f);

        this.generateAnimationObject("Spells_Tornado_Anim",
                "spells_tornado_Anim",
                303, 337, 0.12f);

        LOGGER.info("All animations in game");

    }

    /**
     * Constructor used for testing.
     * @param test Whether the manager is tested.
     */
    @SuppressWarnings("WeakerAccess")
    public AnimationManager(boolean test) {

    }
    /*
        For animation could separated into different file later.
     */

    /**
     * Converts the 2D array of texture regions to 1D row array
     * @param tmpFrames The 2D array after split
     * @return The 1D array
     */
    private TextureRegion[] convert2DTo1D(TextureRegion[][] tmpFrames){
        int height = tmpFrames.length ;
        int width =  tmpFrames[0].length;
        int size = height * width;

        TextureRegion[] animationFrames = new TextureRegion[size];

        int index = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }
        
        return animationFrames;
    }

    /**
     * Creates animation objects
     * @param animationName The name the animation will be put into the animationMap as
     * @param textureName Sprite sheet name in the texture manager
     * @param tileWidth Width of each sprite in the sprite sheet
     * @param tileHeight Height of each sprite in the sprite sheet
     * @param frameRate Framerate of the generate animation object
     */
    public void generateAnimationObject(String animationName, String textureName, int tileWidth, int tileHeight, float frameRate) {
        if (!textureManager.hasTexture(textureName)) {
            LOGGER.error("Texture: {} not found.", textureName);
            return;
        }
        Texture texture = textureManager.getTexture(textureName);

        LOGGER.info("Texture has been fetched {} " , textureName);

        TextureRegion[][] tmpFrames = TextureRegion.split(texture, tileWidth, tileHeight);

        //Assuming tmpFrames is a matrix
        TextureRegion[] animationFrames = convert2DTo1D(tmpFrames);
        animationMap.put(animationName, new Animation<>(frameRate, animationFrames));

        LOGGER.info("Object {} has been generated", animationName);
    }

    /**
     * Generates an animation from a texture atlas
     * @param animationName Register the animation as this name
     * @param atlasPath The path to look at
     * @param frameRate The frame rate of the animation
     */
    private void generateAnimationObject(String animationName,String atlasPath,float frameRate){
        TextureAtlas textureAtlas=new TextureAtlas(Gdx.files.internal(atlasPath));
        LOGGER.info("textureAtlas file has been fetched");
        animationMap.put(animationName, new Animation<TextureRegion>(frameRate,textureAtlas.getRegions()));
        LOGGER.info("Object {} has been generated", animationName);
    }

    /**
     * Gets an animation object
     * @param animationName The name of the animation to retrieve
     * @return Animation object with the associate name or null
     */
    public Animation<TextureRegion> getAnimation(String animationName) {
        if (animationMap.containsKey(animationName)) {
            return animationMap.get(animationName);
        }

        return null;
    }


    /**
     * Please read the gitlab documentation on animations for more info.
     * Generates texture from animation and sprite number
     * @param animationName The name of the animation to get the sprite from.
     * @param index The sprite to load into a texture
     * @return Texture of key frame in the animation. Null if animation cannot
     * be found or index is out of range
     */
    public Texture getKeyFrameFromAnimation(String animationName, int index) {
        Animation<TextureRegion> animation = animationMap.get(animationName);

        if (animation == null) {
            return null;
        }
        TextureRegion[] region = animation.getKeyFrames();

        if (region.length  <= index) {
            LOGGER.error("Index out of range");
            return null;
        }

        TextureRegion textureRegion = region[index];


        //Generates a texture
        TextureData textureData = textureRegion.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap pixmap = new Pixmap(
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                textureData.getFormat()
        );
        pixmap.drawPixmap(textureData.consumePixmap(), 0, 0,
                textureRegion.getRegionX(),
                textureRegion.getRegionY(),
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight());


        Texture texture =  new Texture(pixmap);
        pixmap.dispose(); // save memory
        return texture;
    }
}
