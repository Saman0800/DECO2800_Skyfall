package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.LinkedHashMap;
import java.util.Map;


public class ProgressTable extends AbstractPopUpElement {
    private final QuestManager qm;
    private Skin skin;
    private Table baseTable;
    private Label biomeLabel;
    private Label collectLabel;
    private Label createLabel;
    private Label blueprintLabel;

    public ProgressTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm, GameMenuManager gameMenuManager,
                         QuestManager qm, Skin skin) {
        super(stage,exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.qm = qm;
        Table labelTable = new Table();
        this.draw();
        stage.addActor(baseTable);
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

    @Override
    public void update() {
            qm.checkBuildings();
            updateBiomeText(qm.getBiome());
            updateCollectText(qm.collectNum() + "/4");
            updateCreateText(qm.getBuildingsNum() + "/" + qm.getBuildingsTotal().size());
            updateBlueprintText("1");
    }


    private void updateBiomeText(String text) {
        biomeLabel.setText(text + " BIOME: ");
    }

    private void updateCollectText(String text) {
        collectLabel.setText("COLLECT: " + text);
    }

    private void updateCreateText(String text) {
        createLabel.setText("CREATE: " + text);

    }

    private void updateBlueprintText(String text) {
        blueprintLabel.setText(text + "x  Blueprint: " + "To Purchase");
    }


    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("blue_pill_table"));
        baseTable.setSize(600, 600 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.top();



        Label titleLabel = new Label(" PROGRESS ", skin, "title-pill");
        biomeLabel = new Label("ERR", skin, "game-font",Color.WHITE);
        collectLabel = new Label("ERR", skin, "game-font",Color.WHITE);
        createLabel = new Label("ERR", skin, "game-font",Color.WHITE);
        blueprintLabel = new Label("ERR", skin, "game-font",Color.WHITE);

        titleLabel.getStyle().fontColor = Color.BLACK;

        baseTable.add(titleLabel);
        baseTable.row();
        baseTable.add(biomeLabel).expand().left();
        baseTable.row();
        baseTable.add(collectLabel).expand().left();
        baseTable.row();
        baseTable.add(createLabel).expand().left();
        baseTable.row();
        baseTable.add(blueprintLabel).expand().left();
        baseTable.row();
        baseTable.setVisible(false);
    }

    public Label getBiomeLabel() {
        return biomeLabel;
    }

    public Label getCollectLabel() {
        return collectLabel;
    }

    public Label getCreateLabel() {
        return createLabel;
    }

    public Label getBlueprintLabel() {
        return blueprintLabel;
    }
}
