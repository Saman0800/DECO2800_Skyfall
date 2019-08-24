package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;

public class HealthCircle {
    MainCharacter mainCharacter;
    int currentHealth;
    int newHealth; // maybe for animating it down.
    ImageButton bigger_circle;
    ImageButton smaller_circle;

    public HealthCircle(Stage stage, String t1, String t2, MainCharacter mc) {
        Image bigger_circle = new Image(GameMenuManager.generateTextureRegionDrawableObject(t1));
        bigger_circle.setSize(100, 100);
        bigger_circle.setPosition(250,250);

        Image inner_circle = new Image(GameMenuManager.generateTextureRegionDrawableObject(t2));
        inner_circle.setSize(75, 75);
        inner_circle.setPosition(250,250);
        inner_circle.moveBy(12.5f, 12.5f);
        stage.addActor(bigger_circle);
        stage.addActor(inner_circle);

        mainCharacter = mc;
        currentHealth = mc.getHealth();
    }


    public void update() {

    }

}
