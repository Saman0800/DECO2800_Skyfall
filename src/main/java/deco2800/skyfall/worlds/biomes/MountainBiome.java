package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.PerlinNoiseGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Mountain biome
 */
public class MountainBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();

    /**
     * Constructor for a Biome
     */
    public MountainBiome() {
        super("mountain");
    }


    /**
     * Method that will determine the textures of the forest biome textures
     *
     * @param random the RNG to use to generate the textures
     */

    @Override
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("mountain_1");
        textures.add("mountain_5");
//        textures.add("mountain_0");
        textures.add("mountain_4");

        //Perlin noise generation
        PerlinNoiseGenerator perlinNoise = new PerlinNoiseGenerator(random);
        // perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 2, 30, 0.5);
        perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 4, 30, 0.2);
        //Normalising the values to 0-textures.size()
        perlinNoise.normalisePerlinValues(getTiles(),textures.size());


        for (Tile tile : getTiles()) {
            tile.setPerlinValue((tile.getPerlinValue() == textures.size()) ? tile.getPerlinValue() - 1 : tile.getPerlinValue());
            tile.setTexture(textures.get((int) tile.getPerlinValue()));
        }
    }

}