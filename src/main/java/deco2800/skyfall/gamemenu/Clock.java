package deco2800.skyfall.gamemenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractUIElement;
import deco2800.skyfall.managers.*;
public class Clock extends AbstractUIElement{
    private final GameMenuManager gmm;
    private Image clockImage;
    private float positionX;
    private float positionY;
    private String clockTexture;
    private Image clockDisplay;
    // Season image
    private Image seasonDisplay;
    // Season filename
    private String seasonTexture;

    //new HUD sprint 4
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
        float positionX;
        float positionY;
        positionX = (gmm.getTopLeftX() + 30) ;
        positionY = (gmm.getTopLeftY() - 70);
        // Set clock position
        clockDisplay.setPosition(positionX, positionY);
        // Set season position
        seasonDisplay.setPosition(positionX + 160, positionY + 10);
        clockImage.setPosition(clockLabel.getWidth(), positionY - 20);


        if (clockLabel != null) {
            clockLabel.setPosition(positionX, positionY);
            clockLabel.setWidth(stage.getCamera().viewportWidth / 8);
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
            if (season.equals("Summer")) {
                seasonTexture = "summer";
            } else if (season.equals("Winter")) {
                seasonTexture = "winter";
            } else if (season.equals("Autumn")) {
                seasonTexture = "autumn";
            } else {
                seasonTexture = "spring";
            }
            seasonDisplay.setDrawable(GameMenuManager.generateTextureRegionDrawableObject(seasonTexture));
        }
    }
    @Override
    public void draw() {
        clockTexture = "dawn";
        seasonTexture = "summer";
        this.clockDisplay = new Image(GameMenuManager.generateTextureRegionDrawableObject(clockTexture));
        this.seasonDisplay = new Image(GameMenuManager.generateTextureRegionDrawableObject(seasonTexture));
        this.clockLabel = new Label("Error", skin,  "blue-pill");
        this.clockImage = new Image(GameMenuManager.generateTextureRegionDrawableObject("new_clock"));
        clockLabel.setAlignment(Align.center);
        clockLabel.setWidth(clockLabel.getWidth() + 50);
        clockLabel.setFontScale(0.7f);

        clockImage.setScale(0.3f);

        stage.addActor(clockImage);
        stage.addActor(clockLabel);


        // Update screen
        update();
        // Add displays onto stage
        //stage.addActor(clockDisplay);
        //stage.addActor(seasonDisplay);

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