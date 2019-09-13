package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.Random;

public class BeachBiome extends AbstractBiome {
    /**
     * Constructor for a BeachBiome
     *
     * @param parentBiome The biome that the biome lives in, null if the biome has no parent
     */
    public BeachBiome(AbstractBiome parentBiome, Random random) {
        super("beach", parentBiome);
    }

    @Override
    public void setTileTexture(Tile tile) {
        tile.setTexture("desert1");
    }
}
