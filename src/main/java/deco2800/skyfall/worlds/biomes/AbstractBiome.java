package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.Saveable;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents the biomes
 */
public abstract class AbstractBiome implements Saveable<AbstractBiome.AbstractBiomeMemento> {
    private long id;
    private long worldID;

    // The biome name, i.e forest, desert, mountain
    private String biomeName;
    // The tiles the biome contains
    private ArrayList<Tile> tiles;
    // The biome this is contained in if it's a sub-biome (e.g. a lake)
    private AbstractBiome parentBiome;
    // The biomes which have this biome as a parent
    private ArrayList<AbstractBiome> childBiomes;

    protected NoiseGenerator textureGenerator;

    /**
     * Constructor for a biome being loaded
     *
     * @param memento the memento this biome is being loaded from
     */
    public AbstractBiome(AbstractBiomeMemento memento) {
        this.load(memento);
        this.childBiomes = new ArrayList<>();
        this.biomeName = memento.biomeType;
        this.tiles = new ArrayList<>();
    }

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
        this.id = System.nanoTime();
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
        // FIXME:Ontonator Make this work with chunks.
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

    public long getBiomeID() {
        return this.id;
    }

    public void setBiomeID(int id) {
        this.id = id;
    }


    private void setWorldID(long worldID) {
        this.worldID = worldID;
    }

    @Override
    public AbstractBiomeMemento save() {
        return new AbstractBiomeMemento(this);
    }

    @Override
    public void load(AbstractBiomeMemento memento) {
        this.worldID = memento.worldID;
        this.id = memento.biomeID;
    }

    public class AbstractBiomeMemento extends AbstractMemento {
        // The ID of the world this is in
        private long worldID;

        // The ID of this biome
        private long biomeID;

        // The type of biome this is
        private String biomeType;

        // The ID of this Biome's parent
        private long parentBiomeID;

        //Starting seed for the noise generator for the biome
        protected long noiseGeneratorSeed;

        private AbstractBiomeMemento(AbstractBiome biome) {
            this.worldID = biome.worldID;
            this.biomeID = biome.id;
            this.biomeType = biome.getBiomeName();
            if (biome.textureGenerator == null) {
                System.out.println(biome.getBiomeName());
            }
            this.noiseGeneratorSeed = biome.textureGenerator.getSeed();
            if (biome.getParentBiome() == null) {
                // TODO find a better value to represent null
                this.parentBiomeID = -1;
            } else {
                this.parentBiomeID = biome.parentBiome.id;
            }
        }

        /**
         * Get the biome type of this biome
         *
         * @return the biome type of this biome
         */
        public String getBiomeType() {
            return this.biomeType;
        }

        /**
         * Get the parent biome id of this biome
         *
         * @return the parent biome id of this biome
         */
        public long getParentBiomeID() {
            return this.parentBiomeID;
        }
    }
}
