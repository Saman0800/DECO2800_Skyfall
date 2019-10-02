package deco2800.skyfall.gamemenu;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * Generic Pop up element.
 * By default just displays an exit button.
 */
public class AbstractPopUpElement extends AbstractUIElement {
    protected GameMenuManager gameMenuManager;
    private ImageButton exitButton;
    private boolean isVisible = false;

    /**
     * Hides all of the elements associated with the element.
     */
    public void hide() {
        if (exitButton != null) {
            exitButton.setVisible(false);
        }
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

    /**
     * Shows the element
     */
    public void show(){
        if (exitButton != null) {
            exitButton.setVisible(true);
            exitButton.top();
        }
        isVisible = true;
        pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        float x  = stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2);
        float y = stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2);
        if (exitButton != null) {
            exitButton.setPosition(x * 0.9f, y * 0.9f);
        }
    }

    /**
     * Constructor
     * @param stage Game stage
     * @param exitButton Exit button to display
     * @param textureNames Texture names to fetch
     * @param tm The texture manager
     * @param gameMenuManager The gamemenumanager
     */
    public AbstractPopUpElement(Stage stage, ImageButton exitButton, String[] textureNames,
                                TextureManager tm, GameMenuManager gameMenuManager) {
        super(stage, textureNames, tm);
        this.exitButton = exitButton;
        this.gameMenuManager = gameMenuManager;
        if (exitButton != null) {
            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    hide();
                }
            });
        }
    }

    @Override
    public void draw() {
        if (exitButton != null) {
            exitButton.setSize(80, 80 * 207f / 305);
            updatePosition();
            exitButton.setVisible(false);
            stage.addActor(exitButton);
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

}
