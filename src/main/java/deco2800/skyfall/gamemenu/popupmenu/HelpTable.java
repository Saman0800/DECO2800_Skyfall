package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class HelpTable extends AbstractPopUpElement{
    private Skin skin;
    private Table helpTable;


    public HelpTable(Stage stage, ImageButton exit,
                     String[] textureNames, TextureManager tm,
                     GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding help table");
        helpTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing help table");
        helpTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("Drawing HELPTABLE");
        helpTable = new Table();
        helpTable.setSize(600, 600 * 1346 / 1862f);
        helpTable.setPosition(Gdx.graphics.getWidth()/2f - helpTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - helpTable.getHeight()/2);
        helpTable.setDebug(true);
        helpTable.top();
        helpTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("HELP", skin, "black-text");
        infoBar.add(text);

        helpTable.add(infoBar).width(550).height(550 * 188f / 1756).padTop(20).colspan(3);
        helpTable.row().padTop(20);

        setControl("W", "Move Up", helpTable);
        setControl("A", "Move Left", helpTable);
        setControl("S", "Move Down", helpTable);
        setControl("D", "Move Right", helpTable);

//        Label space = new Label("SPACE", skin, "WASD");
//        space.setAlignment(Align.center);
//        helpTable.add(space).height(50).padLeft(25).colspan(2).expandY();
//        Label spaceDescription = new Label("Description", skin, "WASD");
//        helpTable.add(spaceDescription).height(50).left().expandX().padLeft(20);
//        helpTable.row().padTop(15);
        helpTable.setVisible(false);
        stage.addActor(helpTable);
        //System.out.println("Finished Drawing HELPTABLE");
    }


    private void setControl(String key, String description, Table table) {
        Label label = new Label(key, skin, "white-label");
        table.add(label).padLeft(25).width(50).height(50);
        label.setAlignment(Align.center);
        Label desc = new Label(description, skin, "white-label");
        table.add(desc).left().padLeft(20).height(50).expandX();
//        table.add().expandX().fillX();
        table.row().padTop(15);
    }


}
