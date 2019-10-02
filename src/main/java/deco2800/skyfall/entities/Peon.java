package deco2800.skyfall.entities;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.tasks.*;

/**
 * Base class of character in game where main characters and enemies will
 * inherit from
 */
public abstract class Peon extends AgentEntity implements Tickable {
	// Task being completed by character
	private transient AbstractTask task;

	// Name of the character
	private String name;

	// Health of the character
	private int health;
	// Max Health of the character
	private int maxHealth;

	// Boolean of whether character is dead
	private int deaths;

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
			this.maxHealth = 10;
		} else {
			this.health = health;
			this.maxHealth = health;
		}
		System.out.println(name + " has " + maxHealth);
		this.deaths = 0;
	}

	public Peon(float row, float col, float speed, String name, int health,
				String fixtureDef) {
		super(row, col, 3, speed, fixtureDef);
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

		this.deaths = 0;
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
		int currentHealth = this.getHealth();

		currentHealth += amount;

		this.health = currentHealth;


		if(currentHealth > maxHealth) {
			this.health = maxHealth;
		} else {
			this.health += amount;
		}

		if (this instanceof MainCharacter && this.isDead()) {
			this.health = currentHealth;
			this.deaths += 1;
		} else if (this.isDead()){
			this.health = 0;
			this.deaths += 1;
		}
	}

	/**
	 * Returns health of character
	 * @return health of character
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 * Returns max health of character
	 * @return - max health of character.
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 *
	 * @param newMaxHealth - New max health for the player.
	 */
	protected void setMaxHealth(int newMaxHealth) { this.maxHealth = newMaxHealth; }

	/**
	 * Checks if character is dead
	 * @return true if character's health is less than or equal to 0, else false
	 */
	public boolean isDead() {
		return this.getHealth() <= 0;
	}

	/**
	 * Sets character to be dead
	 */
	public boolean setDead(boolean is_dead) {
		if (is_dead) {
			health = 0;
		}
		return is_dead;
	}

	/**
	 * Gets the number of times the character has died
	 * @return amount of deaths of character
	 */
	public int getDeaths() {
		return this.deaths;
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
    @Override
    public void onTick(long i) {
        if(task != null && task.isAlive()) {
            if(task.isComplete()) {
                this.task = GameManager.getManagerFromInstance(TaskPool.class)
						.getTask(this);
            }
            task.onTick(i);
        } else {
            this.task = GameManager.getManagerFromInstance(TaskPool.class)
					.getTask(this);
        }
    }
}
