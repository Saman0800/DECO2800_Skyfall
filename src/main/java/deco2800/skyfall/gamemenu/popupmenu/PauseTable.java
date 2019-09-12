package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class PauseTable extends AbstractPopUpElement{
    private Skin skin;
    private Table pauseTable;


    public PauseTable(Stage stage, ImageButton exit,
                     String[] textureNames, TextureManager tm,
                     GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding pause table");
        pauseTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing pause table");
        pauseTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("Drawing PAUSETABLE");
        pauseTable = new Table();
//        pauseTable.setDebug(true);
        pauseTable.setSize(500, 500 * 1346 / 1862f);
        pauseTable.setPosition(Gdx.graphics.getWidth()/2f - pauseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - pauseTable.getHeight()/2);
        pauseTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("GAME PAUSED", skin, "black-text");
        infoBar.add(text);

        pauseTable.add(infoBar).width(475).height(475 * 188f / 1756).padTop(20).colspan(3);
        pauseTable.row();

        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("goHome"));
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
//                gameMenuManager.getGame().setScreen(new GameScreen(new SkyfallGame(), 1, true));
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

