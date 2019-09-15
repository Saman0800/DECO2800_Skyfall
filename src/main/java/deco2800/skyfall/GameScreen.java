package deco2800.skyfall;

import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.graphics.PointLight;
import deco2800.skyfall.graphics.ShaderWrapper;
import deco2800.skyfall.graphics.types.*;
import deco2800.skyfall.graphics.*;
import deco2800.skyfall.handlers.KeyboardManager;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.renderers.PotateCamera;
import deco2800.skyfall.renderers.OverlayRenderer;
import deco2800.skyfall.renderers.Renderer3D;
import deco2800.skyfall.worlds.*;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.util.lightinghelpers.*;

import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameScreen implements Screen, KeyDownObserver {
    private final Logger LOG = LoggerFactory.getLogger(Renderer3D.class);
    @SuppressWarnings("unused")
    private final SkyfallGame game;
    /**
     * Set the renderer. 3D is for Isometric worlds Check the documentation for each
     * renderer to see how it handles WorldEntity coordinates
     */
    Renderer3D renderer = new Renderer3D();
    OverlayRenderer rendererDebug = new OverlayRenderer();
    World world;
    static Skin skin;

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
    public static boolean isPaused = false;

    //A wrapper for shader
    ShaderWrapper shader;

    /**
     * This hold the intensity for the ambient light for the ambient light.
     */
    SpectralValue ambientIntensity;

    /**
     * The will be the spectral values for the RBG values of the ambient light
     */
    SpectralValue ambientRed;
    SpectralValue ambientBlue;
    SpectralValue ambientGreen;

    public GameScreen(final SkyfallGame game, long seed, boolean isHost) {
        /* Create an example world for the engine */
        this.game = game;

        GameManager gameManager = GameManager.get();
        GameMenuManager gameMenuManager = GameManager.get().getManagerFromInstance(GameMenuManager.class);
        gameMenuManager.setStage(stage);
        gameMenuManager.setSkin(gameManager.getSkin());
        gameMenuManager.setGame(game);

        //Used to create to the world

        // Create main world
        if (!isHost) {
            //Creating the world
            WorldBuilder worldBuilder = new WorldBuilder();
            WorldDirector.constructServerWorld(worldBuilder);
            world = worldBuilder.getWorld();

            GameManager.get().getManager(NetworkManager.class).connectToHost("localhost", "duck1234");
        } else {
            if (GameManager.get().isTutorial) {

                WorldBuilder worldBuilder = new WorldBuilder();
                WorldDirector.constructTutorialWorld(worldBuilder);
                world = worldBuilder.getWorld();
            } else {

                //Creating the world
                world = WorldDirector.constructNBiomeSinglePlayerWorld(new WorldBuilder(), 5, true).getWorld();
            }
            GameManager.get().getManager(NetworkManager.class).startHosting("host");
        }

        gameManager.setWorld(world);

        // Add first peon to the world
        camera = new PotateCamera(1920, 1080);
        cameraDebug = new PotateCamera(1920, 1080);

        /* Add the window to the stage */
//        GameManager.get().setSkin(skin);
        GameManager.get().setStage(stage);
        GameManager.get().setCamera(camera);

        /* Add inventory to game manager */
        gameManager.addManager(new InventoryManager());

        /* Add construction manager to game manager */
        gameManager.addManager(new ConstructionManager());
        // testing requirement for widget, removed it later
        BuildingFactory bf = new BuildingFactory();
        GameManager.get().getWorld().addEntity(bf.createCabin(3f, 1.5f));
        GameManager.get().getWorld().addEntity(bf.createCabin(-5f, 2f));

        /* Add environment to game manager */
        EnvironmentManager gameEnvironManag = gameManager.getManager(EnvironmentManager.class);
        // For debuggin only!
        gameEnvironManag.setTime(12, 0);

        /* Add BGM to game manager */
        gameManager.addManager(new BGMManager());

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

        PathFindingService pathFindingService = new PathFindingService();

        GameManager.get().addManager(pathFindingService);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(GameManager.get().getManager(KeyboardManager.class));
        multiplexer.addProcessor(GameManager.get().getManager(InputManager.class));
        Gdx.input.setInputProcessor(multiplexer);

        GameManager.get().getManager(KeyboardManager.class).registerForKeyDown(this);

        //Create the shader program from resource files
        //Shader program will be attached later
        shader = new ShaderWrapper("batch");
        //add shader to rendererDebug
        rendererDebug.setShader(shader);
    }

    /**
     * Renderer thread
     * Must update all displayed elements using a Renderer
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
        //set ambient light
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

        //finalise shader parameters and attach to batch
        shader.finaliseAndAttachShader(batch);
        //render batch
        renderer.render(batch, camera);
    }

    @Override
    public void show() {
    }

    /**
     * Resizes the viewport
     *
     * @param width
     * @param height
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

            //Create a random world
            world = WorldDirector.constructNBiomeSinglePlayerWorld(new WorldBuilder(), 3, true).getWorld();

            AbstractEntity.resetID();
            Tile.resetID();
            GameManager gameManager = GameManager.get();
            gameManager.setWorld(world);
        }

        if (keycode == Input.Keys.F11) { // F11
            GameManager.get().showCoords = !GameManager.get().showCoords;
            LOG.info("Show coords is now {}", GameManager.get().showCoords);
        }

        if (keycode == Input.Keys.C) { // F11
            GameManager.get().showCoords = !GameManager.get().showCoords;
            LOG.info("Show coords is now {}", GameManager.get().showCoords);
        }

        if (keycode == Input.Keys.F10) { // F10
            GameManager.get().showPath = !GameManager.get().showPath;
            LOG.info("Show Path is now {}", GameManager.get().showPath);
        }

        if (keycode == Input.Keys.F3) { // F3
            // Save the world to the DB
            DatabaseManager.saveWorld(null);
        }

        if (keycode == Input.Keys.F4) { // F4
            // Load the world to the DB
            DatabaseManager.loadWorld(null);
        }
    }

    public void moveCamera() {
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
}