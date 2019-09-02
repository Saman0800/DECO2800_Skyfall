package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;
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
        super("mountain", null);
    }


    /**
     * Method that will determine the textures of the mountain biome textures
     *
     * @param random the RNG to use to generate the textures
     */

    @Override
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
//        textures.add("mountain_9");
//        textures.add("mountain_10");
//        textures.add("mountain_11");
        textures.add("mountain_1");
        textures.add("mountain_2");
        textures.add("mountain_3");
//        textures.add("snow");

        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random,3  , 60,0.5, Tile::setPerlinValue);


        for (Tile tile : getTiles()) {
            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));
        }
    }

}