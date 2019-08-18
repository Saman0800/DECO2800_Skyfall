package deco2800.skyfall.entities.structures;


import deco2800.skyfall.entities.AgentEntity;

public class House extends AbstractBuilding  {

    private int maxHealth = 10;
    private int currentHealth = 5;
    //Build time in seconds.
    private int buildTime = 10;
    //Currently just uses basic X/Y coords, will be changed at a later date.
    private int sizeX = 1;
    private int sizeY = 1;

    public House(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        this.setTexture("house1");
    }
    /**
     * @return - Health of the House
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
