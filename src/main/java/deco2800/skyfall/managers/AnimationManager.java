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

    private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);
    /**
     * Maps Animation Name to the Animation object
     */
    private Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    /**
     * For logging error msgs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationManager.class);


    /**
     * Constructor Currently loads up all the animations but probably
     * shouldn't/doesn't need to.
     */
    public AnimationManager() {
        final float DEFAULT_FRAME_RATE  = 1f/4f;
        //These are simply test objects.
        this.generateAnimationObject("mario_right",
                "mario_right", 100, 138, DEFAULT_FRAME_RATE);
        this.generateAnimationObject("mario_left",
                "mario_left", 100, 138, DEFAULT_FRAME_RATE);
        this.generateAnimationObject("spider_defence","resources/spiderSheet/SpiderAnimation.atlas",1.0f);
        this.generateAnimationObject("robot_defence","resources/robotSheet/robotAnimation.atlas",1.0f);
//        this.generateAnimationObject("stoneJNE","resources/EnemyAnimationPacked/northEastJump/northEastJump.atlas",0.2f);
//        this.generateAnimationObject("stoneJN","resources/EnemyAnimationPacked/northJump/northJump.atlas",0.2f);
//        this.generateAnimationObject("stoneJNW","resources/EnemyAnimationPacked/northWestJump/northWestJump.atlas",0.2f);
//        this.generateAnimationObject("stoneJS","resources/EnemyAnimationPacked/southJump/southJump.atlas",0.2f);
//        this.generateAnimationObject("stoneJSW","resources/EnemyAnimationPacked/southWestJump/southWestJump.atlas",0.2f);
//        this.generateAnimationObject("stoneJSE","resources/EnemyAnimationPacked/southEastJump/southEastJump.atlas",0.2f);
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
        if (animationMap.containsKey(animationName)) {
            LOGGER.error("Texture:" + textureName + "not found.");
            return;
        }
        Texture texture = textureManager.getTexture(textureName);
        LOGGER.info("Texture has been fetched");

        TextureRegion[][] tmpFrames = TextureRegion.split(texture, tileWidth, tileHeight);
        //Assuming tmpFrames is a matrix;
        TextureRegion[] animationFrames = convert2DTo1D(tmpFrames);
        animationMap.put(animationName, new Animation<>(frameRate, animationFrames));

        LOGGER.info("Object " + animationName + " has been generated");
    }

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


        if (region.length - 1 <= index) {
            LOGGER.error("Index out of range");
            return null;
        }

        TextureRegion textureRegion = region[index];


        TextureData textureData = textureRegion.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap pixmap = new Pixmap(
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                textureData.getFormat()
        );
        pixmap.drawPixmap(
                textureData.consumePixmap(), // The other Pixmap
                0, // The target x-coordinate (top left corner)
                0, // The target y-coordinate (top left corner)
                textureRegion.getRegionX(), // The source x-coordinate (top left corner)
                textureRegion.getRegionY(), // The source y-coordinate (top left corner)
                textureRegion.getRegionWidth(), // The width of the area from the other Pixmap in pixels
                textureRegion.getRegionHeight() // The height of the area from the other Pixmap in pixels
        );


        Texture texture =  new Texture(pixmap);
        pixmap.dispose(); // save memory
        return texture;
    }
}
