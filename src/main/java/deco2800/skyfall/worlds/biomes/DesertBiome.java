package deco2800.skyfall.worlds.biomes;


import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Desert Biome
 */
public class DesertBiome extends AbstractBiome {
    public static final String NAME = "desert";

    /**
     * Constructor for the DesertBiome
     */
    public DesertBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextInt(), 4, 50, 0.5);
    }

    /**
     * Loads a biome from a memento
     * @param memento The memento that holds the save data
     */
    public DesertBiome(AbstractBiomeMemento memento){
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 4, 50, 0.5);
    }
}
