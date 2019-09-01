package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;

    public class HealthCircle{
    private MainCharacter mainCharacter;
    private float currentHealth;
    private int newHealth; // maybe for animating it down.
    private  ImageButton biggerCircle;
    private  ImageButton smallerCircle;
    private Stage s;
    private float positionX;
    private float positionY;
    private float offset;
    private Label label;

    /**
     * Constructor
     * @param stage Stage to display things on
     * @param innerTexture Texture of the inner circle
     * @param outerTexture Texture of the outer circle
     * @param mc Main Character (Needs to be migrated to statsManager)
     */
    public HealthCircle(Stage stage, String innerTexture, String outerTexture, MainCharacter mc) {
        mainCharacter = mc;
        s = stage;

        currentHealth = mc.getHealth();
        newHealth = mc.getHealth();
        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);

        label = new Label("Health: 10", new Label.LabelStyle(bitmapFont, Color.WHITE));

        this.biggerCircle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(innerTexture));
        biggerCircle.setSize(100, 100);

        this.smallerCircle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(outerTexture));
        smallerCircle.setSize(100, 100);

        updateWithViewportChanges();

        stage.addActor(biggerCircle);
        stage.addActor(smallerCircle);
        stage.addActor(label);

    }

    /**
     * Keeps the object on the top left of the screen
     */
    private void updateWithViewportChanges() {
        positionX = (s.getCamera().position.x  + (s.getCamera().viewportWidth / 2) - 100);
        positionY = (s.getCamera().position.y  +  (s.getCamera().viewportHeight / 2) - 100);
        smallerCircle.setPosition(positionX + offset, positionY + offset);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX + 15, positionY + 40);
    }

    /**
     * Updates
     */
    private void updateInnerCircle() {
        float diff = currentHealth - newHealth;

        if (smallerCircle == null) {
            System.out.println("Smaller circle is null");
            if (biggerCircle == null) {
                System.out.println("Bigger circle is null");
                return;
            }
            return;
        }

        smallerCircle.setSize(10 * newHealth, 10 * newHealth);
        offset += (diff * 10) / 2;
        smallerCircle.setPosition(positionX + offset, positionY + offset);
        currentHealth = newHealth;
        label.setText("Health: " + mainCharacter.getHealth());
    }

    /**
     * Updates the health circle and the position if the scren has been resized
     */
    public void update() {
        newHealth = mainCharacter.getHealth();
        updateWithViewportChanges();
        if (!((currentHealth - newHealth) >= 0)) {
            updateInnerCircle();
        }
    }

}
