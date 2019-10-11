package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.worlds.Tile;

public class Spear extends Weapon {

    public Spear(Tile tile, boolean obstructed) {
        super(tile, "spear_tex", obstructed, "spear", "range", "splash", 4, 5, 7);
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
