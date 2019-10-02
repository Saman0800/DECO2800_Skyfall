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

    // the colour of the Apple
    private String colour;

    // the biome the apple is in (will change to different type in future?)
    private String biome;

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


    @Override
    public String getName() {
        return "Apple";
    }


    /**
     * Returns the biome the apple is situated in
     * @return the biome the apple is situated in
     */
    public String getBiome(){
        return biome;
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