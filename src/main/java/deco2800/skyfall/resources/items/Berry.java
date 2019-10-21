package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.resources.Item;

/*
    A berry that gives the player health when they eat it
 */
public class Berry extends HealthResources implements Item {

    /**
     * Creates a default berry item.
     */
    public Berry(){
        this.biome = "Forest";
        this.colour ="wine red";
        this.name ="Berry";
        setHealthValue(6);
        this.description = "This item can be used to heal\n the Main Character.";
    }
}
