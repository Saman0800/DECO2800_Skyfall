package deco2800.skyfall.worlds;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.BowMan;
import deco2800.skyfall.entities.Collectable;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.entities.WoodCube;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.Cube;
import deco2800.skyfall.util.WorldUtil;

import java.util.List;
import java.util.Random;

public class RocketWorld extends AbstractWorld implements TouchDownObserver {
    private static final int RADIUS = 5;

    private boolean generated = false;
    private PlayerPeon player;
    private BowMan bowMan;

    @Override
    protected void generateWorld() {
        Random random = new Random();
        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= RADIUS) {
                    float oddCol = (q % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);
                    String type = "grass_" + elevation;

                    tiles.add(new Tile(type, q, r + oddCol));
                }
            }
        }

        // Create the entities in the game
        player = new PlayerPeon(0f, 0f, 0.05f);
        addEntity(player);

        bowMan = new BowMan(0f, 3.5f);
        addEntity(bowMan);

        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        //Collision detection for entities
        for (AbstractEntity e1 : this.getEntities()) {
            e1.onTick(0);

            Collider c1 = e1.getCollider();
            boolean collided = false;
            for (AbstractEntity e2 : this.getEntities()) {
                Collider c2 = e2.getCollider();

                if (e1 != e2 && c1.overlaps(c2)) {
                    collided = true;

                    //collision handler
                    this.handleCollision(e1, e2);
                    System.out.println("Collision!");

                    break;
                }
            }
            //no collision


//            if (e instanceof Collectable) {
//                if (e.collidesWith(player)) {
//                    removeEntity(e);
//                }
//            }
        }

//        if (!generated) {
//            Tile tile = getTile(1f, 2.5f);
//            addEntity(new Tree(tile, true));
//
//            generated = true;
//        }
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
