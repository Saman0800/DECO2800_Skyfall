package deco2800.skyfall.entities;

/**
 * Used to set conditions about an entity template spawning distributions in EntitySpawnTable
 * Example conditions include probablity, min to max, must be beside another, which biomes
 * Combinations of these are also possible
 */
public class EntitySpawnRule {
    //chance to spawn in tile, value of zero represents uninitialised
    private float chance = 0;
    //minimum number to spawn
    private int min = 0;
    //maximum number to spawn
    private int max = Integer.MAX_VALUE;

    /**
     * sets spawn rule based on chance
     * @param chance The likelihood of spawning an ent in a given tile of the world
     */
    public EntitySpawnRule(float chance) {
        setChance(chance);
    }

    /**
     * creates spawn rule based on min max
     * distribution will be ~U(min, max)
     * @param min minimum number of entities to spawn into the world inclusive
     * @param max maximum number of entities to spawn into the world inclusive
     */
    public EntitySpawnRule(int min, int max) {
        setChance(-1); //entity spawn handler will deal with this
        setMin(min);
        setMax(max);
    }

    /**
     * creates spawn rule based on chance, but will strictly enforce min and max values
     * probability will be but guaranteed at least min and no more than max
     * @param min minimum number of entities to spawn into the world inclusive
     * @param max maximum number of entities to spawn into the world inclusive
     */
    public EntitySpawnRule(float chance, int min, int max) {
        setChance(chance); //entity spawn handler will deal with this
        setMin(min);
        setMax(max);
    }

    /**
     * @return get chance of the spawn rule, a value of -1 is returned when other spawn rules are
     */
    public float getChance() {
        return chance;
    }

    /**
     * @param chance sets chance, negative values will cause other parameters to determine distribution
     */
    public void setChance(float chance) {
        this.chance = chance;
    }


    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
