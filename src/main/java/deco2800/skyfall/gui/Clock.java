package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import deco2800.skyfall.managers.*;

public class Clock {
    // Clock image
    private Image clockDisplay;

    // Clock filename
    private String clockTexture;

    // Stage for images to live
    private Stage stage;

    // Season image
    private Image seasonDisplay;

    // Season filename
    private String seasonTexture;

    /**
     * Constructor to create a clock image in game that changes in accordance with time
     * @param s Stage to display things on
     */
    public Clock(Stage s) {
        // Set stage
        stage = s;

        // Set default clock texture
        clockTexture = "dawn";

        // Set default season texture
        seasonTexture = "summer";

        // Set displays
        this.clockDisplay = new Image(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
        this.seasonDisplay = new Image(GameMenuManager.generateTextureRegionDrawableObject(seasonTexture));

        // Update screen
        updateWithViewportChanges();

        // Add displays onto stage
        stage.addActor(clockDisplay);
        stage.addActor(seasonDisplay);
    }

    /**
     * Maintains position of clockDisplay and seasonDisplay with resizes
     */
    private void updateWithViewportChanges() {
        float positionX;
        float positionY;

        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 300);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 70);

        // Set clock position
        clockDisplay.setPosition(positionX, positionY);

        // Set season position
        seasonDisplay.setPosition(positionX + 160, positionY + 10);
    }

    /**
     * Updates clockDisplay arrow in accordance with time
     */
    private void updateDisplay() {
        // Time of day in hours
        int time = GameManager.get().getManager(EnvironmentManager.class).getTime();

        // Current season
        String season = GameManager.get().getManager(EnvironmentManager.class).getSeason();

        // Monitor hours
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

        // Monitor seasons
        if (season != null) {
            if (season == "Summer") {
                seasonTexture = "summer";
            } else if (season == "Winter") {
                seasonTexture = "winter";
            } else if (season == "Autumn") {
                seasonTexture = "autumn";
            } else {
                seasonTexture = "spring";
            }
            seasonDisplay.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(seasonTexture));
        }
    }

    /**
     * Updates the display and resizes if necessary
     */
    public void update() {
        updateWithViewportChanges();
        updateDisplay();
    }

}
