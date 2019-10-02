package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GenericCollectCreateTable extends AbstractPopUpElement{

    private final String type;
    private GameMenuManager gmm;
    private Skin skin;
    private Table collectTable;


    public GenericCollectCreateTable(Stage stage, ImageButton exit, String[] textureNames,
                                     TextureManager tm, GameMenuManager gameMenuManager,
                                     StatisticsManager sm, Skin skin, String type) {
        super(stage,exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.gmm = gameMenuManager;
        this.type = type;
        this.draw();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        collectTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        collectTable.setVisible(true);
    }


    @Override
    public void draw() {
        super.draw();
        collectTable = new Table();
        collectTable.setBackground(generateTextureRegionDrawableObject("blue_pill_table"));
        collectTable.setSize(600, 600 * 1346 / 1862f);
        collectTable.setPosition(Gdx.graphics.getWidth()/2f - collectTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - collectTable.getHeight()/2);
        collectTable.top();

        Label label;
        if (type.equals("collect")) {
            label = new Label(" COLLECT ", skin,  "title-pill");
        } else {
            label = new Label(" CREATE ", skin,  "title-pill");
        }

        ImageButton complete = new ImageButton(generateTextureRegionDrawableObject("complete_button"));

        stage.addActor(collectTable);
        collectTable.setDebug(true);
        collectTable.add(label);
        collectTable.row();
        collectTable.add(complete).bottom().width(200).expand();
        collectTable.setVisible(false);



    }
}
