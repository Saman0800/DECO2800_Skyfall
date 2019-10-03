package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Apple extends HealthResources implements Item {
    // the name of the item
    private String name;

    // amount of health item provides
    private int health = 4;

    //whether or not the item impacts the player's food fullness
    private Boolean hasFoodEffect;

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(Apple.class);

    public Apple(){
        this.biome = "Forest";
        this.name ="Apple";
        this.colour ="red";
        this.hasFoodEffect = true;
        this.foodValue = 25;
        //TODO: look into this.healthValue = 5;
        this.healthValue = 10;
        //default constructor added for building inventory
    }


    /**
     * Returns the name of the health resource
     * @return the name of the health resource
     */
    @Override
    public String getName() {
        return "Apple";
    }



    /**
     * Returns whether or not the item impacts the player's food fullness
     *
     * @return True if the item impacts on the player's food fullness, false otherwise
     */
    public Boolean hasFoodEffect() {
        return hasFoodEffect;
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
        return "This item can be used to satisfy the" + "\n" + "Main Character's hunger.";
    }

    @Override
    public void use(HexVector position){
        // Check player status
        if (MainCharacter.getInstance().getHealth() < 50 && !MainCharacter.getInstance().isDead()) {
            // Add health to player
            MainCharacter.getInstance().changeHealth(health);

            // Update health message
            logger.info("Apple eaten. Health increased by {}!", health);
        }

    }


}