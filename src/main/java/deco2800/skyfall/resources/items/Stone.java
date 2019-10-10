package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;

/**
 * A class representing a Stone Natural Resource item
 */
public class Stone extends NaturalResources implements Item {

    // the colour of the stone
    private String colour;

    /**
     * Creates a default stone item
     */
    public Stone(){
        this.name = "Stone";
        this.colour = "white";
        this.biome = "Forest";
        description = "This resource can be found in the forest and mountain\n"
                + "biomes and can be used to build a Pickaxe.";
    }


    /**
     * Returns the colour of the stone
     * @return the colour of the stone
     */
    public String getColour(){
        return colour;
    }
}
