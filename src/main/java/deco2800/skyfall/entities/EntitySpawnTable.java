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
     * @param <T> T must extend StaticEntity and have .newInstance inherited
     * @return The duplicated instance with the new tile position.
     * See NewInstance to place items
     */
    public static <T extends StaticEntity> void placeEntity( T entity, Tile tile) {
        AbstractWorld world = GameManager.get().getWorld();
        world.addEntity((T) entity.newInstance(tile));
    }

    /**
     * Randomly distributes an entity with a given spawn rule
     * @param entity The entity to copied and distributed
     * @param rule A spawn rule, which specifies how the entity will be distributed
     *     example rules are chance, min/max, next to, a combination of these, e.g.
     * @param <T> T must extend StaticEntity and have .newInstance inherited
     */
    public static <T extends StaticEntity> void spawnEntities(T entity, EntitySpawnRule rule) {
        AbstractWorld world = GameManager.get().getWorld();
        Random r = new Random();

        List<Tile> tiles = world.getTileMap();
        //randomise tile order
        Collections.shuffle(tiles, r);

        int max = rule.getMax();
        int min = rule.getMin();
        double chance = rule.getChance();

        //generate the number of tiles of this ent to place based on rule
        int toPlace = (chance < 0) ?
                //probability is determined by min max
                r.nextInt(max - min + 1) + min
                //direct determine number of tiles
                : (int)(chance*tiles.size());

        //ensure min max is enforced
        toPlace = java.lang.Math.max(min, toPlace);
        toPlace = java.lang.Math.min(max, toPlace);
        //ensure number of tiles to place isn't more than the world contains
        toPlace = java.lang.Math.min(tiles.size(), toPlace);

        //continue iterating over tiles while rule needs to be satisfied
        for (int i = 0; i < toPlace; i++) {
            Tile tile = tiles.get(i);
            if (tile != null && r.nextDouble() < chance) {
                placeEntity(entity, tile);
            }
        }
    }

    /**
     * Does entity placing with a simple probability, with no need for a EntitySpawnRule
     * TODO: finish documentation
     */
    public static <T extends StaticEntity> void spawnEntities(T entity, float chance) {
        EntitySpawnRule spawnRule = new EntitySpawnRule(chance);
        spawnEntities(entity, spawnRule);
    }
}
