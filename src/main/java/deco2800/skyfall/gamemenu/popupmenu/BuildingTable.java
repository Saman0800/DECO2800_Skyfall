package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class BuildingTable extends AbstractPopUpElement{
    private Skin skin;
    private Table buildingTable;


    public BuildingTable(Stage stage, ImageButton exit,
                      String[] textureNames, TextureManager tm,
                      GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding building table");
        buildingTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing building table");
        buildingTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("Drawing BUILDINGTABLE");
        buildingTable = new Table();
        buildingTable.setDebug(true);
        buildingTable.setSize(800, 800 * 1346 / 1862f);
        buildingTable.setPosition(Gdx.graphics.getWidth()/2f - buildingTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - buildingTable.getHeight()/2);
        buildingTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("BUILDING TABLE", skin, "black-text");
        text.setFontScale(1.2f);
        infoBar.add(text);

        buildingTable.add(infoBar).width(750).height(750 * 188f / 1756).padTop(20).colspan(2);
        buildingTable.row();

        Table blueprint = new Table();
//        blueprint.setDebug(true);
        Label blueprintTitle = new Label("BLUEPRINT", skin, "black-label");
        blueprint.add(blueprintTitle).padTop(10);
        blueprint.row();

        addInfo(blueprint);

        Table items = new Table();
//        items.setDebug(true);
        Label number = new Label("1/12", skin, "black-label");
        items.add(number).padTop(10).colspan(4);
        items.row();

        List<String> blueprintsLearned = gameMenuManager.getMainCharacter().getBlueprintsLearned();
        float itemWidth = 400/4f-10;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    ImageButton testt = new ImageButton(generateTextureRegionDrawableObject(blueprintsLearned.get(4 * i + j)));
                    items.add(testt).width(itemWidth).height(itemWidth).pad(5);
                } catch (IndexOutOfBoundsException e) {
                    Image bg = new Image(generateTextureRegionDrawableObject("inv_background"));
                    items.add(bg).width(itemWidth).height(itemWidth).pad(5);
                }
            }
            items.row();
        }

        buildingTable.add(blueprint).width(400).top().expand();
        buildingTable.add(items).width(400).top().expand();
        buildingTable.row();

        TextButton buildIt = new TextButton("BUILD IT", skin, "game");
        buildingTable.add(buildIt).colspan(2).expand();

        buildingTable.setVisible(false);
        stage.addActor(buildingTable);
    }

    private void addInfo(Table table) {
        Image test = new Image(generateTextureRegionDrawableObject("inv_background"));
        table.add(test).width(110).height(110).padTop(10).padBottom(10);
        table.row();

        Table itemsRequired = new Table();
        itemsRequired.top();
//        itemsRequired.setDebug(true);
        itemsRequired.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("ITEMS REQUIRED", skin, "black-text");
        text.setFontScale(0.5f);
        infoBar.add(text);

        itemsRequired.add(infoBar).width(230).height(230 * 188f / 1756).colspan(6).padTop(5);
        itemsRequired.row();

        for (int i = 0; i < 2; ++i) {
            itemsRequired.add().width(20);
            for (int j = 0; j < 4; ++j){
                itemsRequired.add(new Image(generateTextureRegionDrawableObject("inv_background"))).width((250-20-20)/4f-5).height((250-20-20)/4f-5).pad(5).expandY();
            }
            itemsRequired.add().width(20);
            itemsRequired.row();
        }

        table.add(itemsRequired).width(250).height(250 * 1346 / 1862f);

    }
}


