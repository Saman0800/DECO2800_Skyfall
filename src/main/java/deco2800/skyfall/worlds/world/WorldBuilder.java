package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.BlueprintShop;
import deco2800.skyfall.entities.Chest;
import deco2800.skyfall.entities.enemies.Abductor;
import deco2800.skyfall.entities.enemies.Heavy;
import deco2800.skyfall.entities.enemies.Medium;
import deco2800.skyfall.entities.enemies.Scout;
import deco2800.skyfall.entities.fooditems.*;
import deco2800.skyfall.entities.weapons.Axe;
import deco2800.skyfall.entities.weapons.Bow;
import deco2800.skyfall.entities.weapons.Spear;
import deco2800.skyfall.entities.weapons.Sword;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.ChestManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.LootRarity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
                LootRarity.LEGENDARY)), random.nextInt(), 0.001);
        biomeSpawnRules.add(chestRule);
    }
    // spawn shop
    private void spawnBlueprintShop(Random random, List<EntitySpawnRule> biomeSpawnRules) {
        EntitySpawnRule shop = new EntitySpawnRule(tile -> new BlueprintShop(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(shop);
    }

    /**
     * The method to be used to spawn gold into a particular woorld
     *
     * @param random          Generates random integers
     * @param biomeSpawnRules The hashmap which stores all the biome spawn rules
     */
    private void spawnGold(Random random, List<EntitySpawnRule> biomeSpawnRules) {
        // Spawn gold pieces
        EntitySpawnRule goldRule = new EntitySpawnRule(tile -> new GoldPiece(tile, true), random.nextInt(), 0.002);
        biomeSpawnRules.add(goldRule);

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
                generateDesertEntities(biomeSpawnRules, random);
                break;
            case "snowy_mountains":
                generateSnowyMountainsEntities(biomeSpawnRules, random);
                break;
            case "volcanic_mountains":
                generateVolcanicMountainsEntities(biomeSpawnRules, random, world);
                break;
            case "swamp":
                generateSwampEntities(biomeSpawnRules, random, world);
                break;
            case "ocean":
                generateOceanEntities(biomeSpawnRules, random);
                break;
            default:
                break;
            }
            spawnRules.put(biome, biomeSpawnRules);
        }

        return spawnRules;
    }

    private void generateOceanEntities(List<EntitySpawnRule> biomeSpawnRules, Random random) {

        EntitySpawnRule shipwrecks = new EntitySpawnRule(tile -> new Shipwrecks(tile, true), random.nextInt(), 0.003);
        biomeSpawnRules.add(shipwrecks);
    }

    private void generateForestEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {
        foodItems(biomeSpawnRules, random);

        long worldSeed = world.getSeed();
        weaponsRule(biomeSpawnRules, random);


        // Create a new perlin noise map
        SpawnControl treeControl = x -> (x * x) / 3d + 0.01;
        EntitySpawnRule treeRule = new EntitySpawnRule(tile -> new ForestTree(tile, true), random.nextInt(), true,
                treeControl);
        treeRule.setLimitAdjacent(true);
        biomeSpawnRules.add(treeRule);

        spawnChests(world, random, biomeSpawnRules);
        spawnBlueprintShop(random, biomeSpawnRules);

        // Spawn some ForestShrub uniformly
        EntitySpawnRule forestShrub = new EntitySpawnRule(tile -> new ForestShrub(tile, true), random.nextInt(), 0.03);
        biomeSpawnRules.add(forestShrub);

        // Spawn some Rocks uniformly
        EntitySpawnRule rockRule = new EntitySpawnRule(tile -> new ForestRock(tile, true), random.nextInt(), 0.02);
        rockRule.setLimitAdjacent(true);
        biomeSpawnRules.add(rockRule);

        // Spawn gold pieces
        spawnGold(random, biomeSpawnRules);

        // This generator will cause the mushrooms to clump togteher more
        NoiseGenerator mushroomGen = new NoiseGenerator(new Random(worldSeed).nextLong(), 10, 20, 0.9);
        SpawnControl mushroomControl = x -> (x * x * x * x) / 7d;
        EntitySpawnRule mushroomRule = new EntitySpawnRule(tile -> new ForestMushroom(tile, false), random.nextInt(),
                true, mushroomControl);
        mushroomRule.setNoiseGenerator(mushroomGen);
        mushroomRule.setLimitAdjacent(true);
        biomeSpawnRules.add(mushroomRule);
    }
    // spawn new food items in the world
    private void foodItems(List<EntitySpawnRule> biomeSpawnRules, Random random) {
        EntitySpawnRule cherry = new EntitySpawnRule(tile -> new Cheery(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(cherry);

        EntitySpawnRule cheese = new EntitySpawnRule(tile -> new Cheese(tile, true), random.nextInt(), 0.002);
        biomeSpawnRules.add(cheese);

        EntitySpawnRule cake = new EntitySpawnRule(tile -> new Cake(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(cake);

        EntitySpawnRule biscuit = new EntitySpawnRule(tile -> new Biscuit(tile, true), random.nextInt(), 0.002);
        biomeSpawnRules.add(biscuit);

        EntitySpawnRule drumstick = new EntitySpawnRule(tile -> new Drumstick(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(drumstick);

        EntitySpawnRule curry = new EntitySpawnRule(tile -> new Curry(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(curry);
    }

    private void weaponsRule(List<EntitySpawnRule> biomeSpawnRules, Random random) {
        // Spawn some swords
        EntitySpawnRule swordRule = new EntitySpawnRule(tile -> new Sword(tile, true), random.nextInt(), 0.002);
        biomeSpawnRules.add(swordRule);

        // Spawn some axes
        EntitySpawnRule axeRule = new EntitySpawnRule(tile -> new Axe(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(axeRule);

        // Spawn some spears
        EntitySpawnRule spearRule = new EntitySpawnRule(tile -> new Spear(tile, true), random.nextInt(), 0.001);
        biomeSpawnRules.add(spearRule);

        // Spawn some bows
        EntitySpawnRule bowRule = new EntitySpawnRule(tile -> new Bow(tile, true), random.nextInt(), 0.003);
        biomeSpawnRules.add(bowRule);
    }

    private void generateMountainEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {
        weaponsRule(biomeSpawnRules, random);
        foodItems(biomeSpawnRules, random);

        // Create a new perlin noise map
        SpawnControl cubic = x -> (x * x * x) / 6.0 + 0.01;
        EntitySpawnRule mTreeControl = new EntitySpawnRule(tile -> new MountainTree(tile, true), random.nextInt(), true,
                cubic);
        mTreeControl.setLimitAdjacent(true);
        biomeSpawnRules.add(mTreeControl);

        // Spawn gold pieces
        spawnGold(random, biomeSpawnRules);

        spawnChests(world, random, biomeSpawnRules);
        spawnBlueprintShop(random, biomeSpawnRules);

        // Create a new perlin noise map
        SpawnControl rockControl = x -> (x * x * x * x) / 5.0;
        EntitySpawnRule mRockRule = new EntitySpawnRule(tile -> new MountainRock(tile, true), random.nextInt(), true,
                rockControl);
        mRockRule.setLimitAdjacent(true);
        biomeSpawnRules.add(mRockRule);

        // Spawn some Snow uniformly
        EntitySpawnRule mSnowRule = new EntitySpawnRule(tile -> new SnowClump(tile, false), random.nextInt(), 0.02);
        biomeSpawnRules.add(mSnowRule);
    }

    private void generateDesertEntities(List<EntitySpawnRule> biomeSpawnRules, Random random) {
        weaponsRule(biomeSpawnRules, random);
        foodItems(biomeSpawnRules, random);

        // Create a new perlin noise map
        SpawnControl cactiControl = x -> (x * x * x) / 8.0;
        EntitySpawnRule cactiRule = new EntitySpawnRule(tile -> new DesertCacti(tile, true), random.nextInt(), true,
                cactiControl);
        biomeSpawnRules.add(cactiRule);

        // Spawn gold pieces
        spawnGold(random, biomeSpawnRules);
        spawnBlueprintShop(random, biomeSpawnRules);

        SpawnControl rockControl = x -> (x * x * x * x) / 3.0;
        EntitySpawnRule mRockRule = new EntitySpawnRule(tile -> new VolcanicRock(tile, true), random.nextInt(), true,
                rockControl);
        mRockRule.setLimitAdjacent(true);
        biomeSpawnRules.add(mRockRule);

        SpawnControl cubic = x -> (x * x * x) / 6.0 + 0.01;
        EntitySpawnRule mTreeControl = new EntitySpawnRule(tile -> new VolcanicTree(tile, true), random.nextInt(), true,
                cubic);
        mTreeControl.setLimitAdjacent(true);
        biomeSpawnRules.add(mTreeControl);

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
        EntitySpawnRule desertEnvironment = new EntitySpawnRule(tile -> new DesertEnvironment(tile, true),
                random.nextInt(), 0.01);
        biomeSpawnRules.add(desertEnvironment);

        // Spawn ruined robot
        EntitySpawnRule ruinedRobot = new EntitySpawnRule(tile -> new RuinedRobot(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(ruinedRobot);

        // Spawn ruined city
        EntitySpawnRule ruinedCity = new EntitySpawnRule(tile -> new RuinedCity(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(ruinedCity);
    }

    private void generateSnowyMountainsEntities(List<EntitySpawnRule> biomeSpawnRules, Random random) {
        foodItems(biomeSpawnRules, random);

        // Spawn some bows
        EntitySpawnRule bowRule = new EntitySpawnRule(tile -> new Bow(tile, true), random.nextInt(), 0.03);
        biomeSpawnRules.add(bowRule);

        // Spawn some spears
        EntitySpawnRule spearRule = new EntitySpawnRule(tile -> new Spear(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(spearRule);

        // Spawn some swords
        EntitySpawnRule swordRule = new EntitySpawnRule(tile -> new Sword(tile, true), random.nextInt(), 0.01);
        biomeSpawnRules.add(swordRule);

        // Spawn gold pieces
        spawnGold(random, biomeSpawnRules);

        // Create a new perlin noise map
        SpawnControl sSnowControl = x -> (x * x * x * x * x * x) / 2d + 0.05;
        EntitySpawnRule sSnowRule = new EntitySpawnRule(tile -> new SnowClump(tile, false), random.nextInt(), true,
                sSnowControl);
        biomeSpawnRules.add(sSnowRule);

        // Spawn some Snow Shrubs uniformly
        EntitySpawnRule snowShrubRule = new EntitySpawnRule(tile -> new SnowShrub(tile, true), random.nextInt(), 0.0);
        biomeSpawnRules.add(snowShrubRule);
    }

    private void generateVolcanicMountainsEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {
        foodItems(biomeSpawnRules, random);

        // Spawn some swords
        EntitySpawnRule swordRule2 = new EntitySpawnRule(tile -> new Sword(tile, true), random.nextInt(), 0.03);
        biomeSpawnRules.add(swordRule2);

        // Create a new perlin noise map
        SpawnControl cubic = x -> (x * x * x) / 6.0 + 0.01;
        EntitySpawnRule mTreeControl = new EntitySpawnRule(tile -> new VolcanicTree(tile, true), random.nextInt(), true,
                cubic);
        mTreeControl.setLimitAdjacent(true);
        biomeSpawnRules.add(mTreeControl);

        // Spawn gold pieces
        spawnGold(random, biomeSpawnRules);

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
    }

    private void generateSwampEntities(List<EntitySpawnRule> biomeSpawnRules, Random random, World world) {
        long worldSeed = world.getSeed();

        // Spawn some spears
        EntitySpawnRule spearRule2 = new EntitySpawnRule(tile -> new Spear(tile, true), random.nextInt(), 0.03);
        biomeSpawnRules.add(spearRule2);

        // Create a new perlin noise map
        SpawnControl sTreeControl = x -> (x * x * x * x) / 3d + 0.01;
        EntitySpawnRule treeRule = new EntitySpawnRule(tile -> new SwampTree(tile, true), random.nextInt(), true,
                sTreeControl);
        biomeSpawnRules.add(treeRule);

        // Spawn some ForestShrub uniformly
        EntitySpawnRule swampShrubRule = new EntitySpawnRule(tile -> new SwampShrub(tile, true), random.nextInt(),
                0.04);
        biomeSpawnRules.add(swampShrubRule);

        // Spawn gold pieces
        spawnGold(random, biomeSpawnRules);

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
    }

    /**
     * The method to be used to spawn an electric enemies on a tile, while the
     * electric enemy is randomly chosen from Abductor, Heavy, Scout enemy types
     * with impact of factors.
     *
     * @param random       : an random generator
     * @param tile         : a tile that will spawn an enemy
     * @param world        : the game world
     * @param enemyScaling : scaling factor to enemy states
     */
    private void spawnAnElectricEnemyOnTile(Random random, Tile tile, World world, float enemyScaling) {
        // factor or ratio between Abductor, Heavy, Scout, Medium enemies
        int abductorFactor = 3;
        int heavyFactor = 3;
        int scoutFactor = 4;
        int mediumFactor = 2;
        int total = abductorFactor + heavyFactor + scoutFactor + mediumFactor;

        // check the tile whether is empty or not
        for (AbstractEntity entity : world.getEntities()) {
            if (entity.getPosition().equals(tile.getCoordinates())) {
                return;
            }
        }

        // spawn an random electric enemy on a empty tile only
        int start = 0;
        int factor = random.nextInt(total);
        // chance to choose abductor enemy
        if ((start <= factor) && (factor <= (start + abductorFactor))) {
            world.addEntity(new Abductor(tile.getCol(), tile.getRow(), enemyScaling, tile.getBiome().getBiomeName()));
            return;
        }
        start += abductorFactor;

        // chance to choose heavy enemy
        if ((start <= factor) && (factor <= (start + heavyFactor))) {
            world.addEntity(new Heavy(tile.getCol(), tile.getRow(), enemyScaling, tile.getBiome().getBiomeName()));
            return;
        }
        start += heavyFactor;

        // chance to choose scout enemy
        if ((start <= factor) && (factor <= (start + scoutFactor))) {
            world.addEntity(new Scout(tile.getCol(), tile.getRow(), enemyScaling, tile.getBiome().getBiomeName()));
            return;
        }
        start += scoutFactor;

        // chance to choose medium enemy
        if ((start <= factor) && (factor <= (start + mediumFactor))) {
            world.addEntity(new Medium(tile.getCol(), tile.getRow(), enemyScaling, tile.getBiome().getBiomeName()));
        }
    }

    /**
     * The method to be used to spawn electric enemies.
     *
     * @param random       : an random generator
     * @param chance       : chance to spawn an enemy
     * @param biome        : biome where enemy spawned on
     * @param world        : the game world
     * @param enemyScaling : scaling factor to enemy states
     */
    private void spawnEnemies(Random random, float chance, AbstractBiome biome, World world, float enemyScaling) {
        // NOTE: biome.getTiles() and world.getWorldGenNodes() return a list of loaded tiles only
        // NOTE: world.getLoadedChunks().size() has only 1 loaded chunk
        // calculate manually
        int worldSize = world.getWorldParameters().getWorldSize();
        int chunkSize = Chunk.CHUNK_SIDE_LENGTH;
        int chunkCoordSize = ((worldSize / 2) / chunkSize) - 1;
        int spawnGap = 3;

        for (int col = chunkCoordSize * -1; col <= chunkCoordSize; col += 2) {
            for (int row = chunkCoordSize * -1; row <= chunkCoordSize; row += 2) {
                // get tiles from each chunk
                for (Tile tile : world.getChunk(col, row).getTiles()) {
                    if (random.nextFloat() > chance) {
                        continue;
                    }
                    // get an available tile except the tile of initial start point
                    float offset = Math.abs(tile.getCol()) + Math.abs(tile.getRow()) + (tile.getCol() % 2) * 0.5f;
                    if ((offset > 1) && (offset % spawnGap == 0)
                            && tile.getBiome().getBiomeName().equals(biome.getBiomeName())) {
                        spawnAnElectricEnemyOnTile(random, tile, world, enemyScaling);
                    }
                }
            }
        }
    }

    public void generateNotStaticEntities(World world, float enemyScaling) {
        Random random = new Random(world.getSeed());

        for (AbstractBiome biome : world.getBiomes()) {
            switch (biome.getBiomeName()) {
            case "forest":
                spawnEnemies(random, 0.1f, biome, world, enemyScaling);
                break;
            case "mountain":
                spawnEnemies(random, 0.1f, biome, world, enemyScaling);
                break;
            case "desert":
                spawnEnemies(random, 0.1f, biome, world, enemyScaling);
                break;
            case "snowy_mountains":
                spawnEnemies(random, 0.1f, biome, world, enemyScaling);
                break;
            default:
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

        if ("single_player".equals(type)) {
            world = new World(worldParameters);
        } else {
            throw new IllegalArgumentException("The world type is not valid");
        }

        return world;
    }
}
