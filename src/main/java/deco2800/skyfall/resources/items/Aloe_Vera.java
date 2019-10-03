package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aloe_Vera extends HealthResources implements Item {
    // the name of the item
    private String name;

    // the colour of the Aloe_Vera
    private String colour;

    // amount of health item provides
    private int health = 2;


    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(Aloe_Vera.class);


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
        // Check player status
        if (MainCharacter.getInstance().getHealth() < 50 && !MainCharacter.getInstance().isDead()) {
            // Add health to player
            MainCharacter.getInstance().changeHealth(health);

            // Update health message
            logger.info("Alo Vera eaten. Health increased by {}!", health);
        }
    }
}

