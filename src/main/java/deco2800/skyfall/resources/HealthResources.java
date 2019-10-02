package deco2800.skyfall.resources;

//import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class HealthResources implements deco2800.skyfall.resources.Item {

    // can the item be stored in the inventory
    private Boolean carryable;
    // the name of the item e.g. food, poison
    private String name;
    //impact the player's health or not
    private Boolean hasHealingPower;

    // the name of the subtype the item belongs to
    protected String subtype;
    // the co-ordinates of the tile the item has been placed on
    private HexVector position;

    //Items could change or not e.g. coins, items
    private Boolean exchangeable;

    // the value of the piece of food
    protected int foodValue;

    // the healing ability of the health item
    protected int healthValue;

    private final transient Logger log = LoggerFactory.getLogger(HealthResources.class);

    /**
     * Creates a default health resource.
     */
    public HealthResources(){
        this.name = null;
        this.carryable = true;
        this.subtype = "Health Resource";
        this.exchangeable = true;
        this.hasHealingPower = true;
        //Do we need a new type like FoodResources? and hasFoodEffect may false
        // in here as medicine may not affect the food fullness

    }

    /**
     * Creates a new Health Resource with the given name
     *
     * @param name     the identifying name of the Health Resource
     * @param position the tile which the item has been placed on
     */

    public HealthResources(String name, Tile position) {
        this.name = name;
        this.carryable = true;
        this.subtype = "Health Resource";
        this.hasHealingPower = true;
        //Do we need a new type like FoodResources?
        // and hasFoodEffect may false in here as medicine may not affect the food fullness
        this.exchangeable = true;

        this.position = position.getCoordinates();

        this.healthValue = 10;
    }


    /**
     * Returns the name of the health resource
     *
     * @return The name of the health resource
     */

    public String getName() {
        return name;
    }


    /**
     * Returns whether or not the item can be stored in the inventory
     *
     * @return True if the item can be added to the inventory, false
     * if it is consumed immediately
     */


    public Boolean isCarryable() {
        return carryable;
    }

    /**
     * Returns the subtype which the item belongs to.
     *
     * @return The subtype which the item belongs to.
     */

    public String getSubtype() {
        return subtype;
    }

    /**
     * Returns whether or not the item impacts the player's health
     *
     * @return True if the item impacts on the player's health, false otherwise
     */

    public Boolean hasHealingPower() {
        return hasHealingPower;
    }


/*    *//**
     * Returns whether or not the item could deduct the HP of players
     * @return True if the item deduct the player's health, false otherwise


     */



    /**
     * Returns whether or not the item could be exchanged
     *
     * @return True if the item could be exhanged, false otherwise
     */
    @Override
    public Boolean isExchangeable() {
        return exchangeable;
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

    public int getFoodValue() {
        return foodValue;
    }

    public void setHealthValue(int health){
        this.healthValue = health;
    }

    public int getHealthValue(){
        return healthValue;
    }

    /**
     * Creates a string representation of the health resource in the format:
     *
     * <p>'{Health Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {natural resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Health Resource:Wood </p>
     *
     * @return A string representation of the health resource.
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
        return "This item increases or decreases a player's health.";
    }

}