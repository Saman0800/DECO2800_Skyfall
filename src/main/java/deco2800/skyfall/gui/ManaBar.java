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

    private ImageButton bar;
    private ImageButton biggerCircle;
    private ImageButton smallerCircle;
    private Stage stage;
    private Label label;
    private int currentValue;
    private int initialValue;
    private float offset;
    private float positionX;
    private float positionY;

    public ManaBar(int currentValue) {

        this.initialValue = currentValue;
        this.currentValue = currentValue;

        stage = GameManager.get().getManager(GameMenuManager.class).getStage();

        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        label = new Label("" + currentValue, new Label.LabelStyle(bitmapFont, Color.BLACK));

        this.biggerCircle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject("mana_bar_inner"));
        biggerCircle.setSize(100, 100);

        this.smallerCircle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject("mana_bar"));
        smallerCircle.setSize(100, 100);

        stage.addActor(biggerCircle);
        stage.addActor(smallerCircle);

        stage.addActor(label);
        label.setAlignment(Align.center);

        updateWithViewportChanges();

    }

    /**
     * Updates
     */
    private void updateInnerCircle(int newValue) {
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
    private void updateWithViewportChanges() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 100);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 300);
        smallerCircle.setPosition(positionX, positionY);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX+38, positionY+20);
    }
}
