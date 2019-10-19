package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.NaturalResources;


/**
 * A class representing a Sand Natural Resource item
 */
public class Sand extends NaturalResources {

    /**
     * Creates a default sand item
     */
    public Sand(){
        this.name = "Sand";
        this.biome = "Beach";
        description = "This resource can be found in the" + "\n" + " Desert or Beach biomes.";
    }
}
