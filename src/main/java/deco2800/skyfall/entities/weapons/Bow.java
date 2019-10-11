package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Bow extends Weapon implements Item, IWeapon {

    public Bow(Tile tile, boolean obstructed) {
        super(tile, "bow_tex", obstructed, "bow", "range", "splash", 3, 4, 10);
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
