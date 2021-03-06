package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.HealthResources;

/*
    An aloe vera plant that gives the player health when they eat it
 */
public class AloeVera extends HealthResources {

    /**
     * Creates a default aloe vera item.
     */
    public AloeVera(){
        //As Aloe_Vera usually in desert.
        this.biome = "Desert";
        this.name = "Aloe_Vera";
        this.colour = "green";
        setHealthValue(2);
        this.description = "This item can be used to heal\n the Main Character.";
    }
}