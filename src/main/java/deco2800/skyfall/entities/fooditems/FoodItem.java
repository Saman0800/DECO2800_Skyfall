package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Fooditem abstract class to provide method to use in different food class
 */
public abstract class FoodItem extends StaticEntity implements Item, IFood {

    private String name;
    private int healthValue;
    private HexVector position;
    private String texture;
    private boolean carryable;
    private boolean equippable;
    private String subtype;

    private final Logger logger = LoggerFactory.getLogger(FoodItem.class);

    public FoodItem(Tile tile, String texture,  boolean obstructed, String name) {
        super(tile, 5, texture, obstructed);
        this.name = name;
        this.texture = texture;
        this.subtype = "Food Item";
        this.carryable = true;
        this.equippable = true;
    }

    /**
     * get the name of the food item
     * @return a string represent the name of the food
     */
    public String getName() {return this.name;}

    /**
     * get the texture of the food item
     * @return a string represent the texture of the food
     */
    public String getTexture() {
        return this.texture;
    }

    /**
     * get the health value of the food item
     * @return an int represent the health value of the food
     */
    public int getHealthValue() {
        return healthValue;
    }

    /**
     * set the name of the food item
     * @param name the name to be set
     */
    public void setName(String name) {this.name = name;}

    /**
     * set the health value of the food item
     * @param value the value to be set as the health value for the food
     */
    public void setHealthValue(int value) {this.healthValue = value;}

    /**
     * set texture for the food
     * @param texture String texture the texture to be set
     */
    public void setTexture(String texture) {this.texture = texture;}

    /**
     * check if the item is carryable
     * @return
     */
    public boolean isCarryable() {return this.carryable;}

    /**
     * @return Subtype of food
     */
    public String getSubtype() {
        return this.subtype;
    }

    /**
     * check if this item is equippable
     * @return
     */
    @Override
    public boolean isEquippable() {
        return equippable;
    }


    /**
     * return the string represent this item
     * @return the string represent this item
     */
    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }

    /**
     * get the position of this item
     * @return the position for this item
     */
    @Override
    public HexVector getCoords() {
        return position;
    }

    /**
     * check if this item can be exchange
     * @return false
     */
    @Override
    public boolean isExchangeable() {
        return false;
    }

    protected String description;

    /**
     * get the description of the item
     * @return the description for the item
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * the method for player to use this food item
     * @param position
     */
    @Override
    public void use(HexVector position){
        if (MainCharacter.getInstance().getHealth() < 50 && !MainCharacter.getInstance().isDead()) {
            MainCharacter.getInstance().changeHealth(getHealthValue());
        }
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method
    }
}


