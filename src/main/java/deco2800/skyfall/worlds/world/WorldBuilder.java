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

public class WorldBuilder implements WorldBuilderInterface{

    //Variables for nodes
    private ArrayList<WorldGenNode> worldGenNodes;

    //List of tiles
    private ArrayList<Tile> tiles;

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

    //Node spacing
    private int nodeSpacing;

    private CopyOnWriteArrayList<AbstractEntity> entities;

    //The world type, can either be single_player, server, tutorial or test
    private String type;

    private int rivers;

    private int riverSize;


    //Determines whether static entities are on
    private boolean staticEntities;

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


    @Override
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
        System.out.println(entities);
    }


    @Override
    public void addBiome(AbstractBiome biome, int size) {
        biomes.add(biome);
        biomeSizes.add(size);
    }

    @Override
    public void addLake(int size) {
        numOfLakes++;
        lakeSizes.add(size);
    }

    @Override
    public void setWorldSize(int size) {
        this.worldSize = size;
    }

    @Override
    public void setNodeSpacing(int nodeSpacing) {
        this.nodeSpacing = nodeSpacing;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setType(String type){
        this.type = type;
    }

    public void addRiver(){
        rivers++;
    }

    public void setRiverSize(int size){
        riverSize = size;
    }

    public void setStaticEntities(boolean staticEntities){
        this.staticEntities = staticEntities;
    }


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


    public World getWorld(){
        biomes.add(new OceanBiome());
        int[] biomeSizesArray = biomeSizes.stream().mapToInt(biomeSize -> biomeSize).toArray();
        int[] lakeSizesArray = lakeSizes.stream().mapToInt(lakeSize -> lakeSize).toArray();

        World world;

        switch (type){
            case "single_player":
                 world =  new World(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize);
                 break;
            case "tutorial":
                world =  new TutorialWorld(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize);
                break;
            case "test":
                world =  new TestWorld(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize);
                break;
            case "server":
                world =  new ServerWorld(seed, worldSize, nodeSpacing, biomeSizesArray,numOfLakes,lakeSizesArray, biomes,
                    entities, rivers, riverSize);
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
