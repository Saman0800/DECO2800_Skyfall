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
     * Constructor for a Biome
     */
    public ForestBiome() {
        super("forest", null);
    }

    /**
     * Method that will determine the textures of the forest biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
//        textures.add("forest_1");
//        textures.add("forest_2");
//        textures.add("forest_3");
//        textures.add("forest_4");
        textures.add("forest_1");
        textures.add("forest_2");
        textures.add("forest_3");

        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random, 3, 60,0.4, Tile::setPerlinValue);
        NoiseGenerator noise = new NoiseGenerator(random, 5, 60, 0.4);
        for (Tile tile : getTiles()) {
            tile.setPerlinValue((tile.getPerlinValue() +
                noise.getOctavedPerlinValue(tile.getRow() + tile.getCol(), tile.getRow() - tile.getCol())) / 2);
        }

        for (Tile tile : getTiles()) {
            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));

        }
    }
}
