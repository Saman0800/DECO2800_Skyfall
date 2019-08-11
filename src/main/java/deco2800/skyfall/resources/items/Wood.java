package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Wood extends NaturalResources implements Item {

    // the name of the item
    private String name;


/*    // can the item be stored in the inventory
    private Boolean carryable;

    // the name of the subtype the item belongs to
    private String subtype;

    // does the item impact the player's health
    private Boolean hasHealingPower;

    // the co-ordinates of the tile the item has been placed on
    private HexVector position;

    // determines whether or not the resource can be traded
    private Boolean exchangeable;*/

    // the colour of the wood
    private String colour;

    // the biome the wood is in (will change to different type in future?)
    private String biome;

    /**
     * Creates a default wood item
     */
    public Wood(){
        //default constructor added for building inventory
        this.name = "Wood";
        this.colour = "brown";
        this.biome = biome;

    }

    /**
     * Returns the name of the natural resource
     * @return The name of the natural resource
     */
    @Override
    public String getName() {
        return "Wood";
    }


    /**
     * Returns the colour of the wood
     * @return the colour of the wood
     */
    public String getColour(){
        return colour;
    }

    /**
     * Returns the biome the wood is situated in
     * @return the biome the wood is situated in
     */
    public String getBiome(){
        return biome;
    }

    /**
     * Creates a string representation of the natural resource in the format:
     *
     * <p>'{Nautral Resource}:{Name}'
     *
     * <p>without surrounding quotes and with {natural resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example:
     *
     * <p>Natural Resource:Wood
     *
     * @return A string representation of the natural resource.
     */
    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }


}
