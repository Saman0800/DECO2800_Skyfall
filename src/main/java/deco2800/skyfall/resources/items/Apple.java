package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.HealthResources;

public class Apple extends HealthResources {

    public Apple(){
        this.biome = "Forest";
        this.name ="Apple";
        this.colour ="red";
        setHealthValue(4);
        this.description = "This item can be used to heal\n the Main Character.";
    }
}