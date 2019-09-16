package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;

public class Aloe_Vera extends HealthResources implements Item {
    // the name of the item
    private String name;

    // the colour of the Aloe_Vera
    private String colour;

    // the biome the Aloe_vera is in (will change to different type in future?)
    private String biome;


    public Aloe_Vera(){
        //As Aloe_Vera usually in desert.
        this.biome = "Desert";
        this.name = "Aloe_Vera";
        this.colour = "green";
        this.foodValue = 0;
        //TODO: look into this.healthValue = 40;
        this.foodValue = 0;
        this.healthValue = 40;
        //this.hasFoodEffect = true;

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
        return "This item can be used to heal" + "\n" + "the Main Character.";
    }

    @Override
    public void use(HexVector position){

    }
}

