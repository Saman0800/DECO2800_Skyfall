package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Desert Biome
 */
public class DesertBiome extends AbstractBiome {

    /**
     * Constructor for the DesertBiome
     */
    public DesertBiome() {
        super("desert", null);
    }


    /**
     * Method that will determine the textures of the desert biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("desert_1");
        textures.add("desert_2");
        textures.add("desert_3");

        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random, 4, 50,0.5,  Tile::setPerlinValue);


        //Going through each tile assigning a perlin noise value
        for (Tile tile : getTiles()) {
            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));
        }
    }
}
