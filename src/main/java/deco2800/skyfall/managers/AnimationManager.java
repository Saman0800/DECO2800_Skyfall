package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager extends AbstractManager {
    private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);
    private Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationManager.class);



    public AnimationManager() {
        final float DEFAULT_FRAME_RATE  = 1f/4f;
        //These are simply test objects.
        this.generateAnimationObject("mario_right",
                "mario_right", 100, 138, DEFAULT_FRAME_RATE);
        this.generateAnimationObject("mario_left",
                "mario_left", 100, 138, DEFAULT_FRAME_RATE);
    }


    /*
        For animation could separated into different file later.
     */

    private TextureRegion[] convert2DTo1D(TextureRegion[][] tmpFrames){
        int height = tmpFrames.length ;
        int width =  tmpFrames[0].length;
        int size = height * width;

        System.out.println("After split, Width: " + width + " Height: " +  height);

        TextureRegion[] animationFrames = new TextureRegion[size];

        int index = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }

        return animationFrames;
    }

    public void generateAnimationObject(String animationName, String textureName, int tileWidth, int tileHeight, float frameRate) {

        if (animationMap.containsKey(animationName)) {
            LOGGER.error("Texture:" + textureName + "not found.");
            return;
        }
        Texture texture = textureManager.getTexture(textureName);
        System.out.println("Texture has been fetched");

        TextureRegion[][] tmpFrames = TextureRegion.split(texture, tileWidth, tileHeight);
        //Assuming tmpFrames is a matrix;
        TextureRegion[] animationFrames = convert2DTo1D(tmpFrames);
        animationMap.put(animationName, new Animation<>(frameRate, animationFrames));

        System.out.println("Object " + animationName + " has been generated");
    }

    public Animation<TextureRegion> getAnimation(String animationName) {
        if (animationMap.containsKey(animationName)) {
            return animationMap.get(animationName);
        }

        return null;
    }


    /**Please read the gitlab documentation on animations for more info**/
    public Texture getKeyFrameFromAnimation(String animationName, int index) {
        Animation<TextureRegion> animation = animationMap.get(animationName);

        if (animation == null) {
            return null;
        }
        TextureRegion region[] = animation.getKeyFrames();
        System.out.println(region.length);

        if (region.length - 1 <= index) {
            System.out.println("Index out of range");
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
