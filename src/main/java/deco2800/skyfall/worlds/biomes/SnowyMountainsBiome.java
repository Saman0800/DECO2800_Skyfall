package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Snowy Mountains biome
 */
public class SnowyMountainsBiome extends AbstractBiome {
    public static final String NAME = "snowy_mountains";

    /**
     * Constructor for the snowy mountains biome
     */
    public SnowyMountainsBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.5);
    }

    /**
     * Loads a biome from a memento
     * 
     * @param memento The memento that holds the save data
     */
    public SnowyMountainsBiome(AbstractBiomeMemento memento) {
        super(memento, memento.noiseGeneratorSeed, 3, 60, 0.5);
    }

    @Override
    public void setTileTexture(Tile tile) {
        super.setTileTexture(tile, "sMountain1", "sMountain2", "sMountain3");
    }
}
