package deco2800.skyfall.worlds;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Collectable;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.entities.Rock;
import deco2800.skyfall.entities.EntitySpawnTable;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Cube;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.delaunay.WorldGenException;
import deco2800.skyfall.worlds.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.delaunay.WorldGenTriangle;

import javax.xml.ws.WebServiceException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class RocketWorld extends AbstractWorld implements TouchDownObserver {
    private static final int RADIUS = 4;
    private static final int WORLD_SIZE = 100;
    private static final int NODE_SPACING = 5;

    private boolean generated = false;
    private PlayerPeon player;

    // Generating the biome
    private AbstractBiome biome;

    public RocketWorld(long seed) {
        super(seed);
    }

    @Override
    protected void generateWorld(long seed) {

        int nodeCount = (int) Math.round(
                Math.pow((float) WORLD_SIZE * 2 / (float) NODE_SPACING, 2));

        // TODO: if nodeCount is less than the number of biomes, throw an exception

        Random random = new Random(seed);
        worldGenNodes = new CopyOnWriteArrayList<>();

        for (int i = 0; i < nodeCount; i++) {
            // Sets coordinates to a random number from -WORLD_SIZE to WORLD_SIZE
            float x = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
            float y = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
            worldGenNodes.add(new WorldGenNode(x, y));
        }

        // Apply Delaunay triangulation to the nodes, so that vertices and
        // adjacencies can be calculated. Also apply Lloyd Relaxation twice
        // for more smooth looking polygons
        try {
            WorldGenNode.calculateVertices(worldGenNodes, WORLD_SIZE);
            WorldGenNode.lloydRelaxation(worldGenNodes, 2, WORLD_SIZE);
        } catch (WorldGenException e) {
            // TODO handle this
        }

        // Create a new biome
        biome = new ForestBiome();

        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= RADIUS) {
                    float oddCol = (q % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);
                    // String type = "grass_" + elevation;
                    Tile tile = new Tile(biome, q, r + oddCol);
                    tiles.add(tile);
                    biome.addTile(tile);
                }
            }
        }

        addBiome(biome);

        // Create the entities in the game
        player = new PlayerPeon(0f, 0f, 0.05f);
        addEntity(player);

        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);

            if (e instanceof Collectable) {
                if (e.collidesWith(player)) {
                    removeEntity(e);
                }
            }
        }

        if (!generated) {
            Tile tile = getTile(1f, 2.5f);
            addEntity(new Tree(tile, true));

            generated = true;

            Tile tileRock = getTile(0.0f, 1.0f);
            Rock startRock = new Rock(tileRock, true);
            // EntitySpawnTable rockSpawnRule = new EntitySpawnTable();
            EntitySpawnTable.spawnEntities(startRock, 0.2, biome);
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
