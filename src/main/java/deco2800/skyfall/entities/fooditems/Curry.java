package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.worlds.Tile;

/**
 * The curry class to generate curry to the world
 */
public class Curry extends FoodItem {

    /**
     * Constructor for curry class.
     * @param tile the tile the curry will be spawned on
     * @param obstructed is it obstructed or not
     */
    public Curry(Tile tile, boolean obstructed) {
        super(tile, "curry", obstructed, "curry");
        this.setHealthValue(8);
    }

    public Curry newInstance(Tile tile) {
        return new Curry(tile,this.isObstructed());
    }
}