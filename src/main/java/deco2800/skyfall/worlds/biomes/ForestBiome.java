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
        new TileNoiseGenerator(getTiles(), random, 4, 30,0.2, Tile::setPerlinValue);


        for (Tile tile : getTiles()) {

            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));

        }
    }
}
