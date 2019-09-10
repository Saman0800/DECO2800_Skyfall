package deco2800.skyfall.entities;

public abstract class MeleeWeapon extends AbstractEntity implements IWeapon {

    /**
     * what is the type of the weapon
     * @return a string "melee" represent all melee weapon
     */
    @Override
    public String getWeaponType() {
        return "melee";
    }

    /**
     * What is the damage type of a weapon
     * @return a string "slash" for axe and sword
     */
    @Override
    public String getDamageType() {
        return "slash";
    }

}
