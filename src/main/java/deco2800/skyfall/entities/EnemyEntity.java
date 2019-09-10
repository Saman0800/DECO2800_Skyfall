package deco2800.skyfall.entities;


import deco2800.skyfall.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EnemyEntity extends Peon implements ICombatEntity{

    private final transient Logger log = LoggerFactory.getLogger(EnemyEntity.class);

    //The task this Enemy is set to perform.
    protected transient AbstractTask task;

    //The amount of health this enemy has.
    private int health;

    //The level of Enemy
    private int level;


    private boolean isAttacked=false;

    //A list of status indicators the enemy has.
    //Stun, sleep, paralyze... etc.
    private String[] statusIndicators;

    //A list of resistance attributes the enemy has.
    private int[] resistanceAttributes;

    //The amount of armour this enemy has.
    private int armour;

    //The amount of damage this enemy deals.
    private int damage;


    public EnemyEntity(float col, float row){
        this.setRow(row);
        this.setCol(col);
    }
    public EnemyEntity(float row, float col, String texturename,int health,int armour,int damage) {
        super(row, col, 0.2f,texturename,health);
        this.setTexture(texturename);
    }

    public void onTick(long i) {
        getBody().setTransform(position.getCol(), position.getRow(), getBody().getAngle());

        //@TODO
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }

//        GameManager.get().getWorld().getPlayer();
    }

    /**
     * Return the armour this enemy has.
     * @return The amount of armour this enemy has.
     */
    @Override
    public int getArmour() {
        return this.armour;
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
     * To get the damage of enemy
     * @return damage value
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage){
        this.damage=damage;
    }

    /**
     * Damage taken
     * @param damage hero danage
     */
    public void takeDamage(int damage) {
        //TODO: perform damage calculation factoring in status indicators, armour and resistance attributes.
        this.health -= damage;
        log.info("Enemy took " + damage + " damage.");
        log.info("Enemy has " + this.health + " health remaining.");
        //If the health of this enemy is <= 0, remove it from the game world.
        if (this.health <= 0) {
            this.destroy();
        }
    }

    /**
     * Return the health this enemy has.
     * @return The health this enemy has.
     */
    public int getHealth() {
        return health;
    }

    /**
     * To set enemy heal
     * @param health set heal of enemy
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * To get the level of Enemy
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set Enemy level
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }


    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    /**
     * Set armour of enemy
     * @param armour armour value
     */
    public void setArmour(int armour){
        this.armour=armour;
    }

    /**
     * Remove this enemy from the game world.
     */
    private void destroy() {
        this.setDead(true);
        log.info("Enemy destroyed.");
    }
}