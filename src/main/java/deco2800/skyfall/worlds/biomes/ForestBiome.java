package deco2800.skyfall.worlds.biomes;

import java.util.Random;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

/**
 * Forest Biome
 */
public class ForestBiome extends AbstractBiome {
    public static final String NAME = "forest";

    /**
     * Constructor for the ForestBiome
     */
    public ForestBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.4);
    }

    /**
     * Loads a biome from a memento
     * 
     * @param memento The memento that holds the save data
     */
    public ForestBiome(AbstractBiomeMemento memento) {
        super(memento, memento.noiseGeneratorSeed, 3, 60, 0.4);
    }

    @Override
    public void setTileTexture(Tile tile) {
        super.setTileTexture(tile, "forest_1", "forest_2", "forest_3");
    }

}
