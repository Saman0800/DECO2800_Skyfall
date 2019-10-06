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

        this.generateAnimationObject("whitebearNE","resources/whitebearright/whitebearright.atlas",0.05f);
        this.generateAnimationObject("whitebearN","resources/whitebearback/whitebearback.atlas",0.05f);
        this.generateAnimationObject("whitebearNW","resources/whitebearleft/whitebearleft.atlas",0.05f);
        this.generateAnimationObject("whitebearS","resources/whitebearfront/whitebearfront.atlas",0.05f);
        this.generateAnimationObject("whitebearSW","resources/whitebearleft/whitebearleft.atlas",0.05f);
        this.generateAnimationObject("whitebearSE","resources/whitebearright/whitebearright.atlas",0.05f);

        // Enemies
        this.generateAnimationObject("spider_defence","resources/enemyOld/spiderSheet/SpiderAnimation.atlas",DEFAULT_FRAME_RATE);
        this.generateAnimationObject("robot_defence","resources/enemyOld/robotSheet/robotAnimation.atlas",DEFAULT_FRAME_RATE);

        String enemyOldAnimPackPath = "resources/enemyOld/EnemyAnimationPacked/";
        this.generateAnimationObject("stoneJNE",enemyOldAnimPackPath + "northEastJump/stoneJNE.atlas",0.2f);
        this.generateAnimationObject("stoneJN", enemyOldAnimPackPath + "northJump/stoneJN.atlas",0.2f);
        this.generateAnimationObject("stoneJNW",enemyOldAnimPackPath + "northWestJump/stoneJNW.atlas",0.2f);
        this.generateAnimationObject("stoneJS", enemyOldAnimPackPath + "southJump/stoneJS.atlas",0.2f);
        this.generateAnimationObject("stoneJSW",enemyOldAnimPackPath + "southWestJump/stoneJSW.atlas",0.2f);
        this.generateAnimationObject("stoneJSE",enemyOldAnimPackPath + "southEastJump/stoneJSE.atlas",0.2f);
        this.generateAnimationObject("stoneANW",enemyOldAnimPackPath + "attackAnimation/stoneAttackNorthWest/stoneANW.atlas",0.2f);
        this.generateAnimationObject("stoneAS", enemyOldAnimPackPath + "attackAnimation/stoneAttackSouth/stoneAS.atlas",0.2f);
        this.generateAnimationObject("stoneASE",enemyOldAnimPackPath + "attackAnimation/stoneAttackSouthEast/stoneASE.atlas",0.2f);
        this.generateAnimationObject("stoneASW",enemyOldAnimPackPath + "attackAnimation/stoneAttackSouthWest/stoneASW.atlas",0.2f);

        String enemyOldTreemanPath = "resources/enemyOld/enemyTreemanMovementSheet/";
        this.generateAnimationObject("treemanME", enemyOldTreemanPath + "eastMovement/eastMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMN", enemyOldTreemanPath + "northMovement/northMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMNE",enemyOldTreemanPath + "northMovement/northMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMSE",enemyOldTreemanPath + "southEastMovement/southEastMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMS", enemyOldTreemanPath + "southMovement/southMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMSW",enemyOldTreemanPath + "southWestMovement/southWestMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMW", enemyOldTreemanPath + "westMovement/westMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMNW",enemyOldTreemanPath + "westMovement/westMovement.atlas",0.2f);

        this.generateAnimationObject("treemanAE", enemyOldAnimPackPath + "TreemanAttackAnimation/eastAttack/eastAttack.atlas",0.2f);
        this.generateAnimationObject("treemanAN", enemyOldAnimPackPath + "TreemanAttackAnimation/northAttack/northAttack.atlas",0.2f);
        this.generateAnimationObject("treemanAS", enemyOldAnimPackPath + "TreemanAttackAnimation/southAttack/southAttack.atlas",0.2f);
        this.generateAnimationObject("treemanASE",enemyOldAnimPackPath + "TreemanAttackAnimation/southEastAttack/southEastAttack.atlas",0.2f);
        this.generateAnimationObject("treemanASW",enemyOldAnimPackPath + "TreemanAttackAnimation/southWestAttack/southWestAttack.atlas",0.2f);
        this.generateAnimationObject("treemanAW",enemyOldAnimPackPath +  "TreemanAttackAnimation/westAttack/westAttack.atlas",0.2f);

        this.generateAnimationObject("treemanDead","resources/enemyOld/enemyTreemanDeadSheet/TreemanDead.atlas",0.2f);
        this.generateAnimationObject("flower_defence","resources/enemyOld/enemyFlowerSheet/flower.atlas",0.2f);
        this.generateAnimationObject("flower_melee","resources/enemyOld/enemyFlowerMelee/FlowerMelee.atlas",0.2f);
        this.generateAnimationObject("flower_close","resources/enemyOld/enemyFlowerClose/FlowerClose.atlas",0.2f);

        // Pets
        this.generateAnimationObject("tigerFront","resources/petTigerMovement/tigerMovementFront/tigerMovementFront.atlas",0.2f);

        // Main Character
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

        this.generateAnimationObject("MainCharacter_Attack_E_Anim",
                "MainCharacter_Attack_E_Anim",
                731, 1130, 0.09f);

        this.generateAnimationObject("MainCharacter_Hurt_E_Anim",
                "MainCharacter_Hurt_E_Anim",
                750, 1161, 0.2f);

        this.generateAnimationObject("MainCharacter_Dead_E_Anim",
                "MainCharacter_Dead_E_Anim",
                1020, 1167, 0.2f);
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
