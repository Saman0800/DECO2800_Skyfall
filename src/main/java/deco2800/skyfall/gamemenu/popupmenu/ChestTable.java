package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.Chest;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.PopUpTable;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class ChestTable extends AbstractPopUpElement{
    private final Skin skin;
    private final StatisticsManager sm;
    private Table chestTable;
    private Table resourcePanel;

    public ChestTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm, GameMenuManager gameMenuManager,
                      StatisticsManager sm, Skin skin) {
        super(stage,exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.draw();
        this.sm = sm;
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding chest table");
        chestTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing chest table");
        chestTable.setVisible(true);
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
        chestTable = new Table();
        chestTable.setSize(910, 510);
        chestTable.setPosition(Gdx.graphics.getWidth()/2f - chestTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - chestTable.getHeight()/2);
        chestTable.setDebug(true);
        chestTable.top();
        chestTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));
        chestTable.setName("chestTable");

        Image infoBar = new Image(generateTextureRegionDrawableObject("inventory_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 435);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(generateTextureRegionDrawableObject("info_panel"));

        this.resourcePanel = new Table();
        //updateChestPanel(chest);

        chestTable.addActor(infoBar);
        chestTable.addActor(infoPanel);
        chestTable.addActor(this.resourcePanel);
        chestTable.setVisible(false);
        stage.addActor(chestTable);
    }

    public void updateChestPanel(Chest chest) {
        resourcePanel.clear();
        resourcePanel.setName("resourcePanel");
        resourcePanel.setSize(410, 400);
        resourcePanel.setPosition(475, 18);
        resourcePanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        Map<String, Integer> inventoryAmounts = chest.getManager().getAmounts();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);

            resourcePanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos + 85 + count * 130, ypos + 75);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }

        ImageButton button = new ImageButton(generateTextureRegionDrawableObject("takeall"));
        button.setName("Take all");
        button.setSize(100, 100);
        button.setPosition(xpos + count * 130, ypos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sm.getInventory().inventoryAddMultiple(chest.getManager().getContents());
                hide();
            }
        });
        resourcePanel.addActor(button);
    }
}
