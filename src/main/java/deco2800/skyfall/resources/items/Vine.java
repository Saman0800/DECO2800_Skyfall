package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Vine extends NaturalResources implements Item {

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


    // the biome the vine is in (will change to different type in future?)
    private String biome;

    /**
     * Creates a default vine item
     */
    public Vine(){
        //default constructor added for building inventory
        this.biome = biome;
    }



    /**
     * Returns the name of the natural resource
     * @return The name of the natural resource
     */
    @Override
    public String getName() {
        return "Vine";
    }

    /**
     * Returns the biome the vine is situated in
     * @return the biome the vine is situated in
     */
    public String getBiome(){
        return biome;
    }
}
