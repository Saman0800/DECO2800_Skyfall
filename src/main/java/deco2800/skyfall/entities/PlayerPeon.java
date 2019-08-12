package deco2800.skyfall.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.observers.KeyUpObserver;
import deco2800.skyfall.util.HexVector;

public class PlayerPeon extends Peon implements KeyDownObserver, KeyUpObserver, Tickable {

    protected Vector2 direction;
    protected float currentSpeed;
    private boolean MOVE_UP = false;
    private boolean MOVE_LEFT = false;
    private boolean MOVE_RIGHT = false;
    private boolean MOVE_DOWN = false;
    /**
     * PlayerPeon Constructor
     *
     * @param row the row position in the world
     * @param col the column position in the world
     * @param speed the player's speed
     */
    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.direction = new Vector2(row, col);
        this.direction.limit2(0.05f);
        System.out.println(speed);
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
        if (MOVE_UP){this.direction.add(0.0f, speed);}
        if (MOVE_LEFT){this.direction.sub(speed, 0.0f);}
        if (MOVE_DOWN){this.direction.sub(0.0f, speed);}
        if (MOVE_RIGHT){this.direction.add(speed, 0.0f);}
    }

    /**
     *
     * @param i
     */
	@Override
    public void onTick(long i){
        updateMoveVector();
        this.setCurrentSpeed(this.direction.len());
        this.moveTowards(new HexVector(this.direction.x, this.direction.y));
        //System.out.printf("(%s : %s) diff: (%s, %s)%n", this.direction, this.getPosition(), this.direction.x - this.getCol(), this.direction.y - this.getRow());
        //System.out.printf("%s%n", this.currentSpeed);

    }

    @Override
    public void moveTowards(HexVector destination) {
        position.moveToward(destination, this.currentSpeed);
    }

    /**
     * Sets the Player's current movement speed.
     *
     * @param cSpeed the speed for the player to currently move at.
     */
    private void setCurrentSpeed(float cSpeed){
        this.currentSpeed = cSpeed;
    }

    /**
     * Sets the appropriate movement flags to true on keyDown
     *
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                MOVE_UP = true;
                break;
            case Input.Keys.A:
                MOVE_LEFT = true;
                break;
            case Input.Keys.S:
                MOVE_DOWN = true;
                break;
            case Input.Keys.D:
                MOVE_RIGHT = true;
                break;
        }
    }

    /**
     * Sets the appropriate movement flags to false on keyUp
     *
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        switch(keycode){
            case Input.Keys.W:
                MOVE_UP = false;
                break;
            case Input.Keys.A:
                MOVE_LEFT = false;
                break;
            case Input.Keys.S:
                MOVE_DOWN = false;
                break;
            case Input.Keys.D:
                MOVE_RIGHT = false;
                break;
        }
    }
}
