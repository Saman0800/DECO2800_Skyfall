package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gui.HealthCircle;
import deco2800.skyfall.gui.SettingsTable;
import deco2800.skyfall.gamemenu.PopUpTable;


/**
 * Managers the menu bar during the game
 */
public class GameMenuManager extends TickableManager {



    private static TextureManager textureManager;
    private Stage stage;
    private MainCharacter mainCharacter;
    private HealthCircle healthCircle;
    private InventoryManager inventory;
    private SoundManager soundManager;
    private Skin skin;
    private MainCharacter[] characters;

    private static final int NUMBEROFCHARACTERS = 10;

    public GameMenuManager() {
        textureManager = GameManager.get().getManager(TextureManager.class);
        inventory = GameManager.get().getManager(InventoryManager.class);
        soundManager = GameManager.get().getManager(SoundManager.class);
        stage = null;
        skin = null;
        characters = new MainCharacter[NUMBEROFCHARACTERS];
    }

    @Override
    public void onTick(long i) {
        //Get the current state of the inventory on tick so that display can be updated
        inventory = GameManager.get().getManager(InventoryManager.class);

        if (healthCircle != null) {
            healthCircle.update();
        }



    }

    public InventoryManager getInventory() {
        return inventory;
    }

    public static TextureManager getTextureManager() {
        return textureManager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
    }


    private void pause() {
        GameManager.setPaused(true);
    }

    public void resume(PopUpTable table) {
        GameManager.setPaused(false);
        GameScreen.isPaused = false;
        exit(table);
    }

    private void exit(PopUpTable table) {
        table.setVisible(false);
        table.getExit().setVisible(false);
        PopUpTable.setOpened(null);
        System.out.println("exited " + table.name);
    }

    public void open(PopUpTable table) {
        if (PopUpTable.getOpened() != null) {
            System.out.println("Should be exited: " + PopUpTable.getOpened().name);
            exit(PopUpTable.getOpened());
        }
        table.setVisible(true);
        table.getExit().setVisible(true);
        GameScreen.isPaused = true;
        pause();
        PopUpTable.setOpened(table);
        System.out.println("opened " + table.name);
    }


    public static TextureRegionDrawable generateTextureRegionDrawableObject(String sName) {
        return new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(sName))));
    }

    public void setMainCharacter(MainCharacter mainCharacter) {
        if (stage == null) {
            System.out.println("Please set stage before adding character");
            return;
        }
        this.mainCharacter = mainCharacter;

}

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }


    public void addHealthCircle(HealthCircle hc) {
        this.healthCircle = hc;
    }
}


