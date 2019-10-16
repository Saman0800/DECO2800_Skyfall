package deco2800.skyfall.resources;

import deco2800.skyfall.util.HexVector;

/**
 * An interface representing a generic resource item.
 */
public interface Item{

    /**
     * Returns the name of the item.
     * @return The name of the item.
     */
    String getName();

    /**
     * Returns the subtype which the item belongs to.
     * @return The subtype which the item belongs to.
     */
    String getSubtype();

    /**
     * Returns whether or not the item can be carried.
     * @return True if the item can be carried in the inventory, false
     * if it is consumed immediately.
     */
    boolean isCarryable();

    /**
     * Returns the co-ordinates of the tile the item is on.
     * @return the co-ordinates of the tile the item is on.
     */
    HexVector getCoords();

    /**
     * Returns whether or not the item can be exchanged.
     * @return True if the item can be exchanged, false otherwise.
     */
    boolean isExchangeable();

    /**
     * Returns a description about the item.
     * @return a description about the item.
     */
    String getDescription();

    /**
     * Use the specific function associated with the item.
     */
    void use(HexVector position);

    /**
     * Returns whether or not the item can be equipped from the inventory.
     * @return True if the item can be equipped, false otherwise.
     */
    boolean isEquippable();
}
