package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.managers.GameManager;


/**
 * Simple class containing static methods for placing static entities and NewInstance inheriting entities into the world
 * Does not manage spawn tabling (randomly placing items into the world), requires fixed tile location
 */
public class EntityPlacing {
    /**
     * Takes the given entity and places a deep copy within the world at a given tile
     * @param tile The tile the new entity will occupy
     * @param entity The entity to be deep copied
     * @return The duplicated instance with the new tile position.
     */
    public static <T extends StaticEntity> void placeEntity(Tile tile, T entity) {
        AbstractWorld world = GameManager.get().getWorld();
        world.addEntity( (T) entity.newInstance(tile) );
    }

    /**
     *Overloads placeEntity(Tile, T extends StaticEntity)
     * alternative method for creating entities with parameters
     * Relativly dangerous, there is no compile-time checking on args
     * Best used
     */
    public static <T extends StaticEntity> void placeEntity(Tile tile, Object... args) {
        AbstractWorld world = GameManager.get().getWorld();
        world.addEntity(  new T<>(args));
    }

}
