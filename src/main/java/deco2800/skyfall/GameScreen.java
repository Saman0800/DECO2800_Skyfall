package deco2800.skyfall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.Abductor;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.enemies.EnemySpawnTable;
import deco2800.skyfall.entities.enemies.Heavy;
import deco2800.skyfall.entities.enemies.Scout;
import deco2800.skyfall.graphics.HasPointLight;
import deco2800.skyfall.graphics.PointLight;
import deco2800.skyfall.graphics.ShaderWrapper;
import deco2800.skyfall.graphics.types.vec3;
import deco2800.skyfall.handlers.KeyboardManager;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.NetworkManager;
import deco2800.skyfall.managers.PathFindingService;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.SpawningManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.renderers.OverlayRenderer;
import deco2800.skyfall.renderers.PotateCamera;
import deco2800.skyfall.renderers.Renderer3D;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.lightinghelpers.FunctionalSpectralValue;
import deco2800.skyfall.util.lightinghelpers.IntensityFunction;
import deco2800.skyfall.util.lightinghelpers.LinearSpectralValue;
import deco2800.skyfall.util.lightinghelpers.SpectralValue;
import deco2800.skyfall.util.lightinghelpers.TFTuple;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.packing.BirthPlacePacking;
import deco2800.skyfall.worlds.packing.EnvironmentPacker;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;

/**
 * An instance of a Game screen.
 */
public class GameScreen implements Screen, KeyDownObserver {
    private final Logger logger = LoggerFactory.getLogger(GameScreen.class);
    @SuppressWarnings("unused")
    private final SkyfallGame game;
    /**
     * Set the renderer. 3D is for Isometric worlds Check the documentation for each
     * renderer to see how it handles WorldEntity coordinates
     */
    Renderer3D renderer = new Renderer3D();
    OverlayRenderer rendererDebug = new OverlayRenderer();
    World world;
    Save save;

    /**
     * Create a camera for panning and zooming. Camera must be updated every render
     * cycle.
     */
    PotateCamera camera;
    PotateCamera cameraDebug;
    private Stage stage = new Stage(new ExtendViewport(1280, 720));

    long lastGameTick = 0;

    /**
     * Create an EnvironmentManager for ToD.
     */
    EnvironmentManager timeOfDay;
    private static boolean isPaused = false;

    public static boolean getIsPaused() {
        return isPaused;
    }

    public static void setIsPaused(boolean paused) {
        isPaused = paused;
    }

    // A wrapper for shader
    private ShaderWrapper shader;

    /**
     * This hold the intensity for the ambient light for the ambient light.
     */
    private SpectralValue ambientIntensity;

    /**
     * The will be the spectral values for the RBG values of the ambient light
     */
    private SpectralValue ambientRed;
    private SpectralValue ambientBlue;
    private SpectralValue ambientGreen;

    public GameScreen(final SkyfallGame game, long seed, boolean isHost) {
        /* Create an example world for the engine */
        this.game = game;
        this.save = new Save();

        GameManager gameManager = initializeMenuManager();

        // Create main world
        if (!isHost) {
            // Creating the world
            world = WorldDirector.constructServerWorld(new WorldBuilder(), seed).getWorld();
            save.getWorlds().add(world);
            save.setCurrentWorld(world);
            world.setSave(save);
            MainCharacter.getInstance().setSave(save);

            gameManager.getManager(NetworkManager.class).connectToHost("localhost", "duck1234");
        } else {
            if (GameManager.get().isTutorial) {
                world = WorldDirector.constructTutorialWorld(new WorldBuilder(), seed).getWorld();
            } else {
                // Creating the world
                world = WorldDirector.constructNBiomeSinglePlayerWorld(new WorldBuilder(), seed, 4, true).getWorld();
            }
            save.getWorlds().add(world);
            save.setCurrentWorld(world);
            world.setSave(save);

            EnvironmentPacker packer = new EnvironmentPacker(world);
            packer.addPackingComponent(new BirthPlacePacking(packer));
            packer.doPackings();

            MainCharacter.getInstance().setSave(save);

            // FIXME:jeffvan12 implement better way of creating new stuff things

            // Comment this out when generating the data for the tests
            DatabaseManager.get().getDataBaseConnector().saveGame(save);

            // Uncomment this when generating the data for the tests
            // save.setId(0);
            // world.setId(0);
            // MainCharacter.getInstance().setID(0);
            // DatabaseManager.get().getDataBaseConnector().saveGame(save);
            // DatabaseManager.get().getDataBaseConnector().saveAllTables();

            gameManager.getManager(NetworkManager.class).startHosting("host");
        }

        gameManager.setWorld(world);

        initializeOtherManagers(gameManager);
    }

