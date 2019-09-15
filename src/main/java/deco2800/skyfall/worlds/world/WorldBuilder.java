package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Allows for step by step creation of a world
 */
public class WorldBuilder implements WorldBuilderInterface {

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
        worldParameters.setLakeSizes(new ArrayList<>());
        worldParameters.setBiomeSizes(new ArrayList<>());

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
        worldParameters.addBiomeSize(size);
    }

    /**
     * Adds a lake to the world
     * 
     * @param size The corresponding size of the lake
     */
    @Override
    public void addLake(int size) {
        worldParameters.setNumOfLakes(worldParameters.getNumOfLakes()+1);
        worldParameters.addLakeSize(size);
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
    protected Map<AbstractBiome, List<EntitySpawnRule>> generateStartEntities(World world) {
        HashMap<AbstractBiome, List<EntitySpawnRule>> spawnRules = new HashMap<>();

        long worldSeed = world.getSeed();
        EntitySpawnRule.setNoiseSeed(worldSeed);

        // FIXME:Ontonator Fix the weightings on these so they actually spawn often enough.
        for (AbstractBiome biome : world.getBiomes()) {
            ArrayList<EntitySpawnRule> biomeSpawnRules = new ArrayList<>();

            switch (biome.getBiomeName()) {
            case "forest":

                // Create a new perlin noise map
                SpawnControl treeControl = x -> x / 3 + 0.2;
                EntitySpawnRule treeRule = new EntitySpawnRule(tile -> new Tree(tile, true), biome, true, treeControl);
                biomeSpawnRules.add(treeRule);

                // Spawn some LongGrass uniformly
                EntitySpawnRule longGrassRule =
                        new EntitySpawnRule(tile -> new LongGrass(tile, true), 0.07, 30, 200, biome);
                biomeSpawnRules.add(longGrassRule);

                // Spawn some Rocks uniformly
                EntitySpawnRule rockRule = new EntitySpawnRule(tile -> new Rock(tile, true), 0.04, 10, 50, biome);
                biomeSpawnRules.add(rockRule);

                // This generator will cause the mushrooms to clump togteher more
                NoiseGenerator mushroomGen = new NoiseGenerator(new Random(worldSeed).nextLong(), 10, 20, 0.9);
                SpawnControl mushroomControl = x -> (x * x * x * x * x * x) / 3.0;
                EntitySpawnRule mushroomRule =
                        new EntitySpawnRule(tile -> new ForestMushroom(tile, false), biome, true, mushroomControl);
                mushroomRule.setNoiseGenerator(mushroomGen);
                biomeSpawnRules.add(mushroomRule);
                break;

            case "mountain":

                // Create a new perlin noise map
                SpawnControl cubic = x -> (x * x * x * x * x) / 4.0;
                EntitySpawnRule mTreeControl =
                        new EntitySpawnRule(tile -> new MountainTree(tile, true), biome, true, cubic);
                biomeSpawnRules.add(mTreeControl);

                // Create a new perlin noise map
                SpawnControl rockControl = x -> (x * x * x * x) / 2.0;
                EntitySpawnRule mRockRule = new EntitySpawnRule(tile -> new MountainRock(tile, true), biome, true,
                                                                rockControl);
                biomeSpawnRules.add(mRockRule);

                // Spawn some Snow uniformly
                EntitySpawnRule mSnowRule = new EntitySpawnRule(tile -> new SnowClump(tile, false), 0.07, 30, 200, biome);
                biomeSpawnRules.add(mSnowRule);

                break;

            case "desert":

                // Create a new perlin noise map
                SpawnControl cactiControl = x -> (x * x * x * x) / 4.0;
                EntitySpawnRule cactiRule =
                        new EntitySpawnRule(tile -> new DesertCacti(tile, true), biome, true, cactiControl);
                biomeSpawnRules.add(cactiRule);
                break;

            case "snowy_mountains":

                // Create a new perlin noise map
                SpawnControl sSnowControl = x -> x / 2 + 0.15;
                EntitySpawnRule sSnowRule =
                        new EntitySpawnRule(tile -> new SnowClump(tile, false), biome, true, sSnowControl);
                biomeSpawnRules.add(sSnowRule);

                // Spawn some Snow Shrubs uniformly
                EntitySpawnRule snowShrubRule = new EntitySpawnRule(tile -> new SnowShrub(tile, true), 0.07, 20, 200, biome);
                biomeSpawnRules.add(snowShrubRule);

                break;
            }

            spawnRules.put(biome, biomeSpawnRules);
        }

        return spawnRules;
    }

    /**
     * Creates a world based on the values set in the builder
     * 
     * @return A world
     */
    public World getWorld() {
        World world;

        if (staticEntities) {
            worldParameters.setGenerateSpawnRules(this::generateStartEntities);
        } else {
            worldParameters.setGenerateSpawnRules(generatedWorld -> {
                HashMap<AbstractBiome, List<EntitySpawnRule>> rules = new HashMap<>();
                for (AbstractBiome biome : generatedWorld.getBiomes()) {
                    rules.put(biome, Collections.emptyList());
                }
                return rules;
            });
        }

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

        return world;
    }
}
