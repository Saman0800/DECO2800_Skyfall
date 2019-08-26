package deco2800.skyfall.entities;

/**
 * Defines an entity that participates in combat.
 * Created by chris-poli on 26/7/17.
 */
public interface ICombatEntity extends HasHealth {

    /**
     * Deal damage to this entity.
     */
    void takeDamage(int damage);

    /**
     * Deal damage to another ICombatEntity so damage calculation can be applied.
     */
    void dealDamage(ICombatEntity entity);

    /**
     * Some combat entities will only be able to be attacked.
     *
     * @return If the combat entity can deal damage.
     */
    boolean canDealDamage();

    /**
     * Get the amount of armour this entity has, calculated from the worn gear plus base stats.
     *
     * @return The amount of armour the entity has.
     */
    int getArmour();

    /**
     * Get the amount of damage this entity deals.
     *
     * @return The damage this entity deals.
     */
    int getDamage();

    /**
     * Get an array of the resistance attributes.
     *
     * @return A array containing the resistance attributes of the combat entity.
     */
    int[] getResistanceAttributes();

    /**
     * Get an array of the status indicators for the combat entity,
     * such as "stun", "disable", "poison", "invincible".
     *
     * @return An array of status indicators.
     */
    String[] getStatusIndicators();

}

