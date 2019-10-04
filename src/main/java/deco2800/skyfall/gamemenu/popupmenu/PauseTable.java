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
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

/**
 * A class for pause table pop up.
 */
public class PauseTable extends AbstractPopUpElement{
    private Skin skin;
    private Table pauseTable;


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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        pauseTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        pauseTable.setVisible(true);
    }


    /**
     * {@inheritDoc}
     * Draw pause table.
     */
    @Override
    public void draw() {
        super.draw();
        pauseTable = new Table();
        pauseTable.setSize(600, 430);
        pauseTable.setPosition(Gdx.graphics.getWidth() / 2f - pauseTable.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - pauseTable.getHeight() / 2);
        pauseTable.setBackground(generateTextureRegionDrawableObject("popup_bg"));
        pauseTable.setDebug(true);

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label("GAME PAUSED", skin, "navy-text");
        infoBar.add(text);

        pauseTable.add(infoBar).width(475).height(475 * 188f / 1756).padTop(20).colspan(3);
        pauseTable.row();

//        Label soundEffect = new Label("SOUND EFFECTS", skin, "white-text");
//        soundEffect.setFontScale(0.7f);
//        soundEffect.setWrap(true);
//        soundEffect.setAlignment(Align.center);
//        soundEffect.getStyle().font.getData().setLineHeight(40);
//        pauseTable.add(soundEffect).width(110);
//        pauseTable.row();

        Slider.SliderStyle s = new Slider.SliderStyle();
        s.background = generateTextureRegionDrawableObject("green_pill");
        s.knob = generateTextureRegionDrawableObject("new_clock");
        s.knob.setMinHeight(10);
        s.knob.setMinWidth(10);
        s.knobBefore = generateTextureRegionDrawableObject("light_blue_bg");
        s.knobAfter = generateTextureRegionDrawableObject("green_pill");

        Slider test = new Slider(0, 100, 1, false, s);
        test.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(String.valueOf(test.getValue()));
            }
        });

        pauseTable.add(test).colspan(3).row();
        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("toHome"));
        toHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                gameMenuManager.getGame().batch = new SpriteBatch();
//                gameMenuManager.getGame().setScreen(new MainMenuScreen(gameMenuManager.getGame()));
//            System.out.println(gameMenuManager.getGame().batch == null);
//            gameMenuManager.getGame().create();
            }
        });

        Label homeText = new Label("HOME", skin, "white-text");
        homeText.setFontScale(0.7f);

        ImageButton resume = new ImageButton(generateTextureRegionDrawableObject("resume"));
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        Label resumeText = new Label("RESUME", skin, "white-text");
        resumeText.setFontScale(0.7f);

        ImageButton reset = new ImageButton(generateTextureRegionDrawableObject("reset"));
        Label resetText = new Label("RESET", skin, "white-text");
        resetText.setFontScale(0.7f);

        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        pauseTable.row();
        pauseTable.add(homeText).expandY().right().bottom().padRight(17.1f);//.padRight(25)
        pauseTable.add(resumeText).expandY().bottom().padBottom(12.5f);
        pauseTable.add(resetText).expandY().left().bottom().padLeft(11.85f);//.padLeft(25)
        pauseTable.row();
        pauseTable.add(toHome).width(100).height(100 * 263 / 264f).right().padBottom(70);
        pauseTable.add(resume).width(125).height(125 * 409 / 410f).padBottom(70);
        pauseTable.add(reset).width(100).height(100 * 263 / 264f).left().padBottom(70);

        pauseTable.setVisible(false);
        stage.addActor(pauseTable);
    }


}