    public GameScreen(final SkyfallGame game, long saveID) {
        this.game = game;

        GameManager gameManager = initializeMenuManager();
        DataBaseConnector dbConnector = DatabaseManager.get().getDataBaseConnector();

        save = dbConnector.loadGame(saveID);
        World currentWorld = dbConnector.loadWorlds(save);
        save.setCurrentWorld(currentWorld);

        dbConnector.loadMainCharacter(save);
        world = save.getCurrentWorld();
        MainCharacter mainCharacter = MainCharacter.getInstance();
        mainCharacter.setSave(save);
        world.addEntity(mainCharacter);

        gameManager.getManager(NetworkManager.class).startHosting("host");

        StatisticsManager sm = new StatisticsManager(mainCharacter);
        GameManager.addManagerToInstance(sm);
        GameMenuManager gmm = GameManager.getManagerFromInstance(GameMenuManager.class);
        gmm.addStatsManager(sm);
        gmm.drawAllElements();

        gameManager.setWorld(world);

        initializeOtherManagers(gameManager);
    }

    private GameManager initializeMenuManager() {
        GameManager gameManager = GameManager.get();
        GameMenuManager gameMenuManager = GameManager.getManagerFromInstance(GameMenuManager.class);
        gameMenuManager.setStage(stage);
        gameMenuManager.setSkin(gameManager.getSkin());
        gameMenuManager.setGame(game);
        return gameManager;
    }

    private void initializeOtherManagers(GameManager gameManager) {
        camera = new PotateCamera(1920, 1080);
        cameraDebug = new PotateCamera(1920, 1080);

        /* Add the window to the stage */
        GameManager.get().setStage(stage);
        GameManager.get().setCamera(camera);

        /* Add inventory to game manager */
        gameManager.addManager(new InventoryManager());

        /* Add construction manager to game manager */
        // gameManager.addManager(new ConstructionManager());

        /* Add environment to game manager */
        EnvironmentManager gameEnvironManag = gameManager.getManager(EnvironmentManager.class);
        // For debugging only!
        gameEnvironManag.setTime(12, 0);

        /* Add Quest Manager to game manager */
        gameManager.addManager(new QuestManager());

        /**
         * NOTE: Now that the Environment Manager has been added start creating the
         * SpectralValue instances for the Ambient Light.
         */
        IntensityFunction intensityFunction = (float x) -> {
            double magnitude = 0.3;
            double period = 6.7;
            double vertShift = 2.38;

            double cosEval = Math.cos(((x - 12) * Math.PI) / 12.0);
            double normalise = magnitude * Math.sqrt((1 + period * period) / (1 + period * period * cosEval * cosEval));

            return (float) (normalise * cosEval + magnitude * vertShift);
        };

        ambientIntensity = new FunctionalSpectralValue(intensityFunction, gameEnvironManag);

        // Create the rgb spectral values
        ArrayList<TFTuple> redKeyFrame = new ArrayList<>();
        redKeyFrame.add(new TFTuple(0.0f, 0.15f));
        redKeyFrame.add(new TFTuple(5.0f, 0.15f));
        redKeyFrame.add(new TFTuple(5.5f, 0.2f));
        redKeyFrame.add(new TFTuple(6.0f, 0.7f));
        redKeyFrame.add(new TFTuple(6.3f, 0.6f));
        redKeyFrame.add(new TFTuple(7.0f, 0.9f));
        redKeyFrame.add(new TFTuple(17.0f, 0.9f));
        redKeyFrame.add(new TFTuple(17.5f, 0.8f));
        redKeyFrame.add(new TFTuple(18.5f, 0.6f));
        redKeyFrame.add(new TFTuple(19.0f, 0.15f));
        ambientRed = new LinearSpectralValue(redKeyFrame, gameEnvironManag);

        ArrayList<TFTuple> greenKeyFrame = new ArrayList<>();
        greenKeyFrame.add(new TFTuple(0.0f, 0.12f));
        greenKeyFrame.add(new TFTuple(5.0f, 0.12f));
        greenKeyFrame.add(new TFTuple(5.5f, 0.2f));
        greenKeyFrame.add(new TFTuple(6.0f, 0.45f));
        greenKeyFrame.add(new TFTuple(6.3f, 0.4f));
        greenKeyFrame.add(new TFTuple(7.0f, 0.9f));
        greenKeyFrame.add(new TFTuple(17.0f, 0.9f));
        greenKeyFrame.add(new TFTuple(17.5f, 0.5f));
        greenKeyFrame.add(new TFTuple(18.5f, 0.4f));
        greenKeyFrame.add(new TFTuple(19.0f, 0.12f));
        ambientGreen = new LinearSpectralValue(greenKeyFrame, gameEnvironManag);

        ArrayList<TFTuple> blueKeyFrame = new ArrayList<>();
        blueKeyFrame.add(new TFTuple(0.0f, 0.19f));
        blueKeyFrame.add(new TFTuple(5.0f, 0.19f));
        blueKeyFrame.add(new TFTuple(5.5f, 0.6f));
        blueKeyFrame.add(new TFTuple(6.0f, 0.1f));
        blueKeyFrame.add(new TFTuple(6.3f, 0.45f));
        blueKeyFrame.add(new TFTuple(7.0f, 0.96f));
        blueKeyFrame.add(new TFTuple(17.0f, 0.96f));
        blueKeyFrame.add(new TFTuple(17.5f, 0.35f));
        blueKeyFrame.add(new TFTuple(18.5f, 0.8f));
        blueKeyFrame.add(new TFTuple(19.0f, 0.19f));
        ambientBlue = new LinearSpectralValue(blueKeyFrame, gameEnvironManag);

        enemySetUp(gameEnvironManag, world);

        // create a spawning manager
        SpawningManager.createSpawningManager();

        PathFindingService pathFindingService = new PathFindingService();

        GameManager.get().addManager(pathFindingService);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(GameManager.get().getManager(KeyboardManager.class));
        multiplexer.addProcessor(GameManager.get().getManager(InputManager.class));
        Gdx.input.setInputProcessor(multiplexer);

        GameManager.get().getManager(KeyboardManager.class).registerForKeyDown(this);

        // Create the shader program from resource files
        // Shader program will be attached later
        shader = new ShaderWrapper("batch");
        // add shader to rendererDebug
        rendererDebug.setShader(shader);

        GameLauncher.getApplication().addLifecycleListener(new LifecycleListener() {
            @Override
            public void pause() {
                // Do nothing for the time being.
            }

            @Override
            public void resume() {
                // Do nothing for the time being.
            }

            @Override
            public void dispose() {
                DatabaseManager.get().getDataBaseConnector().saveGame(save);
            }
        });
    }

