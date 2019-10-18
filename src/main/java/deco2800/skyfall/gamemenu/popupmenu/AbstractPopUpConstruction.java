package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

public class AbstractPopUpConstruction extends AbstractPopUpElement {

    protected Table blueprintTable;
    protected Table blueprintPanel;

    AbstractPopUpConstruction(Stage stage, ImageButton exitButton, String[] textureNames, TextureManager tm,
            GameMenuManager gameMenuManager) {
        super(stage, exitButton, textureNames, tm, gameMenuManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        blueprintTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        blueprintTable.setVisible(true);
    }

    @Override
    public void draw() {
        super.draw();

        blueprintTable = new Table();
        blueprintTable.setSize(910, 510);
        blueprintTable.setPosition(Gdx.graphics.getWidth() / 2f - blueprintTable.getWidth() / 2,
                (Gdx.graphics.getHeight() + 160) / 2f - blueprintTable.getHeight() / 2);
        blueprintTable.setDebug(true);
        blueprintTable.top();
        blueprintTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("pop up screen"));
        blueprintTable.setName("chestTable");

        return;
    }

    /**
     * Creates a new table that serves as an info bar, set with the appropriate
     * dimensions and is set in the appropriate position.
     */
    protected Image setNewInfoBar() {

        Image infoBar = new Image(gameMenuManager.generateTextureRegionDrawableObject("blueprint_shop_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 435);

        return infoBar;
    }
}