package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

public class ManaCircle extends AbstractUIElement {
    // Game manager to be used
    private final GameMenuManager gmm;

    // Current health of player
    private float currentMana;

    // New health after health change
    private int newMana;

    // Bigger circle
    private  ImageButton biggerCircle;

    // Inner/changing circle
    private  ImageButton smallerCircle;

    // Position X
    private float positionX;

    // Position Y
    private float positionY;

    // Offset for positions
    private float offset;

    // Mana label
    private Label label;

    // Statistic manager to read player health from
    private StatisticsManager sm;

    // Skin
    private Skin skin;

    /**
     * Updates the inner circle.
     */
    private void updateInnerCircle() {
        // Check if null
        if (smallerCircle == null || biggerCircle == null) {
            return;
        }

        // If health is greater than 50, set health to equal 50
        if (newMana > 100) {
            newMana = 100;
        }

        float diff = currentMana - newMana;

        smallerCircle.setSize((float) newMana,
                (float) newMana);

        offset += (diff) / 2;
        smallerCircle.setPosition(positionX + offset, positionY + offset);

        currentMana = newMana;

        // Set label appropriately
        if(sm.getMana() < 0) {
            label.setText("Mana: 0");
        } else {
            label.setText("Mana: " + (int) currentMana);
        }
    }

    /**
     * Get smaller circle size
     *
     * @return Inner circle image
     */
    public ImageButton getInnerCircle() {
        return this.smallerCircle;
    }

    /**
     * Updates the health circle and the position if the screen has been resized
     */
    @Override
    public void update() {
        super.update();
        newMana = sm.getMana();
        updateInnerCircle();
    }
    /**
     * Keeps the object on the top left of the screen
     */
    @Override
    public void updatePosition() {
        positionX = gmm.getTopRightX() - stage.getCamera().viewportWidth / 2 - 200;
        positionY = gmm.getTopRightY() - 100;
        smallerCircle.setPosition(positionX + offset, positionY + offset);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX + 80, positionY + 30);
        label.toBack();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {

        label = new Label("Mana: ERR", skin,  "blue-pill");
        label.setAlignment(Align.center);
        label.setFontScale(0.7f);

        final int OUTER_CIRCLE = 1;
        final int INNER_CIRCLE = 0;

        this.biggerCircle = new ImageButton(textures[OUTER_CIRCLE]);
        biggerCircle.setSize(100, 100);

        this.smallerCircle = new ImageButton(textures[INNER_CIRCLE]);
        smallerCircle.setSize(100, 100);

        smallerCircle.setName("innerManaCircle");
        biggerCircle.setName("outerManaCircle");

        stage.addActor(biggerCircle);
        stage.addActor(smallerCircle);
        stage.addActor(label);
    }


    /**
     * Constructors
     * @param stage The game stage
     * @param textureNames The texture names to fetch
     * @param tm The texture manager
     * @param sm The statistics manager
     */
    public ManaCircle(Stage stage, String[] textureNames, TextureManager tm, StatisticsManager sm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.sm = sm;
        this.skin = skin;
        this.gmm = gmm;
        this.draw();
        currentMana = sm.getMana();
    }
}