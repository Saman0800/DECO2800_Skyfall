package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Wood extends NaturalResources implements Item {

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

    // the colour of the wood
    private String colour;

    // the biome the wood is in (will change to different type in future?)
    private String biome;

    /**
     * Creates a default wood item
     */
    public Wood(){
        //default constructor added for building inventory
    }

    /**
     * Creates a new Natural Resource with the given name and position
     * @param name the identifying name of the Natural Resource
     * @param position the tile which the item has been placed on
     */
    public Wood(String name, Tile position){
        super(name, position);
        this.colour = "brown";
        this.biome = biome;
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


}
