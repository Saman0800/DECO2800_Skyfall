package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public abstract class FoodItem extends StaticEntity implements Item, IFood {

    private String name;
    private String foodType;
    private int healthValue;
    private HexVector position;
    private String texture;
    private boolean carryable;
    private boolean equippable;

    private final Logger logger = LoggerFactory.getLogger(FoodItem.class);

    public FoodItem(Tile tile, String texture,  boolean obstructed, String name) {
        super(tile, 5, texture, obstructed);
        this.name = name;
        this.texture = texture;
        this.carryable = true;
        this.equippable = true;
        this.position = tile.getCoordinates();
    }

    public String getName() {return this.name;}

    public String getTexture(String texture) {
        return this.texture;
    }
    public int getHealthValue() {
        return healthValue;
    }

    public void setName(String name) {this.name = name;}

    public void setHealthValue(int value) {this.healthValue = value;}

    public void setTexture(String texture) {this.texture = texture;}

    public boolean isCarryable() {return this.carryable;}

    /**
     * @return Subtype of food
     */
    public String getSubtype() {
        return this.foodType;
    }

    @Override
    public boolean isEquippable() {
        return equippable;
    }
    @Override
    public void onTick(long i) {
        // Auto-generated method
    }

    @Override
    public HexVector getCoords() {
        return this.position;
    }

    @Override
    public boolean isExchangeable() {
        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void use(HexVector position){
        if (MainCharacter.getInstance().getHealth() < 50 && !MainCharacter.getInstance().isDead()) {
            MainCharacter.getInstance().changeHealth(getHealthValue());
        }
    }
}


