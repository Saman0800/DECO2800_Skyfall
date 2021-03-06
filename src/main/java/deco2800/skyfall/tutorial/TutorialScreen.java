package deco2800.skyfall.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for tutorial screens.
 */
public class TutorialScreen implements Screen {

    private static final int MIN_HEIGHT = 720;
    private static final int MIN_WIDTH = 1280;

    // Current stage for tutorial screen
    private Stage stage;
    // Current skin used for tutorial screen
    private Skin skin;
    private TextureManager textureManager;
    // List of tutorial screens in Image
    private List<Image> screens;
    // Total number of screens
    private static final int  NUMBER_OF_SCREENS = 7;
    private SkyfallGame game;

    /**
     * Constructs a tutorial screen for the game.
     *
     * @param game Current game
     */
    public TutorialScreen(SkyfallGame game) {
        stage = new Stage(new ExtendViewport(MIN_WIDTH, MIN_HEIGHT));
        this.game = game;
        textureManager = GameManager.get().getManager(TextureManager.class);
        skin = GameManager.get().getSkin();
        // Load up the screens
        screens = new ArrayList<>();
        String[] screenNames = {"story", "your_mission", "tutorial_1", "tutorial_2", "tutorial_3", "tutorial_4", "tutorial_5"};
        for (String screenName : screenNames) {
            screens.add(new Image(new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(screenName))))));
        }
        // Show the first screen first
        int currentScreen = 1;
        toScreen(currentScreen);
    }

    /**
     * Switch to {Screen} and draw back the arrows
     *
     * @param screen screen number
     */
    private void toScreen(int screen) {
        Image currentScreen = screens.get(screen - 1);
        currentScreen.setFillParent(true);
        stage.addActor(currentScreen);

        // Setting up the arrows
        TextureRegionDrawable arrow = new TextureRegionDrawable((new TextureRegion(textureManager.getTexture("arrow"))));
        // Show arrow that goes back to previous screen
        if (screen > 1) {
            ImageButton leftArrow = new ImageButton(arrow);
            // Rotates the arrow (the one pointing to left)
            leftArrow.setTransform(true);
            leftArrow.setOrigin(30, 25);
            leftArrow.setRotation(180);

            leftArrow.setSize(75, 60);
            leftArrow.setPosition(28, 20);
            stage.addActor(leftArrow);
            leftArrow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentScreen.addAction(Actions.removeActor());
                    toScreen(screen - 1);
                }
            });

            Label back = new Label("BACK", skin, "white-text");
            back.setFontScale(0.85f);
            back.setPosition(105, 18);
            stage.addActor(back);
        }

        // Show arrow that goes to the next screen
        if (screen < NUMBER_OF_SCREENS) {
            ImageButton rightArrow = new ImageButton(arrow);
            rightArrow.setSize(75, 60);
            rightArrow.setPosition(MIN_WIDTH - 90f, 10);
            stage.addActor(rightArrow);
            rightArrow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentScreen.addAction(Actions.removeActor());
                    toScreen(screen + 1);
                }
            });

            Label next = new Label("NEXT", skin, "white-text");
            next.setFontScale(0.85f);
            next.setPosition(MIN_WIDTH - 190f, 20);
            stage.addActor(next);
        }

        // Draw out "DONE" in last screen
        if (screen == NUMBER_OF_SCREENS) {
            ImageButton home = new ImageButton(new TextureRegionDrawable((new TextureRegion(textureManager.getTexture("home_button")))));
            home.setSize(250, 65);
            home.setPosition(MIN_WIDTH - 280f, 12);
            stage.addActor(home);

            home.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentScreen.addAction(Actions.removeActor());
                    game.setScreen(game.getMainMenuScreen());
                }
            });
        }
    }

    /**
     * Begins things that need to begin when shown
     */
    @Override
    public void show () {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders the screens
     *
     * @param delta
     */
    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Resizes the tutorial screen stage to a new width and height
     *
     * @param width  the new width for the tutorial screen stage
     * @param height the new width for the tutorial screen stage
     */
    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Pauses the screen
     */
    @Override
    public void pause () {
        // Do nothing
    }

    /**
     * Resumes the screen
     */
    @Override
    public void resume () {
        // Do nothing
    }

    /**
     * Hides the screen
     */
    @Override
    public void hide () {
        // Do nothing
    }

    /**
     * Disposes of the stage that the tutorial screen is on.
     */
    @Override
    public void dispose () {
        stage.dispose();
    }
}

