package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Snowy Mountains biome
 */
public class SnowyMountainsBiome extends AbstractBiome {
    private NoiseGenerator textureGenerator;

    /**
     * Constructor for the snowy mountains biome
     */
    public SnowyMountainsBiome(Random random) {
        super("snowy_mountains", null);

        textureGenerator = new NoiseGenerator(random, 3, 60, 0.5);
    }

    @Override
    public void setTileTexture(Tile tile) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("snowy_mountains_1");
        textures.add("snowy_mountains_2");
        textures.add("snowy_mountains_3");

        double perlinValue =
                NoiseGenerator.fade(textureGenerator.getOctavedPerlinValue(tile.getCol(), tile.getRow()), 2);
        int adjustedPerlinValue = (int) Math.floor(perlinValue * textures.size());
        if (adjustedPerlinValue >= textures.size()) {
            adjustedPerlinValue = textures.size() - 1;
        }
        // TODO Is `setPerlinValue` still required?
        tile.setPerlinValue(adjustedPerlinValue);
        tile.setTexture(textures.get(adjustedPerlinValue));
    }
}
