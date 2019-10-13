package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import deco2800.skyfall.resources.HealthResources;

/**
 * We have a HealthCircle and a Manabar, need to join some common logic.
 *
 * Manabar and healthcircle will perform almost identical with different
 * textures. Just need to pull the mana instead of health from mc.
 */
public abstract class StatBar {

    protected ImageButton biggerCircle;
    protected ImageButton smallerCircle;
    protected Stage stage;
    protected Label label;
    protected int currentValue;
    protected int initialValue;
    protected float offset;
    protected float positionX;
    protected float positionY;
    protected String attributeName;
    protected HealthResources healthResources;

    /**
     *
     * @param currentValue
     * @param attributeName
     * @param biggerTextureName
     * @param smallerTextureName
     */
    public StatBar(int currentValue, String attributeName, String biggerTextureName, String smallerTextureName) {
        // Do nothing for now
    }

    protected abstract void updateInnerCircle(int newValue);

    /**
     * Updates the health circle and the position if the screen has been resized
     */
    public void update(int newValue) {

        updateWithViewportChanges();
        if ((currentValue - newValue) >= 0) {
            updateInnerCircle(newValue);
        }
    }

    /**
     * Keeps the object in the correct position no matter how window is resized.
     * Inspiration for the method was taken from HealthCircle.java.
     */
    protected abstract void updateWithViewportChanges();

}
