package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

/**
 * Biomes that gets assigned to rivers on the map
 */
public class RiverBiome extends AbstractBiome {
    public static final String NAME = "river";

    /**
     * Constructor for the RiverBiome
     */
    public RiverBiome(AbstractBiome parentBiome, Random random) {
        super(NAME, parentBiome);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 40, 0.7);
    }

    /**
     * Loads a biome from a memento
     * 
     * @param memento The memento that holds the save data
     */
    public RiverBiome(AbstractBiomeMemento memento) {
        super(memento, memento.noiseGeneratorSeed, 3, 60, 0.7);
    }

    @Override
    public void setTileTexture(Tile tile) {
        super.setTileTexture(tile, "lake1.1", "lake1.2", "lake1.3");
    }
}
