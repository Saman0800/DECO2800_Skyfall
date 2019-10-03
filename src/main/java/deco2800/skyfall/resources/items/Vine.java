package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;

/**
 * A class representing a Vine Natural Resource item
 */
public class Vine extends NaturalResources implements Item {



    /**
     * Creates a default vine item
     */
    public Vine(){
        this.name = "Vine";
        this.biome = "Forest";
    }




    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item can be found in the forest biome and can " + "\n" +
                "be used to produce rope.";
    }


    @Override
    public void use(HexVector position){

    }


}
