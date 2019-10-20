package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

public class Spear extends Weapon {

    public Spear(Tile tile, boolean obstructed) {
        super(tile, "spear_tex", obstructed, "spear");
        this.setWeaponType("range");
        this.setDamageType("splash");
        this.setAttackRate(4);
        this.setDamage(5);
        this.setDurability(7);
        setCostValues(20, 20, 20, 10);
    }

    public Spear() {
        super("spear");
        setCostValues(20, 20, 20, 10);
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Spear newInstance(Tile tile) {
        return new Spear(tile, this.isObstructed());
    }
}
