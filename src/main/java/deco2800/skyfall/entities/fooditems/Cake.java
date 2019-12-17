package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Cake extends FoodItem implements Item, IFood {

    public Cake(Tile tile, boolean obstructed) {
        super(tile, "cake", obstructed, "cake");
        this.setHealthValue(6);
    }

    public Cake newInstance(Tile tile) {
        return new Cake(tile,this.isObstructed());
    }
}