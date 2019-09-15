package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.worlditems.EntitySpawnRule;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WorldParameters {

    // Seed that is going to be used for world gen
    private long seed;

    // The side length/2 of the world, (worldSize* 2)^2 to get the number of tiles
    private int worldSize;

    // The spacing/distance between the nodes
    private int nodeSpacing;

    // Biomes size
    private ArrayList<Integer> biomeSizes;

    //Sizes of the lakes
    private ArrayList<Integer> lakeSizes;

    //The number of lakes
    private int numOfLakes;

    //List of the biomes in the world
    private List<AbstractBiome> biomes;

    // TODO:Ontonator Consider removing this.
    //List of the entities in the world
    private List<AbstractEntity> entities;

    //The number of rivers
    private int noRivers;

    //The width of the rivers
    private double riverWidth;

    //The width of the beach
    private double beachWidth;

    private Function<World, Map<AbstractBiome, List<EntitySpawnRule>>> generateSpawnRules;

    /**
     * Adds an entity to the list of entities
     * @param entity The entity to be added
     */
    public void addEntity(AbstractEntity entity){
        entities.add(entity);
    }

    /**
     * Adds a biome to the list of biomes
     * @param biome The biome to be added
     */
    public void addBiome(AbstractBiome biome){
        biomes.add(biome);
    }

    /**
     * Adds a corresponding biome size
     * @param size The biome size to be added
     */
    public void addBiomeSize(int size){
        biomeSizes.add(size);
    }

    /**
     * Adds a corresponding lake size
     * @param size The size of the lake
     */
    public void addLakeSize(int size){
        lakeSizes.add(size);
    }

    /**
     * Sets the seed
     * @param seed The value of the seed
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * Seeds the world size value
     * @param worldSize The value the worldsize will be set to
     */
    public void setWorldSize(int worldSize) {
        this.worldSize = worldSize;
    }

    /**
     * Sets the node spacing for the world
     * @param nodeSpacing The value the nodespacing will get set to
     */
    public void setNodeSpacing(int nodeSpacing) {
        this.nodeSpacing = nodeSpacing;
    }

    /**
     * Sets the sizes of the corresponding biomes
     * @param biomeSizes A list of of corresponding biomessizes for each biome
     */
    public void setBiomeSizes(ArrayList<Integer> biomeSizes) {
        this.biomeSizes = biomeSizes;
    }

    /**
     * Sets the sizes of the lakes
     * @param lakeSizes The size of the lakes
     */
    public void setLakeSizes(ArrayList<Integer> lakeSizes) {
        this.lakeSizes = lakeSizes;
    }

    public void setLakeSizes(int[] lakeSizes){
        this.lakeSizes = new ArrayList<>();
        for (int lakeSize : lakeSizes){
            this.lakeSizes.add(lakeSize);
        }
    }

    public void setBiomeSizes(int[] biomeSizes){
        this.biomeSizes = new ArrayList<>();
        for (int biomeSize : biomeSizes) {
            this.biomeSizes.add(biomeSize);
        }
    }

    /**
     * Sets the number of lakes
     * @param numOfLakes The number of lakes
     */
    public void setNumOfLakes(int numOfLakes) {
        this.numOfLakes = numOfLakes;
    }

    /**
     * Sets the biomes
     * @param biomes The biomes
     */
    public void setBiomes(List<AbstractBiome> biomes) {
        this.biomes = biomes;
    }

    /**
     * Sets the entities
     * @param entities A list of entities
     */
    public void setEntities(List<AbstractEntity> entities) {
        this.entities = entities;
    }

    /**
     * Sets the number of rivers
     * @param noRivers The number of rivers
     */
    public void setNoRivers(int noRivers) {
        this.noRivers = noRivers;
    }

    /**
     * Sets the width of the rivers
     * @param riverWidth The width of the rivers
     */
    public void setRiverWidth(double riverWidth) {
        this.riverWidth = riverWidth;
    }

    /**
     * Sets the beach width
     * @param beachWidth The beach width
     */
    public void setBeachWidth(double beachWidth) {
        this.beachWidth = beachWidth;
    }

    /**
     * Sets the function to generate the spawn rules.
     * @param generateSpawnRules the spawn rules
     */
    public void setGenerateSpawnRules(Function<World, Map<AbstractBiome, List<EntitySpawnRule>>> generateSpawnRules) {
        this.generateSpawnRules = generateSpawnRules;
    }

    /**
     * Gets the seed
     * @return The seed
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Gets the world size
     * @return The world size
     */
    public int getWorldSize() {
        return worldSize;
    }

    /**
     * Gets the node spacing
     * @return The node spacing
     */
    public int getNodeSpacing() {
        return nodeSpacing;
    }

    /**
     * Gets the biomes sizes as an array
     * @return The biome sizes
     */
    public int[] getBiomeSizesArray() {
        return biomeSizes.stream().mapToInt(biomeSize -> biomeSize).toArray();
    }


    /**
     * Gets the lake sizes as an array
     * @return The sizes of the lakes
     */
    public int[] getLakeSizesArray() {
        return lakeSizes.stream().mapToInt(lakeSize -> lakeSize).toArray();
    }


    /**
     * Gets the biome sizes
     * @return The biome sizes
     */
    public ArrayList<Integer> getBiomeSizes(){
        return biomeSizes;
    }


    /**
     * Gets the lake sizes
     * @return The lake sizes
     */
    public ArrayList<Integer> getLakeSizes(){
        return lakeSizes;
    }

    /**
     * Gets the number of lakes
     * @return The number of lakes
     */
    public int getNumOfLakes() {
        return numOfLakes;
    }

    /**
     * Gets the biomes
     * @return The biomes
     */
    public List<AbstractBiome> getBiomes() {
        return biomes;
    }

    /**
     * Gets the entities
     * @return The entities
     */
    public List<AbstractEntity> getEntities() {
        return entities;
    }

    /**
     * Gets the number of rivers
     * @return The number of rivers
     */
    public int getNoRivers() {
        return noRivers;
    }

    /**
     * Gets the river width
     * @return The river width
     */
    public double getRiverWidth() {
        return riverWidth;
    }

    /**
     * Gets the beach width
     * @return The beach width
     */
    public double getBeachWidth() {
        return beachWidth;
    }

    /**
     * Gets the function to generate the spawn rules.
     * @return the functions to generate the spawn rules
     */
    public Function<World, Map<AbstractBiome, List<EntitySpawnRule>>> getGenerateSpawnRules() {
        return generateSpawnRules;
    }

    /**
     * Removes an entity
     * @param entity The entity to be removed
     */
    public void removeEntity(AbstractEntity entity){
        entities.remove(entity);
    }
}
