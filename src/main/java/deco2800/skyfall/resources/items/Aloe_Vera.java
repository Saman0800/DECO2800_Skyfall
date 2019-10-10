package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aloe_Vera extends HealthResources {

    public Aloe_Vera(){
        //As Aloe_Vera usually in desert.
        this.biome = "Desert";
        this.name = "Aloe_Vera";
        this.colour = "green";
        setHealthValue(2);
        this.description = "This item can be used to heal\n the Main Character.";
    }
}