package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Managers the menu bar during the game
 */
public class GameMenuManager {

        private Table pauseTable = null;

        // default constructor
        public GameMenuManager() {
        }

        /**
         * Display menu bar at the bottom of the game
         *
         * @param stage Current stage
         */
        private void showMenu(Stage stage){

            Image menuBar = new Image(GameManager.get().getManager(TextureManager.class).getTexture("game menu bar"));
            menuBar.setSize(910, 170);
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
            height = 75;
            ImageButton pause = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("pause")))));
            pause.setSize(height, height*146/207);
            pause.setPosition(208, 115);
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
                    pause();
                }
            });

            ImageButton selectCharacter = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("select-character")))));
            selectCharacter.setSize(height, height*146/207);
            selectCharacter.setPosition(208,41*1000/800);
            stage.addActor(selectCharacter);

            ImageButton info = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("info")))));
            info.setSize(height, height*146/207);
            info.setPosition(992,115);
            stage.addActor(info);

            ImageButton settings = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("settings")))));
            settings.setSize(height, height*146/207);
            settings.setPosition(992,41*1000/800);
            stage.addActor(settings);

            ImageButton build = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("build")))));
            build.setSize(219*0.55f, 207*0.55f);
            build.setPosition(300, 41*1000/800);
            stage.addActor(build);

            ImageButton radar = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("radar")))));
            radar.setSize(219*0.55f, 207*0.55f);
            radar.setPosition(440, 41*1000/800);
            stage.addActor(radar);
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
            pauseTable.setBackground(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("pop up screen")))));


//        Table infoBar = new Table();
            Image infoBar = new Image(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("game menu bar")))));
            infoBar.setSize(475, 475*188/1756);
//        infoBar.setBackground(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("game menu bar")))));

            Table buttons = new Table();
            ImageButton toHome = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("goHome")))));
//        toHome.setSize(200, 200*263/264);
            toHome.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }
            });

            ImageButton resume = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("resume")))));
            resume.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameManager.setPaused(false);
                    pauseTable.setVisible(false);
                }
            });

//        resume.setSize(300, 300*409/410);
            ImageButton reset = new ImageButton(new TextureRegionDrawable((new TextureRegion(GameManager.get().getManager(TextureManager.class).getTexture("reset")))));
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

    /**
     * Pauses the game
     */
    private void pause() {
            GameManager.setPaused(true);
        }


    /**
     * Display eveything created
     *
     * @param stage current stage
     */
    public void show(Stage stage) {
            showMenu(stage);
            showButtons(stage);
        }
    }


