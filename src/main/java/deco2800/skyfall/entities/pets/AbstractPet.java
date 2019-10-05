package deco2800.skyfall.entities.pets;

import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;

public abstract class AbstractPet extends Peon implements ICombatEntity {
    // pet health
    private int health;

    // pet level
    private int level;

    // pet damage
    private int damage;

    // pet armour
    private int armour;

    // is the pet domesticated
    private boolean domesticated = false;

    // is the pet on the way of collecting coins
    private boolean isOnTheWay = false;

    // is the pet summoned
    private boolean isSummoned = false;

    public AbstractPet(float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    public AbstractPet(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, 0.2f, texturename, health);
        this.setTexture(texturename);

    }

    /**
     * Get the health of pet
     *
     * @return health of pet
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setting the health to a specific value
     *
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * is the pet is summoned
     *
     * @return true if the pet is summoned else false
     */
    public boolean isSummoned() {
        return isSummoned;
    }

    /**
     * Setting summoned situation
     *
     * @param summoned true if the pet is summoned
     */
    public void setSummoned(boolean summoned) {
        this.isSummoned = summoned;
    }

    /**
     * Domesticate the pet
     *
     * @param domesticated true if this pet is collect by mian character else false
     */
    public void setDomesticated(boolean domesticated) {
        this.domesticated = domesticated;
    }

    /**
     * Get information whether the pet is domesticated
     *
     * @return true if the pet is collected by main character
     */
    public boolean getDomesticated() {
        return this.domesticated;
    }

    /**
     * Getting the level of pet
     *
     * @return the level of pet
     */
    public int getLevel() {
        return level;
    }

    /**
     * Setting the pet as a specific level
     *
     * @param level level of pet
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * take damage from enemy
     *
     * @param damage damage from enemy
     */
    @Override
    public void takeDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Deal damage to another ICombatEntity.
     *
     * @param mc The combat entity that has been selected to deal damage to.
     */
    @Override
    public void dealDamage(MainCharacter mc) {

    }

    /**
     * Return whether this pet can deal damage.
     *
     * @return Can this enemy deal damage.
     */
    @Override
    public boolean canDealDamage() {
        return false;
    }

    /**
     * Set armour of this pet
     *
     * @param armour armour value
     */
    public void setArmour(int armour) {
        this.armour = armour;
    }

    /**
     * To get the damage of this pet
     *
     * @return damage value
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * Return a list of resistance attributes.
     *
     * @return A list of resistance attributes.
     */
    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

}
