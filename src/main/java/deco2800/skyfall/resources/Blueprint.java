package deco2800.skyfall.resources;

import java.util.*;

/**
 * An interface representing a blueprint which would be required to create
 * certain items, weapons, buildings.
 */
public interface Blueprint {


    enum BluePrintNames {
        ;

        public static final String CABIN_STRING = "Cabin";
        public static final String CABIN_STRING_0 = "cabin_0";
        public static final String STORAGE_UNIT_STRING = "StorageUnit";
        public static final String STORAGE_UNIT_STRING_LOWER = "storage_unit";
        public static final String TOWN_CENTRE_STRING = "TownCentre";
        public static final String TOWN_CENTRE_STRING_LOWER = "town_centre";
        public static final String WOOD_STRING = "Wood";
        public static final String STONE_STRING = "Stone";
        public static final String METAL_STRING = "Metal";
    }

    /**
     * Returns the number of wood required for the item.
     * 
     * @return The name of the item
     */
    int getRequiredWood();

    /**
     * Returns the number of stones required for the item.
     * 
     * @return The name of the item
     */
    int getRequiredStone();

    /**
     * Returns the number of metal required for the item.
     * 
     * @return The name of the item
     */
    int getRequiredMetal();

    /**
     * Returns a map of the name of the required resource and the required number of
     * each resource to create the item.
     * 
     * @return a hashamp of the required resources and their number.
     */
    Map<String, Integer> getAllRequirements();

    /**
     * @return - cost of building the building
     */
    int getCost();

    /**
     * @return - the name of the item
     */
    String getName();

}
