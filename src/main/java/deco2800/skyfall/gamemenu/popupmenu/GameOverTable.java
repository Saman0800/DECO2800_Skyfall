package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

/**
 * A class for game over table pop up.
 */
public class GameOverTable extends AbstractPopUpElement{
    private Skin skin;
    private Table gameOverTable;
    private GameMenuManager gameMenuManager;


    /**
     * Constructs a game over table.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one (Shouldn't have one).
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin Current skin.
     */
    public GameOverTable(Stage stage, ImageButton exit,
                         String[] textureNames, TextureManager tm,
                         GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.gameMenuManager = gameMenuManager;
        this.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        gameOverTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        gameOverTable.setVisible(true);
    }

    /**
     * {@inheritDoc}
     * Draws the game over table.
     */
    @Override
    public void draw() {
        super.draw();
        gameOverTable = new Table();
        gameOverTable.bottom();
        gameOverTable.setSize(500, 500 * 1346 / 1862f);
        gameOverTable.setPosition(Gdx.graphics.getWidth()/2f - gameOverTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - gameOverTable.getHeight()/2);
        gameOverTable.setBackground(generateTextureRegionDrawableObject("game over temp bg"));

        ImageButton retry = new ImageButton(generateTextureRegionDrawableObject("game over retry temp"));
        gameOverTable.add(retry).padBottom(15).width(450).height(450*302/2313f);
        gameOverTable.row();

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                gameMenuManager.getMainCharacter().changeHealth(10);
            }
        });

        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("game over home temp"));
        gameOverTable.add(toHome).padBottom(15).width(450).height(450*302/2313f);;
        gameOverTable.row();
        toHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        gameOverTable.setVisible(false);
        stage.addActor(gameOverTable);
    }

}



