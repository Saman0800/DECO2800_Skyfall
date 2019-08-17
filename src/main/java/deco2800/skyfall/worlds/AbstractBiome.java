package deco2800.skyfall.worlds;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that represents the biomes
 */
public abstract class AbstractBiome {
    //The biome name, i.e forest, desert, mountain
    private String biomeName;
    //The tiles the biome contains
    private ArrayList<Tile> tiles;


//    /**
//     * Constructor that creates biome with biome name and a list of tiles the biome has
//     * @param biomeName The name of the biome
//     * @param tiles The tiles in the biome
//     */
//    public AbstractBiome(String biomeName, ArrayList<Tile> tiles){
//        this.tiles = tiles;
//        this.biomeName = biomeName;
//    }
//
    /**
     * Constructor for a Biome
     * @param biomeName The biome name
     */
    public AbstractBiome(String biomeName) {
//        this(biomeName, new ArrayList<>());
        this.biomeName = biomeName;
        tiles = new ArrayList<>();
    }

    /**
     * Returns all the tiles within a biome
     * @return An ArrayList of all the tiles within a biome
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Gets the biomes name
     * @return The biome name
     */
    public String getBiomeName() {
        return biomeName;
    }

    /**
     * Adds a tile to a biome
     * @param tile The tile to be added
     */
    public void addTile(Tile tile){
        tiles.add(tile);
    }

    /**
     * Sets all the textures within a biome.
     *
     * @param random the RNG to use to generate the textures
     */
    protected abstract void setTileTextures(Random random);



}
