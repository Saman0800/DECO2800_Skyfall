package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Stage stage;
    private Label label;

    public ManaBar() {

        stage = GameManager.get().getManager(GameMenuManager.class).getStage();

        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        label = new Label("Mana: 10", new Label.LabelStyle(bitmapFont, Color.WHITE));

        bar = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject("mana_bar"));
        bar.setSize(150, 150);

        stage.addActor(bar);
        stage.addActor(label);

        updateWithViewportChanges();

    }

    public void update() {

    }

    /**
     * Keeps the object in the correct position no matter how window is resized.
     * Inspiration for the method was taken from HealthCircle.java.
     */
    private void updateWithViewportChanges() {
        float positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 120);
        float positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 300);
        bar.setPosition(positionX, positionY);
        label.setPosition(positionX+35, positionY + 50);
    }
}
