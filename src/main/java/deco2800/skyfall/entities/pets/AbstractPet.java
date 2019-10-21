package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;

public abstract class AbstractPet extends Peon implements ICombatEntity, Item {
    // pet health
    private int health;

    // pet level
    private int level;

    // pet damage
    private int damage;

    // is the pet domesticated
    private boolean domesticated = false;

    // is the pet on the way of collecting coins
    protected boolean isOnTheWay = false;

    // is the pet summoned
    private boolean isSummoned = false;

    public AbstractPet(float col, float row) {
        this.setRow(row);
        this.setCol(col);
        this.setHeight(1);
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

    /**
     * get movement direction
     *
     * @param angle the angle between to tile
     * @return direction
     */
    public Direction movementDirection(double angle) {
        angle = Math.toDegrees(angle - Math.PI);
        if (angle < 0) {
            angle += 360;
        }
        if (angle >= 0 && angle <= 60) {
            return Direction.SOUTH_WEST;
        } else if (angle > 60 && angle <= 120) {
            return Direction.SOUTH;
        } else if (angle > 120 && angle <= 180) {
            return Direction.SOUTH_EAST;
        } else if (angle > 180 && angle <= 240) {
            return Direction.NORTH_EAST;
        } else if (angle > 240 && angle <= 300) {
            return Direction.NORTH;
        } else if (angle > 300 && angle < 360) {
            return Direction.NORTH_WEST;
        }
        return null;

    }

    /**
     * Returns the subtype which the pet belongs to.
     *
     * @return pet type
     */
    @Override
    public String getSubtype() {
        return "pets";
    }

    /**
     * Returns whether or not the item can be carried
     *
     * @return true
     */
    @Override
    public boolean isCarryable() {
        return true;
    }

    /**
     * Returns the co-ordinates of the tile the item is on
     *
     * @return coordinates
     */
    @Override
    public HexVector getCoords() {
        return new HexVector(this.getCol(), this.getRow());
    }

    /**
     * Returns whether or not the pet can be exchanged
     *
     * @return True if the pet can be exchanged, false otherwise
     */
    @Override
    public boolean isExchangeable() {
        return true;
    }

    /**
     * Returns a description about the pet
     *
     * @return description about the pet
     */
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void use(HexVector position) {
        // Do nothing for now.
    }

    public boolean isEquippable() {
        return false;
    }

}
