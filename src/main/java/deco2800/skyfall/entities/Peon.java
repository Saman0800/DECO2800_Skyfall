package deco2800.skyfall.entities;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TaskPool;
import deco2800.skyfall.tasks.AbstractTask;
import deco2800.skyfall.tasks.MovementTask;

public class Peon extends AgentEntity implements Tickable {
	protected transient AbstractTask task;

	// Name of the character
	private String name;

	// Health of the character
	private int health;

	public Peon() {
		super();
		this.setTexture("spacman_ded");
		this.setObjectName("Peon");
		this.setHeight(1);
		this.speed = 0.05f;
	}

	/**
	 * Peon constructor
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
	}

	/**
	 * Returns health of character
	 * @return health of character
	 */
	public int getHealth() {
		return health;
	}

	@Override
	public void onTick(long i) {
		if(task != null && task.isAlive()) {
			if(task.isComplete()) {
				this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
				
				//Resetting moving and angle once Peon has stopped
				if (task instanceof MovementTask) {
                    this.isMoving = false;
                    this.angle = 0;
                }
			}

            task.onTick(i);
		} else {
			this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
		}
	}
	
	public AbstractTask getTask() {
		return task;
	}
}
