package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

public class GameMenuBar2 extends AbstractUIElement {

    private final GameMenuManager gmm;
    private final Skin skin;
    private Table quickAccessPanel;
    private ImageButton imageButton;
    private Table t;
    public GameMenuBar2(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
        this.draw();
    }

    @Override
    public void updatePosition() {
        t.setPosition(gmm.getTopRightX() - 170, gmm.getTopRightY() - 800);
        imageButton.setPosition(gmm.getTopRightX() - 200, gmm.getTopRightY() - 800);
    }

    @Override
    public void draw() {
        t = new Table();
        imageButton = new ImageButton(GameMenuManager.generateTextureRegionDrawableObject("inventory_expand_button"));
        t.setDebug(true);
        t.setBackground(GameMenuManager.generateTextureRegionDrawableObject("inventory_bg"));
        t.setSize(150, 700);
        imageButton.setSize(100, 700); //TODO: reduce chunkiness
        imageButton.scaleBy(1.0f, 0.5f);
        stage.addActor(t);
        stage.addActor(imageButton);
    }
}
