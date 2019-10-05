package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.BGMManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

/**
 * A class for pause table pop up.
 */
public class PauseTable extends AbstractPopUpElement{
    private Skin skin;
    private Table table;
    private BGMManager bgmManager;


    /**
     * Constructs a pause table.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one.
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin Current skin.
     */
    public PauseTable(Stage stage, ImageButton exit,
                     String[] textureNames, TextureManager tm,
                     GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
        bgmManager = GameManager.getManagerFromInstance(BGMManager.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        table.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        table.setVisible(true);
    }


    /**
     * {@inheritDoc}
     * Draw pause table.
     */
    @Override
    public void draw() {
        super.draw();
        table = new Table();
        table.setSize(600, 430);
        table.setPosition(Gdx.graphics.getWidth() / 2f - table.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - table.getHeight() / 2);
        table.setBackground(generateTextureRegionDrawableObject("popup_bg"));
//        table.setDebug(true);

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label("GAME PAUSED", skin, "navy-text");
        infoBar.add(text);

        table.add(infoBar).width(475).height(475 * 188f / 1756).padTop(20).colspan(3);
        table.row();

        String textStyle = "white-text";

        Label soundEffect = new Label("SOUND EFFECTS", skin, textStyle);
        soundEffect.setFontScale(0.7f);
        soundEffect.setWrap(true);
        soundEffect.setAlignment(Align.center);
        soundEffect.getStyle().font.getData().setLineHeight(40);
        table.add(soundEffect).width(110);

        Slider.SliderStyle s = new Slider.SliderStyle();
        s.background = generateTextureRegionDrawableObject("knob_after");
        s.background.setMinHeight(50);
        s.knob = generateTextureRegionDrawableObject("knob");
        s.knob.setMinHeight(50);
        s.knob.setMinWidth(50);
        s.knobBefore = generateTextureRegionDrawableObject("knob_before");
        s.knobBefore.setMinHeight(50);
        s.knobAfter = generateTextureRegionDrawableObject("knob_after");
        s.knobAfter.setMinHeight(50);

        // Slider controlling volume of sound effects
        Slider soundEffectsBar = new Slider(0, 100, 1, false, s);
        soundEffectsBar.setValue(100);
        soundEffectsBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        table.add(soundEffectsBar).height(50).width(300).colspan(2).row();

        Label music = new Label("MUSIC", skin, textStyle);
        music.setFontScale(0.7f);
        table.add(music);

        // Slider controlling volume of BGM (music)
        Slider musicBar = new Slider(0, 100, 1, false, s);
        musicBar.setValue(100);
        musicBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        table.add(musicBar).height(50).width(300).colspan(2).row();

        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("toHome"));
        toHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        Label homeText = new Label("HOME", skin, textStyle);
        homeText.setFontScale(0.7f);

        ImageButton resume = new ImageButton(generateTextureRegionDrawableObject("resume"));
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        Label resumeText = new Label("RESUME", skin, textStyle);
        resumeText.setFontScale(0.7f);

        ImageButton reset = new ImageButton(generateTextureRegionDrawableObject("reset"));
        Label resetText = new Label("RESET", skin, textStyle);
        resetText.setFontScale(0.7f);

        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        table.row();
        table.add(homeText).expandY().right().bottom().padRight(17.1f);//.padRight(25)
        table.add(resumeText).expandY().bottom().padBottom(12.5f);
        table.add(resetText).expandY().left().bottom().padLeft(11.85f);//.padLeft(25)
        table.row();
        table.add(toHome).width(100).height(100 * 263 / 264f).right().padBottom(70);
        table.add(resume).width(125).height(125 * 409 / 410f).padBottom(70);
        table.add(reset).width(100).height(100 * 263 / 264f).left().padBottom(70);

        table.setVisible(false);
        stage.addActor(table);
    }


}

