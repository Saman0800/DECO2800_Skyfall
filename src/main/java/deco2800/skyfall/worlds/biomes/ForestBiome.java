package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Forest Biome
 */
public class ForestBiome extends AbstractBiome {

    /**
     * Constructor for the ForestBiome
     */
    public ForestBiome(Random random) {
        super("forest", null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.4);
    }

    /**
     * Loads a biome from a memento
     * @param memento The memento that holds the save data
     */
    public ForestBiome(AbstractBiomeMemento memento){
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 3, 60, 0.4);
    }

    @Override
    public void setTileTexture(Tile tile) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("forest_1");
        textures.add("forest_2");
        textures.add("forest_3");

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
