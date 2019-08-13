package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.resources.Item;


public class Hatchet extends ManufacturedResources implements Item {


    public Hatchet() {
        super();
    }

    public Hatchet(Tile position) {
        super(position);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Boolean isCarryable() {
        return super.isCarryable();
    }

    @Override
    public String getSubtype() {
        return super.getSubtype();
    }

    @Override
    public HexVector getCoords() {
        return super.getCoords();
    }

    @Override
    public Boolean hasFoodEffect() {
        return super.hasFoodEffect();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Boolean isExchangeable() {
        return null;
    }
}
