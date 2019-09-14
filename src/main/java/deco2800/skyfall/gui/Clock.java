package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.managers.*;
import org.lwjgl.Sys;

public class Clock {

    private EnvironmentManager environmentManager;
    private Image Clock;
    private Stage stage;
    private float positionX;
    private float positionY;
    private String clockTexture;


    /**
     * Constructor
     * @param s Stage to display things on
     */
    public Clock(Stage s) {
        this.environmentManager = new EnvironmentManager();

        stage = s;

        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        clockTexture = "dawn";

        this.Clock = new Image(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));

        updateWithViewportChanges();

        stage.addActor(Clock);
    }

    /**
     * Maintains position of clock with resizes
     */
    private void updateWithViewportChanges() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 280);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 70);

        Clock.setPosition(positionX, positionY);
    }

    /**
     * Updates clock arrow in accordance with time
     */
    private void updateDisplay() {
        long time = GameManager.get().getManager(EnvironmentManager.class).hours;

        if (GameManager.get().getManager(EnvironmentManager.class).getTOD() != null) {
            if (time >= 4 && time <= 9) {
                clockTexture = "dawn";
                Clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 10 && time <= 16) {
                clockTexture = "day";
                Clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 17 && time <= 19) {
                clockTexture = "dusk";
                Clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 20 && time <=  23) {
                clockTexture = "night";
                Clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 0 && time <=  3) {
                clockTexture = "night";
                Clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }
        }
    }

    /**
     * Updates the clock and resizes if necessary
     */
    public void update() {
        updateWithViewportChanges();
        updateDisplay();
    }

}
