package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;


/**
 * A class for building table pop up.
 */
public class BuildWorldProgressPopup extends AbstractPopUpElement {
    private Skin skin;
    private Table table;
    /**
     * Constructs a building table.
     *
     * @param stage           Current stage.
     * @param exit            Exit button if it has one.
     * @param textureNames    Names of the textures.
     * @param tm              Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin            Current skin.
     */
    public BuildWorldProgressPopup(Stage stage, ImageButton exit, String[] textureNames, TextureManager tm,
                         GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        table.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        draw();
        super.show();
        table.setVisible(true);
    }

    /**
     * {@inheritDoc}
     *
     * Draw the progress table
     */
    @Override
    public void draw() {
        super.draw();

        Image image = new Image(gameMenuManager.generateTextureRegionDrawableObject("loading_texture"));

        table = new Table();

        table.setSize(800, 800 * 1346 / 1862f);
        table.setPosition(Gdx.graphics.getWidth() / 2f - table.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - table.getHeight() / 2);
        table.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));

        // table banner
        Table banner = new Table();
        banner.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label("BUILDING WORLD...", skin, "navy-text");
        text.setFontScale(1.1f);
        banner.add(text);

        table.add(banner).width(750).height(750 * 188f / 1756).padTop(20).colspan(2);
        table.row();
        table.add(image);
        table.setVisible(false);
        stage.addActor(table);
    }
}
