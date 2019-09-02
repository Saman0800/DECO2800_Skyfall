package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.TileNoiseGenerator;
import java.util.ArrayList;
import java.util.Random;

/**
 * Snowy Mountains biome
 */
public class SnowyMountainsBiome extends AbstractBiome {

    /**
     * Constructor for the snowy mountains biome
     */
    public SnowyMountainsBiome(){
        super("snowy_mountains", null);
    }

    @Override
    public void setTileTextures(Random random) {

        ArrayList<String> textures = new ArrayList<>();
        textures.add("snowy_mountains_1");
        textures.add("snowy_mountains_2");
        textures.add("snowy_mountains_3");
        //Perlin noise generation
        new TileNoiseGenerator(getTiles(), random,3  , 60,0.5, Tile::setPerlinValue);


        for (Tile tile : getTiles()) {
            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));
        }
    }
}
