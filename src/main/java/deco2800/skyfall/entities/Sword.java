package deco2800.skyfall.entities;

public class Sword extends MeleeWeapon {

    /**
     * Get durability of the weapon
     * @return a number represent the durability of the weapon
     * on the scale of 0 to 10
     */
    @Override
    public Number getDurability() {
        return 6;
    }

    /**
     * Get attack rate of the weapon
     * @return a number represent the attack rate of the weapon
     * on the scale of 1 to 5
     */
    @Override
    public Number getAttackRate() {
        return 5;
    }

    /**
     * Get damage of the weapon
     * @return a number as damage amount of the weapon
     */
    @Override
    public Number getDamage() {
        return 3;
    }

    /**
     * Return the name of the weapon
     * @return a string "sword"
     */
    @Override
    public String getName() {
        return "sword";
    }

    @Override
    public void onTick(long i) {

    }
}

