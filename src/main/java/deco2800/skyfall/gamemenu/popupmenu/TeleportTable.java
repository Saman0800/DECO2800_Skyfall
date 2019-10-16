package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.TextureManager;

public class TeleportTable extends AbstractPopUpElement {
    private final String type;
    private final QuestManager qm;
    private Skin skin;
    private Table baseTable;
    private Label locationLabel;
    private Label teleportLabel;
    public TeleportTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm, GameMenuManager gameMenuManager,
                         QuestManager qm, Skin skin, String type) {
        super(stage,exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.type = type;
        this.qm = qm;
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
        super.show();
        baseTable.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        updateLocation(qm.getBiome());
        updateTeleportTo("SNOW");
    }

    /**
     * Updates the location label
     * @param text The new text for it
     */
    private void updateLocation(String text) {
        locationLabel.setText("LOCATION : " + text);
    }
    /**
     * Updates the teleport to label
     * @param text The new text for it
     */
    private void updateTeleportTo(String text) {
        teleportLabel.setText("TELEPORT TO : " + text);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("blue_pill_table"));
        baseTable.setSize(600, 600 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.top();

        Label titleLabel = new Label(" BIOME COMPLETE ", skin, "green-pill");


        ImageButton teleport = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("green_teleport_button"));


        Image teleportImg = new Image(gameMenuManager.generateTextureRegionDrawableObject("teleporting_man"));
        Table labelTable = new Table();
        labelTable.setSize(300, 600 * 1346 / 1862f);

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
        //TODO: (@Kausta) Teleport Button

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
