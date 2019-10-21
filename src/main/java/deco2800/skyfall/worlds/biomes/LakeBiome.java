package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Lake biome that is used for the lakes
 */
public class LakeBiome extends AbstractBiome {
    public static final String NAME = "lake";

    /**
     * Constructor for a Biome
     */
    public LakeBiome(AbstractBiome parentBiome, Random random) {
        super(NAME, parentBiome);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 40, 0.7);
    }

    /**
     * Loads a biome from a memento
     * 
     * @param memento The memento that holds the save data
     */
    public LakeBiome(AbstractBiomeMemento memento) {
        super(memento, memento.noiseGeneratorSeed, 3, 40, 0.7);
    }

    @Override
    public void setTileTexture(Tile tile) {
        super.setTileTexture(tile, "lake1.1", "lake1.2", "lake1.3");
    }
}
