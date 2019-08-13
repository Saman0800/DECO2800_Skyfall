package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager extends AbstractManager {
    private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);
    private Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();

    private final float DEFAULT_FRAME_RATE  = 1f/4f;

    public AnimationManager() {
        this.generateAnimationObject("mario_right",
                "mario_right", 100, 138, DEFAULT_FRAME_RATE);
        this.generateAnimationObject("mario_left",
                "mario_left", 100, 138, DEFAULT_FRAME_RATE);
    }


    /*
        For animation could separated into different file later.
     */
    //TODO: This.
    private void splitTexture() {

    }

    private void convert2DTo1D(){

    }

    public void generateAnimationObject(String animationName, String textureName, int tileWidth, int tileHeight, float frameRate) {

        if (animationMap.containsKey(animationName)) {
            System.out.println("Texture:" + textureName + "not found.");
            return;
        }
        Texture texture = textureManager.getTexture(textureName);
        System.out.println("Texture has been fetched");


        TextureRegion[][] tmpFrames = TextureRegion.split(texture, tileWidth, tileHeight);
        //Assuming tmpFrames is a matrix;
        int height = tmpFrames.length ;
        int width =  tmpFrames[0].length;
        int size = height * width;

        System.out.println("After split, Width: " + width + " Height: " +  height);

        TextureRegion[] animationFrames = new TextureRegion[height * width];

        int index = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }

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
        return null;
    }
}
