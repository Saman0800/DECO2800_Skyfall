package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.managers.GameManager;

import java.util.Collections;
import java.util.List;
import java.util.Random;



/**
 * Manages spawn tabling (randomly placing items into the world)
 * Uses EntitySpawnRule for precise spawning
 * however, simpler methods exist that will handle this for you
 */
public class EntitySpawnTable {


    /**
     * Simple static method for placing static items
     * Takes the given entity and places a deep copy within the world at a given tile
     * @param tile The tile the new entity will occupy
     * @param entity The entity to be deep copied
     * @return The duplicated instance with the new tile position.
     * See NewInstance to place items
     */
    public static <T extends StaticEntity> void placeEntity( T entity, Tile tile) {
        AbstractWorld world = GameManager.get().getWorld();
        world.addEntity((T) entity.newInstance(tile));
    }

    public static <T extends StaticEntity> void spawnEnities(T entity, EntitySpawnRule rule) {
        int max = rule.getMax();
        int min = rule.getMin();
        double chance = rule.getChance() < 0 ? rule.getChance() : 1 / (double)( max - min );

        AbstractWorld world = GameManager.get().getWorld();
        Random r = new Random();

        List<Tile> tiles = world.getTileMap();
        Collections.shuffle(tiles, r);

        int placed = 0;

        //continue iterating over tiles while rule needs to be satisfied
        for (int i = 0; (i < tiles.size() || placed <= min) && placed <= max; i = (++i)%tiles.size()) {
            Tile tile = tiles.get(i);
            if (tile != null && r.nextDouble() < chance) {
                placeEntity(entity, tile);
            }
        }


    }
}
