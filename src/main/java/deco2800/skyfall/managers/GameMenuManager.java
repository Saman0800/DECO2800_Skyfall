package deco2800.skyfall.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gui.HealthCircle;
import deco2800.skyfall.gui.SettingsTable;

import java.util.*;


/**
 * Managers the menu bar during the game
 */
public class GameMenuManager extends TickableManager {


        private Table pauseTable = null;
        private static TextureManager textureManager;
        private Stage stage;
        private MainCharacter mainCharacter;
        private HealthCircle healthCircle;
        private SettingsTable settingsTable;
        private InventoryManager inventory;
        private Table inventoryTable = null;
        private SoundManager soundManager;

        public GameMenuManager() {
            textureManager = GameManager.get().getManager(TextureManager.class);
            inventory = GameManager.get().getManager(InventoryManager.class);
            soundManager = GameManager.get().getManager(SoundManager.class);
            stage = null;
        }

        public void addStage(Stage stage) {
            this.stage = stage;
        }

        /**
         * Display menu bar at the bottom of the game
         *
         * @param stage Current stage
         */
        private void showMenu(Stage stage){
            Image menuBar = new Image(textureManager.getTexture("game menu bar"));
            menuBar.setSize(910, 140);
            menuBar.setPosition(185, 20);
            stage.addActor(menuBar);
        }




    /**
     * Display buttons in the menu bar
     *
     * @param stage Current stage
     */
    private void showButtons(Stage stage) {
            int height;
            height = 65;
            ImageButton pause = new ImageButton(generateTextureRegionDrawableObject("pause"));

            pause.setSize(height, height*146/207);
            pause.setPosition(208, 105);
            stage.addActor(pause);
            pause.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (pauseTable == null) {
                        setPauseTable();
                        stage.addActor(pauseTable);
                    } else {
                        pauseTable.setVisible(true);
                    }
                    GameScreen.isPaused = true;
                    pause();

                }
            });

            //Temporary inventory button using pause button texture
            ImageButton inventoryButton = new ImageButton(generateTextureRegionDrawableObject("inv_button"));
            inventoryButton.setSize(height, height*146/207);
            inventoryButton.setPosition(900, 105);
            stage.addActor(inventoryButton);

            inventoryButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (inventoryTable == null) {
                        setInventoryTable();
                        stage.addActor(inventoryTable);
                    } else {
                        inventoryTable.setVisible(true);
                    }
                    GameScreen.isPaused = true;
                    GameManager.setPaused(true);
                }
            });


            ImageButton selectCharacter = new ImageButton(generateTextureRegionDrawableObject("select-character"));
            selectCharacter.setSize(height, height*146/207);
            selectCharacter.setPosition(208,30*1000/800);
            stage.addActor(selectCharacter);

            ImageButton info = new ImageButton(generateTextureRegionDrawableObject("info"));
            info.setSize(height, height*146/207);
            info.setPosition(992,105);
            stage.addActor(info);

            ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
            settings.setSize(height, height*146/207);
            settings.setPosition(992,30*1000/800);
            stage.addActor(settings);

            ImageButton build = new ImageButton(generateTextureRegionDrawableObject("build"));
            build.setSize(219*0.55f, 207*0.55f);
            build.setPosition(300, 30*1000/800);
            stage.addActor(build);

            ImageButton radar = new ImageButton(generateTextureRegionDrawableObject("radar"));
            radar.setSize(219*0.55f, 207*0.55f);
            radar.setPosition(440, 30*1000/800);
            stage.addActor(radar);

            healthCircle = new HealthCircle(stage,
                "big_circle",
                "inner_circle",
                    mainCharacter);
        }

    /**
     * Getter of pause pop up table
     * @return
     */
    private Table getPauseTable() {
            return pauseTable;
        }


    /**
     * Sets the pause pop up table
     */
    private void setPauseTable() {
            Table pauseTable = new Table();
            pauseTable.setSize(500, 500*1346/1862);
            pauseTable.setPosition(Gdx.graphics.getWidth()/2 - 200, Gdx.graphics.getHeight()/2 - 90);
            pauseTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));


//        Table infoBar = new Table();
            Image infoBar = new Image(generateTextureRegionDrawableObject("game menu bar"));
            infoBar.setSize(475, 475*188/1756);
//        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar")))));

            Table buttons = new Table();
            ImageButton toHome = new ImageButton(generateTextureRegionDrawableObject("goHome"));
//        toHome.setSize(200, 200*263/264);
            toHome.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }
            });

            ImageButton resume = new ImageButton(generateTextureRegionDrawableObject("resume"));
            resume.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameManager.setPaused(false);
                    pauseTable.setVisible(false);
                    GameScreen.isPaused = false;
                }
            });

//        resume.setSize(300, 300*409/410);
            ImageButton reset = new ImageButton(generateTextureRegionDrawableObject("reset"));
//        reset.setSize(200, 200*263/264);

            buttons.add(toHome).width(100).padRight(10).padLeft(50).padBottom(300);
            buttons.add(resume).width(125).padBottom(290);
            buttons.add(reset).width(100).padLeft(10).padRight(30).padBottom(300);

            Table bar = new Table();
            bar.addActor(infoBar);
            pauseTable.add(bar).width(475).padTop(450).colspan(3).fillX();
            pauseTable.row();
            pauseTable.add(buttons);

            this.pauseTable = pauseTable;
    }



    private void setInventoryTable(){

        //split into set and update

        Table inventoryTable = new Table();
        inventoryTable.setSize(910, 510);
        inventoryTable.setPosition(185, Gdx.graphics.getHeight()/2 - 150);
        inventoryTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        ImageButton exit = new ImageButton(generateTextureRegionDrawableObject("exit"));
        exit.setSize(40, 40);
        exit.setPosition(800, 200);

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.setPaused(false);
                inventoryTable.setVisible(false);
                System.out.println("False");
                GameScreen.isPaused = false;
            }
        });


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
            icon.setPosition(xpos + count*130, ypos);
            resourcePanel.addActor(icon);

            System.out.println(entry.getValue());

            count ++;

            if((count)%3 == 0){
                ypos -= 120;
                count = 0;
            }
        }


        Table content = new Table();
        content.addActor(infoBar);
        content.addActor(infoPanel);
        content.addActor(resourcePanel);
        content.addActor(exit);
        inventoryTable.add(content).width(800).colspan(3).fillX();

        this.inventoryTable = inventoryTable;
    }

    /**
     * Pauses the game
     */
    private void pause() {
            GameManager.setPaused(true);
        }

    @Override
    public void onTick(long i) {

        inventory = GameManager.get().getManager(InventoryManager.class);

        if (healthCircle != null) {
            healthCircle.update();
        }

    }

    public static TextureRegionDrawable generateTextureRegionDrawableObject(String sName) {
        return new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(sName))));
    }

    public void addMainCharacter(MainCharacter mainCharacter) {
        if (stage == null) {
            System.out.println("Please set stage before adding character");
            return;
        }
        this.mainCharacter = mainCharacter;

    }
    /**
     * Display eveything created
     *
     *
     */
    public void show() {
            showMenu(stage);
            showButtons(stage);
    }
}


