package deco2800.skyfall.entities;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TaskPool;
import deco2800.skyfall.tasks.AbstractTask;

/**
 * A generic enemy class for the combat system.
 * The enemy class implements the ICombatEntity interface, allowing it to engage in combat
 * with other combat entities.
 */
public class Enemy extends AgentEntity implements ICombatEntity {

    /**
     * The task this Enemy is set to perform.
     */
    protected transient AbstractTask task;

    /**
     * The amount of damage this enemy deals.
     */
    private int damage;

    /**
     * The amount of health this enemy has.
     */
    private int health;

    /**
     * The amount of armour this enemy has.
     */
    private int armour;

    /**
     * A list of resistance attributes the enemy has.
     */
    private int[] resistanceAttributes;

    /**
     * A list of status indicators the enemy has.
     * Stun, sleep, paralyze... etc.
     */
    private String[] statusIndicators;

    /**
     * Construct a new enemy instance.
     *
     * @param col The column to spawn the enemy.
     * @param row The row to spawn the enemy.
     * @param texture The texture the enemy should be rendered with.
     * @param damage The amount of damage the enemy deals.
     * @param health The amount of health the enemy has.
     * @param armour The amount of armour the enemy has.
     */
    public Enemy(float col, float row, String texture, int damage, int health,
                 int armour) {

        super(col, row, 2, 0.2f);

        this.damage = damage;
        this.health = health;
        this.armour = armour;

        this.setTexture(texture);
        this.setHeight(1);
        this.setObjectName("enemy");
    }

    /**
     * Return the armour this enemy has.
     * @return The amount of armour this enemy has.
     */
    @Override
    public int getArmour() {
        return this.armour;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * Return a list of resistance attributes.
     * @return A list of resistance attributes.
     */
    @Override
    public int[] getResistanceAttributes() {
        return this.resistanceAttributes;
    }

    /**
     * Return a list of status indicators.
     * @return A list of status indicators.
     */
    @Override
    public String[] getStatusIndicators() {
        return this.statusIndicators;
    }

    /**
     * Return the health this enemy has.
     * @return The health this enemy has.
     */
    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Set the amount of health the enemy has.
     * @param health The amount of health to set the enemy.
     */
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Perform damage calculation.
     * @param damage The damage to deal.
     */
    @Override
    public void takeDamage(int damage) {
        //TODO: perform damage calculation factoring in status indicators, armour and resistance attributes.
        this.health -= damage;
    }

    /**
     * Deal damage to another ICombatEntity.
     * @param entity The combat entity that has been selected to deal damage to.
     */
    @Override
    public void dealDamage(ICombatEntity entity) {
        if (this.canDealDamage()) {
            entity.takeDamage(this.damage);
        }
    }

    /**
     * Return whether this enemy can deal damage.
     * @return Can this enemy deal damage.
     */
    @Override
    public boolean canDealDamage() {
        return true;
    }


    /**
     * On tick perform a task.
     * @param i The current game tick.
     */
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
