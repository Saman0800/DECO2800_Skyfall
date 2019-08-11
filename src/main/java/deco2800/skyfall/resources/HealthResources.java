package deco2800.skyfall.resources;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;




public abstract class HealthResources implements Item {

    // can the item be stored in the inventory
    private Boolean carryable;
    // the name of the item e.g. food, poison
    private String name;
    //impact the player's health or not
    private Boolean hasHealingPower;
    //whether or not the item impacts the player's food fullness
    private Boolean hasFoodEffect;
    // the name of the subtype the item belongs to
    private String subtype;
    // the co-ordinates of the tile the item has been placed on
    private HexVector position;
    //How many amount of healing power could be recovered
    private Integer AmountOfHealingPower;
    //How many amount of healing will be deducted if have a poison
    private Integer HealingDeducted;
    //Amount of food fullness increased
    private Integer amoutoffoodeffect;
    //if and only if the items deduct the HP of player
    private Boolean notHealingPower;
    //Items could change or not e.g. coins, items
    private Boolean exchangeable;


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
        //Do we need a new type like FoodResources? and hasFoodEffect may false in here as medicine may not affect the food fullness
        this.hasFoodEffect = true;
        //this.notHealingPower=false;
        this.exchangeable = true;

        this.position = position.getCoordinates();
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

    /**
     * Returns whether or not the item impacts the player's food fullness
     *
     * @return True if the item impacts on the player's food fullness, false otherwise
     */
    public Boolean hasFoodEffect() {
        return hasFoodEffect;
    }
/*    *//**
     * Returns whether or not the item could deduct the HP of players
     * @return True if the item deduct the player's health, false otherwise
<<<<<<< HEAD

     */


    /**public Boolean getNotHealingPower() {
     * return notHealingPower;
     * }*/


    public Boolean getNotHealingPower() {
        return notHealingPower;
    }


    /**
     * Returns the integer of healing power
     *
     * @return the integer of healing power
     */

    public Integer AmountOfHealingPower(Integer AmountOfRecoverHP){
        AmountOfHealingPower=AmountOfRecoverHP;

        return AmountOfHealingPower;
    }

    /**
     * Returns the integer of deducting healing power
     *
     * @return the integer of deducting healing power
     */





    public Integer HealingDeducted(Integer AmountOfDeducted){
        HealingDeducted=AmountOfDeducted;
        return HealingDeducted;
    }

    /**
     * Returns Amount of food fullness increased
     *
     * @return Amount of food fullness increased
     */
    public Integer amoutoffoodeffect(Integer Amountoffood) {
        amoutoffoodeffect = Amountoffood;
        return amoutoffoodeffect;
    }
    /**
     * Returns whether or not the item could be exchanged
     *
     * @return True if the item could be exhanged, false otherwise
     */
    @Override
    public Boolean getExchangeable() {
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
}