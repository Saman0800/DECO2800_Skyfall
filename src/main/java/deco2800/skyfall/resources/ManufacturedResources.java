package deco2800.skyfall.resources;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.HexVector;

/**
 * An abstract class representing a Manufactured Resource item.
 */
public abstract class ManufacturedResources implements Item {

    // the name of the item e.g. Hatchet, Pick Axe
    public String name;

    // can the item be stored in the inventory
    protected Boolean carryable;

    // the name of the subtype the item belongs to
    public String subtype;

    // the co-ordinates of the tile the item has been placed on
    public HexVector position;

    // an AngnetEntity instance representing the owner of the resource.
    protected MainCharacter owner;

    /***
     * Creates a default manufactured resource .
     * @param owner the resource owner.
     * @param position the Hexvector position of the manufactured resource.
     */
    public ManufacturedResources(MainCharacter owner, HexVector position) {
        this.owner = owner;
        this.position = position;
        this.carryable = true;
        this.subtype = "Manufactured Resource";
    }

    public ManufacturedResources(MainCharacter owner){
        this.owner = owner;
        this.subtype= "Manufactured Resource";
    }

    public ManufacturedResources(){
        this.subtype= "Manufactured Resource";
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
     * Returns the co-ordinates of the tile the item is on.
     * @return the co-ordinates of the tile the item is on.
     */
    @Override
    public HexVector getCoords() {
        return position;
    }

    /**
     * A string representation of the manufactured resource.
     * @return name of the natural resource and its subtype as a string.
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
        return "This item can be used to retrieve natural " +
                "resources from the world.";
    }

}


