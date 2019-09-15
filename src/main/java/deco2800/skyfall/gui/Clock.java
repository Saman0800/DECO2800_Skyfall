package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import deco2800.skyfall.managers.*;

public class Clock {
    private Image clockDisplay;
    private Stage stage;
    private String clockTexture;


    /**
     * Constructor
     * @param s Stage to display things on
     */
    public Clock(Stage s) {
        stage = s;

        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        clockTexture = "dawn";

        this.clockDisplay = new Image(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));

        updateWithViewportChanges();

        stage.addActor(clockDisplay);
    }

    /**
     * Maintains position of clockDisplay with resizes
     */
    private void updateWithViewportChanges() {
        float positionX;
        float positionY;

        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 280);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 70);

        clockDisplay.setPosition(positionX, positionY);
    }

    /**
     * Updates clockDisplay arrow in accordance with time
     */
    private void updateDisplay() {
        int time = GameManager.get().getManager(EnvironmentManager.class).getTime();

        if (GameManager.get().getManager(EnvironmentManager.class).getTOD() != null) {
            if (time >= 5 && time <= 9) {
                clockTexture = "dawn";
            } else if (time >= 10 && time <= 16) {
                clockTexture = "day";
            } else if (time >= 17 && time <= 19) {
                clockTexture = "dusk";
            } else {
                clockTexture = "night";
            }
            clockDisplay.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
        }
    }

    /**
     * Updates the clockDisplay and resizes if necessary
     */
    public void update() {
        updateWithViewportChanges();
        updateDisplay();
    }

}
