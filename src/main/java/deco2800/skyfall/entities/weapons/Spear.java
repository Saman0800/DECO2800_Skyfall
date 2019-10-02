package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Spear extends Weapon implements Item, IWeapon {

    public Spear(Tile tile, boolean obstructed) {
        super(tile, "spear_tex", obstructed, "spear",
                "range", "splash",
                4, 5, 7);
    }

    public Spear() {
        super("spear");
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Spear newInstance(Tile tile) {
        return new Spear(tile, this.isObstructed());
    }
}
