package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;

public class PoisonousMushroom extends HealthResources implements Item {
    // the name of the item
    private String name;


    // the colour of the PoisonousMushroom
    private String colour;

    // determines whether or not the resource can be traded
    //private boolean exchangeable;

    // the biome the poisonous mushroom is in (will change to different type in future?)
    private String biome;

    //whether or not the item impacts the player's food fullness
    private boolean hasFoodEffect;


    /**
     * Creates a generic poisonous mushroom.
     */
    public PoisonousMushroom(){

        this.biome = "Forest";
        this.colour ="black white";
        this.name ="PoisonousMushroom";
        this.hasFoodEffect = true;
        //PoisonousMushroom can increase the foodValue but reduce the healthValue
        this.foodValue = -20;
        //Todo: Look into this.healthValue = -20;
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
    public boolean hasFoodEffect() {
        return hasFoodEffect;
    }

    @Override
    public String getDescription(){
        return "This is a poisonous mushroom." + "\n" + " It is bad for your health";
    }

    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }

    @Override
    public void use(HexVector position){

    }



}