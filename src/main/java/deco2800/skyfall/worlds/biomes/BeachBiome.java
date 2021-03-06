package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;

public class BeachBiome extends AbstractBiome {
    public static final String NAME = "beach";

    /**
     * Constructor for a biome being loaded
     *
     * @param memento the memento this biome is being loaded from
     */
    public BeachBiome(AbstractBiomeMemento memento) {
        super(memento);
        Random random = new Random();
        textureGenerator = new NoiseGenerator(random.nextInt(), 3, 40, 0.7);
    }

    /**
     * Constructor for a BeachBiome
     *
     * @param parentBiome The biome that the biome lives in, null if the biome has no parent
     */
    public BeachBiome(AbstractBiome parentBiome, Random random) {
        super(NAME, parentBiome);
        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 40, 0.7);
    }

    @Override
    public void setTileTexture(Tile tile) {
        tile.setTexture("desert1");
    }
}
