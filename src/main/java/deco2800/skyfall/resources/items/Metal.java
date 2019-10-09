package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;


/**
 * A class representing a Metal Natural Resource item
 */
public class Metal extends NaturalResources {


    /**
     * Creates a default metal item
     */
    public Metal(){
        this.name = "Metal";
        this.biome = "Ruined City";
        description = "This item can be collected" + "\n" + " by destroying an enemy.";
    }
}