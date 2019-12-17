package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class Drumstick extends FoodItem {

    public Drumstick(Tile tile, boolean obstructed) {
        super(tile, "drumstick", obstructed, "drumstick");
        this.setHealthValue(2);
    }

    public Drumstick newInstance(Tile tile) {
        return new Drumstick(tile,this.isObstructed());
    }
}