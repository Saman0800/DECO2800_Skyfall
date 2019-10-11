package deco2800.skyfall.buildings;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.world.World;

/**
 * An AbstractPortal is an item that can transport a players position to the
 * specified Biome, given the player has reached the necessary requirements.
 */
public abstract class AbstractPortal extends AbstractEntity {

    // a logger
    private final transient Logger log = LoggerFactory.getLogger(BuildingEntity.class);
    // a building object name
    private static final String ENTITY_ID_STRING = "PortalID";
    // The next biome to teleport to
    public String nextBiome;

    private Map<String, Integer> buildCost;

    protected boolean blueprintLearned = false;
    protected String name = "abstractPortal";
    protected String currentBiome;

    private float col;
    private float row;

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     * @param
     */
    public AbstractPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
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
     * Sets the next Biome to teleport to
     */
    public void setNext(String nextBiome) {
        this.nextBiome = nextBiome;
    }

    /**
     * Move characters location to the next biome To be implemented when a player
     * clicks on the portal
     *
     * @param character - The Character to teleport
     * @param nextBiome - the Biome to teleport to
     */
    public void teleport(MainCharacter character, World nextBiome) {
        // TODO: @CGulley - Create a general teleport method for all portals.
        // For now, individual functionality in child classes

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
        return 0;
    }

    public void unlocknext(MainCharacter character, String next) {

    }

}
