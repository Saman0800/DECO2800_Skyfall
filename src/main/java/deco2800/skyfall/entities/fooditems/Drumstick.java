package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.worlds.Tile;

/**
 * The drumstick class to generate drumstick to the world
 */
public class Drumstick extends FoodItem {

    /**
     * Constructor for drumstick class.
     * @param tile the tile the drumstick will be spawned on
     * @param obstructed is it obstructed or not
     */
    public Drumstick(Tile tile, boolean obstructed) {
        super(tile, "drumstick", obstructed, "drumstick");
        this.setHealthValue(9);
    }

    public Drumstick newInstance(Tile tile) {
        return new Drumstick(tile,this.isObstructed());
    }
}