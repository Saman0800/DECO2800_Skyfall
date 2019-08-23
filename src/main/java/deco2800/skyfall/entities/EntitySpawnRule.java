package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.biomes.AbstractBiome;
import com.esotericsoftware.kryo.NotNull;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * Used to set conditions about an entity template spawning distributions in
 * EntitySpawnTable Example conditions include probablity, min to max, must be
 * beside another, which biomes Combinations of these are also possible
 */
public class EntitySpawnRule {
    // chance to spawn in tile, value of zero represents uninitialised
    private double chance = 0.1;
    // minimum number to spawn
    private int min = 0;
    // maximum number to spawn
    private int max = Integer.MAX_VALUE;
    // Determines if we are using the perlin noise value to use as a probability
    // when placing down tiles
    private boolean usePerlin = false;
    // An adjustment factor to use
    // The biome name, for no biome, default for no selection is ""
    private AbstractBiome biome = null;
    // The default spawn controller is just an identity map
    private SpawnControl map = (double x) -> x;

    /**
     * Sets spawn rule based on chance. This distributes the entities in a uniform
     * manner.
     * 
     * @param chance The likelihood of spawning an ent in a given tile of the world
     */
    public EntitySpawnRule(double chance) {
        setChance(chance);
    }

    /**
     * creates spawn rule based on min max distribution will be ~U(min, max)
     * 
     * @param min minimum number of entities to spawn into the world inclusive
     * @param max maximum number of entities to spawn into the world inclusive
     */
    public EntitySpawnRule(int min, int max) {
        setChance(-1); // entity spawn handler will deal with this
        setMin(min);
        setMax(max);
    }

    /**
     * creates spawn rule based on chance, but will strictly enforce min and max
     * values probability will be but guaranteed at least min and no more than max
     * 
     * @param min minimum number of entities to spawn into the world inclusive
     * @param max maximum number of entities to spawn into the world inclusive
     */
    public EntitySpawnRule(double chance, int min, int max) {
        setChance(chance); // entity spawn handler will deal with this
        setMin(min);
        setMax(max);
    }

    public EntitySpawnRule(double chance, int min, int max, AbstractBiome biome) {
        this(chance, min, max);
        this.biome = biome;
    }

    public EntitySpawnRule(AbstractBiome biome, boolean usePerlin) {
        this.biome = biome;
        this.usePerlin = usePerlin;
    }

    public EntitySpawnRule(AbstractBiome biome, boolean usePerlin, SpawnControl map) {
        this(biome, usePerlin);
        this.map = map;
    }

    public EntitySpawnRule(int min, int max, AbstractBiome biome, boolean usePerlin) {
        this(biome, usePerlin);
        setMin(min);
        setMax(max);
    }

    public EntitySpawnRule(int min, int max, AbstractBiome biome, boolean usePerlin, SpawnControl map) {
        this(min, max, biome, usePerlin);
        this.map = map;
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
     * @param min This minimum is inclusive and stricty enforced
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
     * @param max This maximum is inclusive and stricty enforced
     */
    public void setMax(int max) {
        this.max = max;
    }

    public AbstractBiome getBiome() {
        return biome;
    }

    public boolean getUsePerlin() {
        return this.usePerlin;
    }

    public SpawnControl getAdjustMap() {
        return this.map;
    }
}
