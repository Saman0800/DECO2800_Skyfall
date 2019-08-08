package deco2800.skyfall.resources;

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

    /**
     * Creates a new Natural Resource with the given name
     * @param name the identifying name of the Natural Resource
     */
    public NaturalResources(String name){
        this.name = name;
        this.carryable = true;
        this.subtype = "Natural Resource";
        this.hasHealingPower = false;

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
}
