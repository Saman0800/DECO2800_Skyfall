package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

public class HealthCircle extends AbstractUIElement {
    private float currentHealth;
    private int newHealth; // maybe for animating it down.
    private  ImageButton biggerCircle;
    private  ImageButton smallerCircle;
    private float positionX;
    private float positionY;
    private float offset;
    private Label label;

    // new stuff

    StatisticsManager sm;
//    //private Label label_ref;
//    /**
//     * Constructor
//     * @param stage Stage to display things on
//     * @param innerTexture Texture of the inner circle
//     * @param outerTexture Texture of the outer circle
//     * @param mc Main Character (Needs to be migrated to statsManager)
//     */
//    public HealthCircle(Stage stage, String innerTexture, String outerTexture, MainCharacter mc) {
//        mainCharacter = mc;
//        this.s = stage;
//
//        currentHealth = mc.getHealth();
//        newHealth = mc.getHealth();
//        BitmapFont bitmapFont  = new BitmapFont();
//        bitmapFont.getData().setScale(1f);
//
//        label = new Label("Health: 10", new Label.LabelStyle(bitmapFont, Color.WHITE));
//
//        this.biggerCircle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(innerTexture));
//        biggerCircle.setSize(100, 100);
//
//        this.smallerCircle = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject(outerTexture));
//        smallerCircle.setSize(100, 100);
//
//        updatePosition();
//
//        stage.addActor(biggerCircle);
//        stage.addActor(smallerCircle);
//        stage.addActor(label);
//    }





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
        label.setText("Health: " + sm.getHealth());
    }

    /**
     * Updates the health circle and the position if the screen has been resized
     */
    @Override
    public void update() {
        super.update();
        newHealth = sm.getHealth();
        //System.out.println(newHealth);
        if ((currentHealth - newHealth) >= 0) {
      ///System.out.println(currentHealth- newHealth);
            updateInnerCircle();
        }
    }
    /**
     * Keeps the object on the top left of the screen
     */


    @Override
    public void updatePosition() {
        positionX = (stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2) - 100);
        positionY = (stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2) - 100);
        smallerCircle.setPosition(positionX + offset, positionY + offset);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX + 15, positionY + 40);
    }

    @Override
    public void draw() {
        BitmapFont bitmapFont  = new BitmapFont();
        bitmapFont.getData().setScale(1f);
        label = new Label("Health: 10", new Label.LabelStyle(bitmapFont, Color.WHITE));

        int OUTER_CIRCLE = 1;
        int INNER_CIRCLE = 0;

        this.biggerCircle = new ImageButton(textures[OUTER_CIRCLE]);
        biggerCircle.setSize(100, 100);

        this.smallerCircle = new ImageButton(textures[INNER_CIRCLE]);
        smallerCircle.setSize(100, 100);

        stage.addActor(biggerCircle);
        stage.addActor(smallerCircle);
        stage.addActor(label);
    }


    //Uses new refactored system
    public HealthCircle(Stage stage, String[] textureNames, TextureManager tm, StatisticsManager sm) {
        super(stage, textureNames, tm);
        this.sm = sm;
        this.draw();
        currentHealth = sm.getHealth();
    }
}
