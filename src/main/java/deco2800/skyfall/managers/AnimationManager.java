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


        this.generateAnimationObject("spider_defence","resources/spiderSheet/SpiderAnimation.atlas",DEFAULT_FRAME_RATE);
        this.generateAnimationObject("robot_defence","resources/robotSheet/robotAnimation.atlas",DEFAULT_FRAME_RATE);
        this.generateAnimationObject("stoneJNE","resources/EnemyAnimationPacked/northEastJump/stoneJNE.atlas",0.2f);
        this.generateAnimationObject("stoneJN","resources/EnemyAnimationPacked/northJump/stoneJN.atlas",0.2f);
        this.generateAnimationObject("stoneJNW","resources/EnemyAnimationPacked/northWestJump/stoneJNW.atlas",0.2f);
        this.generateAnimationObject("stoneJS","resources/EnemyAnimationPacked/southJump/stoneJS.atlas",0.2f);
        this.generateAnimationObject("stoneJSW","resources/EnemyAnimationPacked/southWestJump/stoneJSW.atlas",0.2f);
        this.generateAnimationObject("stoneJSE","resources/EnemyAnimationPacked/southEastJump/stoneJSE.atlas",0.2f);
        this.generateAnimationObject("stoneANW","resources/EnemyAnimationPacked/attackAnimation/" +
                "stoneAttackNorthWest/stoneANW.atlas",0.2f);
        this.generateAnimationObject("stoneAS","resources/EnemyAnimationPacked/attackAnimation/" +
                "stoneAttackSouth/stoneAS.atlas",0.2f);
        this.generateAnimationObject("stoneASE","resources/EnemyAnimationPacked/attackAnimation/" +
                "stoneAttackSouthEast/stoneASE.atlas",0.2f);
        this.generateAnimationObject("stoneASW","resources/EnemyAnimationPacked/attackAnimation/" +
                "stoneAttackSouthWest/stoneASW.atlas",0.2f);
        this.generateAnimationObject("flower_defence","resources/enemyFlowerSheet/flower.atlas",0.2f);

        //Animation of treeman
        this.generateAnimationObject("treemanME","resources/enemyTreemanMovementSheet/eastMovement/eastMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMN","resources/enemyTreemanMovementSheet/northMovement/northMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMSE","resources/enemyTreemanMovementSheet/southEastMovement/southEastMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMS","resources/enemyTreemanMovementSheet/southMovement/southMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMSW","resources/enemyTreemanMovementSheet/southWestMovement/southWestMovement.atlas",0.2f);
        this.generateAnimationObject("treemanMW","resources/enemyTreemanMovementSheet/westMovement/westMovement.atlas",0.2f);
        this.generateAnimationObject("treemanAE","resources/EnemyAnimationPacked/TreemanAttackAnimation/" +
                "eastAttack/eastAttack.atlas",0.2f);
        this.generateAnimationObject("treemanAN","resources/EnemyAnimationPacked/TreemanAttackAnimation/" +
                "northAttack/northAttack.atlas",0.2f);
        this.generateAnimationObject("treemanAS","resources/EnemyAnimationPacked/TreemanAttackAnimation/" +
                "southAttack/southAttack.atlas",0.2f);
        this.generateAnimationObject("treemanASE","resources/EnemyAnimationPacked/TreemanAttackAnimation/" +
                "southEastAttack/southEastAttack.atlas",0.2f);
        this.generateAnimationObject("treemanASW","resources/EnemyAnimationPacked/TreemanAttackAnimation/" +
                "southWestAttack/southWestAttack.atlas",0.2f);
        this.generateAnimationObject("treemanAW","resources/EnemyAnimationPacked/TreemanAttackAnimation/" +
                "westAttack/westAttack.atlas",0.2f);

        this.generateAnimationObject("treemanDead","resources/enemyTreemanDeadSheet/TreemanDead.atlas",0.2f);


        this.generateAnimationObject("flower_melee","resources/enemyFlowerMelee/FlowerMelee.atlas",0.2f);
        this.generateAnimationObject("flower_close","resources/enemyFlowerClose/FlowerClose.atlas",0.2f);

        this.generateAnimationObject("MainCharacterN_Anim",
                "MainCharacterN_Anim",
                729, 1134, 0.2f);

        this.generateAnimationObject("MainCharacterNE_Anim",
                "MainCharacterNE_Anim",
                740, 1143, 0.2f);

        this.generateAnimationObject("MainCharacterE_Anim",
                "MainCharacterE_Anim",
                714, 1125, 0.2f);

        this.generateAnimationObject("MainCharacterSE_Anim",
                "MainCharacterSE_Anim",
                729, 1128, 0.2f);

        this.generateAnimationObject("MainCharacterS_Anim",
                "MainCharacterS_Anim",
                729, 1134, 0.2f);

        this.generateAnimationObject("MainCharacterSW_Anim",
                "MainCharacterSW_Anim",
                729, 1129, 0.2f);

        this.generateAnimationObject("MainCharacterW_Anim",
                "MainCharacterW_Anim",
                714, 1125, 0.2f);

        this.generateAnimationObject("MainCharacterNW_Anim",
                "MainCharacterNW_Anim",
                743, 1147, 0.2f);

        this.generateAnimationObject("MainCharacter_Attack_E_Anim",
                "MainCharacter_Attack_E_Anim",
                678, 1029, 0.2f);

        this.generateAnimationObject("MainCharacter_Hurt_E_Anim",
                "MainCharacter_Hurt_E_Anim",
                667, 1032, 0.2f);

        this.generateAnimationObject("MainCharacter_Dead_E_Anim",
                "MainCharacter_Dead_E_Anim",
                727, 861, 0.2f);

        System.out.println("All animations in game");
    }

    /**
     * Constructor used for testing.
     * @param test
     */
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

        LOGGER.info("After split, Width: " + width + " Height: " +  height);

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
            LOGGER.error("Texture:" + textureName + "not found.");
            return;
        }
        Texture texture = textureManager.getTexture(textureName);
        LOGGER.info("Texture has been fetched " + textureName);

        TextureRegion[][] tmpFrames = TextureRegion.split(texture, tileWidth, tileHeight);
        //Assuming tmpFrames is a matrix;
        TextureRegion[] animationFrames = convert2DTo1D(tmpFrames);
        animationMap.put(animationName, new Animation<>(frameRate, animationFrames));

        LOGGER.info("Object " + animationName + " has been generated");
    }

    /**
     * Generates an animation from a texture atlas
     * @param animationName Register the animation as this name
     * @param atlasPath The path to look at
     * @param frameRate The frame rate of the animation
     */
    public void generateAnimationObject(String animationName,String atlasPath,float frameRate){
        TextureAtlas textureAtlas=new TextureAtlas(Gdx.files.internal(atlasPath));
        LOGGER.info("textureAtlas file has been fetched");
        animationMap.put(animationName, new Animation<TextureRegion>(frameRate,textureAtlas.getRegions()));
        LOGGER.info("Object " + animationName + " has been generated");
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
