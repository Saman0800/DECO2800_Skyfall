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
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.LinkedHashMap;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class ProgressTable extends AbstractPopUpElement {
    private final String type;
    private GameMenuManager gmm;
    private Skin skin;
    private Table baseTable;
    private LinkedHashMap<String, Integer> quantityToResources = new LinkedHashMap<>();
    private ImageButton complete;
    private Label titleLabel;
    private Table labelTable;

    public ProgressTable(Stage stage, ImageButton exit, String[] textureNames,
                                     TextureManager tm, GameMenuManager gameMenuManager,
                                     StatisticsManager sm, Skin skin, String type) {
        super(stage,exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.gmm = gameMenuManager;
        this.type = type;

        complete = new ImageButton(generateTextureRegionDrawableObject("complete_button"));
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
        //this.draw();
        super.show();
        baseTable.setVisible(true);
    }

    @Override
    public void update() {
        //TODO: (@Kausta) link with quests manager
        if (checkComplete()) {
            complete.setVisible(true);
        } else {
            complete.setVisible(false);
        }
        updateText();
    }


    public void updateText() {
        labelTable.clear();
        //TODO: Integrate with QuestManager
        //TODO: (@Kausta) Add Currently?
        for (Map.Entry<String, Integer> entry :  quantityToResources.entrySet()) {
            String currentText  = String.format("%d x %s", entry.getValue(), entry.getKey());
            labelTable.add(new Label(currentText, skin, "white-text")).left();
            labelTable.row();
        }
    }


    public boolean checkComplete() {
        return false;
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


        titleLabel = new Label("PROGRESS", skin,  "blue-pill");
        titleLabel.getStyle().fontColor = Color.BLACK;

        baseTable.setDebug(true);
        baseTable.add(titleLabel);

        baseTable.add(complete).bottom().width(200).expand();
        baseTable.setVisible(false);
    }

}
