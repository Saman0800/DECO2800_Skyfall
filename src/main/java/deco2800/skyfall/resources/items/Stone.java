package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;


public abstract class Stone extends NaturalResources implements Item {
    // the name of the item
    private String name;

    // can the item be stored in the inventory
    private Boolean carryable;

    // the name of the subtype the item belongs to
    private String subtype;

    // does the item impact the player's health
    private Boolean hasHealingPower;

    // the co-ordinates of the tile the item has been placed on
    private HexVector position;

    // determines whether or not the resource can be traded
    private Boolean exchangeable;

    // the colour of the stone
    private String colour;

    // the biome the wood is in (will change to different type in future?)
    private String biome;

    /**
     * Creates a default stone item
     */
    public Stone(){
        this.colour = "white";
        this.biome = biome;
        //default constructor added for building inventory
    }

    /**
     * Returns the name of the natural resource
     * @return The name of the natural resource
     */
    @Override
    public String getName() {
        return "Stone";
    }

    /**
     * Returns the colour of the stone
     * @return the colour of the stone
     */
    public String getColour(){
        return colour;
    }

    /**
     * Returns the biome the stone is situated in
     * @return the biome the stone is situated in
     */
    public String getBiome(){
        return biome;
    }
}
