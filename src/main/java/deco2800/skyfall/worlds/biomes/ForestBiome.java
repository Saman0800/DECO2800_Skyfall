package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

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
//        textures.add("grass_0");
//        textures.add("grass_1");
//        textures.add("grass_2");
        textures.add("forest_1");
        textures.add("forest_2");
        textures.add("forest_3");
        textures.add("forest_4");
//        textures.add("grass_4");

        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random, 6, 20,0.4, Tile::setPerlinValue);


        for (Tile tile : getTiles()) {

            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));

        }
    }
}
