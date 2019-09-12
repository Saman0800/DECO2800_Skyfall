package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GameMenuBar extends AbstractUIElement {


    private GameMenuManager gameMenuManager;


    public GameMenuBar(Stage stage, String[] textureNames, TextureManager tm, GameMenuManager gameMenuManager) {
        super(stage, textureNames, tm);
        this.gameMenuManager = gameMenuManager;
        this.draw();
    }



    @Override
    public void updatePosition() {

    }

    @Override
    public void draw() {
        TextureManager textureManager = this.tm;
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
                hideOpened();
                gameMenuManager.setPopUp("pauseTable");
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
                hideOpened();
                gameMenuManager.setPopUp("playerSelectTable");
            }
        });

        ImageButton info = new ImageButton(generateTextureRegionDrawableObject("info"));
        info.setSize(width, width * 146 / 207f);
        info.setPosition(1015, 105);
        stage.addActor(info);

        info.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("helpTable");
            }
        });

        ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
        settings.setSize(width, width * 146 / 207f);
        settings.setPosition(1015, 30 * 1000 / 800f);
        stage.addActor(settings);

        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("settingsTable");
            }
        });

        ImageButton build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(219 * 0.55f, 207 * 0.55f);
        build.setPosition(300, 30 * 1000 / 800f);
        stage.addActor(build);

        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("buildingTable");
            }
        });

        ImageButton radar = new ImageButton(generateTextureRegionDrawableObject("radar"));
        radar.setSize(219 * 0.55f, 207 * 0.55f);
        radar.setPosition(440, 30 * 1000 / 800f);
        stage.addActor(radar);

        System.out.println("Finished drawing menu bar");
    }

    /***
     * Sets the quick access panel and inventory button displayed on the game's hot bar.
     */
    private void setQuickAccessPanel(){
        //Set Quick Access Panel
        Table quickAccessPanel;
        quickAccessPanel = new Table();
        quickAccessPanel.setBackground(generateTextureRegionDrawableObject("quick_access_panel"));
        quickAccessPanel.setSize(450, 207 * 0.55f);
        quickAccessPanel.setPosition(560, 30 * 1000 / 800f);

        //Populate quick access GUI with resources
        //updateQuickAccess();

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
                //gameMenuManager.open(getInventoryTable());
            }
        });
    }

    /**
     * If there is any opened popup, closes it.
     */
    private void hideOpened() {
        if (gameMenuManager.getCurrentPopUp() != null) {
            gameMenuManager.getCurrentPopUp().hide();
        }
    }
}
