package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.gamemenu.AbstractUIElement;
import deco2800.skyfall.managers.*;
import org.lwjgl.Sys;

public class Clock extends AbstractUIElement{

    private EnvironmentManager environmentManager;
    private Image clock;
    private Stage stage;
    private float positionX;
    private float positionY;
    private String clockTexture;


    /**
     * Constructor
     * @param s Stage to display things on
     */
    public Clock(Stage s) {
        this.environmentManager = GameManager.getManagerFromInstance(EnvironmentManager.class);

        stage = s;

        this.draw();
    }

    /**
     * Maintains position of clock with resizes
     */
    @Override
    public void updatePosition() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 280);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 70);

        clock.setPosition(positionX, positionY);
    }

    /**
     * Updates clock arrow in accordance with time
     */
    private void updateDisplay() {
        long time = GameManager.get().getManager(EnvironmentManager.class).hours;

        if (GameManager.get().getManager(EnvironmentManager.class).getTOD() != null) {
            if (time >= 4 && time <= 9) {
                clockTexture = "dawn";
                clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 10 && time <= 16) {
                clockTexture = "day";
                clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 17 && time <= 19) {
                clockTexture = "dusk";
                clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 20 && time <=  23) {
                clockTexture = "night";
                clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }

            if (time >= 0 && time <=  3) {
                clockTexture = "night";
                clock.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
            }
        }
    }

    @Override
    public void draw() {
        clockTexture = "dawn";
        this.clock = new Image(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
        updatePosition();
        stage.addActor(clock);
    }

    /**
     * Updates the clock and resizes if necessary
     */
    @Override
    public void update() {
        super.update();
        updateDisplay();
    }

}
