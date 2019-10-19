package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Swamp biome
 */
public class SwampBiome extends AbstractBiome{
    public static final String NAME = "swamp";

    /**
     * Constructor for the SwampBiome
     */
    public SwampBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.5);
    }

    /**
     * Loads a biome from a memento
     * @param memento The memento that holds the save data
     */
    public SwampBiome(AbstractBiomeMemento memento){
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 3, 60, 0.5);
    }
}