    /**
     * Renderer thread Must update all displayed elements using a Renderer
     */
    @Override
    public void render(float delta) {
        handleRenderables();

        if (!isPaused) {
            moveCamera();
            handleRenderables();
            cameraDebug.position.set(camera.position);
            cameraDebug.update();
            camera.update();
        } else {
            stage.draw();
            pause();
        }

        SpriteBatch batchDebug = new SpriteBatch();
        batchDebug.setProjectionMatrix(cameraDebug.combined);

        shader.begin();

        SpriteBatch batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // Clear the entire display as we are using lazy rendering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rerenderMapObjects(batch, camera);
        rendererDebug.render(batchDebug, cameraDebug);
        stage.act(delta);
        stage.draw();

        /* Refresh the experience UI for if information was updated */

        batch.dispose();
        shader.end();
    }

    private void handleRenderables() {
        if (System.currentTimeMillis() - lastGameTick > 20) {
            lastGameTick = System.currentTimeMillis();
            GameManager.get().onTick(0);
        }
    }

    /**
     * Use the selected renderer to render objects onto the map
     */
    private void rerenderMapObjects(SpriteBatch batch, OrthographicCamera camera) {
        // set ambient light
        shader.setAmbientComponent(
                new vec3(ambientRed.getIntensity(), ambientGreen.getIntensity(), ambientBlue.getIntensity()),
                ambientIntensity.getIntensity());

        // Add all the point lights of entities that implement the HasPointLight
        // interface into the batch
        for (AbstractEntity luminousEntity : GameManager.get().getWorld().getLuminousEntities()) {
            if (luminousEntity instanceof HasPointLight) {
                HasPointLight tempEntity = (HasPointLight) luminousEntity;
                tempEntity.updatePointLight();
                PointLight entityPointLight = tempEntity.getPointLight();
                if (entityPointLight != null) {
                    shader.addPointLight(entityPointLight);
                }
            }
        }

        // finalise shader parameters and attach to batch
        shader.finaliseAndAttachShader(batch);
        // render batch
        renderer.render(batch, camera);
    }

    @Override
    public void show() {
        // Do nothing on show.
    }

