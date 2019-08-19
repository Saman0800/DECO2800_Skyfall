package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class PoisonousMushroom extends HealthResources implements Item {
    // the name of the item
    private String name;

    // can the item be stored in the inventory
    //private Boolean carryable;

    // the name of the subtype the item belongs to
    //private String subtype;

    // does the item impact the player's health
    ///private Boolean hasHealingPower;

    // the co-ordinates of the tile the item has been placed on
    //private HexVector position;

    //whether or not the item impacts the player's food fullness
    //For PoisonousMushroom, it could recover players' food fullness,
    //but deduct the healing power
    //private Boolean hasFoodEffect;

    // the colour of the PoisonousMushroom
    private String colour;

    // determines whether or not the resource can be traded
    //private Boolean exchangeable;

    // the biome the poisonous mushroom is in (will change to different type in future?)
    private String biome;

    //How many amount of healing power could be recovered
    //private Integer HealingDeducted;

    //Amount of food fullness increased
    //private Integer amoutoffoodeffect;

    //How many PoisonousMushroom that players have;
    //private Integer NumberOfPoisonousMushroom;

    //whether or not the item impacts the player's food fullness
    private Boolean hasFoodEffect;


    //PoisonousMushroom could recover the food fullness, but deducting the
    //healing power
    public PoisonousMushroom(){

        this.biome = "Forest";
        this.colour ="black white";
        this.name ="PoisonousMushroom";
        this.hasFoodEffect = true;
        //PoisonousMushroom can increase the foodValue but reduce the healthValue
        this.foodValue = 20;
        //default constructor added for building inventory
    }

    @Override
    public String getName() {
        return "PoisonousMushroom";
    }


    /**
     * Returns the biome the PoisonousMushroom is situated in
     * @return the biome the PoisonousMushroom is situated in
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
     * Returns the integer of deducting healing power
     *
     * @return the integer of deducting healing power
     */


    /*public Integer HealingDeducted(Integer AmountOfDeducted){
        HealingDeducted = AmountOfDeducted;
        return HealingDeducted;
    }
    */

    /**
     * Returns Amount of food fullness increased
     *
     * @return Amount of food fullness increased
     */
    /*
    public Integer amoutoffoodeffect(Integer Amountoffood) {
        amoutoffoodeffect = Amountoffood;
        return amoutoffoodeffect;
    }

     */



    /**
     * Returns the integer of number of Poisonous Mushroom
     *
     * @return the integer of number of Poisonous Mushroom
     */

    /*public Integer getNumberOfPoisonousMushroom(Integer numberOfPoisonousMushroom){

        NumberOfPoisonousMushroom = numberOfPoisonousMushroom;

        if(NumberOfPoisonousMushroom > 99){
            System.out.println("Out of Maximum number of PoisonousMushroom");
        }
        return NumberOfPoisonousMushroom;
    }*/

    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }





}