package deco2800.skyfall.entities.structures;


import com.google.gson.annotations.Expose;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class House extends StaticEntity {

    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "HouseID";
    private int renderOrder;
    private int maxHealth = 10;
    private int currentHealth = 5;
    //Build time in seconds.
    private int buildTime = 10;
    //Currently just uses basic X/Y coords, will be changed at a later date.
    private int sizeX = 1;
    private int sizeY = 1;
    private String texture = "house1";
    private boolean obstructed;


    @Expose
    public Map<HexVector, String> children;

    /**
    Setting up the default constructor
     */
    public House(){
        super();
    }

    public House(Tile tile, int renderOrder) {
        this.setTexture(texture);

        this.setObjectName(ENTITY_ID_STRING);

        this.renderOrder = renderOrder;

        children = new HashMap<>();
        children.put(tile.getCoordinates(), texture);
        if (!WorldUtil.validColRow(tile.getCoordinates())) {
            log.debug(tile.getCoordinates() + "%s Is Invalid:");
            return;
        }

        tile.setParent(this);
        this.currentHealth = maxHealth;

    }












    public House(float x, float y) {
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
