package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.Saveable;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the biomes.
 */
public abstract class AbstractBiome implements Saveable<AbstractBiome.AbstractBiomeMemento>, Serializable {
    private long id;
    private long worldID;

    // The biome name, i.e forest, desert, mountain
    private String biomeName;
    // The tiles the biome contains
    private transient ArrayList<Tile> tiles;
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
     * Constructor for a biome being loaded
     *
     * @param memento     the memento this biome is being loaded from
     * @param seed        The random number generator, allows for seeding. Used for
     *                    the Noise Generator of the biome.
     * @param octaves     The number of sets of perlin values that will be generated
     *                    and than combined. The higher the value The more small
     *                    details and anomlies in the world there will and there
     *                    more perceived realism world will have, as it will have
     *                    small bits of chaos. Used for the Noise Generator of the
     *                    biome.
     * @param startPeriod The intial period, the higher the value the longer it will
     *                    take to go from one value to another. Used for the Noise
     *                    Generator of the biome.
     * @param attenuation The weight of the octaves, the higher the value the more
     *                    sporatic the land will be when there are many octaves.
     *                    Used for the Noise Generator of the biome.
     */
    public AbstractBiome(AbstractBiomeMemento memento, long seed, int octaves, double startPeriod, double attenuation) {
        this(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, octaves, startPeriod, attenuation);
    }

    /**
     * Constructor for a Biome
     *
     * @param biomeName   The biome name
     * @param parentBiome The biome that the biome lives in, null if the biome has
     *                    no parent
     */
    public AbstractBiome(String biomeName, AbstractBiome parentBiome) {
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
     * 
     * @param tiles The list of tiles for the biome
     */
    public void setTiles(List<Tile> tiles) {
        this.tiles = (ArrayList<Tile>) tiles;
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
    private List<AbstractBiome> getChildBiomes() {
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
    public void setTileTexture(Tile tile) {
        ArrayList<String> textures = new ArrayList<>();

        if(getBiomeName().equals("lake") || getBiomeName().equals("swamp")) {
            textures.add(getBiomeName() + "1.1");
            textures.add(getBiomeName() + "1.2");
            textures.add(getBiomeName() + "1.3");
        } else if(getBiomeName().equals("volcanic_mountains")) {
            textures.add("vmountain1.1");
            textures.add("vmountain1.2");
            textures.add("vmountain1.3");
        } else {
            textures.add(getBiomeName() + "_1");
            textures.add(getBiomeName() + "_2");
            textures.add(getBiomeName() + "_3");
        }

        double perlinValue =
                NoiseGenerator.fade(textureGenerator.getOctavedPerlinValue(tile.getCol(), tile.getRow()), 2);
        int adjustedPerlinValue = (int) Math.floor(perlinValue * textures.size());
        if (adjustedPerlinValue >= textures.size()) {
            adjustedPerlinValue = textures.size() - 1;
        }
        tile.setPerlinValue(adjustedPerlinValue);
        tile.setTexture(textures.get(adjustedPerlinValue));
    }

    /**
     * Sets the texture of the given tile assuming it is in this biome for the
     * variable number of textures used for the biome's tiles.
     */
    public void setTileTexture(Tile tile, String... varTextures) {
        ArrayList<String> textures = new ArrayList<>();

        for (String texture : varTextures) {
            textures.add(texture);
        }

        double perlinValue = NoiseGenerator.fade(textureGenerator.getOctavedPerlinValue(tile.getCol(), tile.getRow()),
                2);
        int adjustedPerlinValue = (int) Math.floor(perlinValue * textures.size());
        if (adjustedPerlinValue >= textures.size()) {
            adjustedPerlinValue = textures.size() - 1;
        }
        tile.setPerlinValue(adjustedPerlinValue);
        tile.setTexture(textures.get(adjustedPerlinValue));
    }

    public long getBiomeID() {
        return this.id;
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

    public static class AbstractBiomeMemento implements AbstractMemento , Serializable {
        // The ID of the world this is in
        private long worldID;

        // The ID of this biome
        private long biomeID;

        // The type of biome this is
        private String biomeType;

        // The ID of this Biome's parent
        private long parentBiomeID;

        // Starting seed for the noise generator for the biome
        protected long noiseGeneratorSeed;

        private AbstractBiomeMemento(AbstractBiome biome) {
            this.worldID = biome.worldID;
            this.biomeID = biome.id;
            this.biomeType = biome.getBiomeName();
            this.noiseGeneratorSeed = biome.textureGenerator.getSeed();
            if (biome.getParentBiome() == null) {
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
