package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.AbstractUIElement;
import deco2800.skyfall.gamemenu.Clock;
import deco2800.skyfall.gamemenu.FeedbackBar;
import deco2800.skyfall.gamemenu.GameMenuBar2;
import deco2800.skyfall.gamemenu.GoldStatusBar;
import deco2800.skyfall.gamemenu.HeadsUpDisplay;
import deco2800.skyfall.gamemenu.HealthCircle;
import deco2800.skyfall.gamemenu.ManaCircle;
import deco2800.skyfall.gamemenu.popupmenu.BlueprintShopTable;
import deco2800.skyfall.gamemenu.popupmenu.BuildWorldProgressPopup;
import deco2800.skyfall.gamemenu.popupmenu.BuildingTable;
import deco2800.skyfall.gamemenu.popupmenu.ChestTable;
import deco2800.skyfall.gamemenu.popupmenu.CollectCreateTable;
import deco2800.skyfall.gamemenu.popupmenu.ConstructionTable;
import deco2800.skyfall.gamemenu.popupmenu.EndGameTable;
import deco2800.skyfall.gamemenu.popupmenu.GameOverTable;
import deco2800.skyfall.gamemenu.popupmenu.HelpTable;
import deco2800.skyfall.gamemenu.popupmenu.InventoryTable;
import deco2800.skyfall.gamemenu.popupmenu.PauseTable;
import deco2800.skyfall.gamemenu.popupmenu.ProgressTable;
import deco2800.skyfall.gamemenu.popupmenu.TeleportTable;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the menu bar during the game
 */
public class GameMenuManager extends TickableManager {

    private TextureManager textureManager;
    private Stage stage;
    private EnvironmentManager em;
    private InventoryManager inventory;
    private FeedbackManager fm;
    private Skin skin;
    private SkyfallGame game;

    private StatisticsManager sm;

    //Refactor Code
    private Map<String, AbstractUIElement> uiElements;
    private Map<String, AbstractPopUpElement> popUps;
    private String currentPopUpElement = null;

    private Logger logger = LoggerFactory.getLogger(GameMenuManager.class);
    private QuestManager questManager;

    private float topRightX = 0;
    private float topRightY = 0;

    private float topLeftX = 0;
    private float topLeftY = 0;

    private float bottomLeftX = 0;
    private float bottomLeftY = 0;

    private float bottomRightX = 0;
    private float bottomRightY = 0;
    private boolean drawn = false;

    /**
     * Initialise a new GameMenuManager with stage and skin including the characters in the game.
     * And construct Manager instances for later use.
     */
    public GameMenuManager() {
        updateTextureManager(GameManager.get().getManager(TextureManager.class));
        inventory = GameManager.get().getManager(InventoryManager.class);
        questManager = GameManager.get().getManager(QuestManager.class);
        em = GameManager.get().getManager(EnvironmentManager.class);
        fm = GameManager.get().getManager(FeedbackManager.class);
        stage = null;
        skin = null;
        uiElements = new HashMap<>();
        popUps = new HashMap<>();
    }

    //used for testing
    public GameMenuManager(TextureManager tm,
                           InventoryManager im, Stage stage, Skin skin,
                           Map<String, AbstractPopUpElement> popUps,
                           Map<String, AbstractUIElement> uiElements) {
        this.updateTextureManager(tm);
        inventory = im;
        this.stage = stage;
        this.skin = skin;
        this.popUps = popUps;
        this.uiElements = uiElements;
    }

    public void updateTextureManager(TextureManager tm) {

        textureManager = tm;
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

        for (Map.Entry<String, AbstractUIElement> entry: uiElements.entrySet()) {
            entry.getValue().update();
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
    public TextureManager getTextureManager() {
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
     * Generates an instance of TextureRegionDrawable with the given texture name.
     *
     * @param sName Texture Name.
     *
     * @return An instance of TextureRegionDrawable with the given texture name.
     */
    public TextureRegionDrawable generateTextureRegionDrawableObject(String sName) {
        return new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(sName))));
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
            logger.info("Please add skin and stats manager before drawing");
            return;
        }

