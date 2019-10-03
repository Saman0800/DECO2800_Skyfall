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
    }


    /**
     * Returns the colour of the stone
     * @return the colour of the stone
     */
    public String getColour(){
        return colour;
    }



    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This resource can be found in the forest and mountain" + "\n" +
                " biomes and can be used to build a Pickaxe.";
    }

    /**
     * Creates a string representation of the natural resource in the format:
     *
     * <p>'{Natural Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {natural resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Natural Resource:Wood </p>
     *
     * @return A string representation of the natural resource.
     */
    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }


    @Override
    public void use(HexVector position){

    }

}
