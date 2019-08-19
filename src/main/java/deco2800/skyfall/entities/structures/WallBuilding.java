package deco2800.skyfall.entities.structures;

import java.util.TreeMap;

/**
 * Walls that the player can place. Walls are stationary buildings that
 * impede all land units. Walls block projectiles.
 */
public class WallBuilding extends AbstractBuilding {

    private int maxHealth = 5;
    private int currentHealth;

    public WallBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        //Ignore that the fence is using a building image.
        this.setTexture("buildingA");

        //Build time in seconds.
        int constructionTime = 3;
        //Building size
        int xSize = 1;
        int ySize = 1;
        //Cost of building
        //String: Name of item, can access using item.getName();
        //Integer: The number of that type of item
        TreeMap<String, Integer> constructionCost = new TreeMap<String, Integer>();

        this.setXSize(xSize);
        this.setYSize(ySize);
        this.setBuildTime(constructionTime);
        this.setCost(constructionCost);
    }
    /**
     * @return - Health of the fence
     */
    public int getMaxHealth() {return this.maxHealth;}

    /**
     * @return - Current health
     */
    public int getCurrentHealth() {return this.currentHealth;}


    /**
     * @param newMaxHealth - New max health
     */
    public void setHealth(int newMaxHealth) {this.maxHealth = newMaxHealth;}

    /**
     * @param newCurrentHealth - New current health
     */
    public void setCurrentHealth(int newCurrentHealth) {this.currentHealth = newCurrentHealth;}

    /**
     * @param damage - Amount of damage to take
     */
    public void takeDamage(int damage) {
        if((currentHealth - damage) > 0) {
            currentHealth = currentHealth - damage;
        } else {
            currentHealth = 0;
        }
    }

    @Override
    public void onTick(long i) {
        //Functionality.
    }

}
