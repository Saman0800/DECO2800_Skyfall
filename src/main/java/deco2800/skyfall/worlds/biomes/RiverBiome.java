package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Biomes that gets assigned to rivers on the map
 */
public class RiverBiome extends AbstractBiome {
    private ArrayList<String> textures;
    /**
     * Constructor for the RiverBiome
     */
    public RiverBiome(AbstractBiome parentBiome) {
        super("river", parentBiome);
        textures = new ArrayList<>();
        textures.add("lake_1");
        textures.add("lake_2");
    }


    /**
     * Method that will determine the textures of the river biome tiles
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    public void setTileTextures(Random random) {
        // TODO see if different textures should be used to the ocean

        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random, 3, 40,0.7, Tile::setPerlinValue);

        for (Tile tile : getTiles()) {
            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            String texture = textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1);
            tile.setTexture(texture);
        }
    }
}
