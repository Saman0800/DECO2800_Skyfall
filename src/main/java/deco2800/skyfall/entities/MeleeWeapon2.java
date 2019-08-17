package deco2800.skyfall.entities;

public class MeleeWeapon2 extends Weapon{

    /**
     * Return a weapon type melee
     * @return a string melee
     */
    @Override
    public String getWeaponType() {
        return "melee";
    }

    /**
     * return a damage type slash
     * @return
     */
    @Override
    public String getDamageType() {
        return "slash";
    }

}
