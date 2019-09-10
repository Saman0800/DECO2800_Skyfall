package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.PopUpTable;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class HelpTable extends AbstractPopUpElement{

    public HelpTable(Stage stage, ImageButton exit, String[] textureNames, TextureManager tm, GameMenuManager gameMenuManager) {
        super(stage, exit, textureNames,tm , gameMenuManager);

        this.draw();
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void updatePosition() {

    }

    @Override
    public void draw() {

    }

}
