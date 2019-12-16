package deco2800.skyfall.entities.fooditems;

/**
 * Food item that spawn in the world
 */
public interface IFood {

    /**
     * the value of health this item provided
     * @return a number
     */
   int getHealthValue();

    /**
     * the value of health for this item
     */
   void setHealthValue(int value);
}
