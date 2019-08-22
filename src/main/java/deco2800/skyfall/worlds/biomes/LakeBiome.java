package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.generation.PerlinNoiseGenerator;
import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.Random;

public class LakeBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();

    /**
     * Constructor for a Biome
     */
    public LakeBiome() {
        super("lake");
    }


    /**
     * Method that will determine the textures of the ocean biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    public void setTileTextures(Random random) {
        // TODO see if different textures should be used to the ocean
        ArrayList<String> textures = new ArrayList<>();
        //textures.add("water_3");
        //textures.add("water_0");
        textures.add("water_1");
        textures.add("water_2");

        //Perlin noise generation
        PerlinNoiseGenerator perlinNoise = new PerlinNoiseGenerator(random);
        // perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 2, 30, 0.5);
        perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 3, 20, 0.2);
        //Normalising the values to 0-textures.size()
        perlinNoise.normalisePerlinValues(getTiles(),textures.size());


        for (Tile tile : getTiles()) {
            tile.setPerlinValue((tile.getPerlinValue() == textures.size()) ? 0 : tile.getPerlinValue());
            tile.setTexture(textures.get((int) tile.getPerlinValue()));
        }
    }
}
