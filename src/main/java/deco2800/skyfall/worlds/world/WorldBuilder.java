package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.EntitySpawnRule;
import deco2800.skyfall.entities.EntitySpawnTable;
import deco2800.skyfall.entities.LongGrass;
import deco2800.skyfall.entities.Rock;
import deco2800.skyfall.entities.SpawnControl;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.OceanBiome;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Allows for step by step creation of a world
 */
public class WorldBuilder implements WorldBuilderInterface{


    //List of biomes
    private ArrayList<AbstractBiome> biomes;

    //List of biomes size
    private ArrayList<Integer> biomeSizes;

    //Seed that is going to be used for world gen
    private long seed;

    //Number of lakes
    private int numOfLakes;

    //Corresponding sizes of the lakes
    private ArrayList<Integer> lakeSizes;

    //The side length/2 of the world, (worldSize* 2)^2 to get the number of tiles
    private int worldSize;

    //The spacing/distance between the nodes
    private int nodeSpacing;

    //The entities in the world
    private CopyOnWriteArrayList<AbstractEntity> entities;

    //The world type, can either be single_player, server, tutorial or test
    private String type;

    //The number of rivers
    private int rivers;

    //The size of the rivers
    private int riverSize;

    //The size of the beach
    private int beachSize;


    //Determines whether static entities are on
    private boolean staticEntities;

    /**
     * Constructor for the WorldBuilder
     */
    public WorldBuilder(){
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
     * @param entity The entity to be added to the world
     */
    @Override
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
        System.out.println(entities);
    }


    /**
     * Adds a biome to the world
     * @param biome The biome to be added to the world
     * @param size The size of the biome to be added
     */
    @Override
    public void addBiome(AbstractBiome biome, int size) {
        biomes.add(biome);
        biomeSizes.add(size);
    }

    /**
     * Adds a lake to the world
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
     * @param nodeSpacing The node spacing
     */
    @Override
    public void setNodeSpacing(int nodeSpacing) {
        this.nodeSpacing = nodeSpacing;
    }

    /**
     * Sets a seed for the world
     * @param seed
     */
    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * Sets the type of world to be created
     * @param type A string value representing the type of world, can be single_player, server, test or tutorial
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Adds a single river to the world
     */
    public void addRiver(){
        rivers++;
    }

    /**
     * Sets the size of all the rivers
     * @param size The size which the rivers will be, in node width
     */
    public void setRiverSize(int size){
        riverSize = size;
    }

    /**
     * Sets the size of the beach
     * @param size The size which the beach will be, in tiles
     */
    public void setBeachSize(int size){
        beachSize = size;
    }

    /**
     * Sets whether static entities are off or on
     * @param staticEntities true representing static entities are on, false they are not
     */
    public void setStaticEntities(boolean staticEntities){
        this.staticEntities = staticEntities;
    }


    /**
     * Generates the static entities in a world
     * @param world The world that will get static entities
     * @author  Micheal CC
     */
    protected void generateStartEntities(World world) {

        Tile tileRock = world.getTile(0.0f, 1.0f);
        Rock startRock = new Rock(tileRock, true);
        Tree startTree = new Tree(tileRock, true);
        LongGrass startGrass = new LongGrass(tileRock, true);

        for (AbstractBiome biome : world.getBiomes()) {

            switch (biome.getBiomeName()) {
                case "forest":

                    // Create a new perlin noise map
                    SpawnControl pieceWise = x -> {
                        if ((0 < x) && (x <= 0.5)) {
                            return 0;
                        } else if ((0.5 < x) && (x <= 0.8)) {
                            return 0.05;
                        } else {
                            return 0.4;
                        }
                    };

                    // Create a new perlin noise map
                    SpawnControl cubic = x -> {
                        return x * x * x;
                    };

                    EntitySpawnRule treeRule = new EntitySpawnRule(biome, true, cubic);
                    EntitySpawnTable.spawnEntities(startTree, treeRule, world);
            }
        }
    }


    /**
     * Creates a world based on the values set in the builder
     * @return A world
     */
    public World getWorld(){
        //Converting the ArrayLists to arrays
        int[] biomeSizesArray = biomeSizes.stream().mapToInt(biomeSize -> biomeSize).toArray();
        int[] lakeSizesArray = lakeSizes.stream().mapToInt(lakeSize -> lakeSize).toArray();

        World world;

        switch (type){
            case "single_player":
                 world =  new World(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
                 break;
            case "tutorial":
                world =  new TutorialWorld(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
                break;
            case "test":
                world =  new TestWorld(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
                break;
            case "server":
                world =  new ServerWorld(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize, beachSize);
                break;
            default:
                throw new IllegalArgumentException("The world type is not valid");
        }

        if (staticEntities){
            generateStartEntities(world);
        }
        return world;
    }
}
