package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.Random;
import java.util.function.Function;

import com.esotericsoftware.kryo.NotNull;
import jdk.nashorn.internal.ir.annotations.Immutable;

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
     * The minimum number of items that need to be spawned within the world.
     */
    private int min = 1;

    /**
     * The maximum number of items that need to be spawned within the world.
     */
    private int max = Integer.MAX_VALUE;

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
     * The biome in which the entities are to spawn. If no biome is specified then
     * the entity will spawn in every biome.
     */
    private AbstractBiome biome = null;

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
     * Sets spawn rule based on chance. This distributes the entities in a uniform
     * manner.
     * 
     * @param chance The likelihood of spawning an ent in a given tile of the world
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, double chance) {
        setNewInstance(newInstance);
        setChance(chance);
    }

    /**
     * Creates spawn rule based on min max distribution will be ~U(min, max)
     * 
     * @param min minimum number of entities to spawn into the world inclusive
     * @param max maximum number of entities to spawn into the world inclusive
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, int min, int max) {
        setNewInstance(newInstance);
        setChance(-1); // entity spawn handler will deal with this
        setMin(min);
        setMax(max);
    }

    /**
     * Creates spawn rule based on chance, but will strictly enforce min and max
     * values probability will be but guaranteed at least min and no more than max
     * 
     * @param min minimum number of entities to spawn into the world inclusive
     * @param max maximum number of entities to spawn into the world inclusive
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, double chance, int min, int max) {
        setNewInstance(newInstance);
        setChance(chance); // entity spawn handler will deal with this
        setMin(min);
        setMax(max);
    }

    /**
     * A constructor for the EntitySpawnRule.
     * 
     * @param chance The chance that a tile will uniformly be placed on a tile.
     * @param biome  The biome the tile will be placed in.
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, double chance, AbstractBiome biome) {
        this.newInstance = newInstance;
        this.chance = chance;
        this.biome = biome;
    }

    /**
     * An overloaded constructor for EntitySpawnRule that is more efficient
     *
     * @param chance Likelihood that a tile of a given biome contains a tile
     * @param min    Minimum count
     * @param max    Maximum count
     * @param biome  A reference to a biome
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, double chance, int min, int max, AbstractBiome biome) {
        this(newInstance, chance, min, max);
        this.biome = biome;
    }

    /**
     * A constructor for the EntitySpawnRule.
     * 
     * @param biome     The biome the tile will be placed in.
     * @param usePerlin A boolean value to dictate if the perlin noise value of the
     *                  tile is to used to determine the likeliness of a entity to
     *                  be placed down on a tile.
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, AbstractBiome biome, boolean usePerlin) {
        this.newInstance = newInstance;
        this.biome = biome;
        this.usePerlin = usePerlin;

        if (usePerlin) {
            noiseGenerator = new NoiseGenerator(new Random(EntitySpawnRule.seed), 5, 20, 0.4);
        }
    }

    /**
     * A constructor for the EntitySpawnRule.
     * 
     * @param biome     The biome the tile will be placed in.
     * @param usePerlin A boolean value to dictate if the perlin noise value of the
     *                  tile is to used to determine the likeliness of a entity to
     *                  be placed down on a tile.
     * @param map       A lambda experssion to adjust the perlin noise value when
     *                  using it as the likeliness to spawn an entity
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, AbstractBiome biome, boolean usePerlin, SpawnControl map) {
        this(newInstance, biome, usePerlin);
        this.map = map;
    }

    /**
     * A constructor for the EntitySpawnRule.
     * 
     * @param min       minimum number of entities to spawn into the world inclusive
     * @param max       maximum number of entities to spawn into the world inclusive
     * @param biome     The biome the tile will be placed in.
     * @param usePerlin A boolean value to dictate if the perlin noise value of the
     *                  tile is to used to determine the likeliness of a entity to
     *                  be placed down on a tile.
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, int min, int max, AbstractBiome biome, boolean usePerlin) {
        this(newInstance, biome, usePerlin);
        setMin(min);
        setMax(max);
    }

    /**
     * A constructor for the EntitySpawnRule.
     * 
     * @param min       minimum number of entities to spawn into the world inclusive
     * @param max       maximum number of entities to spawn into the world inclusive
     * @param biome     The biome the tile will be placed in.
     * @param usePerlin A boolean value to dictate if the perlin noise value of the
     *                  tile is to used to determine the likeliness of a entity to
     *                  be placed down on a tile.
     * @param map       A lambda experssion to adjust the perlin noise value when
     *                  using it as the likeliness to spawn an entity
     */
    public EntitySpawnRule(Function<Tile, StaticEntity> newInstance, int min, int max, AbstractBiome biome, boolean usePerlin, SpawnControl map) {
        this(newInstance, min, max, biome, usePerlin);
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
     * @return returns inclusive minimum number of entities this rule will allow to
     *         spawn
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min This minimum is inclusive and strictly enforced
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return returns inclusive maximum number of entities this rule will allow to
     *         spawn
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max This maximum is inclusive and strictly enforced
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return Returns the biome for this spawn rule
     */
    public AbstractBiome getBiome() {
        return biome;
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
}
