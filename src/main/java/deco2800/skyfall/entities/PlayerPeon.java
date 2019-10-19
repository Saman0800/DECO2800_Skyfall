package deco2800.skyfall.entities;


import com.badlogic.gdx.math.*;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.*;
import deco2800.skyfall.util.HexVector;

public class PlayerPeon extends Peon implements KeyDownObserver, KeyUpObserver, Tickable {

    protected Vector2 direction;
    protected float currentSpeed;


    /**
     * PlayerPeon Constructor
     * 
     * @param row   the row position in the world
     * @param col   the column position in the world
     * @param speed the player's speed
     */
    public PlayerPeon(float row, float col, float speed, String name, int health) {
        super(row, col, speed, name, health);
        this.direction = new Vector2(row, col);
        this.direction.limit2(0.05f);
    }

    /**
     * The basic test hitbox created when a player clicks the mouse. If an enemy is
     * in this hitbox, they will be damaged once.
     */
    public PlayerPeon(float row, float col, float speed) {
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
    }

    /**
     * @param i
     */
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

        // Spawn projectile in front of character for now.

        // Get AbstractWorld from static class GameManager.

        // Add the projectile entity to the game world.

    }


    @Override
    public void moveTowards(HexVector destination) {
        position.moveToward(destination, this.currentSpeed);
    }

    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {

        // cant move when click game menu
        if (screenX > 185 && screenX < 1095 && screenY > 615) {
            return;
        }

        if (button == 1) {
            this.attack();
        }
    }

    /**
     * Sets the appropriate movement flags to true on keyDown
     * 
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        // if game is paused, no sound
        if (GameManager.getPaused()) {
            return;
        }


    }

    /**
     * Sets the appropriate movement flags to false on keyUp
     *
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        // No needed implementation.
    }
}
