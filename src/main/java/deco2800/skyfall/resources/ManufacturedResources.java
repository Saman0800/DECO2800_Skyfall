package deco2800.skyfall.resources;


import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;


public abstract class ManufacturedResources implements Item {

    // the name of the item e.g. wood, stone
    public String name;

    // can the item be stored in the inventory
    private Boolean carryable;

    // the name of the subtype the item belongs to
    public String subtype;

    // does the item impact the player's health
    private Boolean hasHealingPower;

    // the co-ordinates of the tile the item has been placed on
    private HexVector position;

    // determines whether or not the resource can be traded
    private Boolean exchangeable;

    //
    private Boolean hasFoodEffect;


    public ManufacturedResources() {

        this.name = null;
        this.carryable = true;
        this.subtype = "Manufactured Resource";
        this.hasHealingPower = false;
        // Manufactured  Resources do not has food effect
        this.hasFoodEffect = false;
        this.exchangeable = true;
    }


    /***
     * Creates a default manufactured  resource
     */
    public ManufacturedResources(Tile position){
        //default constructor added for building inventory
        this.name = null;
        this.carryable = true;
        this.subtype = "Manufactured Resource";
        this.hasHealingPower = false;
        this.hasFoodEffect = false;
        this.exchangeable = true;

    }




    /**
     * Returns the name of the Manufactured resource
     * @return The name of the manufactured resource
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


    /**
     * Returns the co-ordinates of the tile the item is on.
     * @return the co-ordinates of the tile the item is on.
     */
    @Override
    public HexVector getCoords() {
        return position;
    }



    public Boolean hasFoodEffect() {
        return hasFoodEffect;
    }

    /**
     * Creates a string representation of the manufactured resource in the format:
     *
     * <p>'{Manufactured Resource}:{Name}'
     *
     * <p>without surrounding quotes and with {manufactured resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example:
     *
     * <p>Manufactured Resource:Rope
     *
     * @return A string representation of the manufactured resource.
     */
    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }


}


