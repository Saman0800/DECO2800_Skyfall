package deco2800.skyfall.entities;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.tasks.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class of character in game where main characters and enemies will
 * inherit from
 */
public abstract class Peon extends AgentEntity implements Tickable {
	// Task being completed by character
	protected transient AbstractTask task;

	// Name of the character
	private String name;

	// Health of the character
	private int health;
	// Max Health of the character
	private int maxHealth;

	// boolean of whether character is dead
	private int deaths;

	private static final String CHARACTER = "spacman_ded";

	private final Logger logger =
			LoggerFactory.getLogger(Peon.class);

	/**
	 * Constructor with no parameters
	 */
	@SuppressWarnings("WeakerAccess")
	public Peon() {
		super();
		this.setTexture(CHARACTER);
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
	}

	/**
	 * Peon constructor with parameters
     */
	public Peon(float row, float col, float speed, String name, int health) {
		super(row, col, 3, speed);
		this.setTexture(CHARACTER);

		if (name == null || name.equals("")) {
			setName("DEFAULT");
		} else {
			setName(name);
		}

		if (health < 1){
			this.health = 50;
			this.maxHealth = 50;
		} else {
			this.health = health;
			this.maxHealth = health;
		}
		String info = String.format("%s has %d", name, maxHealth);
		logger.info(info);
		this.deaths = 0;
	}

	@SuppressWarnings("WeakerAccess")
	public Peon(float row, float col, float speed, String name, int health,
				String fixtureDef) {
		super(col, row, 3, speed, fixtureDef);
		this.setTexture(CHARACTER);

		if (name == null || name.equals("")) {
			setName("DEFAULT");
		} else {
			setName(name);
		}

		if (health <= 0){
			this.health = 50;
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
		// Current health equals player's current health
		int currentHealth = this.getHealth();

		// Change current health if it is greater than max health
		if(currentHealth > maxHealth) {
			currentHealth = maxHealth;
		}

		// Add health to current health
		currentHealth += amount;
		this.health = currentHealth;

		// Check if player is dead
		if (this.isDead()) {
			health = 0;
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
		return this.getHealth() < 1;
	}

	/**
	 * Sets character to be dead
	 */
	public boolean setDead(boolean isDead) {
		return isDead;
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


	/**
	 * Sets the health
	 * @param health The amount of health the Peon's health will be set to
	 */
	public void setHealth(int health){
    	this.health = health;
	}
}
