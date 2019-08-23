package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents the biomes
 */
public abstract class AbstractBiome {
    // The biome name, i.e forest, desert, mountain
    private String biomeName;
    // The tiles the biome contains
    private ArrayList<Tile> tiles;


    /**
     * Constructor for a Biome
     *
     * @param biomeName The biome name
     */
    public AbstractBiome(String biomeName) {
        // this(biomeName, new ArrayList<>());
        this.biomeName = biomeName;
        tiles = new ArrayList<>();
    }

    /**
     * Returns all the tiles within a biome
     *
     * @return An ArrayList of all the tiles within a biome
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Gets the biomes name
     *
     * @return The biome name
     */
    public String getBiomeName() {
        return biomeName;
    }

    /**
     * Adds a tile to a biome
     *
     * @param tile The tile to be added
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
        tile.setBiome(this);
    }

    /**
     * Sets all the textures within a biome.
     *
     * @param random the RNG to use to generate the textures
     */
    public abstract void setTileTextures(Random random);

}
