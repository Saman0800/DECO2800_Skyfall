package deco2800.skyfall.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.observers.KeyUpObserver;
import deco2800.skyfall.tasks.*;
import deco2800.skyfall.util.HexVector;

/**
 * Base class of character in game where main characters and enemies will
 * inherit from
 */
public class Peon extends AgentEntity implements KeyDownObserver, KeyUpObserver, Tickable {
	// Task being completed by character
	protected transient AbstractTask task;

	// Name of the character
	private String name;

	// Health of the character
	private int health;

	// Boolean of whether character is dead
	private boolean is_dead;

    protected Vector2 direction;
    protected float currentSpeed;
    private boolean MOVE_UP = false;
    private boolean MOVE_LEFT = false;
    private boolean MOVE_RIGHT = false;
    private boolean MOVE_DOWN = false;
	/**
	 * Constructor with no parameters
	 */
	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
	}

	/**
	 * Peon constructor with parameters
     */
	public Peon(float row, float col, float speed, String name, int health) {
		super(row, col, 3, speed);
		this.setTexture("spacman_ded");

		if (name == null || name.equals("")) {
			setName("DEFAULT");
		} else {
			setName(name);
		}

		if (health <= 0){
			this.health = 10;
		} else {
			this.health = health;
		}

		this.is_dead = false;
	}

	/**
	 * Sets name of the character
	 * @param name name being set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns name of character
	 * @return name of character
	 */
	public String getName() {
		return name;
	}

	/**
	 * Increases or decreases the character's health by the given amount
	 * @param amount change being made to player's health
	 */
	public void changeHealth(int amount) {
		this.health += amount;

		if (this.isDead()) {
			this.health = 0;
		}
	}

	/**
	 * Returns health of character
	 * @return health of character
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Checks if character is dead
	 * @return true if character's health is less than or equal to 0, else false
	 */
	public boolean isDead() {
		if (this.getHealth() <= 0) {
			return true;
		}
		return false;
	}


	/**
	 * Gets the task for the character
	 * @return task of character
	 */
	public AbstractTask getTask() {
		return task;
	}


    /**
     * Handles tick based stuff, e.g. movement
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
                movingAnimation = AnimationRole.MOVE_NORTH;
                break;
            case Input.Keys.A:
                MOVE_LEFT = true;
                movingAnimation = AnimationRole.MOVE_WEST;
                break;
            case Input.Keys.S:
                MOVE_DOWN = true;
                movingAnimation = AnimationRole.MOVE_SOUTH;
                break;
            case Input.Keys.D:
                MOVE_RIGHT = true;
                movingAnimation = AnimationRole.MOVE_EAST;
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
        movingAnimation = AnimationRole.NULL;
        switch(keycode){
            case Input.Keys.W:
                MOVE_UP = false;
                break;
            case Input.Keys.A:
                MOVE_LEFT = false;
                this.setTexture("__ANIMATION_mario_left:1");
                break;
            case Input.Keys.S:
                MOVE_DOWN = false;
                break;
            case Input.Keys.D:
                MOVE_RIGHT = false;
                this.setTexture("__ANIMATION_mario_right:1");
                break;
        }
    }
}

