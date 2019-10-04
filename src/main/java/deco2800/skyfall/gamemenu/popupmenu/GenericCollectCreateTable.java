package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GenericCollectCreateTable extends AbstractPopUpElement{

    private final String type;
    private final QuestManager qm;
    private GameMenuManager gmm;
    private Skin skin;
    private Table baseTable;
    private LinkedHashMap<String, Integer> quantityToResources = new LinkedHashMap<>();
    private TextButton complete;
    private Label titleLabel;
    private Type tableType;
    private Table labelTable;
    private static enum Type {
        COLLECT,
        CREATE
    }


    public GenericCollectCreateTable(Stage stage, ImageButton exit, String[] textureNames,
                                     TextureManager tm, GameMenuManager gameMenuManager,
                                     QuestManager qm, Skin skin, String type) {
        super(stage,exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.gmm = gameMenuManager;
        this.type = type;
        this.qm  = qm;
        complete = new TextButton("  COMPLETED!  ", skin);
        complete.getLabel().setStyle(skin.get("green-pill",
                Label.LabelStyle.class));
        complete.getLabel().getStyle().fontColor = Color.BLACK;

        labelTable = new Table();
        labelTable.setDebug(true);
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
        if (checkComplete()) {
            complete.setVisible(true);
        } else {
            complete.setVisible(false);
        }
        updateText();
    }


    private void updateText() {
        labelTable.clear();

        if (type.equals("collect")) {
            String currentText  = String.format("%d x %s", qm.getGoldTotal(), "Gold");
            labelTable.add(new Label(currentText, skin, "white-text")).left();
            labelTable.row();

            currentText  = String.format("%d x %s", qm.getMetalTotal(), "Metal");
            labelTable.add(new Label(currentText, skin, "white-text")).left();
            labelTable.row();

            currentText  = String.format("%d x %s", qm.getStoneTotal(), "Stone");
            labelTable.add(new Label(currentText, skin, "white-text")).left();
            labelTable.row();

            currentText  = String.format("%d x %s", qm.getWoodTotal(), "Wood");
            labelTable.add(new Label(currentText, skin, "white-text")).left();
            labelTable.row();
        } else {
            List<String> buildingsTotal = qm.getBuildingsTotal();

            for (String entry :  buildingsTotal) {
                String currentText  = String.format("1 x %s", entry);
                labelTable.add(new Label(currentText, skin, "white-text")).left();
                labelTable.row();
            }
        }
    }


    private boolean checkComplete() {
        return qm.checkGold() && qm.checkMetal() && qm.checkStone() && qm.checkWood();
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


        if (type.equals("collect")) {
            titleLabel = new Label(" COLLECT ", skin,  "title-pill");
        } else {
            titleLabel = new Label(" CREATE ", skin,  "title-pill");
        }

        baseTable.setDebug(true);
        baseTable.add(titleLabel);
        baseTable.row();
        baseTable.add(labelTable);
        baseTable.row();
        baseTable.add(complete).bottom().width(200).expand();
        baseTable.setVisible(false);
        quantityToResources.put("IRON", 2);
    }


}
