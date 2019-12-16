package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public abstract class FoodItem extends StaticEntity implements Item, IFood {

    private int healthValue;
    private HexVector position;
    private String texture;
    private boolean carryable;
    private boolean equippable;

    private final Logger logger = LoggerFactory.getLogger(FoodItem.class);

    public FoodItem(Tile tile, boolean obstructed) {
    }

    public int getHealthValue() {
        return healthValue;
    }
    
}


