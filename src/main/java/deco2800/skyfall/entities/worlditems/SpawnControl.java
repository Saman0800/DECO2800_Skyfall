package deco2800.skyfall.entities.worlditems;

/**
 * This interface is used the control the spawn level when using the perlin
 * noise to dispersing items.
 */
public interface SpawnControl {
    // TODO This Javadoc doesn't seem right...
    /**
     * This function will create a deep copy of the class it is sitting by calling
     * the constructor for the class it is sitting. The only parameter that changes
     * between the original copy and the new copy of the instance is the tile that
     * the instance has been designated to.
     * 
     * @return The duplicated instance with the new tile position.
     */
    public double probabilityMap(double noiseValue);

}