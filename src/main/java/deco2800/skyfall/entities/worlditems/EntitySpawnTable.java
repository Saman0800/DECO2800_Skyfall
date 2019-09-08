package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.managers.GameManager;
import java.util.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Date;

import java.lang.Math;

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
        World world = GameManager.get().getWorld();
        world.addEntity((T) entity.newInstance(tile));
    }

    /**
     * Adjusts the chance of an entity spawning based on the number of neighbors the
     * tile already has.
     * 
     * @param rule          The EntitySpawn that holds the characteristics of the
     *                      placement of the static entity
     * @param tile          The tile the new entity will occupy
     * @param currentChance The current chance the entity has of spawning on the
     *                      tile
     */
    private static double adjustChanceAdjacent(EntitySpawnRule rule, Tile nextTile, double currentChance) {

        double adjustmentFactor = Math.pow(rule.getLimitAdjacentValue(), (double) nextTile.getNeighbours().size());

        return currentChance / adjustmentFactor;

    }

    /**
     * Places down a entity uniformly into the world.
     * 
     * @param <T>      T must extend StaticEntity and have .newInstance inherited
     * @param entity   The entity to duplicate onto the tile
     * @param rule     The EntitySpawn that holds the characteristics of the
     *                 placement of the static entity
     * @param nextTile The tile on which to place the static entity
     * @param randGen  A random number generator that will create a random number
     *                 that is compared to the uniform probability.
     */
    public static <T extends StaticEntity> void placeUniform(T entity, EntitySpawnRule rule, Tile nextTile,
            Random randGen, World world) {

        // Get the uniform chance from the rule
        double chance = rule.getChance();

        if (rule.getLimitAdjacent()) {
            chance = adjustChanceAdjacent(rule, nextTile, chance);
        }

        if ((randGen.nextDouble() < chance)) {
            T newEntity = (T) entity.newInstance(nextTile);
            int renderOrder = (int) (nextTile.getRow() * -2.0);
            newEntity.setRenderOrder(renderOrder);
            world.addEntity((T) newEntity);
        }

        return;
    }

    /**
     * Places down a entity using a the perlin noise value of the tile.
     * 
     * @param <T>      T must extend StaticEntity and have .newInstance inherited
     * @param entity   The entity to duplicate onto the tile
     * @param rule     The EntitySpawn that holds the characteristics of the
     *                 placement of the static entity
     * @param nextTile The tile on which to place the static entity
     * @param randGen  A random number generator that will create a random number
     *                 that is compared to the adjusted probability.
     */
    public static <T extends StaticEntity> void placePerlin(T entity, EntitySpawnRule rule, Tile nextTile,
            Random randGen, World world) {

        // Get the perlin noise value of the tile and apply the perlin map
        double noise = rule.getNoiseGenerator().getOctavedPerlinValue(nextTile.getRow(), nextTile.getCol());
        SpawnControl perlinMap = rule.getAdjustMap();
        double adjustedProb = perlinMap.probabilityMap(noise);

        if (rule.getLimitAdjacent()) {
            adjustedProb = adjustChanceAdjacent(rule, nextTile, adjustedProb);
        }

        if (randGen.nextDouble() < adjustedProb) {
            T newEntity = (T) entity.newInstance(nextTile);
            int renderOrder = (int) (nextTile.getRow() * -2.0);
            newEntity.setRenderOrder(renderOrder);
            world.addEntity((T) newEntity);
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
    public static <T extends StaticEntity> void spawnEntities(T entity, EntitySpawnRule rule, World world) {

        List<Tile> tiles = null;
        // Use the current time as a seed
        Random rand = new Random((new Date()).getTime());

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

        while (tileItr.hasNext() && (placedDown <= min)) {
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

            // Check if we are using perlin or uniform placement.
            if (rule.getUsePerlin()) {
                placePerlin(entity, rule, nextTile, rand, world);
            } else {
                placeUniform(entity, rule, nextTile, rand, world);
            }

            placedDown++;
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
    public static <T extends StaticEntity, B extends AbstractBiome> void spawnEntities(T entity, double chance,
            World world) {
        EntitySpawnRule spawnRule = new EntitySpawnRule(chance);
        spawnEntities(entity, spawnRule, world);
    }
}
