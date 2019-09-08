package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;

import javax.swing.*;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

/**
 * GUI of the Menu Screen in the game.
 */
public class GameMenuScreen {

    // Current stage of the game.
    private Stage stage;
    // Skin used for the game.
    private Skin skin;
    private static TextureManager textureManager;
    private GameMenuManager gameMenuManager;
    // Game Paused screen pop up
    private PopUpTable pauseTable;
    // Help screen pop up
    private PopUpTable helpTable;
    // Settings screen pop up
    private PopUpTable settingsTable;
    // Player Select screen pop up
    private PopUpTable playerSelect;
    //Building Table
    private PopUpTable buildingTable;
    private MainCharacter mainCharacter;

    //Inventory Manager instance in game
    private InventoryManager inventory;

    //Table in inventory popup containing resource icons
    private Table resourcePanel;
    public static int currentCharacter;

    //Table in hot bar containing quick access resources
    private Table quickAccessPanel;

    //Inventory pop up
    private PopUpTable inventoryTable;

    //Gold Pouch pop ip
    private PopUpTable goldTable;

    /**
     * Construct the menu screen in the game.
     *
     * @param gameMenuManager Current GameMenuManager
     */
    public GameMenuScreen(GameMenuManager gameMenuManager) {
        this.gameMenuManager = gameMenuManager;
        this.stage = gameMenuManager.getStage();
        this.skin = gameMenuManager.getSkin();
        this.textureManager = gameMenuManager.getTextureManager();
        inventory = gameMenuManager.getInventory();
        mainCharacter = gameMenuManager.getMainCharacter();
    }

    /**
     * Display menu bar at the bottom of the game.
     */
    private void showMenu() {
        Image menuBar = new Image(textureManager.getTexture("game menu bar"));
        menuBar.setSize(910, 140);
        menuBar.setPosition(185, 20);
        stage.addActor(menuBar);
        showButtons();
    }

