package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.BlueprintShop;
import deco2800.skyfall.entities.Chest;
import deco2800.skyfall.entities.weapons.Axe;
import deco2800.skyfall.entities.weapons.Bow;
import deco2800.skyfall.entities.weapons.Spear;
import deco2800.skyfall.entities.weapons.Sword;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.ChestManager;
import deco2800.skyfall.resources.LootRarity;
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

    // Contains parameters for the world
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
        worldParameters.setNumOfLakes(worldParameters.getNumOfLakes() + 1);
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
     * @param seed the seed for the world
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
        worldParameters.setNoRivers(worldParameters.getNoRivers() + 1);
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

    private void spawnChests(World world, Random random, List<EntitySpawnRule> biomeSpawnRules) {
        // Spawn chests
        EntitySpawnRule chestRule = new EntitySpawnRule(tile -> new Chest(tile, true, ChestManager.generateRandomLoot(
                (int) Math.floor(NoiseGenerator
                        .fade(world.getStaticEntityNoise().getOctavedPerlinValue(tile.getCol(), tile.getRow()), 2)) + 5,
                LootRarity.LEGENDARY)), random.nextInt(), 0.02);
        biomeSpawnRules.add(chestRule);
    }

    private void spawnBlueprintShop(World world, Random random, List<EntitySpawnRule> biomeSpawnRules) {
        // Spawn chests
        EntitySpawnRule chestRule = new EntitySpawnRule(tile -> new BlueprintShop(tile, true), random.nextInt(), 0.02);
        biomeSpawnRules.add(chestRule);
    }

    /**
     * Generates the static entities in a world
     *
     * @param world The world that will get static entities
     * @author Micheal CC
     */
    Map<AbstractBiome, List<EntitySpawnRule>> generateStartEntities(World world) {
        HashMap<AbstractBiome, List<EntitySpawnRule>> spawnRules = new HashMap<>();

        long worldSeed = world.getSeed();
        EntitySpawnRule.setNoiseSeed(worldSeed);

        Random random = new Random(worldSeed);

        // FIXME:Ontonator Make this work properly.
        // You can't spawn things here.
        // Tile torchTile1 = world.getTile(0.0f, 5.0f);
        // world.addEntity(new TikiTorch(torchTile1, false));
        //
        // Tile torchTile2 = world.getTile(0.0f, -3.0f);
        // world.addEntity(new TikiTorch(torchTile2, false));

        for (AbstractBiome biome : world.getBiomes()) {
            ArrayList<EntitySpawnRule> biomeSpawnRules = new ArrayList<>();
            switch (biome.getBiomeName()) {
            case "forest":
                generateForestEntities(biomeSpawnRules, random, world);
                break;
            case "mountain":
                generateMountainEntities(biomeSpawnRules, random, world);
                break;
            case "desert":
                generateDesertEntities(biomeSpawnRules, random, world);
                break;
            case "snowy_mountains":
                generateSnowyMountainsEntities(biomeSpawnRules, random, world);
                break;
            case "volcanic_mountains":
                generateVolcanicMountainsEntities(biomeSpawnRules, random, world);
                break;
            case "swamp":
                generateSwampEntities(biomeSpawnRules, random, world);
                break;
            case "ocean":
                generateOceanEntities(biomeSpawnRules, random, world);
                break;
            default:
                break;
            }
            spawnRules.put(biome, biomeSpawnRules);
        }

        return spawnRules;
    }

    private void generateOceanEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {
        EntitySpawnRule shipwrecks = new EntitySpawnRule(tile -> new Shipwrecks(tile, true), random.nextInt(), 0.003);
        biomeSpawnRules.add(shipwrecks);
    }
    private void generateForestEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {

        long worldSeed = world.getSeed();

        // Spawn some swords
        EntitySpawnRule swordRule = new EntitySpawnRule(tile -> new Sword(tile, true), random.nextInt(), 0.005);
        biomeSpawnRules.add(swordRule);

        // Spawn some axes
        EntitySpawnRule axeRule = new EntitySpawnRule(tile -> new Axe(tile, true), random.nextInt(), 0.007);
        biomeSpawnRules.add(axeRule);

        // Create a new perlin noise map
        SpawnControl treeControl = x -> (x * x) / 3d + 0.01;
        EntitySpawnRule treeRule = new EntitySpawnRule(tile -> new ForestTree(tile, true), random.nextInt(), true,
                treeControl);
        treeRule.setLimitAdjacent(true);
        biomeSpawnRules.add(treeRule);

        spawnChests(world, random, biomeSpawnRules);
        spawnBlueprintShop(world, random, biomeSpawnRules);

        // Spawn some ForestShrub uniformly
        EntitySpawnRule forestShrub = new EntitySpawnRule(tile -> new ForestShrub(tile, true), random.nextInt(), 0.03);
        biomeSpawnRules.add(forestShrub);

        // Spawn some Rocks uniformly
        EntitySpawnRule rockRule = new EntitySpawnRule(tile -> new ForestRock(tile, true), random.nextInt(), 0.02);
        rockRule.setLimitAdjacent(true);
        biomeSpawnRules.add(rockRule);

        // This generator will cause the mushrooms to clump togteher more
        NoiseGenerator mushroomGen = new NoiseGenerator(new Random(worldSeed).nextLong(), 10, 20, 0.9);
        SpawnControl mushroomControl = x -> (x * x * x * x) / 7d;
        EntitySpawnRule mushroomRule = new EntitySpawnRule(tile -> new ForestMushroom(tile, false), random.nextInt(),
                true, mushroomControl);
        mushroomRule.setNoiseGenerator(mushroomGen);
        mushroomRule.setLimitAdjacent(true);
        biomeSpawnRules.add(mushroomRule);

        return;
    }

    private void generateMountainEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {

        // Spawn some spears
        EntitySpawnRule spearRule = new EntitySpawnRule(tile -> new Spear(tile, true), random.nextInt(), 0.005);
        biomeSpawnRules.add(spearRule);

        // Create a new perlin noise map
        SpawnControl cubic = x -> (x * x * x) / 6 + 0.01;
        EntitySpawnRule mTreeControl = new EntitySpawnRule(tile -> new MountainTree(tile, true), random.nextInt(), true,
                cubic);
        mTreeControl.setLimitAdjacent(true);
        biomeSpawnRules.add(mTreeControl);

        spawnChests(world, random, biomeSpawnRules);

        // Create a new perlin noise map
        SpawnControl rockControl = x -> (x * x * x * x) / 5.0;
        EntitySpawnRule mRockRule = new EntitySpawnRule(tile -> new MountainRock(tile, true), random.nextInt(), true,
                rockControl);
        mRockRule.setLimitAdjacent(true);
        biomeSpawnRules.add(mRockRule);

        // Spawn some Snow uniformly
        EntitySpawnRule mSnowRule = new EntitySpawnRule(tile -> new SnowClump(tile, false), random.nextInt(), 0.02);
        biomeSpawnRules.add(mSnowRule);

        return;
    }

    private void generateDesertEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {

        // Spawn some axes
        EntitySpawnRule axeRule2 = new EntitySpawnRule(tile -> new Axe(tile, true), random.nextInt(), 0.005);
        biomeSpawnRules.add(axeRule2);

        // Create a new perlin noise map
        SpawnControl cactiControl = x -> (x * x * x) / 8.0;
        EntitySpawnRule cactiRule = new EntitySpawnRule(tile -> new DesertCacti(tile, true), random.nextInt(), true,
                cactiControl);
        biomeSpawnRules.add(cactiRule);

        // Spawn some bones
        EntitySpawnRule boneRule = new EntitySpawnRule(tile -> new Bone(tile, true), random.nextInt(), 0.004);
        biomeSpawnRules.add(boneRule);

        // Spawn desert shrubbery
        EntitySpawnRule dShrubRule = new EntitySpawnRule(tile -> new DesertShrub(tile, true), random.nextInt(), 0.008);
        biomeSpawnRules.add(dShrubRule);

        // Spawn desert rocks
        EntitySpawnRule dRockRule = new EntitySpawnRule(tile -> new DesertRock(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(dRockRule);

        // Spawn desert environment
        EntitySpawnRule DesertEnvironment = new EntitySpawnRule(tile -> new DesertEnvironment(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(DesertEnvironment);

        //Spawn ruined robot
        EntitySpawnRule ruinedRobot = new EntitySpawnRule(tile -> new ruinedRobot(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(ruinedRobot);

        //Spawn ruined city
        EntitySpawnRule ruinedCity = new EntitySpawnRule(tile -> new ruinedCity(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(ruinedCity);

        return;
    }

    private void generateSnowyMountainsEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {

        // Spawn some bows
        EntitySpawnRule bowRule = new EntitySpawnRule(tile -> new Bow(tile, true), random.nextInt(), 0.005);
        biomeSpawnRules.add(bowRule);

        // Spawn some spears
        EntitySpawnRule spearRule2 = new EntitySpawnRule(tile -> new Spear(tile, true), random.nextInt(), 0.005);
        biomeSpawnRules.add(spearRule2);

        // Spawn some swords
        EntitySpawnRule swordRule2 = new EntitySpawnRule(tile -> new Sword(tile, true), random.nextInt(), 0.006);
        biomeSpawnRules.add(swordRule2);

        // Create a new perlin noise map
        SpawnControl sSnowControl = x -> (x * x * x * x * x * x) / 2d + 0.05;
        EntitySpawnRule sSnowRule = new EntitySpawnRule(tile -> new SnowClump(tile, false), random.nextInt(), true,
                sSnowControl);
        biomeSpawnRules.add(sSnowRule);

        // Spawn some Snow Shrubs uniformly
        EntitySpawnRule snowShrubRule = new EntitySpawnRule(tile -> new SnowShrub(tile, true), random.nextInt(), 0.0);
        biomeSpawnRules.add(snowShrubRule);

        return;

    }

    private void generateVolcanicMountainsEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {

        // Create a new perlin noise map
        SpawnControl cubic = x -> (x * x * x) / 6 + 0.01;
        EntitySpawnRule mTreeControl = new EntitySpawnRule(tile -> new VolcanicTree(tile, true), random.nextInt(), true,
                cubic);
        mTreeControl.setLimitAdjacent(true);
        biomeSpawnRules.add(mTreeControl);

        spawnChests(world, random, biomeSpawnRules);

        // Create a new perlin noise map
        SpawnControl rockControl = x -> (x * x * x * x) / 3.0;
        EntitySpawnRule mRockRule = new EntitySpawnRule(tile -> new VolcanicRock(tile, true), random.nextInt(), true,
                rockControl);
        mRockRule.setLimitAdjacent(true);
        biomeSpawnRules.add(mRockRule);

        // Spawn some Snow uniformly
        EntitySpawnRule vShrubRule = new EntitySpawnRule(tile -> new VolcanicShrub(tile, true), random.nextInt(), 0.02);
        biomeSpawnRules.add(vShrubRule);

        EntitySpawnRule leavesRule = new EntitySpawnRule(tile -> new Leaves(tile, false), random.nextInt(), 0.02);
        biomeSpawnRules.add(leavesRule);

        return;
    }

    private void generateSwampEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {

        long worldSeed = world.getSeed();

        // Create a new perlin noise map
        SpawnControl sTreeControl = x -> (x * x * x * x) / 3d + 0.01;
        EntitySpawnRule treeRule = new EntitySpawnRule(tile -> new SwampTree(tile, true), random.nextInt(), true,
                sTreeControl);
        biomeSpawnRules.add(treeRule);

        // Spawn some ForestShrub uniformly
        EntitySpawnRule swampShrubRule = new EntitySpawnRule(tile -> new SwampShrub(tile, true), random.nextInt(),
                0.04);
        biomeSpawnRules.add(swampShrubRule);

        // Spawn some Rocks uniformly
        EntitySpawnRule rockRule = new EntitySpawnRule(tile -> new SwampRock(tile, true), random.nextInt(), 0.015);
        biomeSpawnRules.add(rockRule);

        EntitySpawnRule moundRule = new EntitySpawnRule(tile -> new OrganicMound(tile, true), random.nextInt(), 0.01);
        moundRule.setLimitAdjacent(true);
        biomeSpawnRules.add(moundRule);

        // This generator will cause the mushrooms to clump together more
        NoiseGenerator mushroomGen = new NoiseGenerator(new Random(worldSeed).nextLong(), 10, 20, 0.9);
        SpawnControl mushroomControl = x -> (x * x * x * x) / 4d;
        EntitySpawnRule mushroomRule = new EntitySpawnRule(tile -> new ForestMushroom(tile, true), random.nextInt(),
                true, mushroomControl);
        mushroomRule.setNoiseGenerator(mushroomGen);
        biomeSpawnRules.add(mushroomRule);

        return;
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
