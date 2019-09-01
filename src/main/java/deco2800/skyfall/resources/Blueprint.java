package deco2800.skyfall.resources;

import java.util.*;

/**
 * An interface representing a blueprint which would be required
 * to create certain items, weapons, buildings.
 */
public interface Blueprint {

    /**
     * Returns the number of wood required for the item.
     * @return The name of the item
     */
    int getRequiredWood();

    /**
     * Returns the number of stones required for the item.
     * @return The name of the item
     */
    int getRequiredStone();

    /**
     * Returns the number of metal required for the item.
     * @return The name of the item
     */
    int getRequiredMetal();

    /**
     * Returns a map of the name of the required resource and
     * the required number of each resource to create the item.
     * @return a hashamp of the required resources and their number.
     */
    Map<String,Integer> getAllRequirements();

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint.
     */
    boolean isBlueprintLearned();

    /**
     * changes the boolean blueprintLearned to true.
     */
    void toggleBlueprintLearned();






}
