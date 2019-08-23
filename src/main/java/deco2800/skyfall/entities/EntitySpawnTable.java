package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.managers.GameManager;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Manages spawn tabling (randomly placing items into the world) Uses
 * EntitySpawnRule for precise spawning however, simpler methods exist that will
 * handle this for you
 */
@SuppressWarnings("unchecked")
public class EntitySpawnTable {
    /**
     * Simple static method for placing static items. Takes the given entity and
     * places a deep copy within the world at a given tile
     *
     * @param tile   The tile the new entity will occupy
     * @param entity The entity to be deep copied
     * @param <T>    T must extend StaticEntity and have .newInstance inherited
     * @return The duplicated instance with the new tile position. See NewInstance
     *         to place items
     */
    public static <T extends StaticEntity> void placeEntity(T entity, Tile tile) {
        AbstractWorld world = GameManager.get().getWorld();
        world.addEntity((T) entity.newInstance(tile));
    }

    public static <T extends StaticEntity> void spawnUniform(T entity, EntitySpawnRule rule) {

        AbstractWorld world = GameManager.get().getWorld();
        List<Tile> tiles = null;
        Random rand = new Random();

        if (rule.getBiome() != null) {
            tiles = rule.getBiome().getTiles();
        } else {
            tiles = world.getTileMap();
        }

        if (tiles == null) {
            return;
        }

        // randomize tile order
        Collections.shuffle(tiles, rand);
        int max = rule.getMax();
        int min = rule.getMin();
        double chance = rule.getChance();

        Iterator<Tile> tileItr = tiles.iterator();

        // Try and place down the minimum number of elements
        int placedDown = 0;

        while (tileItr.hasNext() && (placedDown < min)) {
            Tile nextTile = tileItr.next();
            if (nextTile.isObstructed()) {
                continue;
            }
            world.addEntity((T) entity.newInstance(nextTile));
            placedDown++;
        }

        // Try to place down the remainder of the tiles up to max tiles
        while (tileItr.hasNext() && (placedDown < max)) {
            Tile nextTile = tileItr.next();
            if (nextTile.isObstructed()) {
                continue;
            }

            if ((rand.nextDouble() < chance)) {
                world.addEntity((T) entity.newInstance(nextTile));
                placedDown++;
            }
        }

        return;
    }

    public static <T extends StaticEntity> void spawnPerlin(T entity, EntitySpawnRule rule) {
        AbstractWorld world = GameManager.get().getWorld();
        List<Tile> tiles = null;
        Random rand = new Random();

        if (rule.getBiome() != null) {
            tiles = rule.getBiome().getTiles();
        } else {
            tiles = world.getTileMap();
        }

        if (tiles == null) {
            return;
        }

        // randomize tile order
        Collections.shuffle(tiles, rand);
        int max = rule.getMax();
        int min = rule.getMin();

        Iterator<Tile> tileItr = tiles.iterator();

        // Try and place down the minimum number of elements
        int placedDown = 0;

        while (tileItr.hasNext() && (placedDown < min)) {
            Tile nextTile = tileItr.next();
            if (nextTile.isObstructed()) {
                continue;
            }
            world.addEntity((T) entity.newInstance(nextTile));
            placedDown++;
        }

        // Try to place down the remainder of the tiles up to max tiles
        while (tileItr.hasNext() && (placedDown < max)) {
            Tile nextTile = tileItr.next();

            if (nextTile.isObstructed()) {
                continue;
            }

            // Get the perlin noise value of the tile and apply the perlin map
            SpawnControl perlinMap = rule.getAdjustMap();
            double adjustedProb = perlinMap.probabilityMap(nextTile.getPerlinValue());

            if (rand.nextDouble() < adjustedProb) {
                world.addEntity((T) entity.newInstance(nextTile));
                placedDown++;
            }
        }

        return;
    }

    /**
     * Randomly distributes an entity with a given spawn rule
     *
     * @param entity The entity to copied and distributed
     * @param rule   A spawn rule, which specifies how the entity will be
     *               distributed example rules are chance, min/max, next to, a
     *               combination of these, e.g.
     * @param <T>    T must extend StaticEntity and have .newInstance inherited
     */
    public static <T extends StaticEntity> void spawnEntities(T entity, EntitySpawnRule rule) {

        if (rule.getUsePerlin()) {
            spawnPerlin(entity, rule);
        } else {
            spawnUniform(entity, rule);
        }

        return;
    }

    /**
     * Does entity placing with a simple probability, with no need for a
     * EntitySpawnRule
     *
     * @param entity Entity to be copied and inserted
     * @param chance probability that the entity will be in a given tile
     * @param <T>    T must extend StaticEntity and have .newInstance inherited
     * @param biome  specified biome to spawn in, null for no specification
     */
    public static <T extends StaticEntity, B extends AbstractBiome> void spawnEntities(T entity, double chance) {
        EntitySpawnRule spawnRule = new EntitySpawnRule(chance);
        spawnEntities(entity, spawnRule);
    }
}
