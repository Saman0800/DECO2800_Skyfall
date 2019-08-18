package deco2800.skyfall.entities;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.*;
import deco2800.skyfall.util.HexVector;

public class PlayerPeon extends Peon implements KeyDownObserver,
        KeyUpObserver, Tickable {

    public SoundManager soundManager = GameManager.get().getManager(SoundManager.class);
    protected Vector2 direction;
    protected float currentSpeed;

    private boolean MOVE_UP = false;
    private boolean MOVE_LEFT = false;
    private boolean MOVE_RIGHT = false;
    private boolean MOVE_DOWN = false;

    private Projectile hitBox;

    /**
     * PlayerPeon Constructor
     * @param row the row position in the world
     * @param col the column position in the world
     * @param speed the player's speed
     */
    public PlayerPeon(float row, float col, float speed, String name,
                      int health) {
        super(row, col, speed, name, health);
        this.direction = new Vector2(row, col);
        this.direction.limit2(0.05f);
        System.out.println(speed);
    }

    /**
     * The basic test hitbox created when a player clicks the mouse.
     * If an enemy is in this hitbox, they will be damaged once.
     */
    public PlayerPeon(float row, float col, float speed) {
//        super(row, col, speed, name, health);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyUpListener(this);
    }

    /**
     * Calculates the new movement point depending on what movement keys are held down.
     */
    private void updateMoveVector() {
        if (MOVE_UP) {
            this.direction.add(0.0f, speed);
        }
        if (MOVE_LEFT) {
            this.direction.sub(speed, 0.0f);
        }
        if (MOVE_DOWN) {
            this.direction.sub(0.0f, speed);
        }
        if (MOVE_RIGHT) {
            this.direction.add(speed, 0.0f);
        }
    }

    /**
     * @param i
     */
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
        this.hitBox = new Projectile("slash",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1, 1);

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
    public void moveTowards(HexVector destination){
        position.moveToward(destination, this.currentSpeed);
    }

    public void notifyTouchDown ( int screenX, int screenY, int pointer,
                                  int button) {
        //TODO: Add game state conditions so player does not always attack.
        // I.e. when menu is open this should be ignored.
        //System.out.println(screenX);
        //System.out.println(screenY);

        // Comment out click to move controls when merging into master and
        // uncomment below.

        if (button == 1) {
            this.attack();
        }
//        else if (button == 1) {
//            this.specialAttack();
//        }
    }
        //Click to move logic replaced by WASD controls.

    /**
     * Sets the Player's current movement speed.
     * @param cSpeed the speed for the player to currently move at.
     */
    private void setCurrentSpeed ( float cSpeed){
        this.currentSpeed = cSpeed;
//        this.task = new MovementTask(this, new HexVector(clickedPosition[0],
//                clickedPosition[1]));
    }

    /**
     * Sets the appropriate movement flags to true on keyDown
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown ( int keycode){
        switch (keycode) {
            case Input.Keys.W:
                System.out.println("yes");
                MOVE_UP = true;
                SoundManager.loopSound("people_walk_normal");
                break;
            case Input.Keys.A:
                MOVE_LEFT = true;
                SoundManager.loopSound("people_walk_normal");
                break;
            case Input.Keys.S:
                MOVE_DOWN = true;
                SoundManager.loopSound("people_walk_normal");
                break;
            case Input.Keys.D:
                MOVE_RIGHT = true;
                SoundManager.loopSound("people_walk_normal");
                break;
        }
    }

    /**
     * Sets the appropriate movement flags to false on keyUp
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp ( int keycode){
        switch (keycode) {
            case Input.Keys.W:
                MOVE_UP = false;
                SoundManager.stopSound("people_walk_normal");
                break;
            case Input.Keys.A:
                MOVE_LEFT = false;
                SoundManager.stopSound("people_walk_normal");
                break;
            case Input.Keys.S:
                MOVE_DOWN = false;
                SoundManager.stopSound("people_walk_normal");
                break;
            case Input.Keys.D:
                MOVE_RIGHT = false;
                SoundManager.stopSound("people_walk_normal");
                break;
        }
    }
}