    /**
     * Display buttons in the menu bar.
     */
    private void showButtons() {
        int width;
        width = 65;
        ImageButton pause = new ImageButton(generateTextureRegionDrawableObject("pause"));

        pause.setSize(width, width * 146 / 207f);
        pause.setPosition(208, 105);
        stage.addActor(pause);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getPauseTable());
            }
        });

        //Set quick access panel with inventory button
        setQuickAccessPanel();


        ImageButton selectCharacter = new ImageButton(generateTextureRegionDrawableObject("select-character"));
        selectCharacter.setSize(width, width * 146 / 207f);
        selectCharacter.setPosition(208, 30 * 1000 / 800f);
        stage.addActor(selectCharacter);

        selectCharacter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getPlayerSelect());
            }
        });

        ImageButton info = new ImageButton(generateTextureRegionDrawableObject("info"));
        info.setSize(width, width * 146 / 207f);
        info.setPosition(1015, 105);
        stage.addActor(info);

        info.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getHelpTable());
            }
        });

        ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
        settings.setSize(width, width * 146 / 207f);
        settings.setPosition(1015, 30 * 1000 / 800f);
        stage.addActor(settings);

        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getSettingsTable());
            }
        });

        ImageButton build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(219 * 0.55f, 207 * 0.55f);
        build.setPosition(300, 30 * 1000 / 800f);
        stage.addActor(build);

        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getBuildingTable());
            }
        });

        ImageButton goldPouchButton = new ImageButton(generateTextureRegionDrawableObject("goldPiece5"));
        goldPouchButton.setSize(200 * 0.55f, 207 * 0.55f);
        goldPouchButton.setPosition(440, 30 * 1000 / 800f);
        stage.addActor(goldPouchButton);

        //Add gold piece button listener
        goldPouchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getGoldTable());
            }
        });
    }



    /**
     * Getter of Game Paused screen.
     *
     * @return Game Paused screen.
     */
    private PopUpTable getPauseTable() {
        if (pauseTable == null) {
            setPauseTable();
            setExitButton(pauseTable);
            stage.addActor(pauseTable);
            stage.addActor(pauseTable.getExit());
        }
        return pauseTable;
    }

    /**
     * Initialise Game Paused Screen pop up.
     */
    private void setPauseTable() {
        PopUpTable pauseTable = new PopUpTable(500, 500 * 1346 / 1862f, "p");
        pauseTable.setDebug(true);

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("GAME PAUSED", skin, "black-text");
        infoBar.add(text);

        pauseTable.add(infoBar).width(475).height(475 * 188f / 1756).padTop(20).colspan(3);
        pauseTable.row();

        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("goHome"));
        toHome.addListener(new ClickListener() {
            @Override
        public void clicked(InputEvent event, float x, float y) {
//                gameMenuManager.getGame().batch = new SpriteBatch();
//                gameMenuManager.getGame().setScreen(new MainMenuScreen(gameMenuManager.getGame()));
//            System.out.println(gameMenuManager.getGame().batch == null);
//            gameMenuManager.getGame().create();
        }
    });

        Label homeText = new Label("HOME", skin, "white-text");
        homeText.setFontScale(0.7f);
        System.out.println("homeText width" + homeText.getWidth());

        ImageButton resume = new ImageButton(generateTextureRegionDrawableObject("resume"));
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.resume(pauseTable);
            }
        });

        Label resumeText = new Label("RESUME", skin, "white-text");
        resumeText.setFontScale(0.7f);

        ImageButton reset = new ImageButton(generateTextureRegionDrawableObject("reset"));
        Label resetText = new Label("RESET", skin, "white-text");
        resetText.setFontScale(0.7f);
        System.out.println("resetText width" + resetText.getWidth());

        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                gameMenuManager.getGame().setScreen(new GameScreen(new SkyfallGame(), 1, true));
            }
        });

        pauseTable.row();
        pauseTable.add(homeText).expandY().right().bottom().padRight(17.1f);//.padRight(25)
        pauseTable.add(resumeText).expandY().bottom().padBottom(12.5f);
        pauseTable.add(resetText).expandY().left().bottom().padLeft(11.85f);//.padLeft(25)
        pauseTable.row();
        pauseTable.add(toHome).width(100).height(100 * 263 / 264f).right().padBottom(70);
        pauseTable.add(resume).width(125).height(125 * 409 / 410f).padBottom(70);
        pauseTable.add(reset).width(100).height(100 * 263 / 264f).left().padBottom(70);

        this.pauseTable = pauseTable;
    }

    /**
     * Getter of Help Screen.
     *
     * @return Help Screen.
     */
    private PopUpTable getHelpTable() {
        if (helpTable == null) {
            setHelpTable();
            setExitButton(helpTable);
            stage.addActor(helpTable);
            stage.addActor(helpTable.getExit());
        }
        return helpTable;
    }

    /**
     * Initialise Help Screen pop up.
     */
    private void setHelpTable() {
        PopUpTable helpTable = new PopUpTable(600, 600 * 1346 / 1862f, "h");
        helpTable.setDebug(true);
        helpTable.top();

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("HELP", skin, "black-text");
        infoBar.add(text);

        helpTable.add(infoBar).width(550).height(550 * 188f / 1756).padTop(20).colspan(3);
        helpTable.row().padTop(20);

        setControl("W", "Move Up", helpTable);
        setControl("A", "Move Left", helpTable);
        setControl("S", "Move Down", helpTable);
        setControl("D", "Move Right", helpTable);

//        Label space = new Label("SPACE", skin, "WASD");
//        space.setAlignment(Align.center);
//        helpTable.add(space).height(50).padLeft(25).colspan(2).expandY();
//        Label spaceDescription = new Label("Description", skin, "WASD");
//        helpTable.add(spaceDescription).height(50).left().expandX().padLeft(20);
//        helpTable.row().padTop(15);

        this.helpTable = helpTable;
    }

    /**
     * Setup the label of control keys and their description.
     *
     * @param key Control key.
     * @param description Function of the key.
     * @param table Table to add on.
     */
    private void setControl(String key, String description, PopUpTable table) {
        Label label = new Label(key, skin, "white-label");
        table.add(label).padLeft(25).width(50).height(50);
        label.setAlignment(Align.center);
        Label desc = new Label(description, skin, "white-label");
        table.add(desc).left().padLeft(20).height(50).expandX();//.width(desc.getWidth() * 2).height(50)
//        table.add().expandX().fillX();
        table.row().padTop(15);
    }

    /**
     * Get current building table, if null, create one and return it.
     *
     * @return Current building table.
     */
    private PopUpTable getBuildingTable() {
        if (buildingTable == null) {
            setBuildingTable();
            setExitButton(buildingTable);
            stage.addActor(buildingTable);
            stage.addActor(buildingTable.getExit());
        }
        return buildingTable;
    }

    /**
     * Setup Building Table.
     */
    public void setBuildingTable() {
        PopUpTable buildingTable = new PopUpTable(700, 700 * 1346 / 1862f, "build");
//        buildingTable.setDebug(true);

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("BUILDING TABLE", skin, "black-text");
        infoBar.add(text);

        buildingTable.add(infoBar).width(650).height(650 * 188f / 1756).padTop(20).colspan(2);
        buildingTable.row();

        Table blueprint = new Table();
        Label blueprintTitle = new Label("BLUEPRINT", skin, "black-label");
        blueprint.add(blueprintTitle);

        Table items = new Table();
        items.setDebug(true);
        Label number = new Label("1/36", skin, "black-label");
        items.add(number).colspan(4);
        items.row();
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Drawable test = generateTextureRegionDrawableObject("left_arrow");
        Drawable testTexture = generateTextureRegionDrawableObject("enemyTreeman");
        style.up = test;
        style.down = test;
        style.checked = test;
        style.imageUp = testTexture;
        style.imageDown = testTexture;
        style.imageChecked = testTexture;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                ImageButton testt = new ImageButton(style);
                items.add(testt).width(50).height(50*testt.getHeight()/testt.getWidth());
            }
            items.row();
        }
        buildingTable.add(blueprint).width(325);
        buildingTable.add(items).width(325);

        this.buildingTable = buildingTable;
    }

    /***
     * Updates and returns current state of the inventory table.
     *
     * @return inventoryTable
     */
    private PopUpTable getInventoryTable() {
        if (inventoryTable == null) {
            setInventoryTable();
            setExitButton(inventoryTable);
            stage.addActor(inventoryTable);
            stage.addActor(inventoryTable.getExit());
        } else {
            inventoryTable.removeActor(resourcePanel);
            updateResourcePanel();
            inventoryTable.addActor(resourcePanel);
        }
        return inventoryTable;
    }

    /***
     * Sets all images and buttons in the inventory table.
     */
    private void setInventoryTable() {
        PopUpTable inventoryTable = new PopUpTable(910, 510, "i");
        inventoryTable.setName("inventoryTable");

        Image infoBar = new Image(generateTextureRegionDrawableObject("inventory_banner"));
        //width(650).height(650 * 188f / 1756).padTop(20).colspan(2);
        infoBar.setSize(650 * 188f / 1756, 55);
        infoBar.setPosition(130, 435);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(generateTextureRegionDrawableObject("info_panel"));


        //updateResourcePanel();

        inventoryTable.addActor(infoBar);
        inventoryTable.addActor(infoPanel);
        inventoryTable.addActor(this.resourcePanel);

        this.inventoryTable = inventoryTable;
    }

    /**
     * Updates and returns current state of the goldPouch table.
     *
     * @return inventoryTable
     */
    private PopUpTable getGoldTable() {
        if (goldTable == null) {
            setGoldTable();
            setExitButton(goldTable);
            stage.addActor(goldTable);
            stage.addActor(goldTable.getExit());
        } else {
            //goldTable.removeActor(resourcePanel);
            //updateResourcePanel();
            //goldTable.addActor(resourcePanel);
        }
        return goldTable;
    }

    /***
     * Sets all images and buttons in the gold pouch table.
     */
    private void setGoldTable() {
        PopUpTable goldTable = new PopUpTable(700, 700, "gold");
        goldTable.setName("goldTable");

        Image infoBar = new Image(generateTextureRegionDrawableObject("inventory_banner"));
        infoBar.setSize(550, 55);
        infoBar.setPosition(100, 435);

        //updateResourcePanel();

        goldTable.addActor(infoBar);
        //goldTable.addActor(this.resourcePanel);

        this.goldTable = goldTable;
    }

    /***
     * Updates the resources panel to display the current inventory contents.
     */
    private void updateResourcePanel(){
        resourcePanel = new Table();
        resourcePanel.setName("resourcePanel");
        resourcePanel.setSize(410, 400);
        resourcePanel.setPosition(475, 18);
        resourcePanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        Map<String, Integer> inventoryAmounts = gameMenuManager.getInventory().getInventoryAmounts();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);

            resourcePanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos + 85 + count * 130, ypos + 75);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }

    }

    private void updateInventoryTable() {
        Map<String, Integer> inventoryAmounts = gameMenuManager.getInventory().getInventoryAmounts();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);

            resourcePanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos + 85 + count * 130, ypos + 75);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }

    }

    /***
     * Sets the quick access panel and inventory button displayed on the game's hot bar.
     */
    private void setQuickAccessPanel(){
        //Set Quick Access Panel
        quickAccessPanel = new Table();
        quickAccessPanel.setBackground(generateTextureRegionDrawableObject("quick_access_panel"));
        quickAccessPanel.setSize(450, 207 * 0.55f);
        quickAccessPanel.setPosition(560, 30 * 1000 / 800f);

        //Populate quick access GUI with resources
        updateQuickAccess();

        stage.addActor(quickAccessPanel);


        //Set open inventory button icon in quick access
        ImageButton inventoryButton = new ImageButton(generateTextureRegionDrawableObject("inv_button"));
        inventoryButton.setSize(50, 50 * 146 / 207f);
        inventoryButton.setPosition(950, 78);

        stage.addActor(inventoryButton);

        //Add inventory button listener
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getInventoryTable());
            }
        });
    }

    /***
     * Updates the quick access inventory display to show the current contents
     * of the quick access inventory.
     */
    private void updateQuickAccess(){
        Map<String, Integer> quickAccess = gameMenuManager.getInventory().getQuickAccess();

        int count = 0;
        int xpos = 15;
        int ypos = 28;

        for (Map.Entry<String, Integer> entry : quickAccess.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setSize(60, 60);
            icon.setPosition(xpos + 68*count, ypos);

            quickAccessPanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos + 50 + 64*count, ypos + 40);
            quickAccessPanel.addActor(num);

            count++;

        }
    }

    /**
     * gets the settings table
     */
    private PopUpTable getSettingsTable() {
        if (settingsTable == null) {
            setSettingsTable();
            setExitButton(settingsTable);
            stage.addActor(settingsTable);
            stage.addActor(settingsTable.getExit());
        }
        return settingsTable;
    }

    /**
     * Initialise Settings Screen pop up.
     * Sets the settings table to be the current table. (Currently incomplete)
     */
    private void setSettingsTable() {

        //split into set and update

        PopUpTable settingsTable = new PopUpTable(910, 510, "settings");

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("SETTINGS", skin, "black-text");
        infoBar.add(text);

        settingsTable.add(infoBar).width(550).height(475 * 188f / 1756).padTop(20).colspan(3);
        settingsTable.row().padTop(20);

        this.settingsTable = settingsTable;
    }

    /**
     * Getter of Player Select Screen.
     *
     * @return Player Select Screen.
     */
    private PopUpTable getPlayerSelect() {
        if (playerSelect == null) {
            setPlayerSelect();
            setExitButton(playerSelect);
            stage.addActor(playerSelect);
            stage.addActor(playerSelect.getExit());
        }
        return playerSelect;
    }

    /**
     * Initialise Player Select Screen pop up.
     */
    private void setPlayerSelect() {
        PopUpTable playerSelect = new PopUpTable(600, 600f * 1346 / 1862, "playerSelect");
//        playerSelect.setDebug(true);
        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("PLAYER SELECT", skin, "black-text");
        infoBar.add(text);

        playerSelect.add(infoBar).width(550).height(550f * 180 / 1756).padTop(20).colspan(5).padBottom(20);
        playerSelect.row();

        int arrowWidth = 60;

        //height = width
        ImageButton leftArrow = new ImageButton(generateTextureRegionDrawableObject("left_arrow"));

        playerSelect.add(leftArrow).width(arrowWidth).height(arrowWidth).expandY();

        Table[] characterTables = new Table[3];

        for (int i = 0; i < 3; i++) {
            Table characterTable = new Table();
//            characterTable.setDebug(true);
            characterTables[i] = characterTable;
        }

        float characterTableWidth = (550 - arrowWidth * 2) / 3f;

        updateCharacters(characterTables, characterTableWidth);

        for (int i = 0; i < 3; i++) {
            playerSelect.add(characterTables[i]).width(characterTableWidth).height(600f * 1346 / 1862 - 550f * 180 / 1756 - 40 - 30 - 200f*138/478).spaceLeft(5).spaceRight(5);
        }

        ImageButton rightArrow = new ImageButton(generateTextureRegionDrawableObject("right_arrow"));
        playerSelect.add(rightArrow).width(arrowWidth).height(arrowWidth).expandY();

        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentCharacter = (currentCharacter + gameMenuManager.NUMBEROFCHARACTERS - 1) % gameMenuManager.NUMBEROFCHARACTERS;
                updateCharacters(characterTables, characterTableWidth);
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentCharacter = (currentCharacter + 1) % gameMenuManager.NUMBEROFCHARACTERS;
                updateCharacters(characterTables, characterTableWidth);
            }
        });

        playerSelect.row().padTop(20).padBottom(10);

        ImageButton select = new ImageButton(generateTextureRegionDrawableObject("select"));
        playerSelect.add(select).width(200).height(200 * select.getHeight() /select.getWidth()).expandX().colspan(5);

        select.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String texture = gameMenuManager.getCharacters()[currentCharacter];
                if (texture != null) {
                    mainCharacter.changeTexture(texture);
                    gameMenuManager.resume(playerSelect);
                }
            }
        });

        this.playerSelect = playerSelect;
    }

    /**
     * Updates the characters displayed on the pop up screen.
     *
     * @param characterTables Array of Tables for Images of character to add on.
     * @param characterTableWidth Width of each characterTable.
     */
    private void updateCharacters(Table characterTables[], float characterTableWidth) {
        for (int i = currentCharacter; i < currentCharacter + 3; i++) {
            Table characterTable = characterTables[i - currentCharacter];
            characterTable.clearChildren();
            Label characterName = new Label("CHARACTER\nNAME", skin, "white-label");
            characterName.setAlignment(Align.center);
            characterName.setFontScaleX(0.5f);
            characterName.setFontScaleY(0.5f);
            characterTable.add(characterName).top();
            characterTable.row();
            String texture = gameMenuManager.getCharacters()[(i + gameMenuManager.NUMBEROFCHARACTERS - 1) % gameMenuManager.NUMBEROFCHARACTERS];
            try {
                TextureRegionDrawable characterTexture = generateTextureRegionDrawableObject(texture);
                Image character = new Image(characterTexture);
                characterTable.add(character).expandY().width(characterTableWidth * 0.8f).height(characterTableWidth * 0.8f * character.getHeight() / character.getWidth());
            } catch (NullPointerException e) {
                characterTable.add().expandY();
                //DO NOTHING
            }
        }
    }

    /**
     * Initialise the Exit button for given PopUpTable
     *
     * @param table PopUpTable of the Exit Button
     */
    private void setExitButton(PopUpTable table) {
        ImageButton exit = table.getExit();
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.resume(table);
            }
        });
    }

    /**
     * Display everything created
     */
    public void show() {
        showMenu();
    }
}










