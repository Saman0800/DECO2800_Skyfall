package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * A class for EndGame baseTable pop up.
 */
public class EndGameTable extends AbstractPopUpElement{

    /**
     * Constructor
     *
     * @param stage           Game stage
     * @param exitButton      Exit button to display
     * @param textureNames    Texture names to fetch
     * @param tm              The texture manager
     * @param gameMenuManager The gamemenumanager
     */
    public EndGameTable(Stage stage, ImageButton exitButton, String[] textureNames,
                        TextureManager tm, GameMenuManager gameMenuManager) {
        super(stage, exitButton, textureNames,tm , gameMenuManager);
        this.draw();
    }

    /**
     * {@inheritDoc}
     * Draw endgame baseTable.
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.bottom();
        baseTable.setSize(800, 500);
        baseTable.setPosition(Gdx.graphics.getWidth() / 2f - baseTable.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - baseTable.getHeight() / 2);
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("endGame"));
        baseTable.setDebug(false);

        ImageButton continueButton =
                new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("continueGame"));
        baseTable.add(continueButton).padBottom(65).width(450).height(450*302/2313f);
        baseTable.row();

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }


    /**
     * Restarts the entire game
     */
    public void restartGame() {
        // Return home here to start a new game
    }
}


