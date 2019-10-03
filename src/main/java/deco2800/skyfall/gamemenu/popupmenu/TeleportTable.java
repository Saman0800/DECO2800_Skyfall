package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.LinkedHashMap;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class TeleportTable extends AbstractPopUpElement {
    private final String type;
    private GameMenuManager gmm;
    private Skin skin;
    private Table baseTable;
    private Label locationLabel;
    private Label teleportLabel;
    public TeleportTable(Stage stage, ImageButton exit, String[] textureNames,
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
        baseTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        //this.draw();
        super.show();
        baseTable.setVisible(true);
    }

    @Override
    public void update() {
        updateLocation("FOREST");
        updateTeleportTo("SNOW");
    }

    public void updateLocation(String text) {
        locationLabel.setText("LOCATION : " + text);
    }

    public void updateTeleportTo(String text) {
        teleportLabel.setText("TELEPORT TO : " + text);
    }




    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setBackground(generateTextureRegionDrawableObject("blue_pill_table"));
        baseTable.setSize(600, 600 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.top();

        Label titleLabel = new Label(" BIOME COMPLETE ", skin, "green-pill");


        ImageButton teleport = new ImageButton(generateTextureRegionDrawableObject("green_teleport_button"));


        Image teleportImg = new Image(generateTextureRegionDrawableObject("teleporting_man"));
        Table labelTable = new Table();
        labelTable.setSize(300, 600 * 1346 / 1862f);
        labelTable.setDebug(true);

        baseTable.setDebug(true);
        baseTable.add(titleLabel).align(Align.center).colspan(2);
        baseTable.row();
        baseTable.add(teleportImg).width(300).left().expandY();
        baseTable.add(labelTable).width(300).right().expandY();
        baseTable.row();
        baseTable.setVisible(false);



        locationLabel = new Label("LOCATION: ERR", skin, "white-text");
        locationLabel.setFontScale(0.8f);
        teleportLabel = new Label("TELEPORT TO: ERR", skin, "white-text");
        teleportLabel.setFontScale(0.8f);

        labelTable.add();
        labelTable.row();
        labelTable.add(locationLabel);
        labelTable.row();
        labelTable.add(teleportLabel);
        labelTable.row();
        labelTable.add(teleport).colspan(2).bottom().width(200).expand().right();

        stage.addActor(baseTable);
    }

}
