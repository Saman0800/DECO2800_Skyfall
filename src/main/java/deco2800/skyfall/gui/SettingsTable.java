package deco2800.skyfall.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.SoundManager;

public class SettingsTable {

    Table settingsTable;
    SoundManager soundManager;
    Stage stage;
    boolean isVisible;
    public SettingsTable(Stage stage, SoundManager soundManager, String background) {
        Table settingsTable = new Table();
        settingsTable.setSize(500, 500*1346/1862);
        settingsTable.setPosition(Gdx.graphics.getWidth()/2 - 200, Gdx.graphics.getHeight()/2 - 90);
        settingsTable.setBackground(GameMenuManager.generateTextureRegionDrawableObject("pop up screen"));

        this.soundManager = soundManager;
        this.stage = stage;
        stage.addActor(settingsTable);

    }

    public void isVisible() {
        settingsTable.setVisible(true);
    }

    public void onTick() {

    }
}
