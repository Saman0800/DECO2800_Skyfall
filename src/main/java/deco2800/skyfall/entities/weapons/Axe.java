package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Axe extends Weapon implements Item, IWeapon {

    public Axe(Tile tile, boolean obstructed) {
        super(tile, "axe_tex", obstructed, "axe",
                "melee", "slash",
                4, 4, 10);
    }

    public Axe() {
        super("axe");
    }

    /**
     * @return a new instance of axe
     */
    @Override
    public Axe newInstance(Tile tile) {
        return new Axe(tile, this.isObstructed());
    }
}

