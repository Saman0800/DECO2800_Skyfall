package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;

import java.util.function.Function;

/**
 * Manages spawn tabling (randomly placing items into the world) Uses
 * EntitySpawnRule for precise spawning however, simpler methods exist that will
 * handle this for you
 */
public final class EntitySpawnTable {
    // Private constructor to prevent construction.
    private EntitySpawnTable() {
    }

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

        // Tiles always have 6 neighbours, so always just put it to the power of 6
        // instead of checking the number of
        // neighbours each time.
        double adjustmentFactor = rule.getLimitAdjacentValue();
        // FIXME:Ontonator Check that this actualy works with chunks.
        adjustmentFactor = Math.pow(adjustmentFactor,
                nextTile.getNeighbours().values().stream().filter(Tile::isObstructed).count());

        return currentChance / adjustmentFactor;

    }

    /**
     * <em>Approximately</em> normalises the static entity noise used for entity
     * spawn chance to a uniform distribution. This was obtained through
     * experimental methods and must be recalculated if the noise generator
     * parameters ever change.
     *
     * @param x the value to normalise
     *
     * @return the normalised value
     */
    public static double normalizeStaticEntityNoise(double x) {
        return 1 - 1 / (1 + Math.exp(23 * (x - 0.5)));
    }

    public static double getRandomValue(World world, EntitySpawnRule spawnRule, Tile tile) {
        return normalizeStaticEntityNoise(world.getStaticEntityNoise().getOctavedPerlinValue(
                tile.getCol() + spawnRule.getIndex() % 50, tile.getRow() + spawnRule.getIndex() % 100));
    }

    private static void placeWithChance(Function<Tile, StaticEntity> newInstance, EntitySpawnRule rule, Tile nextTile,
            World world, double chance) {
        if (rule.getLimitAdjacent()) {
            chance = adjustChanceAdjacent(rule, nextTile, chance);
        }

        if (getRandomValue(world, rule, nextTile) < chance) {
            StaticEntity newEntity = newInstance.apply(nextTile);
            int renderOrder = (int) (nextTile.getRow() * -2.0);
            newEntity.setRenderOrder(renderOrder);
            world.addEntity(newEntity);
        }
    }

    /**
     * Places down a entity uniformly into the world.
     *
     * @param newInstance A function which creates a new instance to place
     * @param rule        The EntitySpawn that holds the characteristics of the
     *                    placement of the static entity
     * @param nextTile    The tile on which to place the static entity
     */
    public static void placeUniform(Function<Tile, StaticEntity> newInstance, EntitySpawnRule rule, Tile nextTile,
            World world) {
        // Get the uniform chance from the rule
        double chance = rule.getChance();

        placeWithChance(newInstance, rule, nextTile, world, chance);
    }

    /**
     * Places down a entity using a the perlin noise value of the tile.
     *
     * @param newInstance A function which creates a new instance to place
     * @param rule        The EntitySpawn that holds the characteristics of the
     *                    placement of the static entity
     * @param nextTile    The tile on which to place the static entity
     */
    public static void placePerlin(Function<Tile, StaticEntity> newInstance, EntitySpawnRule rule, Tile nextTile,
            World world) {
        // Get the perlin noise value of the tile and apply the perlin map
        double noise = rule.getNoiseGenerator().getOctavedPerlinValue(nextTile.getRow(), nextTile.getCol());
        SpawnControl perlinMap = rule.getAdjustMap();
        double adjustedProb = perlinMap.probabilityMap(noise);

        placeWithChance(newInstance, rule, nextTile, world, adjustedProb);
    }

    /**
     * Randomly distributes an entity with a given spawn rule
     *
     * @param rule A spawn rule, which specifies how the entity will be distributed
     *             example rules are chance, min/max, next to, a combination of
     *             these, e.g.
     */
    public static void spawnEntity(EntitySpawnRule rule, World world, Tile tile) {
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
}
