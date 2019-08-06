package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.Tile;

/**
 * This interface is required to be implement if a entity is to be randomly
 * dispersed on game start up.
 * 
 * @param <E> The type/class of the entity that you would like to disperse, for
 *            example: Rock (class), Tree (class).
 */
public interface NewInstance<E> {
    /**
     * This function will create a deep copy of the class it is sitting by calling
     * the constructor for the class it is sitting. The only parameter that changes
     * between the original copy and the new copy of the instance is the tile that
     * the instance has been designated to.
     * 
     * @param tile The new tile on which the duplicated instance will be placed on
     * @return The duplicated instance with the new tile position
     */
    E newInstance(Tile tile);
}