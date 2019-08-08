package deco2800.skyfall.resources;

import deco2800.skyfall.util.HexVector;

/**
 * An interface representing a generic resource item.
 */
public interface Item {

    /**
     * Returns the name of the item
     * @return The name of the item
     */
    String getName();

    /**
     * Returns whether or not the item can be stored in the inventory
     * @return True if the item can be added to the inventory, false
     * if it is consumed immediately
     */
    String getSubtype();

    /**
     * Returns the subtype which the item belongs to.
     * @return The subtype which the item belongs to.
     */
    Boolean isCarryable();


    /**
     * Returns whether or not the item impacts the player's health
     * @return True if the item impacts on the player's health, false otherwise
     */
    Boolean hasHealingPower();

    /**
     * Returns the co-ordinates of the tile the item is on
     * @return the co-ordinates of the tile the item is on
     */
    HexVector getCoords();

    //method for getting the biome?

}