    /**
     * Resizes the viewport
     *
     * @param width  The new width of the viewport
     * @param height The new height of the viewport
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        cameraDebug.viewportWidth = width;
        cameraDebug.viewportHeight = height;
        cameraDebug.update();
    }

    @Override
    public void pause() {
        // do nothing
    }

    @Override
    public void resume() {
        // do nothing
    }

    @Override
    public void hide() {
        // do nothing
    }

    /**
     * Disposes of assets etc when the rendering system is stopped.
     */
    @Override
    public void dispose() {
        // Don't need this at the moment
        System.exit(0);
    }

    @Override
    public void notifyKeyDown(int keycode) {
        if (keycode == Input.Keys.F12) {
            GameManager.get().debugMode = !GameManager.get().debugMode;
        }

        if (keycode == Input.Keys.F5) {

            // Create a random world
            world = WorldDirector.constructNBiomeSinglePlayerWorld(new WorldBuilder(), world.getSeed() + 1, 4, true)
                    .getWorld();

            // Add this world to the save
            save.getWorlds().add(world);
            save.setCurrentWorld(world);
            world.setSave(save);
            DatabaseManager.get().getDataBaseConnector().saveGame(save);

            AbstractEntity.resetID();
            Tile.resetID();
            GameManager gameManager = GameManager.get();
            gameManager.setWorld(world);
        }

        if (keycode == Input.Keys.F11) { // F11
            GameManager.get().showCoords = !GameManager.get().showCoords;
            logger.info("Show coords is now {}", GameManager.get().showCoords);
        }

        if (keycode == Input.Keys.C) { // F11
            GameManager.get().showCoords = !GameManager.get().showCoords;
            logger.info("Show coords is now {}", GameManager.get().showCoords);
        }

        if (keycode == Input.Keys.F10) { // F10
            GameManager.get().showPath = !GameManager.get().showPath;
            logger.info("Show Path is now {}", GameManager.get().showPath);
        }

        // FIXME:jeffvan12 should replace with acutal world saving and loading
        if (keycode == Input.Keys.F3) { // F3
            // Save the world to the DB
            // DatabaseManager.saveWorld(null);
        }

        if (keycode == Input.Keys.F4) { // F4
            // Load the world to the DB
            // DatabaseManager.loadWorld(null);
        }

        if (keycode == Input.Keys.P) {
            DatabaseManager.get().getDataBaseConnector().saveGame(this.save);
        }
    }

