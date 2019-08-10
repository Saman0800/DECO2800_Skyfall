package deco2800.skyfall.entities;

/**
 * Created by James Nguyen on 06/08/2019
 * Edited by Christopher Poli on 10/08/2019
 */
public interface IWeapon {
    /**
     * Get weapon type
     * @return the type of a weapon
     */
    String getWeaponType();

    /**
     * Get weapon name
     * @return the name of the weapon
     */
    String getWeaponName();

    /**
     * Get durability of the weapon
     * @return a number represent the durability of the weapon
     * on the scale of 0 to 10
     */
    Number getDurability();

    /**
     * Get attack rate of the weapon
     * @return a number represent the attack rate of the weapon
     * on the scale of 1 to 5
     */
    Number getAttackRate();

    /**
     * Get damage of the weapon
     * @return a number as damage amount of the weapon
     */
    Number getDamage();

    /**
     * Get damage type of the weapon
     * @return a String represent the type of damage
     * the type of damage will be for specific weapon such as
     * sword and axe weapon will have slash effect
     * bow and spear will have splash effect
     */
    String getDamageType();




}
