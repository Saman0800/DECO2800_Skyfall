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
    private final Logger logger = LoggerFactory.getLogger(AnimationLinker.class);
    private boolean isCompleted = false;
    private boolean looping;
    /**
     * Construct
     * @param type Animation Type
     * @param animationName The name of the animation name
     * @param direction Direction the animation is in
     * @param fetchAnimation Whether to get animation straight away
     * @param looping Is the animation looping
     */
    public AnimationLinker(String animationName, AnimationRole type,
               Direction direction, boolean looping, boolean fetchAnimation) {
        if (fetchAnimation) {
            getAnimation(animationName);
        }
        this.type = type;
        this.animationName = animationName;
        this.startingTime = 0f;
        this.offset = new int[2];
        this.looping = looping;
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

    public boolean isLooping() {
        return looping;
    }
    /**
     * Gets the animation from the animation manager.
     * @param animationName The animation name as registered in the animation manager
     */
    private void getAnimation(String animationName) {
        AnimationManager animationManager =
                GameManager.get().getManager(AnimationManager.class);
        try {
            this.animation = animationManager.getAnimation(animationName);
        } catch (Exception e) {
            logger.error("BAD! Could not find ANIMATION MANAGER");
            this.animation = null;
        }

        if (this.animation == null && logger.isDebugEnabled()) {
            logger.error(String.format("%s for entity not found.", animationName));
        }
    }
}
