package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.worlds.Tile;

/**
 * The cheese class to generate cheese to the world
 */
public class Cheese extends FoodItem {

    /**
     * Constructor for cheese class.
     * @param tile the tile the cheese will be spawned on
     * @param obstructed is it obstructed or not
     */
    public Cheese(Tile tile, boolean obstructed) {
        super(tile, "cheese", obstructed, "cheese");
        this.setHealthValue(7);
    }

    public Cheese newInstance(Tile tile) {
        return new Cheese(tile,this.isObstructed());
    }
}