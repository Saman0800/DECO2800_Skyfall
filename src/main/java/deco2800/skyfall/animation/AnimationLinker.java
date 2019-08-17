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

    private final String entityName;
    private int offset[];
    private AnimationRole type;
    private String animationName;
    private Animation<TextureRegion> animation;
    private float startingTime;
    private static AnimationManager animationManager = GameManager.get().getManager(AnimationManager.class);
    private final Logger logger = LoggerFactory.getLogger(AnimationLinker.class);
    private boolean isCompleted = false;

    public AnimationLinker(AnimationRole type, String animationName,
                           String entityName, int offset[]) {
        this.type = type;
        this.animationName = animationName;
        this.entityName = entityName;
        
        this.animation = animationManager.getAnimation(animationName);
        if (animation == null) {
            logger.error(animationName + " for entity " + entityName + "not found.");
        }
        if (offset.length != 2) {
            logger.error(animationName + " for entity " + entityName + ": incorrect offset specified. Must of length 2");

        }
        this.startingTime = 0f;
        this.offset = new int[2];
        this.offset[0] = offset[0];
        this.offset[1] = offset[1];
    }
     /**
      * Increments the internal animation time
     **/
    public void incrTime(float incr) {
        this.startingTime += incr;
    }


    /*GETTERS AND SETTERS*/

    public String getEntityName() {
        return entityName;
    }

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
