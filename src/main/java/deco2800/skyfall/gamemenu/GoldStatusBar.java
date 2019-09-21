package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

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


    @Override
    public void updatePosition() {
        float positionX = stage.getCamera().position.x - goldLabel.getWidth();
        float positionY = stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2);

        goldLabel.setPosition(positionX + 100, gmm.getTopLeftY() - 70);
        goldPouchButton.setPosition(positionX + goldLabel.getWidth() + 60, gmm.getTopLeftY() - 80);
        //goldLabel.setPosition(positionX + 30, positionY + 40 + 10);

    }

    @Override
    public void draw() {
        this.goldLabel = new Label("Gold (TODO) ", skin,  "blue-pill");
        goldLabel.setAlignment(Align.center);
        goldLabel.setFontScale(0.7f);

        goldPouchButton = new ImageButton(generateTextureRegionDrawableObject("goldPouch"));
        goldPouchButton.setSize(200 * 0.35f, 207 * 0.35f);

        stage.addActor(goldLabel);
        stage.addActor(goldPouchButton);

        updatePosition();
    }

    @Override
    public void update() {
        super.update();
        //TODO: How to update gold count

    }
}
