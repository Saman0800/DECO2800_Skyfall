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
     * Returns the subtype which the item belongs to.
     * @return The subtype which the item belongs to.
     */
    String getSubtype();

    /**
     * Returns whether or not the item can be stored in the inventory
     * @return True if the item can be added to the inventory, false
     * if it is consumed immediately
     */
    Boolean isCarryable();


    /**
     * Returns the co-ordinates of the tile the item is on
     * @return the co-ordinates of the tile the item is on
     */
    HexVector getCoords();

    /**
     * Returns whether or not the item can be exchanged
     * @return True if the item can be exhanged, false otherwise
     */
    Boolean isExchangeable();

    //method for getting the biome?








}
