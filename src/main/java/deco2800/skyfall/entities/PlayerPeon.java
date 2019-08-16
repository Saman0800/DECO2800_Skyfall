package deco2800.skyfall.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

/**
 * The PlayerPeon represents a player in the game world.
 */
public class PlayerPeon extends Peon implements TouchDownObserver {

    /**
     * The basic test hitbox created when a player clicks the mouse.
     * If an enemy is in this hitbox, they will be damaged once.
     */
    private Projectile hitBox;

    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);
    }


	@Override
    public void onTick(long i) {
        this.updateCollider();

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
        //TODO: Need to calculate an angle that the character is facing.
        HexVector position = this.getPosition();

        //Spawn projectile in front of character for now.
        this.hitBox = new Projectile("",
                "",
                position.getCol()+1,
                position.getRow(),
                1);

        //Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();

        //Add the projectile entity to the game world.
        manager.getWorld().addEntity(this.hitBox);
    }

    /**
     * Perform a special attack with the right click.
     */
    public void specialAttack() {
        //TODO: release a more powerful attack.
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        //TODO: Add game state conditions so player does not always attack.
        // I.e. when menu is open this should be ignored.
        //System.out.println(screenX);
        //System.out.println(screenY);
        if (button == 0) {
            this.attack();
        } else if (button == 1) {
            this.specialAttack();
        }

        //Click to move logic replaced by WASD controls.
        /*
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        this.task = new MovementTask(this, new HexVector (clickedPosition[0],clickedPosition[1]));
         */
    }
}
