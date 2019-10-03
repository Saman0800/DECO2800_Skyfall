package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;


/**
 * A class representing a Metal Natural Resource item
 */
public class Metal extends NaturalResources implements Item {


    /**
     * Creates a default metal item
     */
    public Metal(){
        this.name = "Metal";
        this.biome = "Ruined City";

    }


    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item can be collected" + "\n" + " by destroying an enemy.";
    }

    @Override
    public void use(HexVector position){

    }

}