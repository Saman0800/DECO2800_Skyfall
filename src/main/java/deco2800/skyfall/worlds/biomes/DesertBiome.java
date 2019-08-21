package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Desert Biome
 */
public class DesertBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();

    /**
     * Constructor for a Biome
     */
    public DesertBiome() {
        super("desert");
    }


    //Likes grouped with likes
    /**
     * Method that will determine the textures of the desert biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("desert_3");
        textures.add("desert_1");
        textures.add("desert_2");

        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random, 2, 10,0.2, textures.size());


        for (Tile tile : getTiles()) {
            tile.setPerlinValue((tile.getPerlinValue() == textures.size()) ? tile.getPerlinValue() - 1 : tile.getPerlinValue());
            tile.setTexture(textures.get((int) tile.getPerlinValue()));
        }
    }
}
