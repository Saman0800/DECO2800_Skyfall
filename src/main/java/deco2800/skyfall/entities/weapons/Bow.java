package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Bow extends Weapon implements Item, IWeapon {

    public Bow(Tile tile, boolean obstructed) {
        super(tile, "bow_tex", obstructed, "bow");
        this.setWeaponType("range");
        this.setDamageType("splash");
        this.setAttackRate(3);
        this.setDamage(4);
        this.setDurability(10);
        setCostValues(40, 20, 15, 30);
    }

    public Bow() {
        super("bow");
        setCostValues(40, 20, 15, 30);
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Bow newInstance(Tile tile) {
        return new Bow(tile, this.isObstructed());
    }
}
