package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.Chest;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.Blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class BlueprintShopTable extends AbstractPopUpElement{
    private final Skin skin;
    private final StatisticsManager sm;
    private Table blueprintTable;
    private Table blueprintPanel;

    public BlueprintShopTable(Stage stage, ImageButton exit, String[] textureNames,
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
        System.out.println("Hiding blueprint shop");
        blueprintTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing blueprint shop");
        blueprintTable.setVisible(true);
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
        blueprintTable = new Table();
        blueprintTable.setSize(910, 510);
        blueprintTable.setPosition(Gdx.graphics.getWidth()/2f - blueprintTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - blueprintTable.getHeight()/2);
        blueprintTable.setDebug(true);
        blueprintTable.top();
        blueprintTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));
        blueprintTable.setName("chestTable");

        Image infoBar = new Image(generateTextureRegionDrawableObject("inventory_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 435);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(generateTextureRegionDrawableObject("info_panel"));

        this.blueprintPanel = new Table();
        //updateChestPanel(chest);

        blueprintTable.addActor(infoBar);
        blueprintTable.addActor(infoPanel);
        blueprintTable.addActor(this.blueprintPanel);
        blueprintTable.setVisible(false);
        stage.addActor(blueprintTable);
    }

    public void updateBlueprintShopPanel() {
        blueprintPanel.clear();
        blueprintPanel.setName("resourcePanel");
        blueprintPanel.setSize(410, 400);
        blueprintPanel.setPosition(475, 18);
        blueprintPanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        List<Blueprint> unlocked = sm.getCharacter().getUnlockedBlueprints();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Blueprint b : unlocked) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(b.getName()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);
            if (isBought(b)) {
                Label cost = new Label("X", skin, "white-label");
                cost.setName(b.getName());
                cost.setPosition(xpos + 85 + count * 130, ypos + 75);
                blueprintPanel.addActor(cost);
            } else {
                Label cost = new Label("$" + b.getCost(), skin, "white-label");
                cost.setPosition(xpos + 85 + count * 130, ypos + 75);
                cost.setName(b.getName());
                blueprintPanel.addActor(cost);

                icon.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (sm.getCharacter().getGoldPouchTotalValue() >= b.getCost()) {
                            sm.getCharacter().removeGold(b.getCost());
                            sm.getCharacter().addBlueprint(b);
                        } else {
                            System.out.println("Not enough money");
                        }
                        updateBlueprintShopPanel();
                    }
                });
            }
            blueprintPanel.addActor(icon);

            Label cost = new Label("$" + b.getCost(), skin, "white-label");
            cost.setPosition(xpos + 85 + count * 130, ypos + 75);
            blueprintPanel.addActor(cost);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }
    }

    private boolean isBought(Blueprint b) {
        for (Blueprint bt : sm.getCharacter().getBlueprintsLearned()) {
            if (b.getName().equals(bt.getName())) {
                return true;
            }
        }
        return false;
    }
}