    private void moveCamera() {
        // timmeh to fix hack. // fps is not updated cycle by cycle
        float normilisedGameSpeed = (60.0f / Gdx.graphics.getFramesPerSecond());

        int goFastSpeed = (int) (5 * normilisedGameSpeed * camera.zoom);

        if (!camera.isPotate()) {

            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                goFastSpeed *= goFastSpeed * goFastSpeed;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                camera.translate(-goFastSpeed, 0, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                camera.translate(goFastSpeed, 0, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                camera.translate(0, -goFastSpeed, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                camera.translate(0, goFastSpeed, 0);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
                camera.zoom *= 1 - 0.01 * normilisedGameSpeed;
                if (camera.zoom < 0.5) {
                    camera.zoom = 0.5f;
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
                camera.zoom *= 1 + 0.01 * normilisedGameSpeed;
            }
        }

    }

    private void enemySetUp(EnvironmentManager gameEnvironManag, World world) {

        ForestEnemySetup(gameEnvironManag, world);
        DesertEnemySetup(gameEnvironManag, world);
        MountainEnemySetup(gameEnvironManag, world);
        VolcanicMountainEnemySetup(gameEnvironManag, world);

        return;
    }

    private void ForestEnemySetup(EnvironmentManager gameEnvironManag, World world) {

        Function<HexVector, ? extends Enemy> spawnAbductor = hexPos -> new Scout(hexPos.getCol(), hexPos.getRow(), 0.7f,
                "Forest");

        Map<String, List<Function<HexVector, ? extends Enemy>>> biomeToConstructor = new HashMap<>();
        List<Function<HexVector, ? extends Enemy>> forestList = new ArrayList<>();
        forestList.add(spawnAbductor);

        biomeToConstructor.put("forest", forestList);

        Function<EnvironmentManager, Double> probAdjFunc = environMang -> {

            if ((environMang.getHourDecimal() >= 19) && (environMang.getHourDecimal() <= 3)) {
                return 0.05;
            }

            return 0.0;
        };

        EnemySpawnTable newEnemyTable = new EnemySpawnTable(70, 30, 3, biomeToConstructor, gameEnvironManag,
                probAdjFunc, world);

        gameEnvironManag.addTimeListener(newEnemyTable);

        return;
    }

    private void DesertEnemySetup(EnvironmentManager gameEnvironManag, World world) {

        Function<HexVector, ? extends Enemy> spawnScout = hexPos -> new Scout(hexPos.getCol(), hexPos.getRow(), 0.9f,
                "Desert");
        Function<HexVector, ? extends Enemy> spawnAbductor = hexPos -> new Abductor(hexPos.getCol(), hexPos.getRow(),
                0.9f, "Desert");

        Map<String, List<Function<HexVector, ? extends Enemy>>> biomeToConstructor = new HashMap<>();
        List<Function<HexVector, ? extends Enemy>> desertList = new ArrayList<>();
        desertList.add(spawnScout);
        desertList.add(spawnAbductor);

        biomeToConstructor.put("desert", desertList);

        // Set up
        Function<EnvironmentManager, Double> probAdjFunc = environMang -> {

            if ((environMang.getHourDecimal() >= 19) && (environMang.getHourDecimal() <= 3)) {
                return 0.02;
            }

            return 0.0;
        };

        EnemySpawnTable newEnemyTable = new EnemySpawnTable(100, 30, 2, biomeToConstructor, gameEnvironManag,
                probAdjFunc, world);

        gameEnvironManag.addTimeListener(newEnemyTable);

        return;
    }

    private void MountainEnemySetup(EnvironmentManager gameEnvironManag, World world) {

        Function<HexVector, ? extends Enemy> spawnScout = hexPos -> new Scout(hexPos.getCol(), hexPos.getRow(), 1.1f,
                "Mountain");
        Function<HexVector, ? extends Enemy> spawnAbductor = hexPos -> new Abductor(hexPos.getCol(), hexPos.getRow(),
                1.1f, "Mountain");
        Function<HexVector, ? extends Enemy> spawnHeavy = hexPos -> new Heavy(hexPos.getCol(), hexPos.getRow(), 1.1f,
                "Mountain");

        Map<String, List<Function<HexVector, ? extends Enemy>>> biomeToConstructor = new HashMap<>();
        List<Function<HexVector, ? extends Enemy>> mountainList = new ArrayList<>();
        mountainList.add(spawnScout);
        mountainList.add(spawnAbductor);
        mountainList.add(spawnHeavy);

        biomeToConstructor.put("mountain", mountainList);

        // Set up
        Function<EnvironmentManager, Double> probAdjFunc = environMang -> {

            if ((environMang.getHourDecimal() >= 17) && (environMang.getHourDecimal() <= 5)) {
                return 0.04;
            }

            return 0.0;
        };

        EnemySpawnTable newEnemyTable = new EnemySpawnTable(70, 20, 2, biomeToConstructor, gameEnvironManag,
                probAdjFunc, world);

        gameEnvironManag.addTimeListener(newEnemyTable);

        return;
    }

    private void VolcanicMountainEnemySetup(EnvironmentManager gameEnvironManag, World world) {

        Function<HexVector, ? extends Enemy> spawnScout = hexPos -> new Scout(hexPos.getCol(), hexPos.getRow(), 1.1f,
                "VolcanicMountain");
        Function<HexVector, ? extends Enemy> spawnAbductor = hexPos -> new Abductor(hexPos.getCol(), hexPos.getRow(),
                1.1f, "VolcanicMountain");
        Function<HexVector, ? extends Enemy> spawnHeavy = hexPos -> new Heavy(hexPos.getCol(), hexPos.getRow(), 1.1f,
                "VolcanicMountain");

        Map<String, List<Function<HexVector, ? extends Enemy>>> biomeToConstructor = new HashMap<>();
        List<Function<HexVector, ? extends Enemy>> volcanicMountainList = new ArrayList<>();
        volcanicMountainList.add(spawnScout);
        volcanicMountainList.add(spawnAbductor);
        volcanicMountainList.add(spawnHeavy);

        biomeToConstructor.put("volcanic", volcanicMountainList);

        // Set up
        Function<EnvironmentManager, Double> probAdjFunc = environMang -> {

            if ((environMang.getHourDecimal() >= 18) && (environMang.getHourDecimal() <= 4)) {
                return 0.05;
            }

            return 0.0;
        };

        EnemySpawnTable newEnemyTable = new EnemySpawnTable(70, 15, 2, biomeToConstructor, gameEnvironManag,
                probAdjFunc, world);

        gameEnvironManag.addTimeListener(newEnemyTable);

        return;
    }
}