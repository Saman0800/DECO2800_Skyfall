package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    }


    /**
     * Restarts the entire game
     */
    public void restartGame() {
        // Return home here to start a new game
    }
}


