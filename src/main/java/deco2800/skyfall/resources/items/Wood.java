package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;

/**
 * A class representing a Wood Natural Resource item
 */
public class Wood extends NaturalResources implements Item {

    // the colour of the wood
    private String colour;

    /**
     * Creates a default wood item
     */
    public Wood(){
        this.name = "Wood";
        this.colour = "brown";
        this.biome = "Forest";
    }

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item can be found in the forest biome " + "\n" +
                "and can be used to create a pickaxe and start a fire.";
    }


    /**
     * Returns the colour of the wood
     * @return the colour of the wood
     */
    public String getColour(){
        return colour;
    }


    @Override
    public void use(HexVector position){

    }

}
