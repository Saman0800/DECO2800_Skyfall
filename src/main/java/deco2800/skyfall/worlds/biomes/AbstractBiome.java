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
    // The biome this is contained in if it's a sub-biome (e.g. a lake)
    private AbstractBiome parentBiome;


    /**
     * Constructor for a Biome
     *
     * @param biomeName The biome name
     */
    public AbstractBiome(String biomeName, AbstractBiome parentBiome) {
        // this(biomeName, new ArrayList<>());
        this.biomeName = biomeName;
        this.parentBiome = parentBiome;
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
        if (tile.getBiome() != null) {
            tile.getBiome().tiles.remove(tile);
        }
        tiles.add(tile);
        tile.setBiome(this);
    }

    /**
     * Gets the parent biome of this biome
     *
     * @return the parent biome of this biome
     */
    public AbstractBiome getParentBiome() {
        return parentBiome;
    }

    /**
     * Sets all the textures within a biome.
     *
     * @param random the RNG to use to generate the textures
     */
    public abstract void setTileTextures(Random random);

}
