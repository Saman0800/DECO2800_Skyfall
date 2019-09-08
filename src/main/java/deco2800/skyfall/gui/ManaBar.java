package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;

/**
 * This should be used by the MainCharacter class, and only called when damage is taken in order to update it.
 *
 * Inspired from HealthCircle.java, but this is held in the MainCharacter class
 * and it only needs to be updated when the character takes/restores mana.
 *
 * TODO inherit a onWindowResize event
 */
public class ManaBar extends StatBar {

    public ManaBar(int currentValue, String biggerTextureName, String smallerTextureName) {
        super (currentValue,"Mana",biggerTextureName,smallerTextureName);
    }

    /**
     * Updates
     */
    protected void updateInnerCircle(int newValue) {
        float diff = initialValue - newValue;

        if (smallerCircle == null) {
            System.out.println("Smaller circle is null");
            if (biggerCircle == null) {
                System.out.println("Bigger circle is null");
                return;
            }
            return;
        }

        float percentageDiff = (float)newValue/(float)initialValue;
        int height = (int) (100 * percentageDiff);

        smallerCircle.setSize(100, height);
        //smallerCircle.setScaleY(percentageDiff);
        //offset += (diff * 10) / 2;
        smallerCircle.setPosition(positionX, positionY);
        currentValue = newValue;
        label.setText(""+newValue);
    }

    /**
     * Keeps the object in the correct position no matter how window is resized.
     * Inspiration for the method was taken from HealthCircle.java.
     */
    protected void updateWithViewportChanges() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 100);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 300);
        smallerCircle.setPosition(positionX, positionY);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX+20, positionY+20);
    }
}
