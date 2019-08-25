package deco2800.skyfall.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.managers.AnimationManager;
import deco2800.skyfall.managers.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Links the Entity and Non-Looping animations for ease of use for the
 * Renderer3D.
 */
public class AnimationLinker {

    private int[] offset;
    private AnimationRole type;
    private String animationName;
    private Animation<TextureRegion> animation;
    private float startingTime;
    private static AnimationManager animationManager = GameManager.get().getManager(AnimationManager.class);
    private final Logger logger = LoggerFactory.getLogger(AnimationLinker.class);
    private boolean isCompleted = false;
    private Direction direction;
    private boolean looping;
    /**
     * Construct
     * @param type Animation Type
     * @param animationName The name of the animation name
     * @param direction Direction the animation is in
     */
    public AnimationLinker(String animationName, AnimationRole type, Direction direction, boolean looping) {
            this.type = type;
        this.animationName = animationName;

        this.animation = animationManager.getAnimation(animationName);
        if (animation == null) {
            logger.error(animationName + " for entity" + "not found.");
        }

        this.startingTime = 0f;
        this.offset = new int[2];
        this.looping = looping;
        this.direction = direction;
    }
     /**
      * Increments the internal animation time
      * @param incr Time to increment by this is done by Renderer3D.
     **/
    public void incrTime(float incr) {
        this.startingTime += incr;
    }

    public void resetStartingTime() {
        this.startingTime = 0f;
    }

    /*GETTERS AND SETTERS*/

    public String getAnimationName() {
        return animationName;
    }

    public AnimationRole getType() {
        return type;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }


    public void setAnimationName(String animationName) {
        this.animationName = animationName;
    }

    public float getStartingTime() {
        return startingTime;
    }


    public int[] getOffset() {
        return offset;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
