package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyItem implements Item {

    // Item attributes
    private String name;
    private String subType;
    private boolean carryable;
    private boolean exchangeable;
    private boolean equippable;
    private HexVector position;
    private int durability;

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(EmptyItem.class);

    public EmptyItem() {
        this.name = "No Item";
        this.subType = "Empty";
        this.carryable = false;
        this.exchangeable = false;
        this.equippable = true;
        this.position = null;
        this.durability = 0;
    }

    /**
     * Returns the name of the item.
     * 
     * @return The name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the subtype which the item belongs to.
     * 
     * @return The subtype which the item belongs to.
     */
    public String getSubtype() {
        return this.subType;
    }

    /**
     * Returns whether or not the item can be carried.
     * 
     * @return True if the item can be carried in the inventory, false if it is
     *         consumed immediately.
     */
    public boolean isCarryable() {
        return this.carryable;
    }

    /**
     * Returns the co-ordinates of the tile the item is on.
     * 
     * @return the co-ordinates of the tile the item is on.
     */
    public HexVector getCoords() {
        return this.position;
    }

    /**
     * Returns whether or not the item can be exchanged.
     * 
     * @return True if the item can be exchanged, false otherwise.
     */
    public boolean isExchangeable() {
        return this.exchangeable;
    }

    /**
     * Returns a description about the item.
     * 
     * @return a description about the item.
     */
    public String getDescription() {
        return "No item equipped.";
    }

    @Override
    public String toString() {
        return this.getDescription();
    }

    /**
     * Gets the durability of the item.
     * 
     * @return 0.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Use the specific function associated with the item.
     */
    public void use(HexVector position) {
        logger.warn("Durability: " + this.getDurability());
    }

    /**
     * Returns whether or not the item can be equipped from the inventory.
     * 
     * @return True if the item can be equipped, false otherwise.
     */
    public boolean isEquippable() {
        return this.equippable;
    }
}
