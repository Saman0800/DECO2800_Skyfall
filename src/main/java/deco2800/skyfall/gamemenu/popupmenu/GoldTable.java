package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.PopUpTable;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.entities.MainCharacter;


import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GoldTable extends AbstractPopUpElement{
    private final Skin skin;
    private final StatisticsManager sm;
    private Table goldTable;
    private Table goldPanel;

    public GoldTable(Stage stage, ImageButton exit, String[] textureNames,
                      TextureManager tm, GameMenuManager gameMenuManager,
                      StatisticsManager sm, Skin skin) {
        super(stage,exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.sm = sm;
        this.draw();
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding gold table");
        goldTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing gold table");
        goldTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    public void update() {
        super.update();

    }

    public void draw() {
        super.draw();

        goldTable = new Table();
        goldTable.setSize(700,700);
        goldTable.setPosition(Gdx.graphics.getWidth()/2f - goldTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - goldTable.getHeight()/2);
        goldTable.setDebug(true);
        goldTable.top();
        goldTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));
        goldTable.setName("goldTable");

        Image infoBar = new Image(generateTextureRegionDrawableObject("goldBanner"));
        infoBar.setSize(550, 55);
        infoBar.setPosition(90, 600);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(generateTextureRegionDrawableObject("info_panel"));

        updateGoldPanel();

        goldTable.addActor(infoBar);
        goldTable.addActor(this.goldPanel);
        goldTable.setVisible(false);

        stage.addActor(goldTable);
    }

    /***
     * Updates the gold panel to display the current value of each coin.
     */
    private void updateGoldPanel(){
        goldPanel = new Table();
        goldPanel.setName("goldPanel");
        goldPanel.setSize(500, 450);
        goldPanel.setPosition(110, 100);
        goldPanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        Map<Integer, Integer> goldAmounts = sm.getCharacter().getGoldPouch();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Map.Entry<Integer, Integer> entry : goldAmounts.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject("goldPiece" + entry.getKey()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);

            goldPanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos + 85 + count * 130, ypos + 75);
            goldPanel.addActor(num);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }

    }

}
