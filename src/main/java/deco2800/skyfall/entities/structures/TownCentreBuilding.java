package deco2800.skyfall.entities.structures;

import java.util.TreeMap;

/**
 * Town centre that defines the middle of the players base/village.
 */
public class TownCentreBuilding extends AbstractBuilding {

    private int maxHealth = 80;
    private int currentHealth;

    public TownCentreBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        this.setTexture("buildingA");

        int constructionTime = 6;
        int xSize = 3;
        int ySize = 3;

        TreeMap<String, Integer> constructionCost = new TreeMap<String, Integer>();
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
    public void onTick(long i) {}
}
