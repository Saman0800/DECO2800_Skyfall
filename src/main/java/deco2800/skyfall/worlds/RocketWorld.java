package deco2800.skyfall.worlds;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Cube;
import deco2800.skyfall.util.WorldUtil;

import java.util.List;
import java.util.Random;

public class RocketWorld extends AbstractWorld implements TouchDownObserver {
    private static final int RADIUS = 4;
    private static final int WORLD_SIZE = 10;
    private static final int NODE_SPACING = 5;

    private boolean generated = false;
    private PlayerPeon player;

    @Override
    protected void generateWorld() {

        int nodeCount = (int) Math.round(Math.pow((float)WORLD_SIZE / (float)NODE_SPACING, 2));

        // TODO: if nodeCount is less than the number of biomes, throw an exception

        Random random = new Random();

//        for (int i = 0; i < nodeCount; i++) {
//            float x = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
//            float y = (float) (random.nextFloat() - 0.5) * 2 * WORLD_SIZE;
//            worldGenNodes.add(new WorldGenNode(x, y));
//        }

        //Generating the biome
        AbstractBiome biome = new MountainBiome();

        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= RADIUS) {
                    float oddCol = (q % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);
//                    String type = "grass_" + elevation;
                    Tile tile = new Tile(biome, q, r+oddCol);
                    tiles.add(tile);
                    biome.addTile(tile);
                }
            }
        }

        //Setting all the textures
        biome.setTileTextures();

        // Create the entities in the game
        player = new PlayerPeon(0f, 0f, 0.05f);
        addEntity(player);

        EnemyEntity spider=new Spider(-4f,1f,"spider","spider");
        addEntity(spider);
        EnemyEntity robot=new Robot(-4f,-2f,"robot","robot");
        addEntity(robot);

        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);
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
