package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.SaveableEntity;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * An AbstractPortal is an item that can transport a players position to the
 * specified Biome, given the player has reached the necessary requirements.
 */
public abstract class AbstractPortal extends SaveableEntity {
    // a logger

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPortal.class);
    private static final String ENTITY_ID_STRING = "PortalID";
    public static final int COST = 100;
    // The next biome to teleport to
    String nextBiome;

    private Map<String, Integer> buildCost;

    String currentBiome;
    String name;
    boolean blueprintLearned;

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public AbstractPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            LOGGER.debug("Invalid position");
        }
    }

    @Override
    public void onTick(long i) {
        // do nothing so far
    }

    public Map<String, Integer> getBuildCost() {
        return this.buildCost;
    }

    /**
     * Gets the next biome to teleport to.
     *
     * @return the next biome to teleport to
     */
    public String getNext() {
        return nextBiome;
    }

    /**
     * Sets the next Biome to teleport to
     */
    public void setNext(String nextBiome) {
        this.nextBiome = nextBiome;
    }

    /**
     * Move characters location to the next biome To be implemented when a player
     * clicks on the portal
     *
     * @param save - The Save file this is for
     */
    public void teleport(Save save) {
        // Stop the music from the previous biome/world
        SoundManager.stopSound(GameManager.get().getManager(EnvironmentManager.class).getFilename());

        // Create a random world
        MainCharacter character = save.getMainCharacter();
        World currentWorld = character.getSave().getCurrentWorld();
        World nextWorld = WorldDirector.constructSingleBiomeWorld(new WorldBuilder(), currentWorld.getSeed() + 1, true, nextBiome).getWorld();
        // Add this world to the save
        save.getWorlds().add(nextWorld);
        save.incrementGameStage();
        save.setCurrentWorld(nextWorld);
        nextWorld.setSave(save);
        // Move main character to origin of new world
        save.getMainCharacter().setPosition(0, 0);
        DatabaseManager.get().getDataBaseConnector().saveGame(save);

        AbstractEntity.resetID();
        Tile.resetID();
        GameManager gameManager = GameManager.get();
        gameManager.setWorld(nextWorld);
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The amount of wood needed
     */
    public int getRequiredWood() {
        return 0;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The amount of stone needed
     */
    public int getRequiredStone() {
        return 0;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The amount of metal needed
     */
    public int getRequiredMetal() {
        return 0;
    }

    /**
     * Returns a map of the name of the required resource and the required number of
     * each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    public Map<String, Integer> getAllRequirements() {
        return getBuildCost();
    }

    /**
     * Get the name of the Portal
     *
     * @return String - The name of the portal
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the biome for the Portal
     *
     * @return String - The name of the biome
     */
    public String getCurrentBiome() {
        return this.currentBiome;
    }

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint, false otherwise
     */
    public boolean isBlueprintLearned() {
        return blueprintLearned;
    }

    /**
     * Toggles the boolean blueprintLearned between a true and false state.
     */
    public void toggleBlueprintLearned() {
        blueprintLearned = !blueprintLearned;
    }

    /**
     * @return - cost of building the building
     */
    public int getCost() {
        return COST;
    }

    public void unlocknext(MainCharacter character, String next) {

    }

    /**
     * Returns the next biome for this portal
     *
     * @return the next biome for this portal
     */
    public String getNextBiome() {
        return this.nextBiome;
    }



}
