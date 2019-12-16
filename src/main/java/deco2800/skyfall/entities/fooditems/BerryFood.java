package deco2800.skyfall.entities.fooditems;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class BerryFood extends FoodItem implements Item, IFood {

    public BerryFood(Tile tile, boolean obstructed) {
        super(tile, "cherry", obstructed, "Cherry");
        this.setHealthValue(10);
    }



}
