package deco2800.skyfall.worlds;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class AbstractBiome {
    private String biomeName;
    //The tiles the biome contains
    private ArrayList<Tile> tiles;

    /**
     * Constructer that creates biome with biome name and a list of tiles the biome has
     * @param biomeName The name of the biome
     * @param tiles The tiles in the biome
     */
    public AbstractBiome(String biomeName, ArrayList<Tile> tiles){
        this.tiles = tiles;
        this.biomeName = biomeName;
    }

    /**
     * Constructer for a Biome
     * @param biomeName The biome name
     */
    public AbstractBiome(String biomeName) {
        this.biomeName = biomeName;
        this.tiles = new ArrayList<>();
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
    public void addTileToBiome(Tile tile){
        tiles.add(tile);
    }

    /**
     * Adds a whole list of tiles to a biome
     * @param tiles
     */
    public void addTileListToBiome(ArrayList<Tile> tiles){
        this.tiles.addAll(tiles);
    }


    /**
     * Sets all the textures within a biome
     */
    abstract public void setTileTextures();
}
