package deco2800.skyfall.resources;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

/**
 * An abstract class representing a Natural Resource item
 */
public abstract class NaturalResources implements Item {

    // the name of the item e.g. wood, stone
    protected String name;

    // can the item be carried in the inventory or not
    private Boolean carryable;

    // the name of the subtype the item belongs to
    protected String subtype;

    // the co-ordinates of the tile the item has been placed on
    private HexVector position;

    // determines whether or not the resource can be traded
    private Boolean exchangeable;

    // the name of the biome the resource is situated in
    protected String biome;

    // Can be item be equipped
    public boolean equippable;

    /***
     * Creates a default natural resource where the position is unknown
     */
    public NaturalResources(){
        this.name = null;
        this.carryable = true;
        this.subtype = "Natural Resource";
        this.exchangeable = true;
        this.equippable = false;
    }


    /**
     * Creates a new Natural Resource with the given position
     * @param position the tile which the item has been placed on
     */
    public NaturalResources(Tile position){
        this.name = null;
        this.carryable = true;
        this.subtype = "Natural Resource";
        this.position = position.getCoordinates();
        this.exchangeable = true;
        this.equippable = false;
    }

    /**
     * Returns the name of the natural resource
     * @return The name of the natural resource
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the biome the stone is situated in
     * @return the biome the stone is situated in
     */
    public String getBiome(){
        return biome;
    }


    /**
     * Returns whether or not the item can be carried
     * @return True if the item can be carried in the inventory, false
     * if it is consumed immediately
     */
    @Override
    public Boolean isCarryable() {
        return carryable;
    }

    /**
     * Returns the subtype which the item belongs to.
     * @return The subtype which the item belongs to.
     */
    @Override
    public String getSubtype() {
        return subtype;
    }


    /**
     * Returns the co-ordinates of the tile the item is on.
     * @return the co-ordinates of the tile the item is on.
     */
    @Override
    public HexVector getCoords() {
        return position;
    }

    @Override
    public Boolean isExchangeable() {
        return exchangeable;
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

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "These items exist naturally in the world.";
    }

    /**
     * Returns whether or not the item can be equipped from the inventory
     * @return True if the item can be equipped, false otherwise
     */
    public Boolean isEquippable() {
        return this.equippable;
    }

}
