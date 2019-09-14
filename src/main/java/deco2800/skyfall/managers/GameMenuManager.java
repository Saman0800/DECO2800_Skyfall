package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.*;
import deco2800.skyfall.gamemenu.popupmenu.SettingsTable;
import deco2800.skyfall.gamemenu.popupmenu.*;

import java.util.*;


/**
 * Manages the menu bar during the game
 */
public class GameMenuManager extends TickableManager {

    private static TextureManager textureManager;
    private Stage stage;
    private MainCharacter mainCharacter;
    private InventoryManager inventory;
    private SoundManager soundManager;
    private Skin skin;
    private String[] characters;
    private SkyfallGame game;

    // Number of characters in the game.
    public static final int NUMBEROFCHARACTERS = 5;
    private StatisticsManager sm;

    //Refactor Code
    private Map<String, AbstractUIElement> uiElements;
    private Map<String, AbstractPopUpElement> popUps;
    private String currentPopUpElement = null;

    //TODO: REMOVE WHEN REFACTOR IS FINISHED
    public final static boolean runRefactored  = true;

    /**
     * Initialise a new GameMenuManager with stage and skin including the characters in the game.
     * And construct Manager instances for later use.
     */
    public GameMenuManager() {
        textureManager = GameManager.get().getManager(TextureManager.class);
        inventory = GameManager.get().getManager(InventoryManager.class);
        soundManager = GameManager.get().getManager(SoundManager.class);
        stage = null;
        skin = null;
        characters = new String[NUMBEROFCHARACTERS];
        // testing
        characters[0] = "MainCharacter";
        characters[1] = "bowman";
        characters[2] = "robot";
        characters[3] = "spider";
        characters[4] = "spacman_ded";
        uiElements = new HashMap<>();
        popUps = new HashMap<>();
    }

    //used for testing
    public GameMenuManager(TextureManager tm, SoundManager sm,
                           InventoryManager im, Stage stage, Skin skin,
                           Map<String, AbstractPopUpElement> popUps,
                           Map<String, AbstractUIElement> uiElements) {
        GameMenuManager.textureManager = tm;
        soundManager = sm;
        inventory = im;
        this.stage = stage;
        this.skin = skin;
        this.popUps = popUps;
        this.uiElements = uiElements;
    }

    @Override
    public void onTick(long i) {

        if (currentPopUpElement != null) {
            AbstractPopUpElement popUp = popUps.get(currentPopUpElement);

            if (popUp != null && !popUp.isVisible()) {
                popUp.update();
                popUp.show();
            }
        }

        for (String key: uiElements.keySet()) {
            AbstractUIElement uiElement = uiElements.get(key);
            uiElement.update();
        }
    }

    /**
     * Sets the current game to be {game}.
     *
     * @param game Current game.
     */
    public void setGame(SkyfallGame game) {
        this.game = game;
    }

    /**
     * Getter of current game.
     *
     * @return Current game.
     */
    public SkyfallGame getGame() {
        return game;
    }

    /**
     * Getter of current Inventory.
     *
     * @return Current Inventory.
     */
    public InventoryManager getInventory() {
        return inventory;
    }

    /**
     * Getter of the TextureManager of the menu.
     *
     * @return TextureManager of the menu.
     */
    public static TextureManager getTextureManager() {
        return textureManager;
    }

    /**
     * Sets the stage of the menu to be {stage}.
     *
     * @param stage Current stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Getter of current stage.
     *
     * @return Current stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the skin of the menu to be {skin}.
     *
     * @param skin Current skin.
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    /**
     * Getter of current stage.
     *
     * @return Current stage.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Pauses the game.
     */
    private void pause() {
        GameManager.setPaused(true);
        GameScreen.isPaused = true;
    }

    /**
     * Generates an instance of TextureRegionDrawable with the given texture name.
     *
     * @param sName Texture Name.
     *
     * @return An instance of TextureRegionDrawable with the given texture name.
     */
    public static TextureRegionDrawable generateTextureRegionDrawableObject(String sName) {
        return new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(sName))));
    }

    /**
     * Getter of main character of the game.
     *
     * @return Main character of the game.
     */
    public MainCharacter getMainCharacter() {
        return sm.getCharacter();
    }


    /**
     * Getter of all characters in the game.
     *
     * @return Texture names of the characters.
     */
    public String[] getCharacters() {
        return characters;
    }

    //refactor
    public void addStatsManager(StatisticsManager statsManager) {
        sm = statsManager;
    }

    public void drawAllElements(){
        if (sm == null) {
            System.out.println("Please add stats manager before drawing");
            return;
        }
        if (runRefactored) {
            uiElements.put("healthCircle", new HealthCircle(stage, new String[]{"inner_circle", "big_circle"}, textureManager, sm));

            uiElements.put("gameMenuBar", new GameMenuBar(stage, null, textureManager, this));

            popUps.put("inventoryTable",
                    new InventoryTable(stage, new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                            null, textureManager, skin,this));

            popUps.put("settingsTable", new SettingsTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this,
                    skin, soundManager));

            popUps.put("helpTable", new HelpTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this,
                    skin));

            popUps.put("pauseTable", new PauseTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this,
                    skin));

            popUps.put("playerSelectTable", new PlayerSelectTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this,
                    skin));

            popUps.put("buildingTable", new BuildingTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this,
                    skin));

            popUps.put("goldTable", new GoldTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this, sm, skin));

            popUps.put("chestTable",new ChestTable(stage,
                    new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                    null, textureManager, this, sm, skin));

            popUps.put("gameOverTable", new GameOverTable(stage,
                    null,
                    null, textureManager, this,
                    skin));
        }
    }

    /**
     * Getter of current pop up table displayed
     *
     * @return Current pop up table.
     */
    public AbstractPopUpElement getCurrentPopUp() {
        return popUps.get(currentPopUpElement);
    }

    public AbstractUIElement getUIElement(String key) {
        return uiElements.get(key);
    }

    /**
     * Getter of specific popup
     * @param name the string name of the popup
     * @return
     */
    public AbstractPopUpElement getPopUp(String name) {
        return popUps.get(name);
    }

    /**
     * Sets the current popup element
     * @param popUpName the name of popup to set.
     */
    public void setPopUp(String popUpName) {
        currentPopUpElement = popUpName;
    }


}


