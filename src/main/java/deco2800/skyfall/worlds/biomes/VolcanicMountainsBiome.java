package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Volcanic Mountains Biome
 */
public class VolcanicMountainsBiome extends AbstractBiome {
    public static final String NAME = "volcanic_mountains";

    /**
     * Constructor for the VolcanicMountainsBiome
     */
    public VolcanicMountainsBiome(Random random){
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.5);
    }

    /**
     * Loads a biome from a memento
     * @param memento The memento that holds the save data
     */
    public VolcanicMountainsBiome(AbstractBiomeMemento memento){
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 3, 60, 0.5);
    }
}
