package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.Random;
import java.util.function.Function;

/**
 * Used to set conditions about an entity template spawning distributions in
 * EntitySpawnTable Example conditions include probability, min to max, must be
 * beside another, and which biomes the entities are to be placed in.
 */
public class EntitySpawnRule {
    /**
     * If a uniform mode is used, the chance parameter indicates the probability
     * that the entity will be place on a tile without any biases for biome type.
     */
    private double chance = 0.1;

    /**
     * Determines if we are using a perlin noise value to use as a probability when
     * placing down tiles
     */
    private boolean usePerlin = false;

    /**
     * A seed to use for all the perlin noise
     */
    private static long seed = 0;

    /**
     * If the usePerlin parameter is set to true then a perlin noise generator will
     * be used to dictate the probability of an entity spawn on a tile. If no noise
     * generator is provided this will become the default noise generator.
     */
    NoiseGenerator noiseGenerator = null;

    /**
     * A lambda expression can be used to adjust the probabilities of the noise
     * generator. If no map is provided on instantion a default cubic function will
     * be used.
     */
    private SpawnControl map = (double x) -> x * x * x;

    /**
     * A boolean value that if true limits the amount of entities spawning next to
     * each other.
     */
    private boolean limitAdjacent = false;

    /**
     * A parameter to adjust spawning of adjacent entities when limitAdjacent is set
     * to true. The larger the value the less likely it is for entities to spawn
     * next to each other.
     */
    private double limitAdjacentValue = 4.0;

    /**
     * A function that returns a new instance to place.
     */
    private Function<Tile, StaticEntity> newInstance;

    /**
     * A unique index for this rule that is consistent across runs for any given seed.
     */
    private int index;

    /**
     * Sets spawn rule based on chance. This distributes the entities in a uniform
     * manner.
     *
     * @param newInstance The method to use to generate a new instance
     * @param index       The unique index of the rule
     * @param chance The likelihood of spawning an ent in a given tile of the world
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, int index, double chance) {
        this.newInstance = newInstance;
        this.index = index;
        this.chance = chance;
    }

    /**
     * A constructor for the EntitySpawnRule.
     *
     * @param newInstance The method to use to generate a new instance
     * @param index       The unique index of the rule
     * @param usePerlin A boolean value to dictate if the perlin noise value of the
     *                  tile is to used to determine the likeliness of a entity to
     *                  be placed down on a tile.
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, int index, boolean usePerlin) {
        this.newInstance = newInstance;
        this.index = index;
        this.usePerlin = usePerlin;

        if (usePerlin) {
            noiseGenerator = new NoiseGenerator(new Random(EntitySpawnRule.seed).nextLong(), 5, 20, 0.4);
        }
    }

    /**
     * A constructor for the EntitySpawnRule.
     *
     * @param newInstance The method to use to generate a new instance
     * @param index       The unique index of the rule
     * @param usePerlin   A boolean value to dictate if the perlin noise value of the tile is to used to determine the
     *                    likeliness of a entity to be placed down on a tile.
     * @param map         A lambda experssion to adjust the perlin noise value when using it as the likeliness to spawn
     *                    an entity
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, int index, boolean usePerlin, SpawnControl map) {
        this(newInstance, index, usePerlin);
        this.map = map;
    }

    /**
     * Sets the seed to use for the Perlin noise values.
     * 
     * @param newSeed The new seed value.
     */
    public static void setNoiseSeed(long newSeed) {
        EntitySpawnRule.seed = newSeed;
    }

    /**
     * @return get chance of the spawn rule, a value of -1 is returned when other
     *         spawn rules are
     */
    public double getChance() {
        return chance;
    }

    /**
     * @param chance sets chance, negative values will cause other parameters to
     *               determine distribution
     */
    public void setChance(double chance) {
        this.chance = chance;
    }

    /**
     * @return Returns true if perlin noise is being used as placement probabilites
     */
    public boolean getUsePerlin() {
        return this.usePerlin;
    }

    /**
     * @return Returns the map used to adjust the perlin noise probabilites.
     */
    public SpawnControl getAdjustMap() {
        return this.map;
    }

    /**
     * @return Returns true if adjacent spawn is being limited.
     */
    public boolean getLimitAdjacent() {
        return this.limitAdjacent;
    }

    /**
     * Sets the adjacency limiting value for the rule.
     * 
     * @param limitAdjacent A boolean value that dictates if adjacency limiting is
     *                      to be used or not.
     */
    public void setLimitAdjacent(boolean limitAdjacent) {
        this.limitAdjacent = limitAdjacent;
    }

    /**
     * @return Returns the current adjacent limiting value.
     */
    public double getLimitAdjacentValue() {
        return this.limitAdjacentValue;
    }

    /**
     * @param limitAdjacentValue The new limiting value. The larger the value the
     *                           less likely it is for entities to spawn next to
     *                           each other.
     */
    public void setLimitAdjacentValue(double limitAdjacentValue) {
        this.limitAdjacentValue = limitAdjacentValue;
    }

    /**
     * @return Returns the current generator used to create noise values.
     */
    public NoiseGenerator getNoiseGenerator() {
        return this.noiseGenerator;
    }

    /**
     * Sets a new noise generator to create probabilities.
     * 
     * @param noiseGen The new noise generator to create probabilities.
     */
    public void setNoiseGenerator(NoiseGenerator noiseGen) {
        this.noiseGenerator = noiseGen;
    }

    /**
     * Gets the function that returns a new instance to place.
     * @return gets the function that returns a new instance to place
     */
    public Function<Tile, StaticEntity> getNewInstance() {
        return newInstance;
    }

    /**
     * Sets the function that returns a new instance to place.
     * @return sets the function that returns a new instance to place
     */
    public void setNewInstance(
            Function<Tile, StaticEntity> newInstance) {
        this.newInstance = newInstance;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
