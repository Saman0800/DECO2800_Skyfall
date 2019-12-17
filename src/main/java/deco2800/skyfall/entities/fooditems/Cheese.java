package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Cheese extends FoodItem implements Item, IFood {

    public Cheese(Tile tile, boolean obstructed) {
        super(tile, "cheese", obstructed, "cheese");
        this.setHealthValue(7);
    }

    public Cheese newInstance(Tile tile) {
        return new Cheese(tile,this.isObstructed());
    }
}