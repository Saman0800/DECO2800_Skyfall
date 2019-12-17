package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Cake extends FoodItem {

    public Cake(Tile tile, boolean obstructed) {
        super(tile, "cake", obstructed, "cake");
        this.setHealthValue(11);
    }

    public Cake newInstance(Tile tile) {
        return new Cake(tile,this.isObstructed());
    }
}