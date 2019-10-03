package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Sword extends Weapon implements Item, IWeapon {

    public Sword(Tile tile, boolean obstructed) {
        super(tile, "sword_tex", obstructed, "sword",
                "melee", "slash",
                5, 3, 6);
    }

    public Sword() {
        super("sword");
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Sword newInstance(Tile tile) {
        return new Sword(tile, this.isObstructed());
    }
}

