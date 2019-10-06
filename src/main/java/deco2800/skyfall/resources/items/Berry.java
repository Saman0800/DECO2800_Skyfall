package deco2800.skyfall.resources.items;


import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.util.HexVector;

public class Berry extends HealthResources implements Item {
    // the name of the item
    private String name;

    // the biome the berry is in (will change to different type in future?)
    private String biome;

    //The color of the berry
    private String colour;

    //whether or not the item impacts the player's food fullness
    private boolean hasFoodEffect;

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
    public boolean hasFoodEffect() {
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

    }

}
