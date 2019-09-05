package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

/**
 * Pop up screens.
 */
public class PopUpTable extends Table {

    // Exit button
    private ImageButton exit;
    float width;
    float height;
    private static PopUpTable opened = null;
    public String name;

    /**
     * Construct a table with the given width and height and name (for debugging).
     *
     * @param width Width of the table.
     * @param height Height of the table.
     * @param name Name of the table (for debugging).
     */
    public PopUpTable(float width, float height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        setPopUpTable();
        setExit();
    }

    /**
     * Initialise the table with its dimension and set its position and background.
     */
    private void setPopUpTable() {
        setSize(width, height);
        setPosition(Gdx.graphics.getWidth()/2f - getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - getHeight()/2);
        setBackground(generateTextureRegionDrawableObject("pop up screen"));
    }

    /**
     * Getter of the exit button.
     *
     * @return Exit button.
     */
    public ImageButton getExit() {
        return exit;
    }

    /**
     * Initialise the exit button with a fixed dimension and position.
     */
    private void setExit() {
        exit = new ImageButton(generateTextureRegionDrawableObject("exitButton"));
        exit.setSize(80, 80 * 207f / 305);
        exit.setPosition(Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f);
    }

    /**
     * Getter of opened table.
     *
     * @return Opened table.
     */
    public static PopUpTable getOpened() {
        return opened;
    }

    /**
     * Set opened table to be {table}.
     *
     * @param table Opened table.
     */
    public static void setOpened(PopUpTable table) {
        opened = table;
    }
}
