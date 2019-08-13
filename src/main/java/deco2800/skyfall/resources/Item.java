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
     * Returns whether or not the item impacts the player's health (HP)
     * @return True if the item impacts on the player's health, false otherwise
     */
    Boolean hasHealingPower();


    /**
     * Returns whether or not the item impacts the player's food fullness
     * @return True if the item impacts on the player's food fullness,
     * false otherwise
     */
    Boolean hasFoodEffect();

    /**
     * Returns the co-ordinates of the tile the item is on
     * @return the co-ordinates of the tile the item is on
     */
    HexVector getCoords();

    /**
     * Returns whether or not the item can be exchanged
     * @return True if the item can be exhanged, false otherwise
     */
    Boolean getExchangeable();

    //method for getting the biome?


    // don't think this method is needed:
    /**
     * Returns whether or not the item could deduct the HP of players
     * @return True if the item deduct the player's health, false otherwise
     */


    // default Boolean getNotHealingPower();
    /**
     * Returns whether or not the item could be exchanged
     * @return True if the item could be exhanged, false otherwise
     */







}
