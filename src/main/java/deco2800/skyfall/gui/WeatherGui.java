package deco2800.skyfall.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import deco2800.skyfall.managers.*;

public class WeatherGui {

    private Stage stage;
    private ImageButton weatherEvent;

    /**
     * Constructor
     * @param s is the stage to display weather textures on.
     * @param weatherTexture is the texture of a specified weather event.
     */
    public WeatherGui(Stage s, String weatherTexture) {
        stage = s;

        this.weatherEvent = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(weatherTexture));
        weatherEvent.setSize(1500, 1500);

        updateWithViewportChanges();

        stage.addActor(weatherEvent);
    }

    /**
     * Keeps the object on the screen.
     */
    private void updateWithViewportChanges() {
        float positionX = (stage.getCamera().position.x + (stage.getCamera().viewportWidth) - 2000);
        float positionY = (stage.getCamera().position.y + (stage.getCamera().viewportHeight) - 1500);

        weatherEvent.setPosition(positionX, positionY);
    }

    /**
     * Updates the screen with current weather event.
     */
    public void update() {
        updateWithViewportChanges();
    }
}
