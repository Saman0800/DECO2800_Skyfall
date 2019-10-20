package deco2800.skyfall.worlds.biomes;

import java.util.Random;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

/**
 * Volcanic Mountains Biome
 */
public class VolcanicMountainsBiome extends AbstractBiome {
    public static final String NAME = "volcanic_mountains";

    /**
     * Constructor for the VolcanicMountainsBiome
     */
    public VolcanicMountainsBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.5);
    }

    /**
     * Loads a biome from a memento
     * 
     * @param memento The memento that holds the save data
     */
    public VolcanicMountainsBiome(AbstractBiomeMemento memento) {
        super(memento, memento.noiseGeneratorSeed, 3, 60, 0.5);
    }

    @Override
    public void setTileTexture(Tile tile) {
        super.setTileTexture(tile, "vmountain1.1", "vmountain1.2", "vmountain1.3");
    }
}
