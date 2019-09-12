package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class SettingsTable extends AbstractPopUpElement{
    private final SoundManager soundManager;
    private Skin skin;
    private Table settingsTable;

    public SettingsTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm,
                         GameMenuManager gameMenuManager, Skin skin,
                         SoundManager soundManager) {
        super(stage,exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.draw();
        this.soundManager = soundManager;
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding settings table");
        settingsTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing settings table");
        settingsTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("Drawing settingsTable");
        settingsTable = new Table();
        settingsTable.setSize(600, 600 * 1346 / 1862f);
        settingsTable.setPosition(Gdx.graphics.getWidth()/2f - settingsTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - settingsTable.getHeight()/2);
        settingsTable.setDebug(true);
        settingsTable.top();
        settingsTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("SETTINGS", skin, "black-text");
        infoBar.add(text);

        settingsTable.add(infoBar).width(550).height(550 * 188f / 1756).padTop(20).colspan(3);
        settingsTable.row().padTop(20);

        settingsTable.setVisible(false);
        stage.addActor(settingsTable);
    }
}
