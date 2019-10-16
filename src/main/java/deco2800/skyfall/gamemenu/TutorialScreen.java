package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.ArrayList;
import java.util.List;

public class TutorialScreen implements Screen {

    private static final int MIN_HEIGHT = 720;
    private static final int MIN_WIDTH = 1280;

    private Stage stage;
    private TextureManager textureManager;
    private List<Image> screens;

    public TutorialScreen() {
        stage = new Stage(new ExtendViewport(MIN_WIDTH, MIN_HEIGHT));
        textureManager = GameManager.get().getManager(TextureManager.class);
        screens = new ArrayList<>();
        String[] screenNames = {"story", "your_mission", "tutorial_1", "tutorial_2", "tutorial_3", "tutorial_4", "tutorial_5"};
        for (String screenName : screenNames) {
            screens.add(new Image(new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(screenName))))));
        }
        int currentScreen = 1;
        toScreen(currentScreen);
    }

    void toScreen(int screen) {
        Image currentScreen = screens.get(screen - 1);
        currentScreen.setFillParent(true);
        stage.addActor(currentScreen);
    }

    @Override
    public void show () {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause () {
        //nth
    }

    @Override
    public void resume () {
        // nth
    }

    @Override
    public void hide () {
        //Nth
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
