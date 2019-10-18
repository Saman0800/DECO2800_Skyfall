package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;


/**
 * A class for building table pop up.
 */
public class BuildWorldProgressPopup extends AbstractPopUpElement {
    private Skin skin;
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
    public void show() {
        draw();
        super.show();
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

        buidlingAndBuildWorldCommonFunctionality(skin, "BUILDING WORLD...");
        baseTable.add(image);
        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }
}
