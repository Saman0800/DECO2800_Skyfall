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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    List<AbstractUIElement> uiElements = new ArrayList<>();
    Map<String, AbstractPopUpElement> popUps = new HashMap<>();
    private String currentPopUpElement = null;
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
        GameMenuScreen.currentCharacter = 0;
    }

    @Override
    public void onTick(long i) {
        //Get the current state of the inventory on tick so that display can be updated
        inventory = GameManager.get().getManager(InventoryManager.class);

        for (AbstractUIElement element: uiElements) {

            if (currentPopUpElement != null) {
                AbstractPopUpElement popUp = popUps.get(currentPopUpElement);

                if (popUp != null && !popUp.isVisible()) {
                    popUp.show();
                }
            }
            element.update();
//            System.out.println(currentPopUpElement);
      //System.out.println("Updating " + element.getClass().toString());
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
     * Resumes the game and make the PopUpTable disappear.
     *
     * @param table PopUpTable to be exited.
     */
    public void resume(PopUpTable table) {
        GameManager.setPaused(false);
        GameScreen.isPaused = false;
        exit(table);
    }

    /**
     * Makes the PopUpTable not visible to users.
     *
     * @param table PopUpTable to be exited.
     */
    private void exit(PopUpTable table) {
        table.setVisible(false);
        table.getExit().setVisible(false);
        PopUpTable.setOpened(null);
        System.out.println("exited " + table.name);
        BGMManager.unmute(); // Un-mute the BGM when menu is closed
    }

    /**
     * Opens up the pop up screen with its exit button.
     *
     * @param table PopUpTable to be opened.
     */
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
        BGMManager.mute(); // Mute the BGM when menu is opened
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
        System.out.println("Stats Manager added and drawing HealthCircle and GameMenuBar");
    }

    public void drawAllElements(){
        if (sm == null) {
            System.out.println("Please add stats manager before drawing");
            return;
        }
//        uiElements.add(new HealthCircle(stage, new String[]{"inner_circle", "big_circle"}, textureManager, sm));
//        popUps.put("settingsTable", new SettingsTable(stage,
//                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
//                null, textureManager, this,
//                skin));
//
//        popUps.put("helpTable", new HelpTable(stage,
//                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
//                null, textureManager, this,
//                skin));
//
//        popUps.put("pauseTable", new PauseTable(stage,
//                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
//                null, textureManager, this,
//                skin));
//
//        popUps.put("playerSelectTable", new PlayerSelectTable(stage,
//                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
//                null, textureManager, this,
//                skin));
//
//        popUps.put("buildingTable", new BuildingTable(stage,
//                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
//                null, textureManager, this,
//                skin));
//
//        uiElements.add(new GameMenuBar(stage, null, textureManager, this));
    }

    /**
     * Getter of current pop up table.
     *
     * @return Current pop up table.
     */
    public AbstractPopUpElement getPopUp() {
        return popUps.get(currentPopUpElement);
    }

    public AbstractPopUpElement getPopUp(String key) {
        return popUps.get(key);
    }
    public void setPopUp(String popUpName) {
        currentPopUpElement = popUpName;
    }


}


