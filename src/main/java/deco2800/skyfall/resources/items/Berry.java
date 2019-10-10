package deco2800.skyfall.resources.items;


import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
