package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.weapons.*;
import deco2800.skyfall.entities.worlditems.*;
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

    // List of biomes size
    private ArrayList<Integer> biomeSizes;

    // Corresponding sizes of the lakes
    private ArrayList<Integer> lakeSizes;

    // The entities in the world
    private CopyOnWriteArrayList<AbstractEntity> entities;

    // The world type, can either be single_player, server, tutorial or test
    private String type;

    // Determines whether static entities are on
    private boolean staticEntities;

    //Contains parameters for the world
    private WorldParameters worldParameters;

    /**
     * Constructor for the WorldBuilder
     */
    public WorldBuilder() {
        worldParameters = new WorldParameters();
        worldParameters.setNumOfLakes(0);
        worldParameters.setSeed(0);
        worldParameters.setNoRivers(0);
        worldParameters.setNoRivers(0);
        worldParameters.setEntities(new CopyOnWriteArrayList<>());
        worldParameters.setBiomes(new ArrayList<>());

        biomeSizes = new ArrayList<>();
        lakeSizes = new ArrayList<>();
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
        worldParameters.addEntity(entity);
    }

    /**
     * Adds a biome to the world
     * 
     * @param biome The biome to be added to the world
     * @param size  The size of the biome to be added
     */
    @Override
    public void addBiome(AbstractBiome biome, int size) {
        worldParameters.addBiome(biome);
        biomeSizes.add(size);
    }

    /**
     * Adds a lake to the world
     * 
     * @param size The corresponding size of the lake
     */
    @Override
    public void addLake(int size) {
        worldParameters.setNumOfLakes(worldParameters.getNumOfLakes()+1);
        lakeSizes.add(size);
    }

    @Override
    public void setWorldSize(int size) {
        worldParameters.setWorldSize(size);
    }

    /**
     * Sets the node spacing
     * 
     * @param nodeSpacing The node spacing
     */
    @Override
    public void setNodeSpacing(int nodeSpacing) {
        worldParameters.setNodeSpacing(nodeSpacing);
    }

    /**
     * Sets a seed for the world
     * 
     * @param seed
     */
    @Override
    public void setSeed(long seed) {
        worldParameters.setSeed(seed);
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
        worldParameters.setNoRivers(worldParameters.getNoRivers()+1);
    }

    /**
     * Sets the size of all the rivers
     * 
     * @param size The size which the rivers will be, in node width
     */
    public void setRiverSize(int size) {
        worldParameters.setRiverWidth(size);
    }

    /**
     * Sets the size of the beach
     * 
     * @param size The size which the beach will be, in tiles
     */
    public void setBeachSize(int size) {
        worldParameters.setBeachWidth(size);
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

        long worldSeed = world.getSeed();
        EntitySpawnRule.setNoiseSeed(worldSeed);

        // Nothing will ever be placed on this tile. Only a dummy tile.
        Tile startTile = world.getTile(0.0f, 1.0f);

        for (AbstractBiome biome : world.getBiomes()) {
            switch (biome.getBiomeName()) {
            case "forest":

                // Spawn some swords
                Weapon startSword = new Sword(startTile, true);
                EntitySpawnRule swordRule = new EntitySpawnRule(0.04, 10, 20, biome);
                EntitySpawnTable.spawnEntities(startSword, swordRule, world);


                Tree startTree = new Tree(startTile, true);
                // Create a new perlin noise map
                SpawnControl treeControl = x -> (x * x * x) / 3.0;
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

                ForestMushroom startMushroom = new ForestMushroom(startTile, false);
                // This generator will cause the mushrooms to clump togteher more
                NoiseGenerator mushroomGen = new NoiseGenerator(new Random(worldSeed), 10, 20, 0.9);
                SpawnControl mushroomControl = x -> (x * x * x * x * x * x) / 3.0;
                EntitySpawnRule mushroomRule = new EntitySpawnRule(biome, true, mushroomControl);
                mushroomRule.setNoiseGenerator(mushroomGen);
                EntitySpawnTable.spawnEntities(startMushroom, mushroomRule, world);
                break;

            case "mountain":

                // Spawn some spears
                Weapon startSpear = new Spear(startTile, true);
                EntitySpawnRule spearRule = new EntitySpawnRule(0.05, 1, 10, biome);
                EntitySpawnTable.spawnEntities(startSpear, spearRule, world);

                MountainTree startMTree = new MountainTree(startTile, true);
                // Create a new perlin noise map
                SpawnControl cubic = x -> (x * x * x * x * x) / 4.0;
                EntitySpawnRule mTreeControl = new EntitySpawnRule(biome, true, cubic);
                EntitySpawnTable.spawnEntities(startMTree, mTreeControl, world);

                MountainRock startMRock = new MountainRock(startTile, true);
                // Create a new perlin noise map
                SpawnControl rockControl = x -> (x * x * x * x) / 2.0;
                EntitySpawnRule mRockRule = new EntitySpawnRule(biome, true, rockControl);
                EntitySpawnTable.spawnEntities(startMRock, mRockRule, world);

                // Spawn some Snow uniformly
                SnowClump startMountainSnow = new SnowClump(startTile, false);
                EntitySpawnRule mSnowRule = new EntitySpawnRule(0.07, 30, 200, biome);
                EntitySpawnTable.spawnEntities(startMountainSnow, mSnowRule, world);

                break;

            case "desert":

                // Spawn some axes
                Weapon startAxe = new Axe(startTile,true);
                EntitySpawnRule axeRule = new EntitySpawnRule(0.05, 1, 10,
                        biome);
                EntitySpawnTable.spawnEntities(startAxe, axeRule, world);

                DesertCacti startDCacti = new DesertCacti(startTile, true);
                // Create a new perlin noise map
                SpawnControl cactiControl = x -> (x * x * x * x) / 4.0;
                EntitySpawnRule cactiRule = new EntitySpawnRule(biome, true, cactiControl);
                EntitySpawnTable.spawnEntities(startDCacti, cactiRule, world);
                break;

            case "snowy_mountains":

                // Spawn some bows
                Weapon startBow = new Bow(startTile,true);
                EntitySpawnRule bowRule = new EntitySpawnRule(0.05, 1, 10,
                        biome);
                EntitySpawnTable.spawnEntities(startBow, bowRule, world);

                SnowClump startSnowyMountainSnow = new SnowClump(startTile, false);
                // Create a new perlin noise map
                SpawnControl sSnowControl = x -> (x * x * x);
                EntitySpawnRule sSnowRule = new EntitySpawnRule(biome, true, sSnowControl);
                EntitySpawnTable.spawnEntities(startSnowyMountainSnow, sSnowRule, world);

                // Spawn some Snow Shrubs uniformly
                SnowShrub startSnowShrub = new SnowShrub(startTile, true);
                EntitySpawnRule snowShrubRule = new EntitySpawnRule(0.07, 20, 200, biome);
                EntitySpawnTable.spawnEntities(startSnowShrub, snowShrubRule, world);

                break;
            }
        }
    }

    /**
     * Creates a world based on the values set in the builder
     * 
     * @return A world
     */
    public World getWorld() {
        // Converting the ArrayLists to arrays
        worldParameters.setBiomeSizes(biomeSizes.stream().mapToInt(biomeSize -> biomeSize).toArray());
        worldParameters.setLakeSizes(lakeSizes.stream().mapToInt(lakeSize -> lakeSize).toArray());

        World world;

        switch (type) {
        case "single_player":
            world = new World(worldParameters);
            break;
        case "tutorial":
            world = new TutorialWorld(worldParameters);
            break;
        case "test":
            world = new TestWorld(worldParameters);
            break;
        case "server":
            world = new ServerWorld(worldParameters);
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
