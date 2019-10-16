package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.ArrayList;
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

    @Override
    public void setTileTexture(Tile tile) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("desert_1");
        textures.add("desert_2");
        textures.add("desert_3");

        double perlinValue =
                NoiseGenerator.fade(textureGenerator.getOctavedPerlinValue(tile.getCol(), tile.getRow()), 2);
        int adjustedPerlinValue = (int) Math.floor(perlinValue * textures.size());
        if (adjustedPerlinValue >= textures.size()) {
            adjustedPerlinValue = textures.size() - 1;
        }
        tile.setPerlinValue(adjustedPerlinValue);
        tile.setTexture(textures.get(adjustedPerlinValue));
    }
}
