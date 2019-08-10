package deco2800.skyfall.entities;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.tasks.*;

/**
 * Base class of character in game where main characters and enemies will
 * inherit from
 */
public class Peon extends AgentEntity implements Tickable {
	// Task being completed by character
	protected transient AbstractTask task;

	// Name of the character
	private String name;

	// Health of the character
	private int health;

	// Boolean of whether character is dead
	private boolean is_dead;

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
	 * Increases of decreases the character's health by the given amount
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

	@Override
	/**
	 * Handles tick based stuff, e.g. movement
	 */
	public void onTick(long i) {
		if(task != null && task.isAlive()) {
			if(task.isComplete()) {
				this.task = GameManager.getManagerFromInstance(TaskPool.class)
						.getTask(this);
				
				// Resetting moving and angle once Peon has stopped
				if (task instanceof MovementTask) {
                    this.isMoving = false;
                    this.angle = 0;
                }
			}

            task.onTick(i);
		} else {
			this.task = GameManager.getManagerFromInstance(TaskPool.class)
					.getTask(this);
		}
	}

	/**
	 * Gets the task for the character
	 * @return task of character
	 */
	public AbstractTask getTask() {
		return task;
	}
}