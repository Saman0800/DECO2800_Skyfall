package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Aloe_Vera extends HealthResources implements Item {
    // the name of the item
    private String name;

    // can the item be stored in the inventory
    //private Boolean carryable;

    // the name of the subtype the item belongs to
    //private String subtype;

    //does the item impact the players' health
    //private Boolean hasHealingPower;

    //whether or not the item impacts the player's food fullness
    //private Boolean hasFoodEffect;

    // the co-ordinates of the tile the item has been placed on
    //private HexVector position;

    // determines whether or not the resource can be traded


    // the colour of the Aloe_Vera
    private String colour;

    // the biome the Aloe_vera is in (will change to different type in future?)
    private String biome;

    //How many amount of healing power could be recovered
    //private Integer AmountOfHealingPower;

    //How many AloeVeras that players have;
    //private Integer NumberOfAloeVera;

    //whether or not the item impacts the player's food fullness
    private Boolean hasFoodEffect;


    public Aloe_Vera(){
        this.biome = "Forest";
        this.name = "Aloe_Vera";
        this.colour = "green";
        this.hasFoodEffect = true;
        //default constructor added for building inventory
    }


    @Override
    public String getName() {
        return "Aloe_Vera";
    }


    /**
     * Returns the biome the Aloe_Vera is situated in
     * @return the biome the Aloe_Vera is situated in
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
     * Returns the integer of number of Aloe Vera
     *
     * @return the integer of number of Aloe Vera
     */

     /*public Integer getNumberOfAloeVera(Integer numberOfAloeVera){

        NumberOfAloeVera = numberOfAloeVera;

        if(NumberOfAloeVera > 99){
            System.out.println("Out of Maximum number of Aloe Vera");
        }
        return NumberOfAloeVera;
    }*/


    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }



}

