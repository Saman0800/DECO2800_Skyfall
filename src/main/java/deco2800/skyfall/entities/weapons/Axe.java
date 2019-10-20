package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Axe extends Weapon implements Item, IWeapon {

    public Axe(Tile tile, boolean obstructed) {
        super(tile, "axe_tex", obstructed, "axe");
        this.setWeaponType("melee");
        this.setDamageType("slash");
        this.setAttackRate(4);
        this.setDamage(4);
        this.setDurability(10);
        setCostValues(20, 10, 10, 5);
    }

    public Axe() {
        super("axe");
        setCostValues(20, 10, 10, 5);
    }

    /**
     * @return a new instance of axe
     */
    @Override
    public Axe newInstance(Tile tile) {
        return new Axe(tile, this.isObstructed());
    }
}
