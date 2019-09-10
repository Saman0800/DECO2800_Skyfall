package deco2800.skyfall.entities;

public abstract class RangeWeapon extends AbstractEntity implements IWeapon {

    /**
     * what is the type of the weapon
     * @return a string "melee" represent all melee weapon
     */
    @Override
    public String getWeaponType() {
        return "range";
    }

    /**
     * What is the damage type of a weapon
     * @return a string "splash" for bow and spear
     */
    @Override
    public String getDamageType() {
        return "splash";
    }

}
