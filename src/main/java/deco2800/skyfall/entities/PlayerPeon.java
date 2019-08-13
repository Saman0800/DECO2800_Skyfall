package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class PlayerPeon extends Peon implements TouchDownObserver {

    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);
    }


	@Override
    public void onTick(long i) {
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }
    }

    /**
     * Attack with the weapon the character has equip.
     */
    public void attack() {
        //TODO: Need to calculate an angle that the character is facing. @Movement Team
        HexVector position = this.getPosition();

        //Spawn projectile in front of character for now.
        Projectile projectile = new Projectile("yellow_selection",
                "hitBox",
                position.getCol()-1,
                position.getRow(),
                1);

        //Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();
        //Add the projectile entity to the game world.
        manager.getWorld().addEntity(projectile);
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow left clicks to move player
        if (button == 0) {
            this.attack();
        }

        //attack

        //click to move logic replaced by WASD
        /*
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        this.task = new MovementTask(this, new HexVector (clickedPosition[0],clickedPosition[1]));
         */
    }
}
