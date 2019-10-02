package deco2800.skyfall.entities.weapons;

public interface IWeapon {

    /**
     * Get weapon type
     * @return the type of a weapon
     */
    String getWeaponType();

    /**
     * Get durability of the weapon
     * @return a number represent the durability of the weapon
     * on the scale of 0 to 10
     */
    int getDurability();

    /**
     * Get attack rate of the weapon
     * @return a number represent the attack rate of the weapon
     * on the scale of 1 to 5
     */
    int getAttackRate();

    /**
     * Get damage of the weapon
     * @return a number as damage amount of the weapon
     */
    int getDamage();

    /**
     * Get damage type of the weapon
     * @return a String represent the type of damage
     * the type of damage will be for specific weapon such as
     * sword and axe weapon will have slash effect
     * bow and spear will have splash effect
     */
    String getDamageType();
}
