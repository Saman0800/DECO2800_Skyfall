package deco2800.skyfall.entities;

/**
 * Defines an entity that participates in combat.
 * Created by chris-poli on 26/7/17.
 */
public interface ICombatEntity {

    /**
     * Deal damage to the entity.
     */
     void takeDamage(int damage);

    /**
     * Deal damage to another entity.
     */
    void dealDamage(ICombatEntity entity);

    /**
     * Some combat entities will only be able to be attacked.
     * @return If the combat entity can deal damage.
     */
    boolean canDealDamage();

    /**
     * Get the amount of armour this entity has, calculated from the worn gear plus base stats.
     * @return The amount of armour the entity has.
     */
    int getArmour();

    /**
     *
     * @return an array containing the resistance attributes of the combat entity.
     */
    int[] getResistanceAttributes();

    /**
     * Check the status indicators for the enemy, such as "stun", "disable", "poison".
     * @return An array of status indicators for the enemy.
     */
    String[] statusIndicators();
    
}
