package deco2800.skyfall.entities;

import deco2800.skyfall.managers.*;
import deco2800.skyfall.tasks.*;

/**
 * Base character class for all other characters in the game (e.g. main,
 * enemies) to inherit from.
 */
public abstract class Character extends AgentEntity {
    protected transient AbstractTask task;

    // Name of the character
    private String name;

    // Health of the character
    private int health;

    public Character(float row, float col, float speed, String name, int health) {
        super(col, row, 3, 2f);

        // Might not need below, because this is abstract class
        this.setTexture("character");
        this.setHeight(1);
        this.setObjectName("Character");
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
            }
            task.onTick(i);
        } else {
            this.task = GameManager.getManagerFromInstance(TaskPool.class).getTask(this);
        }
    }
}