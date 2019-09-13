package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

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
    // The biomes which have this biome as a parent
    private ArrayList<AbstractBiome> childBiomes;

    /**
     * Constructor for a Biome
     *
     * @param biomeName The biome name
     * @param parentBiome The biome that the biome lives in, null if the biome has no parent
     */
    public AbstractBiome(String biomeName, AbstractBiome parentBiome) {
        // this(biomeName, new ArrayList<>());
        this.biomeName = biomeName;
        setParentBiome(parentBiome);
        tiles = new ArrayList<>();
        childBiomes = new ArrayList<>();
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
     * Sets the tiles in the biome
     * @param tiles The list of tiles for the biome
     */
    public void setTiles(ArrayList<Tile> tiles){
        this.tiles = tiles;
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
        if (tile.getBiome() == this) {
            return;
        }
        if (tile.getBiome() != null) {
            tile.getBiome().tiles.remove(tile);
        }
        tiles.add(tile);
        tile.setBiome(this);
        setTileTexture(tile);
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
     * Sets the parent boime of this biome.
     *
     * @param parentBiome the new parent biome of this biome
     */
    public void setParentBiome(AbstractBiome parentBiome) {
        if (this.parentBiome != null) {
            this.parentBiome.childBiomes.remove(this);
        }
        this.parentBiome = parentBiome;
        if (parentBiome != null) {
            parentBiome.childBiomes.add(this);
        }
    }

    /**
     * Returns a list of the child biomes.
     *
     * @return a list of the child biomes
     */
    public List<AbstractBiome> getChildBiomes() {
        return this.childBiomes;
    }

    /**
     * Returns a list of all of the descendants of this biome.
     *
     * @return a list of all of the descendants
     */
    public List<AbstractBiome> getDescendantBiomes() {
        ArrayList<AbstractBiome> descendants = new ArrayList<>();
        aggregateDescendantBiomes(descendants);
        return descendants;
    }

    /**
     * Adds this biome its descendants to the provided list.
     *
     * @param biomes the list to which the biomes are added
     */
    private void aggregateDescendantBiomes(List<AbstractBiome> biomes) {
        biomes.add(this);
        for (AbstractBiome child : getChildBiomes()) {
            child.aggregateDescendantBiomes(biomes);
        }
    }

    /**
     * Returns whether {@code ancestor} contains this biome.
     *
     * @param ancestor the ancestor to check
     * @return whether {@code ancestor} contains this biome
     */
    public boolean isDescendedFrom(AbstractBiome ancestor) {
        return this == ancestor || (parentBiome != null && parentBiome.isDescendedFrom(ancestor));
    }

    /**
     * Sets the texture of the given tile assuming it is in this biome.
     */
    public abstract void setTileTexture(Tile tile);
}
