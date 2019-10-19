package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

public class GenericCircle extends AbstractUIElement {
    protected GameMenuManager gmm;



    // Bigger circle
    protected ImageButton biggerCircle;

    // Inner/changing circle
    protected   ImageButton smallerCircle;

    // Position X
    protected float positionX;

    // Position Y
    protected float positionY;

    // Offset for positions
    protected float offset;

    // Health label
    protected Label label;

    // Statistic manager to read player health from
    protected StatisticsManager sm;

    // Skin
    protected Skin skin;

    /**
     * Constructors
     * @param stage The game stage
     * @param textureNames The texture names to fetch
     * @param tm The texture manager
     * @param sm The statistics manager
     */
    public GenericCircle(Stage stage, String[] textureNames, TextureManager tm, StatisticsManager sm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.sm = sm;
        this.skin = skin;
        this.gmm = gmm;
        this.draw();
    }


    @Override
    public void updatePosition() {
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

        label = new Label("Health: 50", skin,  "blue-pill");
        label.setAlignment(Align.center);
        label.setFontScale(0.7f);

        final int OUTER_CIRCLE = 1;
        final int INNER_CIRCLE = 0;

        this.biggerCircle = new ImageButton(textures[OUTER_CIRCLE]);
        biggerCircle.setSize(100, 100);

        this.smallerCircle = new ImageButton(textures[INNER_CIRCLE]);
        smallerCircle.setSize(100, 100);

        smallerCircle.setName("innerHealthCircle");
        biggerCircle.setName("outerHealthCircle");

        stage.addActor(biggerCircle);
        stage.addActor(smallerCircle);
        stage.addActor(label);
    }

}
