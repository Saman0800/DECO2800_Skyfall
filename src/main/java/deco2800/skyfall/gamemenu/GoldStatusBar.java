package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * The widget diplaying the gold, on the HUD
 */
public class GoldStatusBar extends AbstractUIElement {
    private final Skin skin;
    private final GameMenuManager gmm;
    private Label goldLabel;
    private ImageButton goldPouchButton;

    public GoldStatusBar(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
        this.draw();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        goldLabel.setPosition(gmm.getTopLeftX() + 30, gmm.getTopLeftY() - 170);
        goldPouchButton.setPosition(gmm.getTopLeftX() + goldLabel.getWidth(), gmm.getTopLeftY() - 175);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        this.goldLabel = new Label("   " + MainCharacter.getInstance().getGoldPouchTotalValue() + " G   ", skin,  "blue-pill");
        goldLabel.setAlignment(Align.center);
        goldLabel.setFontScale(0.7f);

        goldPouchButton = new ImageButton(gmm.generateTextureRegionDrawableObject("goldPouch"));
        goldPouchButton.setSize(200 * 0.35f, 207 * 0.35f);


        goldPouchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gmm.hideOpened();
                gmm.setPopUp("goldTable");
            }
        });

        stage.addActor(goldLabel);
        stage.addActor(goldPouchButton);

    }

    public void updateLabel() {
        int total = gmm.getMainCharacter().getGoldPouchTotalValue();
        goldLabel.setText(" " + total + " G   ");
    }

    /**
     * Used in unit testing
     */
    public Label getGoldLabel() {
        return goldLabel;
    }
}
