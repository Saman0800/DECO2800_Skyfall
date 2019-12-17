package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Biscuit extends FoodItem {

    public Biscuit(Tile tile, boolean obstructed) {
        super(tile, "biscuit", obstructed, "biscuit");
        this.setHealthValue(5);
    }

    public Biscuit newInstance(Tile tile) {
        return new Biscuit(tile,this.isObstructed());
    }
}