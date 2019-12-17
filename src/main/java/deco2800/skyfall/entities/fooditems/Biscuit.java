package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.worlds.Tile;

/**
 * Biscuit class to generate biscuit to the world
 */
public class Biscuit extends FoodItem {

    /**
     * Constructor for biscuit class.
     * @param tile the tile the biscuit will be spawned on
     * @param obstructed is it obstructed or not
     */
    public Biscuit(Tile tile, boolean obstructed) {
        super(tile, "biscuit", obstructed, "biscuit");
        this.setHealthValue(6);
    }

    public Biscuit newInstance(Tile tile) {
        return new Biscuit(tile,this.isObstructed());
    }
}