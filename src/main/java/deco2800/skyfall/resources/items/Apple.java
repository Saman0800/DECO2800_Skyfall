package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Apple extends HealthResources {

    public Apple(){
        this.biome = "Forest";
        this.name ="Apple";
        this.colour ="red";
        setHealthValue(4);
        this.description = "This item can be used to heal\n the Main Character.";
    }
}