package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.mainmenu.MainMenuScreen;
import deco2800.skyfall.managers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

/**
 * A class for game over table pop up.
 */
public class GameOverTable extends AbstractPopUpElement{

    // Main menu of game
    private MainMenuScreen menuScreen;

    private Table mainTable;
    private GameMenuManager gameMenuManager;

    public static boolean retry = false;

    private StatisticsManager sm;

    /**
     * Constructs a game over table.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one (Shouldn't have one).
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     */
    public GameOverTable(Stage stage, ImageButton exit,
                         String[] textureNames, TextureManager tm,
                         GameMenuManager gameMenuManager) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.gameMenuManager = gameMenuManager;
        this.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        mainTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        mainTable.setVisible(true);
    }

    /**
     * {@inheritDoc}
     * Draws the game over table.
     */
    @Override
    public void draw() {
        super.draw();
        mainTable = new Table();
        mainTable.bottom();
        mainTable.setSize(500, 500 * 1346 / 1862f);
        mainTable.setPosition(Gdx.graphics.getWidth()/2f - mainTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - mainTable.getHeight()/2);
        mainTable.setBackground(generateTextureRegionDrawableObject("game_over_temp_bg"));

        ImageButton retry = new ImageButton(generateTextureRegionDrawableObject("game over retry temp"));
        mainTable.add(retry).padBottom(15).width(450).height(450*302/2313f);
        mainTable.row();

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                retryQuest();
            }
        });

        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("game over home temp"));
        mainTable.add(toHome).padBottom(15).width(450).height(450*302/2313f);
        mainTable.row();
        toHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                returnHome();
            }
        });

        mainTable.setVisible(false);
        stage.addActor(mainTable);
    }

    /**
     * Resets the quest once play dies and chooses retry.
     */
    private void retryQuest() {
        GameManager.getManagerFromInstance(QuestManager.class).resetQuest();
        MainCharacter.getInstance().changeHealth(50);
    }

    /**
     * Allows player to return home and start new game.
     */
    private void returnHome() {
        gameMenuManager.getGame().create();
        ((Game)Gdx.app.getApplicationListener()).setScreen(gameMenuManager.getGame().mainMenuScreen);
    }
}