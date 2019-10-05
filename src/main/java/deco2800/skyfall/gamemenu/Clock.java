package deco2800.skyfall.gamemenu;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.*;

public class Clock extends AbstractUIElement{
    private final GameMenuManager gmm;
    private Image clockImage;
    private Skin skin;
    private Label clockLabel;
    /**
     * Constructor to create a clock image in game that changes in accordance with time
     * @param s Stage to display things on
     */
    public Clock(Stage s, Skin skin, GameMenuManager gmm) {
        // Set stage
        stage = s;
        this.skin = skin;
        this.gmm = gmm;
        this.draw();
    }
    /**
     * Maintains position of clockDisplay and seasonDisplay with resizes
     */
    public void updatePosition() {
        float positionX = (gmm.getTopLeftX() + 30);
        float positionY = (gmm.getTopLeftY() - 70);
        // Set clock position
        clockImage.setPosition(positionX + 130, positionY - 10);


        if (clockLabel != null) {
            clockLabel.setPosition(positionX, positionY);
            clockLabel.toFront();
        }
        clockImage.toFront();
    }

    /**
     * Updates clockDisplay arrow in accordance with time
     */
    private void updateDisplay() {
        // Time of day in hours
        long time = GameManager.get().getManager(EnvironmentManager.class).getTime();
        int decimal = GameManager.get().getManager(EnvironmentManager.class).getMinutes();

        // Current season
        String convTime = String.valueOf(time);
        String convDecimal = String.valueOf(decimal);

        if (decimal < 10) {
            clockLabel.setText(convTime + " : 0" + convDecimal);
        } else {
            clockLabel.setText(convTime + " : " + convDecimal);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        this.clockLabel = new Label("Error", skin,  "blue-pill");
        this.clockImage = new Image(gmm.generateTextureRegionDrawableObject("new_clock"));
        clockLabel.setAlignment(Align.center);
        clockLabel.setWidth(clockLabel.getWidth() + 50);
        clockLabel.setFontScale(0.7f);

        clockImage.setScale(0.25f);

        stage.addActor(clockImage);
        stage.addActor(clockLabel);


        // Update screen
        update();
    }
    /**
     * Updates the display and resizes if necessary
     */
    @Override
    public void update() {
        super.update();
        updateDisplay();
    }
}