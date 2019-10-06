package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.*;


/**
 * A class for game over table pop up.
 */
public class GameOverTable extends AbstractPopUpElement{

    // Table to be displayed
    private Table mainTable;

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
        mainTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("game_over_temp_bg"));

        ImageButton retry = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("game over retry temp"));
        mainTable.add(retry).padBottom(15).width(450).height(450*302/2313f);
        mainTable.row();

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // If pressed, restart level and hide screen
                retryQuest();
                hide();
            }
        });

        ImageButton toHome = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("game over home temp"));
        mainTable.add(toHome).padBottom(15).width(450).height(450*302/2313f);
        mainTable.row();
        toHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // If pressed, return home and hide screen
                returnHome();
                hide();
            }
        });

        mainTable.setVisible(false);
        stage.addActor(mainTable);
    }

    /**
     * Resets the quest once play dies and chooses retry.
     */
    public void retryQuest() {
        // Restore health to full
        MainCharacter.getInstance().changeHealth(50);

        // Reset quest
        GameManager.getManagerFromInstance(QuestManager.class).resetQuest();
    }

    /**
     * Allows player to return home and start new game.
     */
    public void returnHome() {
        // Create a game and set screen to main menu
        gameMenuManager.getGame().create();
        ((Game)Gdx.app.getApplicationListener()).setScreen(gameMenuManager.getGame().mainMenuScreen);
    }
}