package deco2800.skyfall.saving;

import deco2800.skyfall.worlds.biomes.AbstractBiome;

public class BiomeSaveInfo {

    // The ID of the world this is in
    private int worldID;

    // The ID of this biome
    private int biomeID;

    // The type of biome this is
    private String biomeType;

    // The ID of this Biome's parent
    private int parentBiomeID;

    /**
     * Constructor for a new BiomeSaveInfo
     *
     * @param biome the biome this is for
     */
    public BiomeSaveInfo(AbstractBiome biome) {
        this.biomeID = biome.getSaveID();
        this.biomeType = biome.getBiomeName();
        if (biome.getParentBiome() == null) {
            // TODO find a better value to represent null
            this.parentBiomeID = -1;
        } else {
            this.parentBiomeID = biome.getParentBiome().getSaveID();
        }
    }

    /**
     * Returns the ID of the world this is in
     *
     * @return the ID of the world this is in
     */
    public int getWorldID() {
        return this.worldID;
    }

    /**
     * Returns the ID of this biome
     *
     * @return the ID of this biome
     */
    public int getBiomeID() {
        return this.biomeID;
    }

    /**
     * Returns the type of biome this is
     *
     * @return the type of biome this is
     */
    public String getBiomeType() {
        return this.biomeType;
    }

    /**
     * Returns the ID of this biome's parent
     *
     * @return the ID of this biome's parent
     */
    public int getParentBiomeID() {
        return this.parentBiomeID;
    }
}
