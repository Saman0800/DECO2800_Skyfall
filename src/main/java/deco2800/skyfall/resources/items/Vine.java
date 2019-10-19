package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;

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
        description = "This item can be found in the forest biome and can\n" +
                "be used to produce rope.";
    }
}
