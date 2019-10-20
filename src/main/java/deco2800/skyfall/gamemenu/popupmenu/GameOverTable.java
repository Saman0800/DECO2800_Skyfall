package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.*;

/**
 * A class for game over table pop up.
 */
public class GameOverTable extends AbstractPopUpElement{

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
     * Draws the game over table.
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.bottom();
        baseTable.setSize(500, 500 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("game_over_temp_bg"));

        ImageButton retry = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("game over retry temp"));
        baseTable.add(retry).padBottom(65).width(450).height(450*302/2313f);
        baseTable.row();

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                retryQuest();
                hide();
            }
        });

        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /**
     * Resets the quest once play dies and chooses retry.
     */
    public void retryQuest() {
        // Reset quest
        GameManager.getManagerFromInstance(QuestManager.class).resetQuest();
    }
}