package deco2800.skyfall.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.tutorial.TutorialScreen;

import java.util.List;
import java.util.Random;

public class MainMenuScreen implements Screen {
    public static final String MAIN_MENU_STYLE = "main_menu";
    public static final String LOAD_GAME_STYLE = "load-game";

    private final SkyfallGame game;
    private Stage stage;

    private static final int MIN_HEIGHT = 720;
    private static final int MIN_WIDTH = 1280;

    // Used for generating readable save names.
    private static final char[] CONSONANTS = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V',
            'W', 'X', 'Z'};
    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U', 'Y'};

    private final List<Save> saveInfoList;
    private Window loadGameWindow;

    private Window startGameWindow;

    /**
     * The constructor of the MainMenuScreen
     *
     * @param game the Iguana Chase Game to run
     */
    public MainMenuScreen(final SkyfallGame game) {
        this.game = game;
        stage = new Stage(new ExtendViewport(MIN_WIDTH, MIN_HEIGHT), game.getBatch());

        Skin skin = GameManager.get().getSkin();

        saveInfoList = DatabaseManager.get().getDataBaseConnector().loadSaveInformation();

        Image background = new Image(GameManager.get().getManager(TextureManager.class).getTexture("background"));
        background.setFillParent(true);
        stage.addActor(background);

        TextButton newGameBtn = new TextButton("SINGLE PLAYER", skin, MAIN_MENU_STYLE);
        newGameBtn.getStyle().fontColor = Color.BLACK;

        newGameBtn.setPosition(50, MIN_HEIGHT - 220f);
        stage.addActor(newGameBtn);

        Button connectToServerButton = new TextButton("CONNECT TO SERVER", skin, MAIN_MENU_STYLE);
        connectToServerButton.setPosition(50, MIN_HEIGHT - 170f);
        stage.addActor(connectToServerButton);

        Button tutorialButton = new TextButton("TUTORIAL", skin, MAIN_MENU_STYLE);
        tutorialButton.setPosition(50, MIN_HEIGHT - 270f);
        stage.addActor(tutorialButton);

        if (!saveInfoList.isEmpty()) {
            Button loadGameButton = new TextButton("LOAD GAME", skin, MAIN_MENU_STYLE);
            loadGameButton.setPosition(50, MIN_HEIGHT - 320f);
            stage.addActor(loadGameButton);

            loadGameButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showLoadGameWindow();
                }
            });
        }

        createStartGameWindow();
        createLoadGameWindow();

        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TutorialScreen(game));
            }
        });

        newGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showStartGameWindow();
            }
        });

        connectToServerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, 3, false));
            }
        });
    }

    /**
     * Creates the window element for the start game window.
     */
    private void createStartGameWindow() {
        Skin skin = GameManager.get().getSkin();

        startGameWindow = new Window("", skin, LOAD_GAME_STYLE);
        startGameWindow.setColor(Color.DARK_GRAY);
        startGameWindow.pad(15, 10, 10, 10);

        Label startGameWindowTitle = new Label("START GAME", skin, LOAD_GAME_STYLE);
        startGameWindowTitle.setAlignment(Align.center);
        startGameWindow.add(startGameWindowTitle).colspan(3).minWidth(200).minHeight(50).pad(0, 15, 15, 15).fill();

        startGameWindow.row();

        TextField seedInput = new TextField("", skin, LOAD_GAME_STYLE);
        seedInput.setMessageText("SEED (OPTIONAL)");
        seedInput.setColor(Color.LIGHT_GRAY);
        seedInput.setHeight(35);
        startGameWindow.add(seedInput).minWidth(320).minHeight(35).padBottom(12).fill();

        startGameWindow.row();

        Button startGameButton = new TextButton("START GAME", skin, LOAD_GAME_STYLE);
        startGameButton.setColor(Color.LIGHT_GRAY);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String seedString = seedInput.getText();
                long seed = calculateSeed(seedString);
                game.setScreen(new GameScreen(game, seed, true));
            }
        });
        startGameWindow.add(startGameButton).minWidth(150).minHeight(35);

        startGameWindow.pack();
        startGameWindow.setVisible(false);
        stage.addActor(startGameWindow);
    }

    /**
     * Shows the window element for the start game window.
     */
    private void showStartGameWindow() {
        startGameWindow.setVisible(true);
        startGameWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
    }

    /**
     * Calculates a seed based on the given string. If the string is empty, a random
     * number is generated. If the seed is an integer, that integer is used.
     * Otherwise, the hash of the string is used.
     *
     * @param seedString the string from which to calculate the seed
     * @return the seed
     */
    private long calculateSeed(String seedString) {
        long seed;
        if (seedString.isEmpty()) {
            seed = new Random().nextLong();
        } else {
            try {
                seed = Long.parseLong(seedString);
            } catch (NumberFormatException e) {
                seed = seedString.hashCode();
            }
        }
        return seed;
    }



    /**
     * Creates the window element for the load game window.
     */
    private void createLoadGameWindow() {
        TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);

        Skin skin = GameManager.get().getSkin();

        loadGameWindow = new Window("", skin, LOAD_GAME_STYLE);
        loadGameWindow.setColor(Color.DARK_GRAY);
        loadGameWindow.pad(15, 10, 10, 10);

        Label loadGameWindowTitle = new Label("LOAD GAME", skin, LOAD_GAME_STYLE);
        loadGameWindowTitle.setAlignment(Align.center);
        loadGameWindow.add(loadGameWindowTitle).colspan(3).minWidth(200).minHeight(50).pad(0, 15, 15, 15).fill();

        loadGameWindow.row();

        for (Save saveInfo : saveInfoList) {
            final long saveID = saveInfo.getSaveID();
            String saveName = "SAVE " + generateReadableName(saveID);

            loadGameWindow.add();

            Button loadSaveButton = new TextButton(saveName, skin, LOAD_GAME_STYLE);
            loadSaveButton.setColor(Color.LIGHT_GRAY);
            loadSaveButton.pad(-15);
            loadSaveButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, saveID));
                }
            });
            loadGameWindow.add(loadSaveButton).minWidth(320).minHeight(35).pad(6, 4, 6, 4).fill();

            Image deleteIcon = new Image(textureManager.getTexture("load_game_delete"));

            Button deleteSaveButton = new Button(skin, LOAD_GAME_STYLE);
            deleteSaveButton.setColor(Color.LIGHT_GRAY);
            deleteSaveButton.add(deleteIcon).pad(-50).center();
            deleteSaveButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    DatabaseManager.get().getDataBaseConnector().deleteSave(saveID);
                    loadGameWindow.removeActor(loadSaveButton);
                    loadGameWindow.removeActor(deleteSaveButton);
                }
            });
            loadGameWindow.add(deleteSaveButton).minWidth(35).minHeight(35).pad(6, 4, 6, 10);

            loadGameWindow.row();
        }

        Image backIcon = new Image(textureManager.getTexture("load_game_back"));

        Button loadGameBackButton = new Button(skin, LOAD_GAME_STYLE);
        loadGameBackButton.setColor(Color.LIGHT_GRAY);
        loadGameBackButton.add(backIcon).pad(-50).center();
        loadGameBackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideLoadGameWindow();
            }
        });
        loadGameWindow.add(loadGameBackButton).minWidth(35).minHeight(35).pad(6, 0, 0, 4);

        loadGameWindow.pack();
        loadGameWindow.setVisible(false);
        stage.addActor(loadGameWindow);
    }

    /**
     * Generates a random name based on a save ID.
     *
     * @param saveID the save ID on which to base the name
     * @return the name of the save
     */
    private String generateReadableName(long saveID) {
        char[] saveCode = new char[5];
        for (int i = 0; i < saveCode.length; i++) {
            char[] selectionList = i % 2 == 0 ? CONSONANTS : VOWELS;
            saveCode[i] = selectionList[(int) (saveID % selectionList.length)];
            saveID /= selectionList.length;
        }
        return String.copyValueOf(saveCode);
    }

    /**
     * Shows the window element for the load game window.
     */
    private void showLoadGameWindow() {
        loadGameWindow.setVisible(true);
        loadGameWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
    }

    /**
     * Creates the window element for the load game window.
     */
    private void hideLoadGameWindow() {
        loadGameWindow.setVisible(false);
    }

    /**
     * Begins things that need to begin when shown
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Pauses the screen
     */
    @Override
    public void pause() {
        // do nothing
    }

    /**
     * Hides the screen
     */
    @Override
    public void hide() {
        // do nothing
    }

    /**
     * Resizes the main menu stage to a new width and height
     *
     * @param width  the new width for the menu stage
     * @param height the new width for the menu stage
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Renders the menu
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    /**
     * Disposes of the stage that the menu is on
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Resumes the screen
     */
    @Override
    public void resume() {
        // do nothing
    }

}
