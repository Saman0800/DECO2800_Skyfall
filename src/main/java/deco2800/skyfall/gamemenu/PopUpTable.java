package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class PopUpTable extends Table {


    private ImageButton exit;
    float width;
    float height;
    private static PopUpTable opened = null;
    public String name;


    public PopUpTable(float width, float height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        setPopUpTable();
        setExit();
    }

    private void setPopUpTable() {
        setSize(width, height);
        setPosition(Gdx.graphics.getWidth()/2f - getWidth()/2,
                Gdx.graphics.getHeight() / 2f - getHeight()/2);
        setBackground(generateTextureRegionDrawableObject("pop up screen"));
    }

    public ImageButton getExit() {
        return exit;
    }

    private void setExit() {
        exit = new ImageButton(generateTextureRegionDrawableObject("exitButton"));
        exit.setSize(80, 80 * 207f / 305);
        exit.setPosition(Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.9f);
    }

    public static PopUpTable getOpened() {
        return opened;
    }

    public static void setOpened(PopUpTable table) {
        opened = table;
    }
}
