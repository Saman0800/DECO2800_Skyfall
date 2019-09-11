package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.managers.*;

public class Clock {

    private EnvironmentManager environmentManager;
    private long currentTime;
    private long moveTime; // maybe for animating it down.
    private  ImageButton RoundedRectangle;
    private Stage stage;
    private float positionX;
    private float positionY;
    private float offset;
    private Label label;


    /**
     * Constructor
     * @param s Stage to display things on
     * @param clockTexture Texture of the clock
     */
    public Clock(Stage s, String clockTexture) {
        this.environmentManager = new EnvironmentManager();

        stage = s;

        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        label = new Label("Time:", new Label.LabelStyle(bitmapFont, Color.WHITE));

        this.RoundedRectangle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
        RoundedRectangle.setSize(100, 100);

        updateWithViewportChanges();

        stage.addActor(RoundedRectangle);
        stage.addActor(label);

    }

    /**
     * Keeps the object on the top left of the screen
     */
    private void updateWithViewportChanges() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 210);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 105);

        RoundedRectangle.setPosition(positionX, positionY);
        label.setPosition(positionX, positionY - 20);
    }

    /**
     * Updates label in accordance with time
     */
    private void updateLabel() {
        if (GameManager.get().getManager(EnvironmentManager.class).getTOD() != null) {
            label.setText("Time: " + GameManager.get().getManager(EnvironmentManager.class).getTOD());
        }
    }

    /**
     * Updates the health circle and the position if the screen has been resized
     */
    public void update() {
        updateWithViewportChanges();
        updateLabel();
    }

}
