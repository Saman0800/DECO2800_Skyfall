package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class AbstractPopUpElement extends AbstractUIElement {
    protected GameMenuManager gameMenuManager;
    ImageButton exitButton;
    protected boolean isVisible = false;


    public void hide() {
        System.out.println("hiding exit");
        exitButton.setVisible(false);
        isVisible = false;
        resume();
        gameMenuManager.setPopUp(null);
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        GameManager.setPaused(false);
        GameScreen.isPaused = false;
    }

    /**
     * Pause the game.
     */
    private void pause() {
        GameManager.setPaused(true);
        GameScreen.isPaused = true;
    }

    public void show(){
        System.out.println("showing exit");
        exitButton.setVisible(true);
        isVisible = true;
        pause();
    }

    @Override
    public void updatePosition() {
        exitButton.setPosition(Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f);
    }

    public AbstractPopUpElement(Stage stage, ImageButton exitButton, String[] textureNames, TextureManager tm, GameMenuManager gameMenuManager) {
        super(stage, textureNames, tm);
        this.exitButton = exitButton;
        this.gameMenuManager = gameMenuManager;
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                   System.out.println("Clicked exit");
                   hide();
            }
        });
    }

    @Override
    public void draw() {
        System.out.println("Drawing exit");
        exitButton.setSize(80, 80 * 207f / 305);
        exitButton.setPosition(Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f);
        exitButton.setVisible(false);
        stage.addActor(exitButton);
    }

    public boolean isVisible() {
        return isVisible;
    }

}
