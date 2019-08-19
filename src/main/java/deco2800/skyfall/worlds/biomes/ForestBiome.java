package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.generation.PerlinNoiseGenerator;
import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Forest Biome
 */
public class ForestBiome extends AbstractBiome {

    /**
     * Constructor for a Biome
     */
    public ForestBiome() {
        super("forest");
    }

    // TODO implement an algorithm that determines the ground patterns
    // TODO add seeding to the random generation so it can be tested
    // Likes grouped with likes
    /**
     * Method that will determine the textures of the forest biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
//        textures.add("grass_0");
//        textures.add("grass_1");
//        textures.add("grass_2");
        textures.add("grass_3");
        textures.add("grass_4");
        textures.add("grass_5");
        textures.add("grass_6");
//        textures.add("grass_4");

        //Perlin noise generation
        PerlinNoiseGenerator perlinNoise = new PerlinNoiseGenerator(random);
        // perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 2, 30, 0.5);
        perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 2, 60, 0.2);
        //Normalising the values to 0-textures.size()
        perlinNoise.normalisePerlinValues(getTiles(),textures.size());


        for (Tile tile : getTiles()) {
            tile.setPerlinValue((tile.getPerlinValue() == textures.size()) ? tile.getPerlinValue() - 1 : tile.getPerlinValue());
            tile.setTexture(textures.get((int) tile.getPerlinValue()));
        }
    }
}
