package deco2800.skyfall.entities;

/**
 * Used to set rules about an entity template in EntitySpawnTable
 * Example rules include probablity, min to max and must eb beside another
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
     * @param chance The likelihood of spawning in a given tile
     */
    public EntitySpawnRule(float chance) {
        setChance(chance);
    }

    /**
     * sets spawn rule based on min max
     * probability will be ~U(min, max) but guaranteed at least min and no more than max
     * min == max is valid
     */
    public EntitySpawnRule(int min, int max) {
        setChance(-1);
        setMin(min);
        setMax(max);
    }

    //A value of -1 means probability is set by other parameters
    public float getChance() {
        return chance;
    }

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