        if (drawn) {
            return;
        }
        String whiteText = "white";
        String exitText = "exitButton";

        Drawable bgBluePill = generateTextureRegionDrawableObject("blue_pill");
        Drawable bgGreenPill = generateTextureRegionDrawableObject("green_pill");
        BitmapFont gameFont = skin.getFont("game-font");


        Label.LabelStyle bluePill = new Label.LabelStyle();
        bluePill.font = gameFont;
        bluePill.fontColor = skin.getColor(whiteText);
        bluePill.background = bgBluePill;
        skin.add("blue-pill", bluePill);



        Label.LabelStyle greenPill = new Label.LabelStyle();
        greenPill.font = gameFont;
        greenPill.fontColor = skin.getColor(whiteText);
        greenPill.background = bgGreenPill;
        skin.add("green-pill", greenPill);

        Label.LabelStyle titlePill = new Label.LabelStyle();
        titlePill.font = gameFont;
        titlePill.fontColor = skin.getColor("navy");
        titlePill.background = generateTextureRegionDrawableObject("light_blue_bg");
        skin.add("title-pill", titlePill);

        TextButton.TextButtonStyle textBluePill = new TextButton.TextButtonStyle();
        textBluePill.font = gameFont;
        textBluePill.fontColor = skin.getColor(whiteText);
        skin.add("blue-pill", textBluePill);

        Slider.SliderStyle s = new Slider.SliderStyle();
        s.background = generateTextureRegionDrawableObject("knob_after");
        s.background.setMinHeight(50);
        s.knob = generateTextureRegionDrawableObject("knob");
        s.knob.setMinHeight(50);
        s.knob.setMinWidth(50);
        s.knobBefore = generateTextureRegionDrawableObject("knob_before");
        s.knobBefore.setMinHeight(50);
        s.knobAfter = generateTextureRegionDrawableObject("knob_after");
        s.knobAfter.setMinHeight(50);
        skin.add("default-slider", s);

        popUps.put("helpTable", new HelpTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this,
                skin));

        popUps.put("pauseTable", new PauseTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this,
                skin));

        popUps.put("buildingTable", new BuildingTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this,
                skin));

        popUps.put("gameOverTable", new GameOverTable(stage,
                null, null, textureManager, this));


        popUps.put("collectTable", new CollectCreateTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, skin, "collect"));

        popUps.put("createTable", new CollectCreateTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, skin, "create"));

        popUps.put("progressTable", new ProgressTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, questManager, skin));

        popUps.put("teleportTable", new TeleportTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, skin));

        popUps.put("endGameTable", new EndGameTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this));

        Map<String, AbstractUIElement> hudElements = new HashMap<>();

        hudElements.put("healthCircle", new HealthCircle(stage, new String[]{"inner_circle", "big_circle"},
                textureManager, sm, skin, this));

        hudElements.put("manaCircle", new ManaCircle(stage, new String[]{"mana_inner_circle", "mana_big_circle"},
                textureManager, sm, skin, this));

        hudElements.put("goldPill", new GoldStatusBar(stage, null, textureManager,  skin, this));
        hudElements.put("gameMenuBar2", new GameMenuBar2(stage, null, textureManager, skin, this));
        hudElements.put("clock" , new Clock(stage, skin, this, em));

        hudElements.put("feedbackBar", new FeedbackBar(stage, new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, skin, this, fm));

        uiElements.put("HUD", new HeadsUpDisplay(stage, null, textureManager, skin, this, hudElements, questManager));

        popUps.put("blueprintShopTable", new BlueprintShopTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, sm, skin));

        popUps.put("inventoryTable",
                new InventoryTable(stage, new ImageButton(generateTextureRegionDrawableObject(exitText)),
                        null, textureManager, skin,this));

        popUps.put("chestTable",new ChestTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, sm, skin));

        popUps.put("loadingTable", new BuildWorldProgressPopup(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this,
                skin));

        popUps.put("constructionTable",new ConstructionTable(stage,
                new ImageButton(generateTextureRegionDrawableObject(exitText)),
                null, textureManager, this, sm, skin));

        drawn = true;
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


    public QuestManager getQuestManager() {
        return questManager;
    }
}


