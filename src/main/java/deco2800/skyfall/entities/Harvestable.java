package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.Tile;

import java.util.List;

/**
 * An interface for entities which can be harvested to drop material.
 */
public interface Harvestable {
    /**
     * @param tile The tile which was harvested.
     * @return A list of entities to spawn when the present entity is harvested.
     */
    List<AbstractEntity> harvest(Tile tile);
}
