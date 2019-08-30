package deco2800.skyfall.GameMenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gui.HealthCircle;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GameMenuScreen {

    private Stage stage;
    private Skin skin;
    private static TextureManager textureManager;
    private GameMenuManager gameMenuManager;
    private PopUpTable pauseTable, helpTable, inventoryTable;
    private InventoryManager inventory;
    private HealthCircle healthCircle;
    private MainCharacter mainCharacter;

    public GameMenuScreen(GameMenuManager gameMenuManager) {
        this.gameMenuManager = gameMenuManager;
        this.stage = gameMenuManager.getStage();
        this.skin = gameMenuManager.getSkin();
        this.textureManager = gameMenuManager.getTextureManager();
        inventory = gameMenuManager.getInventory();
        mainCharacter = gameMenuManager.getMainCharacter();
    }

    /**
     * Display menu bar at the bottom of the game
     */
    private void showMenu() {
        Image menuBar = new Image(textureManager.getTexture("game menu bar"));
        menuBar.setSize(910, 140);
        menuBar.setPosition(185, 20);
        stage.addActor(menuBar);
        showButtons();
    }

    /**
     * Display buttons in the menu bar
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

        //Temporary inventory button using pause button texture
        ImageButton inventoryButton = new ImageButton(generateTextureRegionDrawableObject("inv_button"));
        inventoryButton.setSize(width, width * 146 / 207f);
        inventoryButton.setPosition(900, 105);
        stage.addActor(inventoryButton);

        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getInventoryTable());
            }
        });

        ImageButton selectCharacter = new ImageButton(generateTextureRegionDrawableObject("select-character"));
        selectCharacter.setSize(width, width * 146 / 207f);
        selectCharacter.setPosition(208, 30 * 1000 / 800f);
        stage.addActor(selectCharacter);

        ImageButton info = new ImageButton(generateTextureRegionDrawableObject("info"));
        info.setSize(width, width * 146 / 207f);
        info.setPosition(992, 105);
        stage.addActor(info);

        info.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.open(getHelpTable());
            }
        });

        ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
        settings.setSize(width, width * 146 / 207f);
        settings.setPosition(992, 30 * 1000 / 800f);
        stage.addActor(settings);

        ImageButton build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(219 * 0.55f, 207 * 0.55f);
        build.setPosition(300, 30 * 1000 / 800f);
        stage.addActor(build);

        ImageButton radar = new ImageButton(generateTextureRegionDrawableObject("radar"));
        radar.setSize(219 * 0.55f, 207 * 0.55f);
        radar.setPosition(440, 30 * 1000 / 800f);
        stage.addActor(radar);

        healthCircle = new HealthCircle(stage,
                "big_circle",
                "inner_circle",
                mainCharacter);
    }

    /**
     * Getter of pause pop up table
     *
     * @return pauseTable
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
     * Sets the pause pop up table
     */
    private void setPauseTable() {
        PopUpTable pauseTable = new PopUpTable(500, 500 * 1346 / 1862f, "p");
//        pauseTable.setDebug(true);

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("Game Paused", skin, "default");
        infoBar.add(text);

        pauseTable.add(infoBar).width(475).height(475 * 188f / 1756).padTop(20).colspan(3);
        pauseTable.row();

        ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("goHome"));
        toHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        Label homeText = new Label("HOME", skin, "pop-up");

        ImageButton resume = new ImageButton(generateTextureRegionDrawableObject("resume"));
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.resume(pauseTable);
            }
        });

        Label resumeText = new Label("RESUME", skin, "pop-up");

        ImageButton reset = new ImageButton(generateTextureRegionDrawableObject("reset"));
        Label resetText = new Label("RESET", skin, "pop-up");

        pauseTable.row();
        pauseTable.add(homeText).expandY().right().bottom().padRight(25);
        pauseTable.add(resumeText).expandY().bottom().padBottom(12.5f);
        pauseTable.add(resetText).expandY().left().bottom().padLeft(25);
        pauseTable.row();
        pauseTable.add(toHome).width(100).height(100 * 263 / 264f).right().padBottom(70);
        pauseTable.add(resume).width(125).height(125 * 409 / 410f).padBottom(70);
        pauseTable.add(reset).width(100).height(100 * 263 / 264f).left().padBottom(70);

        this.pauseTable = pauseTable;
    }

    private PopUpTable getHelpTable() {
        if (helpTable == null) {
            setHelpTable();
            setExitButton(helpTable);
            stage.addActor(helpTable);
            stage.addActor(helpTable.getExit());
        }
        return helpTable;
    }

    private void setHelpTable() {
        PopUpTable helpTable = new PopUpTable(600, 600 * 1346 / 1862f, "h");
//        helpTable.setDebug(true);

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("HELP", skin, "default");
        infoBar.add(text);

        helpTable.add(infoBar).width(550).height(475 * 188f / 1756).padTop(20).colspan(3);
        helpTable.row().padTop(20);

        setControl("W", "Move Up", helpTable);
        setControl("A", "Move Left", helpTable);
        setControl("S", "Move Down", helpTable);
        setControl("D", "Move Right", helpTable);

        Label space = new Label("SPACE", skin, "WASD");
        space.setAlignment(Align.center);
        helpTable.add(space).height(50).padLeft(25).fillX().colspan(2).expandY();
        Label spaceDescription = new Label("Description", skin, "WASD");
        helpTable.add(spaceDescription).width(spaceDescription.getWidth()*2).height(50).left().expandX().padLeft(20);
        helpTable.row().padTop(15);

        this.helpTable = helpTable;
    }

    private void setControl(String key, String description, PopUpTable table) {
        Label label = new Label(key, skin, "WASD");
        table.add(label).width(50).height(50).padLeft(25);
        label.setAlignment(Align.center);
        Label desc = new Label(description, skin, "WASD");
        table.add(desc).width(desc.getWidth()*2).height(50).left().padLeft(20);
        table.add().expandX().fillX();
        table.row().padTop(15);
    }

    private PopUpTable getInventoryTable() {
        if (inventoryTable == null) {
            setInventoryTable();
            setExitButton(inventoryTable);
            stage.addActor(inventoryTable);
            stage.addActor(inventoryTable.getExit());
        }
        return inventoryTable;
    }

    private void setInventoryTable() {

        //split into set and update

        PopUpTable inventoryTable = new PopUpTable(910, 510, "i");

        Image infoBar = new Image(generateTextureRegionDrawableObject("inventory_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(75, 185);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(-30, -232);
        infoPanel.setBackground(generateTextureRegionDrawableObject("info_panel"));


        Table resourcePanel = new Table();
        resourcePanel.setSize(410, 400);
        resourcePanel.setPosition(420, -232);
        resourcePanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        Map<String, Integer> inventoryAmounts = inventory.getInventoryAmounts();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);
            resourcePanel.addActor(icon);

            System.out.println(entry.getValue());

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }

        Table content = new Table();
        content.addActor(infoBar);
        content.addActor(infoPanel);
        content.addActor(resourcePanel);
//        content.addActor(exit);
        inventoryTable.add(content).width(800).colspan(3).fillX();

        this.inventoryTable = inventoryTable;
    }

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
     * Display eveything created
     */
    public void show() {
        showMenu();
    }
}
