package deco2800.skyfall.entities.structures;

/**
 * Walls that the player can place. Walls are stationary buildings that
 * impede all land units. Walls block projectiles.
 */
public class WallBuilding extends AbstractBuilding {

    private int maxHealth = 5;
    private int currentHealth;
    //Build time in seconds.
    private int buildTime = 3;
    //Currently just uses basic X/Y coords, will be changed at a later date.
    private int sizeX = 1;
    private int sizeY = 1;

    public WallBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        //Ignore that the fence is using a building image.
        this.setTexture("buildingA");
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
     * @return - Build time
     */
    public int getBuildTime() {return this.buildTime;}

    /**
     * @return - X length
     */
    public int getXSize() {return this.sizeX;}

    /**
     * @return - Y length
     */
    public int getYSize() {return this.sizeY;}

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
            currentHealth = currentHealth = damage;
        } else {
            currentHealth = 0;
        }
    }

    /**
     * @param newXSize - New X length
     */
    public void setXSize(int newXSize) {this.sizeX = newXSize;}

    /**
     * @param newYSize - New Y length
     */
    public void setYSize(int newYSize) {this.sizeY = newYSize;}

    @Override
    public void onTick(long i) {
        //Functionality.
    }

}
