package deco2800.skyfall.resources.items;


import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Berry extends HealthResources implements Item {
    // the name of the item
    private String name;

    // can the item be stored in the inventory
    //private Boolean carryable;

    // the name of the subtype the item belongs to
    //private String subtype;

    // does the item impact the player's health
    //private Boolean hasHealingPower;

    //whether or not the item impacts the player's food fullness
    //private Boolean hasFoodEffect;

    // the co-ordinates of the tile the item has been placed on
    //private HexVector position;

    // determines whether or not the resource can be traded
    //private Boolean exchangeable;

    //Amount of food fullness increased
     //private Integer amoutoffoodeffect;

    // the biome the berry is in (will change to different type in future?)
    private String biome;

    //How many amount of healing power could be recovered
    //private Integer AmountOfHealingPower;

    //The color of the berry
    private String colour;

    //How many Berry that players have;
    //private Integer NumberOfBerry;

    //whether or not the item impacts the player's food fullness
    private Boolean hasFoodEffect;

    public Berry(){
        this.biome = "Forest";
        this.colour ="wine red";
        this.name ="Berry";
        this.hasFoodEffect = true;
        //default constructor added for building inventory
    }


    @Override
    public String getName() {
        return "Berry";
    }


    /**
     * Returns the biome the sand is situated in
     * @return the biome the sand is situated in
     */
    public String getBiome(){
        return biome;
    }

    /**
     * Returns whether or not the item impacts the player's food fullness
     *
     * @return True if the item impacts on the player's food fullness, false otherwise
     */
    public Boolean hasFoodEffect() {
        return hasFoodEffect;
    }

    /**
     * Returns the integer of healing power
     *
     * @return the integer of healing power
     */

    /*public Integer AmountOfHealingPower(Integer AmountOfRecoverHP){
        AmountOfHealingPower = AmountOfRecoverHP;

        return AmountOfHealingPower;
    }
    */

    /**
     * Returns Amount of food fullness increased
     *
     * @return Amount of food fullness increased
     */
    /*public Integer amoutoffoodeffect(Integer Amountoffood) {
        amoutoffoodeffect = Amountoffood;
        return amoutoffoodeffect;
    }

     */

    /**
     * Returns the integer of number of Berry
     *
     * @return the integer of number of Berry
     */

    /*public Integer getNumberOfBerry(Integer numberOfBerry){

        NumberOfBerry = numberOfBerry;

        if(NumberOfBerry > 99){
            System.out.println("Out of Maximum number of Berry");
        }
        return NumberOfBerry;
    }*/

    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }



}
