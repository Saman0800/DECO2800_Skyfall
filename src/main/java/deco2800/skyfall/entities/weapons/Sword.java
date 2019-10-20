package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

public class Sword extends Weapon {

    public Sword(Tile tile, boolean obstructed) {
        super(tile, "sword_tex", obstructed, "sword");
        this.setWeaponType("melee");
        this.setDamageType("slash");
        this.setAttackRate(5);
        this.setDamage(3);
        this.setDurability(6);
        setCostValues(30, 30, 10, 10);
    }

    public Sword() {
        super("sword");
        setCostValues(30, 30, 10, 10);
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Sword newInstance(Tile tile) {
        return new Sword(tile, this.isObstructed());
    }
}
