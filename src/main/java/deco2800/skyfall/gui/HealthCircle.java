package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;

    public class HealthCircle extends StatBar{


    public HealthCircle(int currentValue, String biggerTextureName, String smallerTextureName) {
        super (currentValue,"Health",biggerTextureName,smallerTextureName);
    }

    /**
     * Keeps the object on the top left of the screen
     */
    protected void updateWithViewportChanges() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 100);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 100);
        smallerCircle.setPosition(positionX + offset, positionY + offset);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX + 15, positionY + 40);
    }

    /**
     * Updates
     */
    protected void updateInnerCircle(int newValue) {
        float diff = currentValue - newValue;

        if (smallerCircle == null) {
            System.out.println("Smaller circle is null");
            if (biggerCircle == null) {
                System.out.println("Bigger circle is null");
                return;
            }
            return;
        }

        smallerCircle.setSize(10 * newValue, 10 * newValue);
        offset += (diff * 10) / 2;
        smallerCircle.setPosition(positionX + offset, positionY + offset);
        currentValue = newValue;
        label.setText("Health: " + newValue);
    }


}
