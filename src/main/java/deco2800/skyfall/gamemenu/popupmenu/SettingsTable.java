package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.BGMManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.managers.TextureManager;


import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class SettingsTable extends AbstractPopUpElement{
    private SoundManager soundManager;

    private Skin skin;
    private Table settingsTable;
    private CheckBox soundFX;
    private CheckBox bgm;
    public SettingsTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm,
                         GameMenuManager gameMenuManager, Skin skin,
                         SoundManager soundManager) {
        super(stage,exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.draw();
        this.soundManager = soundManager;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        settingsTable.setVisible(false);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        settingsTable.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        super.draw();
        settingsTable = new Table();
        settingsTable.setSize(600, 600 * 1346 / 1862f);
        settingsTable.setPosition(Gdx.graphics.getWidth()/2f - settingsTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - settingsTable.getHeight()/2);
        settingsTable.setName("settingsTable");
        settingsTable.top();
        settingsTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("SETTINGS", skin, "black-text");
        infoBar.add(text);


        BitmapFont font = new BitmapFont();
        font.getData().setScale(1f);

        CheckBox.CheckBoxStyle cbs = new CheckBox.CheckBoxStyle(
                generateTextureRegionDrawableObject("unchecked"),
                generateTextureRegionDrawableObject("checked"),
                font, Color.BLACK);

        soundFX = new CheckBox("", cbs);
        bgm = new CheckBox("", cbs);

        bgm.setChecked(true);
        soundFX.setChecked(true);

        soundFX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Map<String, Sound> soundLoops = soundManager.getSoundMap();

                if (!soundFX.isChecked()) {
                    SoundManager.setPaused(true);

                    for (String sound : soundLoops.keySet()) {
                        SoundManager.stopSound(sound);
                    }

                } else {
                    SoundManager.setPaused(false);

                    for (String sound : soundLoops.keySet()) {
                        SoundManager.playSound(sound);
                    }

                }
            }
        });

        bgm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!bgm.isChecked()) {
                    BGMManager.paused = true;
                    BGMManager.mute();
                } else {
                    BGMManager.paused = false;
                    BGMManager.unmute();
                }

            }
        });
        Label muteSoundFXText = new Label("Sound FX", skin,"white-text");
        Label muteSoundBGMText = new Label("Background Music", skin,"white-text");

        muteSoundFXText.setSize(50, 50);


        settingsTable.add(infoBar).width(550).height(550 * 188f / 1756).padTop(20).colspan(3);
        settingsTable.row().padTop(20);

        settingsTable.add(soundFX).height(250).expand();
        settingsTable.add(muteSoundFXText).height(100).expand();

        settingsTable.row().padTop(20);

        settingsTable.add(bgm).height(250).expand();
        settingsTable.add(muteSoundBGMText).height(100).expand();

        settingsTable.setVisible(false);

        stage.addActor(settingsTable);
    }
}