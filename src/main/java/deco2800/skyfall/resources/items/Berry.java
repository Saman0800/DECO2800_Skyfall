package deco2800.skyfall.resources.items;


import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Berry extends HealthResources implements Item {
    // the name of the item
    private String name;

    // the biome the berry is in (will change to different type in future?)
    private String biome;

    // amount of health item provides
    private int health = 6;

    //The color of the berry
    private String colour;

    //whether or not the item impacts the player's food fullness
    private Boolean hasFoodEffect;

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(Berry.class);

    /**
     * Creates a default berry item.
     */
    public Berry(){

        this.biome = "Forest";
        this.colour ="wine red";
        this.name ="Berry";
        this.hasFoodEffect = true;
        this.foodValue = 20;
        //TODO: look into this.healthValue = 5;
        this.foodValue = 10;
        this.healthValue = 5;
    }


    @Override
    public String getName() {
        return "Berry";
    }


    /**
     * Returns the biome the sand is situated in
     * @return the biome the sand is situated in
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
        return "This item can be used to satisfy" + "\n" + "the Main Character's hunger";
    }

    @Override
    public void use(HexVector position){
        // Check player status
        if (MainCharacter.getInstance().getHealth() < 50 && !MainCharacter.getInstance().isDead()) {
            // Add health to player
            MainCharacter.getInstance().changeHealth(health);

            // Update health message
            logger.info("Berry eaten. Health increased by {}!", health);
        }
    }

}
