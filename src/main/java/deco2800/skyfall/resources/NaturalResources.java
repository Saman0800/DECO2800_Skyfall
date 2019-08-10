package deco2800.skyfall.resources;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

/**
 * An abstract class representing a Natural Resource item
 */
public abstract class NaturalResources implements Item {

    // the name of the item e.g. wood, stone
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

    /***
     * Creates a default natural resource
     */
    public NaturalResources(){
        //default constructor added for building inventory
    }


    /**
     * Creates a new Natural Resource with the given name and position
     * @param name the identifying name of the Natural Resource
     * @param position the tile which the item has been placed on
     */
    public NaturalResources(String name, Tile position){
        this.name = name;
        this.carryable = true;
        this.subtype = "Natural Resource";
        this.hasHealingPower = false;
        this.position = position.getCoordinates();
        this.exchangeable = true;

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
     * Returns whether or not the item can be stored in the inventory
     * @return True if the item can be added to the inventory, false
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
     * Returns whether or not the item impacts the player's health
     * @return True if the item impacts on the player's health, false otherwise
     */
    @Override
    public Boolean hasHealingPower() {
        return hasHealingPower;
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
    public Boolean getExchangeable() {
        return exchangeable;
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
