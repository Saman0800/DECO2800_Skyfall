package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

public class HealthCircle extends GenericCircle {
    // Game manager to be used
    // Current health of player
    protected float currentHealth;

    // New health after health change
    protected int newHealth; // maybe for animating it down.

    /**
     * Updates the inner circle.
     */
    private void updateInnerCircle() {
        // Check if null
        if (smallerCircle == null || biggerCircle == null) {
            return;
        }

        // If health is greater than 50, set health to equal 50
        if (newHealth > 50) {
            newHealth = 50;
        }

        float diff = currentHealth - newHealth;

        smallerCircle.setSize((float) 2 * newHealth,
                (float) 2 * newHealth);

        offset += (diff * 2) / 2;
        smallerCircle.setPosition(positionX + offset, positionY + offset);

        currentHealth = newHealth;

        // Set label appropriately
        if(sm.getHealth() < 1) {
            label.setText("DEAD");
        } else {
            label.setText("Health: " + (int) currentHealth);
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
        newHealth = sm.getHealth();
        updateInnerCircle();
    }
    /**
     * Keeps the object on the top left of the screen
     */
    @Override
    public void updatePosition() {
        super.updatePosition();
        positionX = gmm.getTopRightX() - stage.getCamera().viewportWidth / 2 + 100;

    }


    /**
     * Constructors
     * @param stage The game stage
     * @param textureNames The texture names to fetch
     * @param tm The texture manager
     * @param sm The statistics manager
     */
    public HealthCircle(Stage stage, String[] textureNames, TextureManager tm, StatisticsManager sm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm, sm, skin, gmm);
        currentHealth = sm.getHealth();
    }
}
