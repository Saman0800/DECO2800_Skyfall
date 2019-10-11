package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Axe extends Weapon implements Item, IWeapon {

    public Axe(Tile tile, boolean obstructed) {
        super(tile, "axe_tex", obstructed, "axe", "melee", "slash", 4, 4, 10);
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

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint.
     */
    @Override
    public boolean isBlueprintLearned() {
        return false;
    }
}
