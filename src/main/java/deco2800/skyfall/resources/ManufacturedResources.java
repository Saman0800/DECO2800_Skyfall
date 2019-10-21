package deco2800.skyfall.resources;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.HexVector;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class representing a Manufactured Resource item.
 */
public abstract class ManufacturedResources extends AbstractEntity implements Item, Blueprint {

    // the name of the item e.g. Hatchet, Pick Axe
    protected String name;

    // can the item be stored in the inventory
    protected boolean carryable;

    // the name of the subtype the item belongs to
    protected String subtype;

    // an AngnetEntity instance representing the owner of the resource.
    protected MainCharacter owner;

    // a list of all required resources needed to create a manufactured resource
    // item.
    protected Map<String, Integer> allRequirements = new HashMap<>();

    // Can be item be equipped
    protected boolean equippable;

    // Durability of the item (how many times it can be used
    private int durability;

    protected String description;

    public ManufacturedResources() {
        this.subtype = "Manufactured Resource";
        this.equippable = true;
        this.durability = 50;
        description = "This item can be used to retrieve natural " + "resources from the world.";
    }

    /**
     * Returns whether or not the item can be stored in the inventory
     * 
     * @return True if the item can be added to the inventory, false if it is
     *         consumed immediately
     */
    @Override
    public boolean isCarryable() {
        return carryable;
    }

    /**
     * Returns the name of the Manufactured Resource.
     * 
     * @return The subtype which the item belongs to.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return Returns true if this subtype is equippable.
     */
    public boolean getEquippable() {
        return this.equippable;
    }

    /**
     * Sets the equippable value of this sub-type.
     */
    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    /**
     * Returns the co-ordinates of the tile the item is on.
     * 
     * @return the co-ordinates of the tile the item is on.
     */
    @Override
    public String getSubtype() {
        return subtype;
    }

    /**
     * Returns the co-ordinates of the tile the item is on.
     * 
     * @return the co-ordinates of the tile the item is on.
     */
    @Override
    public HexVector getCoords() {
        return position;
    }

    /**
     * A string representation of the manufactured resource.
     * 
     * @return name of the natural resource and its subtype as a string.
     */
    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }

    /**
     * Returns the item description
     * 
     * @return the item description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        Integer amt = getAllRequirements().get("wood");
        if (amt == null) {
            return 0;
        }
        return amt;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        Integer amt = getAllRequirements().get("stone");
        if (amt == null) {
            return 0;
        }
        return amt;
    }

    @Override
    public Map<String, Integer> getAllRequirements() {
        return allRequirements;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        Integer amt = getAllRequirements().get("metal");
        if (amt == null) {
            return 0;
        }
        return amt;
    }

    /**
     * Returns whether or not the item can be equipped from the inventory
     * 
     * @return True if the item can be equipped, false otherwise
     */
    public boolean isEquippable() {
        return this.equippable;
    }

    /**
     * @return the durability of the hatchet
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Reduces durability of hatchet by 1
     */
    public void decreaseDurability() {
        this.durability -= 1;
    }

    /**
     * If the durability of the hatchet have durability bigger than 0
     * 
     * @return whether to hatchet is still usable
     */
    public boolean isUsable() {
        return this.getDurability() > 0;
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }

}
