package deco2800.skyfall.entities;

/**
 * This interface is used the control the spawn level when using the perlin
 * noise to dispersing items.
 */
public interface SpawnControl {
    /**
     * This function will create a deep copy of the class it is sitting by calling
     * the constructor for the class it is sitting. The only parameter that changes
     * between the original copy and the new copy of the instance is the tile that
     * the instance has been designated to.
     * 
<<<<<<< HEAD
     * @param tile The new tile on which the duplicated instance will be placed on.
=======
>>>>>>> master
     * @return The duplicated instance with the new tile position.
     */
    public double probabilityMap(double noiseValue);

}