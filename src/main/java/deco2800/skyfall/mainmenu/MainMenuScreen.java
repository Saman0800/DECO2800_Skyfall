package deco2800.skyfall.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;

public class MainMenuScreen implements Screen {
    final SkyfallGame game;
    private Stage stage;
    private Skin skin;

    private static final int MIN_HEIGHT = 720;
    private static final int MIN_WIDTH = 1280;

    /**
     * The constructor of the MainMenuScreen
     *
     * @param game the Iguana Chase Game to run
     */
    public MainMenuScreen(final SkyfallGame game) {
        this.game = game;
        stage = new Stage(new ExtendViewport(MIN_WIDTH, MIN_HEIGHT), game.batch);
        skin = GameManager.get().getSkin();

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("background"));
        background.setFillParent(true);
        stage.addActor(background);

        // Label logo = new Label("BIG PHARMA", skin);
        // logo.setFontScale(5.0f);
        // logo.setPosition(1280/2 - 225, 720/2 + 100);
        // stage.addActor(logo);

        TextButton newGameBtn = new TextButton("SINGLE PLAYER", skin, "main_menu");
        newGameBtn.getStyle().fontColor = Color.BLACK;

        newGameBtn.setPosition(50, MIN_HEIGHT - 220);
        stage.addActor(newGameBtn);

        Button connectToServerButton = new TextButton("CONNECT TO SERVER", skin, "main_menu");
        connectToServerButton.setPosition(50, MIN_HEIGHT - 170);
        stage.addActor(connectToServerButton);

        Button tutorialButton = new TextButton("TUTORIAL", skin, "main_menu");
        tutorialButton.setPosition(50, MIN_HEIGHT - 270);
        stage.addActor(tutorialButton);

        //Button startServerButton = new TextButton("START SERVER", skin, "main_menu");
        //startServerButton.setPosition(10, 0);
        //stage.addActor(startServerButton);

        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().isTutorial = true;
                // TODO Accept user-provided seed or generate random seed.
                game.setScreen(new GameScreen(new SkyfallGame(), 3, true));
            }
        });

        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().isTutorial = false;
                // TODO Accept user-provided seed or generate random seed.
                game.setScreen(new GameScreen(new SkyfallGame(), 1, true));
            }
        });

        connectToServerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.get().isTutorial = false;
                // TODO Accept user-provided seed or generate random seed.
                game.setScreen(new GameScreen(new SkyfallGame(), 3, false));
            }
        });
    }

    /**
     * Begins things that need to begin when shown
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Pauses the screen
     */
    public void pause() {
        //do nothing
    }

    /**
     * Resumes the screen
     */
    public void resume() {
        //do nothing
    }

    /**
     * Hides the screen
     */
    public void hide() {
        //do nothing
    }

    /**
     * Resizes the main menu stage to a new width and height
     *
     * @param width  the new width for the menu stage
     * @param height the new width for the menu stage
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Renders the menu
     *
     * @param delta
     */
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act(delta);
        stage.draw();
    }

    /**
     * Disposes of the stage that the menu is on
     */
    public void dispose() {
        stage.dispose();
    }
}
