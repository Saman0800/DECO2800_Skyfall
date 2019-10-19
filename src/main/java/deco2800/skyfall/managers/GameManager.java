package deco2800.skyfall.managers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.skyfall.renderers.PotateCamera;
import deco2800.skyfall.worlds.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    // debug values stored here
    private int entitiesRendered;
    private int entitiesCount;
    private int tilesRendered;
    private int tilesCount;

    private boolean isTutorial = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private static GameManager instance = null;

    // The list of all instantiated managers classes.
    private List<AbstractManager> managers = new ArrayList<>();

    // The game world currently being played on.
    private World gameWorld;

    // The camera being used by the Game Screen to project the game world.
    private PotateCamera camera;

    // The stage the game world is being rendered on to.
    private Stage stage;

    // The UI skin being used by the game for libGDX elements.
    private Skin skin;

    // Showing if the game is paused.
    private static boolean paused = false;

    private boolean debugMode = false;

    /**
     * Whether or not we render info over the tiles.
     */
    // Whether or not we render the movement path for Players.
    private boolean showCoords = false;

    // The game screen for a game that's currently running.
    private boolean showPath = false;

    /**
     * Whether or not we render info over the entities
     */
    private boolean showCoordsEntity = false;

    /**
     * Returns an instance of the GM
     *
     * @return GameManager
     */
    public static GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Private constructor to inforce use of get()
     */
    private GameManager() {

    }

    /**
     * Add a manager to the current instance, making a new instance if none exist
     *
     * @param manager
     */
    public static void addManagerToInstance(AbstractManager manager) {
        get().addManager(manager);
    }

    /**
     * Adds a manager component to the GM
     *
     * @param manager
     */
    public void addManager(AbstractManager manager) {
        managers.add(manager);
    }

    /**
     * Retrieves a manager from the list. If the manager does not exist one will be
     * created, added to the list and returned
     *
     * @param type The class type (ie SoundManager.class)
     * @return A Manager component of the requested type
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractManager> T getManager(Class<T> type) {
        /* Check if the manager exists */
        for (AbstractManager m : managers) {
            if (m.getClass() == type) {
                return (T) m;
            }
        }
        LOGGER.info("creating new manager instance");
        /* Otherwise create one */
        AbstractManager newInstance;
        try {
            Constructor<?> ctor = type.getConstructor();
            newInstance = (AbstractManager) ctor.newInstance();
            this.addManager(newInstance);
            return (T) newInstance;
        } catch (Exception e) {
            // Gotta catch 'em all
            LOGGER.error("Exception occurred when adding Manager.");
        }

        LOGGER.warn("GameManager.getManager returned null! It shouldn't have!");
        return null;
    }

    /**
     * Retrieve a manager from the current GameManager instance, making a new
     * instance when none are available.
     *
     * @param type The class type (ie SoundManager.class)
     * @return A Manager component of the requested type
     */
    public static <T extends AbstractManager> T getManagerFromInstance(Class<T> type) {
        return get().getManager(type);
    }

    /*
     * ------------------------------------------------------------------------
     * GETTERS AND SETTERS BELOW THIS COMMENT.
     * ------------------------------------------------------------------------
     */

    /**
     * Returns true if this is a tutorial world.
     * 
     * @return The debugMode boolean
     */
    public boolean getDebugMode() {
        return this.debugMode;
    }

    /**
     * Sets the debugging state for this class.
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Toggles the debugging state
     */
    public void toggleDebugMode() {
        this.debugMode = !debugMode;
    }

    /**
     * Returns true if the path is to be shown
     * 
     * @return The showPath boolean
     */
    public boolean getShowPath() {
        return this.showPath;
    }

    /**
     * Sets the showing the path parameter
     */
    public void setShowPath(boolean showPath) {
        this.showPath = showPath;
    }

    /**
     * Toggles showing the path
     */
    public void toggleShowPath() {
        this.showPath = !showPath;
    }

    /**
     * Returns true if this is a tutorial world.
     * 
     * @return The isTutorial boolean
     */
    public boolean getIsTutorial() {
        return this.isTutorial;
    }

    /**
     * Sets the is tutorial variable for this class.
     */
    public void setIsTutorial(boolean isTutorial) {
        this.isTutorial = isTutorial;
    }

    /**
     * Returns true if this is a tutorial world.
     * 
     * @return The showCoords boolean
     */
    public boolean getShowCoords() {
        return this.showCoords;
    }

    /**
     * Sets the is tutorial variable for this class.
     */
    public void setShowCoords(boolean showCoords) {
        this.showCoords = showCoords;
    }

    /**
     * Toggle showing the coordinates
     */
    public void toggleShowCoords() {
        this.showCoords = !showCoords;
    }

    /**
     * Returns true is entity coords are beign shown
     * 
     * @return The showCoordsEntity boolean
     */
    public boolean getShowCoordsEntity() {
        return this.showCoordsEntity;
    }

    /**
     * Sets the is wether or not entity coords are shown
     */
    public void setShowCoordsEntity(boolean showCoordsEntity) {
        this.showCoordsEntity = showCoordsEntity;
    }

    /**
     * Toggle showing the entity coordinates
     */
    public void toggleShowCoordsEntity() {
        this.showCoordsEntity = !showCoordsEntity;
    }

    /**
     * Get entities rendered count
     * 
     * @return entities rendered count
     */
    public int getEntitiesRendered() {
        return this.entitiesRendered;
    }

    /**
     * Set entities rendered to new amount
     * 
     * @param entitiesRendered the new amount
     */
    public void setEntitiesRendered(int entitiesRendered) {
        this.entitiesRendered = entitiesRendered;
    }

    /**
     * Get number of entities
     * 
     * @return entities count
     */
    public int getEntitiesCount() {
        return this.entitiesCount;
    }

    /**
     * Set entities count to new amount
     * 
     * @param entitiesCount the new amount
     */
    public void setEntitiesCount(int entitiesCount) {
        this.entitiesCount = entitiesCount;
    }

    /**
     * Get tiles rendered count
     * 
     * @return tiles rendered count
     */
    public int getTilesRendered() {
        return this.tilesRendered;
    }

    /**
     * Set tiles rendered to new amount
     * 
     * @param tilesRendered the new amount
     */
    public void setTilesRendered(int tilesRendered) {
        this.tilesRendered = tilesRendered;
    }

    /**
     * Get number of tiles
     * 
     * @return tiles count
     */
    public int getTilesCount() {
        return this.tilesCount;
    }

    /**
     * Set tiles count to new amount
     * 
     * @param tilesCount the new amount
     */
    public void setTilesCount(int tilesCount) {
        this.tilesCount = tilesCount;
    }

    /**
     * Sets the current game world
     *
     * @param world
     */
    public void setWorld(World world) {
        this.gameWorld = world;
    }

    /**
     * Gets the current game world
     *
     * @return the game world
     */
    public World getWorld() {
        return gameWorld;
    }

    public void setCamera(PotateCamera camera) {
        this.camera = camera;
    }

    /**
     * @return current game's stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage - the current game's stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return current game's skin
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * @param skin - the current game's skin
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public PotateCamera getCamera() {
        return camera;
    }

    /**
     * On tick method for ticking managers with the TickableManager interface
     *
     * @param i
     */
    public void onTick(long i) {
        for (AbstractManager m : managers) {
            if (m instanceof TickableManager) {
                ((TickableManager) m).onTick(i);
            }
        }
        gameWorld.onTick(i);
    }

    /**
     * Pause or resume the game.
     *
     * @param pause
     */
    public static void setPaused(boolean pause) {
        paused = pause;
    }

    /**
     * Get if the game is paused
     * 
     * @return paused
     */
    public static boolean getPaused() {
        return paused;
    }

}