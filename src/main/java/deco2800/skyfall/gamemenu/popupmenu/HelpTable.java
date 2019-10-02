package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * A class for help table pop up.
 */
import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class HelpTable extends AbstractPopUpElement{
    private Skin skin;
    private Table helpTable;

    /**
     * Constructs a help table.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one.
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin Current skin.
     */
    public HelpTable(Stage stage, ImageButton exit,
                     String[] textureNames, TextureManager tm,
                     GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        helpTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        helpTable.setVisible(true);
    }


    /**
     * {@inheritDoc}
     * Draw help table.
     */
    @Override
    public void draw() {
        super.draw();
//        System.out.println("Drawing HELPTABLE");
        helpTable = new Table();
        helpTable.setSize(600, 600 * 1346 / 1862f);
        helpTable.setPosition(Gdx.graphics.getWidth()/2f - helpTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - helpTable.getHeight()/2);
        helpTable.setDebug(true);
        helpTable.top();
        helpTable.setBackground(generateTextureRegionDrawableObject("popup_bg"));

        Table banner = new Table();
        banner.setBackground(generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label("HELP", skin, "navy-text");
        banner.add(text);

        helpTable.add(banner).width(550).height(550 * 188f / 1756).padTop(20).colspan(3);
        helpTable.row().padTop(20);


        helpTable.setVisible(false);
        stage.addActor(helpTable);
        //System.out.println("Finished Drawing HELPTABLE");
    }



}
