package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import java.util.Map;


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
        goldTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        goldTable.setVisible(true);
    }

    @Override
    public void update() {
        super.update();
        updateGoldPanel();
    }

    @Override
    public void draw() {
        super.draw();

        goldTable = new Table();
        goldTable.setSize(700,700);
        goldTable.setPosition(Gdx.graphics.getWidth()/2f - goldTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - goldTable.getHeight()/2);
        goldTable.top();
        goldTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("pop up screen"));
        goldTable.setName("goldTable");

        Image infoBar = new Image(gameMenuManager.generateTextureRegionDrawableObject("goldBanner"));
        infoBar.setSize(550, 55);
        infoBar.setPosition(90, 600);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("info_panel"));

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
        if (goldPanel != null) {
            goldPanel.clear();
        } else {
            goldPanel = new Table();
        }
        goldPanel.setName("goldPanel");
        goldPanel.setSize(500, 450);
        goldPanel.setPosition(110, 100);
        goldPanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("menu_panel"));

        Map<Integer, Integer> goldAmounts = sm.getCharacter().getGoldPouch();

        float count = 0;
        float xpos = 20;
        float ypos = 280;

        for (Map.Entry<Integer, Integer> entry : goldAmounts.entrySet()) {
            ImageButton icon = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("goldPiece" + entry.getKey()));
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
