package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.inventory.Inventory;


public class Hatchet extends ManufacturedResources implements Item {



    public Hatchet(AgentEntity owner, HexVector position, String name) {
        super(owner, position, name);
        this.name="Hatchet";
    }


    @Override
    public String getName() {

        return this.name;
    }


    @Override
    public String getSubtype() {

        return super.subtype;
    }

    @Override
    public HexVector getCoords() {
        return this.position;
    }


    @Override
    public String toString() {

        return "" + subtype + ":" + name;
    }

    @Override
    public Boolean isExchangeable() {
        return true;
    }








}
