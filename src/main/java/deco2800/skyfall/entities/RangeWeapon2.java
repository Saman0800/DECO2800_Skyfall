package deco2800.skyfall.entities;
import java.util.Random;

public class RangeWeapon2 extends Weapon {

    /**
     * Return the weapon type range
     * @return a string range
     */
    @Override
    public String getWeaponType() {
        return "range";
    }

    /**
     * Return the damage type for melee weapon
     * @return a string splash
     */
    @Override
    public String getDamageType() {
        return "splash";
    }


}
