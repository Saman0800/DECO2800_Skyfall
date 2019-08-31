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
//        this.generateAnimationObject("mario_right",
//                "mario_right", 100, 138, DEFAULT_FRAME_RATE);
//        this.generateAnimationObject("mario_left",
//                "mario_left", 100, 138, DEFAULT_FRAME_RATE);
        this.generateAnimationObject("spider_defence","resources/spiderSheet/SpiderAnimation.atlas",DEFAULT_FRAME_RATE);
        this.generateAnimationObject("robot_defence","resources/robotSheet/robotAnimation.atlas",DEFAULT_FRAME_RATE);

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
