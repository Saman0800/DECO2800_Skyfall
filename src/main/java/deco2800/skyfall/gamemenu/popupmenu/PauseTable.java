package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.managers.TextureManager;


/**
 * A class for pause baseTable pop up.
 */
public class PauseTable extends AbstractPopUpElement {
    private Skin skin;
    private SoundManager sm;

    /**
     * Constructs a pause baseTable.
     *
     * @param stage           Current stage.
     * @param exit            Exit button if it has one.
     * @param textureNames    Names of the textures.
     * @param tm              Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin            Current skin.
     */
    public PauseTable(Stage stage, ImageButton exit,
                      String[] textureNames, TextureManager tm,
                      GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.draw();
        sm = GameManager.getManagerFromInstance(SoundManager.class);
    }


    /**
     * {@inheritDoc}
     * Draw pause baseTable.
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setSize(600, 430);
        baseTable.setPosition(Gdx.graphics.getWidth() / 2f - baseTable.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - baseTable.getHeight() / 2);
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));
        baseTable.setDebug(false);

        Table infoBar = new Table();
        infoBar.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label("GAME PAUSED", skin, "navy-text");
        infoBar.add(text);

        baseTable.add(infoBar).width(475).height(475 * 188f / 1756).padTop(20).colspan(3);
        baseTable.row();

        String textStyle = "white-text";

        Label soundEffect = new Label("SOUND EFFECTS", skin, textStyle);
        soundEffect.setFontScale(0.7f);
        soundEffect.setWrap(true);
        soundEffect.setAlignment(Align.center);
        soundEffect.getStyle().font.getData().setLineHeight(40);
        baseTable.add(soundEffect).width(110);

        // Slider controlling volume of sound effects
        Slider soundEffectsBar = new Slider(0, 100, 1, false, skin, "default-slider");
        soundEffectsBar.setValue(100);
        soundEffectsBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sm.setSoundVolume(soundEffectsBar.getValue());
            }
        });

        baseTable.add(soundEffectsBar).height(50).width(300).colspan(2).row();

        Label music = new Label("MUSIC", skin, textStyle);
        music.setFontScale(0.7f);
        baseTable.add(music);

        // Slider controlling volume of BGM (music)
        Slider musicBar = new Slider(0, 100, 1, false, skin, "default-slider");
        musicBar.setValue(100);
        musicBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sm.setMusicVolume(musicBar.getValue());
            }
        });

        baseTable.add(musicBar).height(50).width(300).colspan(2).row();

        ImageButton resume = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("resume"));
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        Label resumeText = new Label("RESUME", skin, textStyle);
        resumeText.setFontScale(0.7f);

        ImageButton reset = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("reset"));
        Label resetText = new Label("RESET", skin, textStyle);
        resetText.setFontScale(0.7f);

        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retryQuest();
                hide();
            }
        });

        baseTable.row();
        baseTable.add(resumeText).expandY().padBottom(10f);
        baseTable.add(resetText).expandY().padBottom(8f);
        baseTable.row();
        baseTable.add(resume).width(125).height(125 * 409 / 410f).padBottom(70);
        baseTable.add(reset).width(125).height(125 * 409 / 410f).padBottom(70);

        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /**
     * Resets the quest once play dies and chooses retry.
     */
    public void retryQuest() {
        // Reset quest
        GameManager.getManagerFromInstance(QuestManager.class).resetQuest();
    }
}