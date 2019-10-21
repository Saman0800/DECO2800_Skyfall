package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Ocean Biome
 */
public class OceanBiome extends AbstractBiome {
    public static final String NAME = "ocean";

    /**
     * Constructor for a Biome
     */
    public OceanBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 5, 160, 0.9);
    }

    /**
     * Loads a biome from a memento
     *
     * @param memento The memento that holds the save data
     */
    public OceanBiome(AbstractBiomeMemento memento) {
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 5, 160, 0.9);
    }

    @Override
    public void setTileTexture(Tile tile) {
        tile.setTexture("ocean_1");
    }
}
