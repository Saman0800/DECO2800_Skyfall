package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.worlds.Tile;

/**
 * The cake class to generate the cake to the world
 */
public class Cake extends FoodItem {

    /**
     * Constructor for cake class.
     * @param tile the tile the cake will be spawned on
     * @param obstructed is it obstructed or not
     */
    public Cake(Tile tile, boolean obstructed) {
        super(tile, "cake", obstructed, "cake");
        this.setHealthValue(11);
    }

    public Cake newInstance(Tile tile) {
        return new Cake(tile,this.isObstructed());
    }
}