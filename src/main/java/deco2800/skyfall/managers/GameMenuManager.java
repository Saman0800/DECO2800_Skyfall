package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.Clock;
import deco2800.skyfall.gamemenu.*;
import deco2800.skyfall.gamemenu.popupmenu.SettingsTable;
import deco2800.skyfall.gamemenu.popupmenu.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(GameMenuManager.class);


    private float topRightX = 0;
    private float topRightY = 0;

    private float topLeftX = 0;
    private float topLeftY = 0;

    private float bottomLeftX = 0;
    private float bottomLeftY = 0;

    private float bottomRightX = 0;
    private float bottomRightY = 0;

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

    /**
     * Runs the update for all of the registered ui elements
     * @param i Tick number
     */
    @Override
    public void onTick(long i) {
        if (stage != null) {
            topRightX = stage.getCamera().position.x  + (stage.getCamera().viewportWidth / 2);
            topRightY = stage.getCamera().position.y  +  (stage.getCamera().viewportHeight / 2);

            topLeftX = stage.getCamera().position.x  - (stage.getCamera().viewportWidth / 2);
            topLeftY = topRightY;

            bottomLeftX = topLeftX;
            bottomLeftY =  stage.getCamera().position.y  - (stage.getCamera().viewportHeight / 2);

            bottomRightX = topRightX;
            bottomRightY = bottomLeftY;
        }
        //Get the current state of the inventory on tick so that display can be updated
        if (currentPopUpElement != null) {
            //Checks to see a new pop up needs to be displayed.
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
     * Pause the game.
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
     * Set main character of the game to be {mainCharacter}.
     *
     * @param mainCharacter Main character of the game.
     */
    public void setMainCharacter(MainCharacter mainCharacter) {
        if (stage == null) {
            System.out.println("Please set stage before adding character");
            return;
        }
        this.mainCharacter = mainCharacter;

    }

    /**
     * Getter of main character of the game.
     *
     * @return Main character of the game.
     */
    public MainCharacter getMainCharacter() {
        try {
            return sm.getCharacter();
        } catch (NullPointerException npe) {
            logger.error("Please add stats manager returning default c");
            return MainCharacter.getInstance(0, 0, 0.05f, "Main Piece", 10);
        }
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

    /**
     * Before elements can be drawn a statistic manager needs to be added
     * @param statsManager The initialized stats maanger to be added
     */
    public void addStatsManager(StatisticsManager statsManager) {
        sm = statsManager;
    }


    /**
     * Draws all of the elements in UI
     */
    public void drawAllElements(){
        if (sm == null || skin == null) {
            System.out.println("Please add skin and stats manager before drawing");
            return;
        }


        Label.LabelStyle bluePill = new Label.LabelStyle();
        bluePill.font = skin.getFont("game-font");
        bluePill.fontColor = skin.getColor("white");
        bluePill.background = generateTextureRegionDrawableObject("blue_pill");
        skin.add("blue-pill", bluePill);

        Label.LabelStyle titlePill = new Label.LabelStyle();
        titlePill.font = skin.getFont("game-font");
        titlePill.fontColor = skin.getColor("black");
        titlePill.background = generateTextureRegionDrawableObject("light_blue_bg");
        skin.add("title-pill", titlePill);

        TextButton.TextButtonStyle textBluePill = new TextButton.TextButtonStyle();
        textBluePill.font = skin.getFont("game-font");
        textBluePill.fontColor = skin.getColor("white");
        skin.add("blue-pill2", textBluePill);

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
                null, null, textureManager, this));

        popUps.put("blueprintShopTable", new BlueprintShopTable(stage,
                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                null, textureManager, this, sm, skin));

        popUps.put("collectTable", new GenericCollectCreateTable(stage,
                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                null, textureManager, this, sm, skin, "collect"));

        popUps.put("createTable", new GenericCollectCreateTable(stage,
                new ImageButton(generateTextureRegionDrawableObject("exitButton")),
                null, textureManager, this, sm, skin, "create"));

        //uiElements.put("weatherGUI", new WeatherGui(stage, EnvironmentManager.currentWeather()));
        Map<String, AbstractUIElement> hudElements = new HashMap<>();
        hudElements.put("healthCircle", new HealthCircle(stage, new String[]{"inner_circle", "big_circle"}, textureManager, sm, skin, this));
        hudElements.put("goldPill", new GoldStatusBar(stage, null, textureManager,  skin, this));
        hudElements.put("gameMenuBar", new GameMenuBar(stage, null, textureManager, this));
        hudElements.put("gameMenuBar2", new GameMenuBar2(stage, null, textureManager, skin, this));
        hudElements.put("clock" , new Clock(stage, skin, this));

        uiElements.put("HUD", new HeadsUpDisplay(stage, null, textureManager, skin, this, hudElements));
    }

    /**
     * Getter of current pop up table displayed
     *
     * @return Current pop up table.
     */
    public AbstractPopUpElement getCurrentPopUp() {
        return popUps.get(currentPopUpElement);
    }

    /**
     * Element associated with key
     * @param key
     * @return
     */
    public AbstractUIElement getUIElement(String key) {
        return uiElements.get(key);
    }

    /**
     * Gets a specific pop up
     * @param key The key
     * @return UI element associated with the
     */
    public AbstractPopUpElement getPopUp(String key) {
        return popUps.get(key);
    }

    /**
     * Sets the current popup element
     * @param popUpName the name of popup to set.
     */
    public void setPopUp(String popUpName) {
        currentPopUpElement = popUpName;
    }

    public float getTopRightX() {
        return topRightX;
    }

    public float getTopRightY() {
        return topRightY;
    }

    public float getTopLeftX() {
        return topLeftX;
    }

    public float getTopLeftY() {
        return topLeftY;
    }

    public float getBottomLeftX() {
        return bottomLeftX;
    }

    public float getBottomLeftY() {
        return bottomLeftY;
    }

    public float getBottomRightX() {
        return bottomRightX;
    }

    public float getBottomRightY() {
        return bottomRightY;
    }

    /**
     * If there is any opened popup, closes it.
     */
    public void hideOpened() {
        if (this.getCurrentPopUp() != null) {
            this.getCurrentPopUp().hide();
        }
    }
}


