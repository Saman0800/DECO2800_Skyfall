package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.worlds.Tile;

/**
 *  A method to generate cherry to the world
 */
public class Cheery extends FoodItem {

    /**
     * Constructor for berry class.
     * @param tile the tile the berry will be spawned on
     * @param obstructed is it obstructed or not
     */
    public Cheery(Tile tile, boolean obstructed) {
        super(tile, "cherry", obstructed, "cherry");
        this.setHealthValue(10);
    }

    public Cheery newInstance(Tile tile) {
        return new Cheery(tile,this.isObstructed());
    }
}
