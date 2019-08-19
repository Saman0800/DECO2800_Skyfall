package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Apple extends HealthResources implements Item {
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

    // the colour of the Apple
     private String colour;

    // the biome the apple is in (will change to different type in future?)
    private String biome;

    //How many amount of healing power could be recovered
    //private Integer AmountOfHealingPower;

    //Amount of food fullness increased
    //private Integer amoutoffoodeffect;

    //How many Apple that players have;
    //private Integer NumberOfApple;

    //whether or not the item impacts the player's food fullness
     private Boolean hasFoodEffect;



    public Apple(){

        this.biome = "Forest";
        this.name ="Apple";
        this.colour ="red";
        this.hasFoodEffect = true;
        this.foodValue = 25;
        //TODO: look into this.healthValue = 5;
        //default constructor added for building inventory
    }


    @Override
    public String getName() {
        return "Apple";
    }


    /**
     * Returns the biome the apple is situated in
     * @return the biome the apple is situated in
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
     * Returns the integer of number of Apple
     *
     * @return the integer of number of Apple
     */

    /*public Integer getNumberOfApple(Integer numberOfApple){

        NumberOfApple = numberOfApple;

        if(NumberOfApple > 99){
            System.out.println("Out of Maximum number of Apple");
        }
        return NumberOfApple;
    }*/

    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }


}