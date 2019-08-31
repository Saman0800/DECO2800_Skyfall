package deco2800.skyfall.worlds;

import deco2800.skyfall.entities.*;
import com.badlogic.gdx.Gdx;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.entities.Rock;
import deco2800.skyfall.entities.LongGrass;
import deco2800.skyfall.entities.EntitySpawnRule;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Cube;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.biomes.*;
import deco2800.skyfall.worlds.generation.BiomeGenerator;
import deco2800.skyfall.worlds.generation.DeadEndGenerationException;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RocketWorld extends AbstractWorld implements TouchDownObserver {
    private boolean generated = false;

    long entitySeed;

    public RocketWorld(long seed, int worldSize, int nodeSpacing, int[] biomeSizes, int numOfLakes, int lakeSize) {
        super(seed, worldSize, nodeSpacing, biomeSizes, numOfLakes, lakeSize);
    }

    @Override
    protected void generateWorld(Random random) {
        this.entitySeed = random.nextLong();

        // World generation loop: restarts world generation if it reaches an
        // unresolvable layout
        while (true) {
            ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();
            ArrayList<Tile> tiles = new ArrayList<>();
            ArrayList<AbstractBiome> biomes = new ArrayList<>();

            int nodeCount = Math.round((float) worldSize * worldSize * 4 / nodeSpacing / nodeSpacing);
            // TODO: if nodeCount is less than the number of biomes, throw an exception

            for (int i = 0; i < nodeCount; i++) {
                // Sets coordinates to a random number from -WORLD_SIZE to WORLD_SIZE
                float x = (float) (random.nextFloat() - 0.5) * 2 * worldSize;
                float y = (float) (random.nextFloat() - 0.5) * 2 * worldSize;
                worldGenNodes.add(new WorldGenNode(x, y));
            }

            // Apply Delaunay triangulation to the nodes, so that vertices and
            // adjacencies can be calculated. Also apply Lloyd Relaxation twice
            // for more smooth looking polygons
            try {
                WorldGenNode.calculateVertices(worldGenNodes, worldSize);
                WorldGenNode.lloydRelaxation(worldGenNodes, 2, worldSize);
            } catch (WorldGenException e) {
                continue;
            }

            for (int q = -worldSize; q <= worldSize; q++) {
                for (int r = -worldSize; r <= worldSize; r++) {
                    // if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <=
                    // worldSize) {
                    float oddCol = (q % 2 != 0 ? 0.5f : 0);

                    Tile tile = new Tile(q, r + oddCol);
                    tiles.add(tile);
                    // }
                }
            }
            // TODO Fix this.
            generateNeighbours(tiles);

            try {
                WorldGenNode.assignTiles(worldGenNodes, tiles, random, nodeSpacing);
                WorldGenNode.removeZeroTileNodes(worldGenNodes, worldSize);
                WorldGenNode.assignNeighbours(worldGenNodes);
            } catch (WorldGenException e) {
                continue;
            }
            biomes.add(new ForestBiome());
            biomes.add(new DesertBiome());
            biomes.add(new MountainBiome());
            biomes.add(new OceanBiome());

            try {
                BiomeGenerator.generateBiomes(worldGenNodes, random, biomeSizes, biomes, numOfLakes, lakeSize);
            } catch (NotEnoughPointsException | DeadEndGenerationException e) {
                continue;
            }

            this.worldGenNodes.addAll(worldGenNodes);
            this.tiles.addAll(tiles);
            this.biomes.addAll(biomes);

            break;
        }

        // Create the entities in the game
        // player = new PlayerPeon(0f, 0f, 0.05f);
        // addEntity(player);

        // Generate items that will be present on game start up
        generateStartEntities(this);

        // MainCharacter is now being put into the game instead of PlayerPeon
        MainCharacter testCharacter = new MainCharacter(0f, 0f, 0.05f, "Main Piece", 10);
        addEntity(testCharacter);

        EnemyEntity spider=new Spider(-4f,1f, testCharacter);
        addEntity(spider);
        EnemyEntity robot=new Robot(-4f,-2f, testCharacter);
        addEntity(robot);
        EnemyEntity stone=new Stone(-6f,1f,testCharacter);
        addEntity(stone);

        //EnemyEntity treeman=new Treeman(2f,2f);
        //addEntity(treeman);

        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }

    protected void generateStartEntities(AbstractWorld world) {

        Tile tileRock = getTile(0.0f, 1.0f);
        Rock startRock = new Rock(tileRock, true);
        Tree startTree = new Tree(tileRock, true);
        LongGrass startGrass = new LongGrass(tileRock, true);

        for (AbstractBiome biome : this.getBiomes()) {

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

    @Override
    public void onTick(long i) {
        super.onTick(i);

        if (!generated) {
            generated = true;
        }
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow right clicks to collect resources
        if (button != 1) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        Tile tile = getTile(clickedPosition[0], clickedPosition[1]);

        if (tile == null) {
            return;
        }
        // todo: more efficient way to find entities
        for (AbstractEntity entity : getEntities()) {
            if (!tile.getCoordinates().equals(entity.getPosition())) {
                continue;
            }

            if (entity instanceof Harvestable) {
                removeEntity(entity);
                List<AbstractEntity> drops = ((Harvestable) entity).harvest(tile);

                for (AbstractEntity drop : drops) {
                    addEntity(drop);
                }
            }
        }
    }
}
