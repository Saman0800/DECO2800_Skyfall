package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

public class BerryFood extends FoodItem {

    public BerryFood(Tile tile, boolean obstructed) {
        super(tile, "cherry", obstructed, "cherry");
        this.setHealthValue(10);
    }

    public BerryFood newInstance(Tile tile) {
        return new BerryFood(tile,this.isObstructed());
    }
}
