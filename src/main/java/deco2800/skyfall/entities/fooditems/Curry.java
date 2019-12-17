package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Curry extends FoodItem {

    public Curry(Tile tile, boolean obstructed) {
        super(tile, "curry", obstructed, "curry");
        this.setHealthValue(8);
    }

    public Curry newInstance(Tile tile) {
        return new Curry(tile,this.isObstructed());
    }
}