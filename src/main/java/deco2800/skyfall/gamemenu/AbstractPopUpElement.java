package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.skyfall.GameScreen;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * Generic Pop up element. By default just displays an exit button.
 */
public class AbstractPopUpElement extends AbstractUIElement {
    protected GameMenuManager gameMenuManager;
    protected Table baseTable;
    private ImageButton exitButton;
    private boolean isVisible = false;
    private SoundManager sm;

    /**
     * Constructor
     * 
     * @param stage           Game stage
     * @param exitButton      Exit button to display
     * @param textureNames    Texture names to fetch
     * @param tm              The texture manager
     * @param gameMenuManager The gamemenumanager
     */
    public AbstractPopUpElement(Stage stage, ImageButton exitButton, String[] textureNames, TextureManager tm,
            GameMenuManager gameMenuManager) {
        super(stage, textureNames, tm);
        this.exitButton = exitButton;
        this.gameMenuManager = gameMenuManager;
        sm = GameManager.getManagerFromInstance(SoundManager.class);
        if (exitButton != null) {
            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    hide();
                }
            });
        }
    }

    /**
     * Hides all of the elements associated with the element.
     */
    public void hide() {
        if (exitButton != null) {
            exitButton.setVisible(false);
        }
        // Play menu sound when the player click exit button
        sm.playSound("menu");
        isVisible = false;
        gameMenuManager.getMainCharacter().resetVelocity();
        if (baseTable != null) {
            baseTable.setVisible(false);
        }
        resume();
        gameMenuManager.setPopUp(null);
    }

    /**
     * Resumes the game.
     */
    public static void resume() {
        GameManager.setPaused(false);
        GameScreen.setIsPaused(false);
    }

    /**
     * Pause the game.
     */
    private static void pause() {
        GameManager.setPaused(true);
        GameScreen.setIsPaused(true);
    }

    /**
     * Shows the element
     */
    public void show(){
        if (exitButton != null) {
            exitButton.setVisible(true);
            exitButton.top();
        }
        // Play menu sound when the player open the menu
        sm.playSound("menu");
        gameMenuManager.getMainCharacter().resetVelocity();
        if (baseTable != null) {
            baseTable.setZIndex(30);
            baseTable.setVisible(true);
        }
        isVisible = true;
        pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        float x = stage.getCamera().position.x + (stage.getCamera().viewportWidth / 2);
        float y = stage.getCamera().position.y + (stage.getCamera().viewportHeight / 2);
        if (exitButton != null) {
            exitButton.setPosition(x * 0.8f, y * 0.9f);
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


    protected void buidlingAndBuildWorldCommonFunctionality(Skin skin, String title) {
        baseTable = new Table();
        baseTable.setSize(800, 800 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth() / 2f - baseTable.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - baseTable.getHeight() / 2);
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));

        // baseTable banner
        Table banner = new Table();
        banner.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label(title, skin, "navy-text");
        text.setFontScale(1.1f);
        banner.add(text);

        baseTable.add(banner).width(750).height(750 * 188f / 1756).padTop(20).colspan(2);
        baseTable.row();

    }


    protected void blueprintShopTableDuplicatedCode () {
        baseTable = new Table();
        baseTable.setSize(910, 510);
        baseTable.setPosition(Gdx.graphics.getWidth() / 2f - baseTable.getWidth() / 2 + 60,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight() / 2);
        baseTable.top();
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));
    }


    protected  void goldAndConstructionTableDuplicatedFunctionality (String bannerName) {

        Image infoBar = new Image(gameMenuManager.generateTextureRegionDrawableObject(bannerName));
        infoBar.setSize(550, 55);
        infoBar.setPosition(90, 600);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("info_panel"));
        baseTable.addActor(infoBar);

    }
}
