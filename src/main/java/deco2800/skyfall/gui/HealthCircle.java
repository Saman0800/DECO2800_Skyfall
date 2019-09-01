package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.ScreenManager;

public class HealthCircle {
    private ScreenManager.MainCharacter mainCharacter;
    private float currentHealth;
    private int newHealth; // maybe for animating it down.
    private  ImageButton bigger_circle;
    private  ImageButton smaller_circle;
    private Stage s;
    private float positionX;
    private float positionY;
    private float offset;
    private Label label;
    //TODO: change stage.

    public HealthCircle(Stage stage, String t1, String t2, ScreenManager.MainCharacter mc) {
        mainCharacter = mc;
        s = stage;

        currentHealth = mc.getHealth();
        newHealth = mc.getHealth();
        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        label = new Label("Health: 10", new Label.LabelStyle(bitmapFont, Color.WHITE));

        this.bigger_circle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(t1));
        bigger_circle.setSize(100, 100);

        this.smaller_circle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(t2));
        smaller_circle.setSize(100, 100);

        updateWithViewportChanges();

        stage.addActor(bigger_circle);
        stage.addActor(smaller_circle);
        stage.addActor(label);
        //Testing functionality
//        smaller_circle.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                mc.changeHealth(-1);
//                updateInnerCircle();
//            }
//        });

    }

    private void updateWithViewportChanges() {
        positionX = (s.getCamera().position.x  + (s.getCamera().viewportWidth / 2) - 100);
        positionY = (s.getCamera().position.y  +  (s.getCamera().viewportHeight / 2) - 100);
        smaller_circle.setPosition(positionX + offset, positionY + offset);
        bigger_circle.setPosition(positionX, positionY);
        label.setPosition(positionX + 15, positionY + 40);
    }

    private void updateInnerCircle() {
        float diff = currentHealth - newHealth;

        if (smaller_circle == null) {
            System.out.println("Smaller circle is null");
            if (bigger_circle == null) {
                System.out.println("Bigger circle is null");
                return;
            }
            return;
        }

        smaller_circle.setSize(10 * newHealth, 10 * newHealth);
        offset += (diff * 10) / 2;
        smaller_circle.setPosition(positionX + offset, positionY + offset);
        currentHealth = newHealth;
        label.setText("Health: " + mainCharacter.getHealth());
    }


    public void update() {
        newHealth = mainCharacter.getHealth();
        updateWithViewportChanges();
        if (!((currentHealth - newHealth) < 0)) {
            updateInnerCircle();
        }
    }

}
