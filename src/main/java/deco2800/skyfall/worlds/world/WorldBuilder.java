package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.ChestManager;
import deco2800.skyfall.resources.LootRarity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;

/**
 * Allows for step by step creation of a world
 */
public class WorldBuilder implements WorldBuilderInterface {

    // List of biomes
    private ArrayList<AbstractBiome> biomes;

    // List of biomes size
    private ArrayList<Integer> biomeSizes;

    // Seed that is going to be used for world gen
    private long seed;

    // Number of lakes
    private int numOfLakes;

    // Corresponding sizes of the lakes
    private ArrayList<Integer> lakeSizes;

    // The side length/2 of the world, (worldSize* 2)^2 to get the number of tiles
    private int worldSize;

    // The spacing/distance between the nodes
    private int nodeSpacing;

    // The entities in the world
    private CopyOnWriteArrayList<AbstractEntity> entities;

    // The world type, can either be single_player, server, tutorial or test
    private String type;

    // The number of rivers
    private int rivers;

    // The size of the rivers
    private int riverSize;

    // The size of the beach
    private int beachSize;

    // Determines whether static entities are on
    private boolean staticEntities;

    /**
     * Constructor for the WorldBuilder
     */
    public WorldBuilder() {
        numOfLakes = 0;
        seed = 0;
        rivers = 0;
        biomeSizes = new ArrayList<>();
        lakeSizes = new ArrayList<>();
        entities = new CopyOnWriteArrayList<>();
        biomes = new ArrayList<>();
        type = "single_player";
        staticEntities = false;
    }

    /**
     * Adds an entity to the world
     * 
     * @param entity The entity to be added to the world
     */
    @Override
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    /**
     * Adds a biome to the world
     * 
     * @param biome The biome to be added to the world
     * @param size  The size of the biome to be added
     */
    @Override
    public void addBiome(AbstractBiome biome, int size) {
        biomes.add(biome);
        biomeSizes.add(size);
    }

    /**
     * Adds a lake to the world
     * 
     * @param size The corresponding size of the lake
     */
    @Override
    public void addLake(int size) {
        numOfLakes++;
        lakeSizes.add(size);
    }

    @Override
    public void setWorldSize(int size) {
        this.worldSize = size;
    }

    /**
     * Sets the node spacing
     * 
     * @param nodeSpacing The node spacing
     */
    @Override
    public void setNodeSpacing(int nodeSpacing) {
        this.nodeSpacing = nodeSpacing;
    }

    /**
     * Sets a seed for the world
     * 
     * @param seed
     */
    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * Sets the type of world to be created
     * 
     * @param type A string value representing the type of world, can be
     *             single_player, server, test or tutorial
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Adds a single river to the world
     */
    public void addRiver() {
        rivers++;
    }

    /**
     * Sets the size of all the rivers
     * 
     * @param size The size which the rivers will be, in node width
     */
    public void setRiverSize(int size) {
        riverSize = size;
    }

    /**
     * Sets the size of the beach
     * 
     * @param size The size which the beach will be, in tiles
     */
    public void setBeachSize(int size) {
        beachSize = size;
    }

    /**
     * Sets whether static entities are off or on
     * 
     * @param staticEntities true representing static entities are on, false they
     *                       are not
     */
    public void setStaticEntities(boolean staticEntities) {
        this.staticEntities = staticEntities;
    }

    /**
     * Generates the static entities in a world
     * 
     * @param world The world that will get static entities
     * @author Micheal CC
     */
    protected void generateStartEntities(World world) {

        // Nothing will ever be placed on this tile. Only a dummy tile.
        Tile startTile = world.getTile(0.0f, 1.0f);

        for (AbstractBiome biome : world.getBiomes()) {
            switch (biome.getBiomeName()) {
            case "forest":

                Tree startTree = new Tree(startTile, true);
                // Create a new perlin noise map
                SpawnControl treeControl = x -> (x * x * x) / 4.0;
                EntitySpawnRule treeRule = new EntitySpawnRule(biome, true, treeControl);
                EntitySpawnTable.spawnEntities(startTree, treeRule, world);

                // Spawn some LongGrass uniformly
                LongGrass startLongGrass = new LongGrass(startTile, true);
                EntitySpawnRule longGrassRule = new EntitySpawnRule(0.07, 30, 200, biome);
                EntitySpawnTable.spawnEntities(startLongGrass, longGrassRule, world);

                // Spawn some Rocks uniformly
                Rock startRock = new Rock(startTile, true);
                EntitySpawnRule rockRule = new EntitySpawnRule(0.04, 10, 50, biome);
                EntitySpawnTable.spawnEntities(startRock, rockRule, world);

                spawnChests(10, startTile, biome, world);

                ForestMushroom startMushroom = new ForestMushroom(startTile, false);
                // This generator will cause the mushrooms to clump togteher more
                NoiseGenerator mushroomGen = new NoiseGenerator(new Random(), 10, 20, 0.9);
                SpawnControl mushroomControl = x -> (x * x * x * x * x * x) / 2.0;
                EntitySpawnRule mushroomRule = new EntitySpawnRule(biome, true, mushroomControl);
                mushroomRule.setNoiseGenerator(mushroomGen);
                EntitySpawnTable.spawnEntities(startMushroom, mushroomRule, world);
                break;
            case "mountain":

                MountainTree startMTree = new MountainTree(startTile, true);
                // Create a new perlin noise map
                SpawnControl cubic = x -> (x * x * x * x * x) / 4.0;
                EntitySpawnRule mTreeControl = new EntitySpawnRule(biome, true, cubic);
                EntitySpawnTable.spawnEntities(startMTree, mTreeControl, world);

                spawnChests(10, startTile, biome, world);

                MountainRock startMRock = new MountainRock(startTile, true);
                // Create a new perlin noise map
                SpawnControl rockControl = x -> (x * x * x * x) / 2.0;
                EntitySpawnRule mRockControl = new EntitySpawnRule(biome, true, rockControl);
                EntitySpawnTable.spawnEntities(startMRock, mRockControl, world);
                break;
            }
        }
    }

    public void spawnChests(int num, Tile startTile, AbstractBiome biome, World world) {
        // Spawn chests
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            Chest chest = new Chest(startTile, true, ChestManager.generateRandomLoot(random.nextInt(10) + 5, LootRarity.LEGENDARY));
            EntitySpawnRule chestRule = new EntitySpawnRule(0.04, 0, 1, biome);
            EntitySpawnTable.spawnEntities(chest, chestRule, world);
        }
    }

    /**
     * Creates a world based on the values set in the builder
     * 
     * @return A world
     */
    public World getWorld() {
        // Converting the ArrayLists to arrays
        int[] biomeSizesArray = biomeSizes.stream().mapToInt(biomeSize -> biomeSize).toArray();
        int[] lakeSizesArray = lakeSizes.stream().mapToInt(lakeSize -> lakeSize).toArray();

        World world;

        switch (type) {
        case "single_player":
            world = new World(seed, worldSize, nodeSpacing, biomeSizesArray, numOfLakes, lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
            break;
        case "tutorial":
            world = new TutorialWorld(seed, worldSize, nodeSpacing, biomeSizesArray, numOfLakes, lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
            break;
        case "test":
            world = new TestWorld(seed, worldSize, nodeSpacing, biomeSizesArray, numOfLakes, lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
            break;
        case "server":
            world = new ServerWorld(seed, worldSize, nodeSpacing, biomeSizesArray, numOfLakes, lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
            break;
        default:
            throw new IllegalArgumentException("The world type is not valid");
        }

        if (staticEntities) {
            generateStartEntities(world);
        }
        return world;
    }
}
