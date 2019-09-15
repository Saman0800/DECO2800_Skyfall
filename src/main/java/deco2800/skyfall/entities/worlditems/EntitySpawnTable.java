package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.managers.GameManager;

import java.lang.Math;
import java.util.function.Function;

/**
 * Manages spawn tabling (randomly placing items into the world) Uses
 * EntitySpawnRule for precise spawning however, simpler methods exist that will
 * handle this for you
 */
public class EntitySpawnTable {
    /**
     * Simple static method for placing static items. Takes the given entity and
     * places a deep copy within the world at a given tile
     *
     * @param tile   The tile the new entity will occupy
     * @param entity The entity to be deep copied
     * @param <T>    T must extend StaticEntity and have .newInstance inherited
     */
    public static <T extends StaticEntity> void placeEntity(T entity, Tile tile) {
        World world = GameManager.get().getWorld();
        world.addEntity(entity.newInstance(tile));
    }

    /**
     * Adjusts the chance of an entity spawning based on the number of neighbors the
     * tile already has.
     * 
     * @param rule          The EntitySpawn that holds the characteristics of the
     *                      placement of the static entity
     * @param nextTile      The tile the new entity will occupy
     * @param currentChance The current chance the entity has of spawning on the
     *                      tile
     */
    private static double adjustChanceAdjacent(EntitySpawnRule rule, Tile nextTile, double currentChance) {

        // Tiles always have 6 neighbours, so always just put it to the power of 6 instead of checking the number of
        // neighbours each time.
        double adjustmentFactor = rule.getLimitAdjacentValue();
        adjustmentFactor =
                adjustmentFactor * adjustmentFactor * adjustmentFactor * adjustmentFactor * adjustmentFactor *
                        adjustmentFactor;

        return currentChance / adjustmentFactor;

    }

    /**
     * Places down a entity uniformly into the world.
     *
     * @param <T>         T must extend StaticEntity and have .newInstance inherited
     * @param newInstance A function which creates a new instance to place
     * @param rule        The EntitySpawn that holds the characteristics of the placement of the static entity
     * @param nextTile    The tile on which to place the static entity
     */
    public static <T extends StaticEntity> void placeUniform(Function<Tile, StaticEntity> newInstance,
                                                             EntitySpawnRule rule, Tile nextTile,
                                                             World world) {

        // Get the uniform chance from the rule
        double chance = rule.getChance();

        if (rule.getLimitAdjacent()) {
            chance = adjustChanceAdjacent(rule, nextTile, chance);
        }

        if (world.getStaticEntityNoise().getOctavedPerlinValue(nextTile.getCol(), nextTile.getRow()) < chance) {
            StaticEntity newEntity = newInstance.apply(nextTile);
            int renderOrder = (int) (nextTile.getRow() * -2.0);
            newEntity.setRenderOrder(renderOrder);
            world.addEntity(newEntity);
        }
    }

    /**
     * Places down a entity using a the perlin noise value of the tile.
     *
     * @param <T>         T must extend StaticEntity and have .newInstance inherited
     * @param newInstance A function which creates a new instance to place
     * @param rule        The EntitySpawn that holds the characteristics of the placement of the static entity
     * @param nextTile    The tile on which to place the static entity
     */
    public static <T extends StaticEntity> void placePerlin(Function<Tile, StaticEntity> newInstance,
                                                            EntitySpawnRule rule, Tile nextTile,
                                                            World world) {

        // Get the perlin noise value of the tile and apply the perlin map
        double noise = rule.getNoiseGenerator().getOctavedPerlinValue(nextTile.getRow(), nextTile.getCol());
        SpawnControl perlinMap = rule.getAdjustMap();
        double adjustedProb = perlinMap.probabilityMap(noise);

        if (rule.getLimitAdjacent()) {
            adjustedProb = adjustChanceAdjacent(rule, nextTile, adjustedProb);
        }

        if (world.getStaticEntityNoise().getOctavedPerlinValue(nextTile.getCol(), nextTile.getRow()) < adjustedProb) {
            StaticEntity newEntity = newInstance.apply(nextTile);
            int renderOrder = (int) (nextTile.getRow() * -2.0);
            newEntity.setRenderOrder(renderOrder);
            world.addEntity(newEntity);
        }
    }

    /**
     * Randomly distributes an entity with a given spawn rule
     *
     * @param rule        A spawn rule, which specifies how the entity will be distributed example rules are chance,
     *                    min/max, next to, a combination of these, e.g.
     * @param <T>         T must extend StaticEntity and have .newInstance inherited
     */
    public static <T extends StaticEntity> void spawnEntity(EntitySpawnRule rule, World world, Tile tile) {
        if (tile == null) {
            return;
        }

        if (tile.isObstructed()) {
            return;
        }

        // Check if we are using perlin or uniform placement.
        if (rule.getUsePerlin()) {
            placePerlin(rule.getNewInstance(), rule, tile, world);
        } else {
            placeUniform(rule.getNewInstance(), rule, tile, world);
        }
    }

    // TODO:Ontonator Consider changing the signiature of this method and/or deprecating it.

    /**
     * Does entity placing with a simple probability, with no need for a EntitySpawnRule
     *
     * @param newInstance a function which creates a new instance to place
     * @param chance      probability that the entity will be in a given tile
     * @param <T>         T must extend StaticEntity and have .newInstance inherited
     */
    public static <T extends StaticEntity, B extends AbstractBiome> void spawnEntity(
            Function<Tile, StaticEntity> newInstance, double chance, World world, Tile tile) {
        EntitySpawnRule spawnRule = new EntitySpawnRule(newInstance, chance);
        EntitySpawnTable.spawnEntity(spawnRule, world, tile);
    }
}
