package deco2800.skyfall.entities.structures;


import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.worlds.world.World;

import java.util.HashMap;
import java.util.Map;


/**
 * An Building is an item that can be placed in the world
 * by the player.
 */
public class Building extends AbstractEntity {


    private String name;
    private int maxHealth;
    private int sizeX;
    private int sizeY;
    private int buildTime;
    private String texture;
    private Map<String, Integer> buildCost = new HashMap<>();


    private float xcoord;
    private float ycoord;

    /**
     * The Constructor for a building
     * @param name The name of the building type
     * @param maxHealth The max health of the building
     * @param sizeX the size of the building in the x direction
     * @param sizeY the size of the building in the y direction
     * @param buildTime the build time for the building
     * @param texture the texture for the building
     */
    public Building(String name, int maxHealth, int sizeX, int sizeY, int buildTime, String texture) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.buildTime = buildTime;
        this.texture = texture;
    }

    public void onTick(long i){

        //Do all of the functionality behind this later!!

    }

    /**
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param height - Render height
     * @param world - World to place building in
     */
    public void placeBuilding(float x, float y, int height, World world) {
        //Construction_Permissions
        setPosition(x, y, height);
        world.addEntity(this);
    }

    /**
     * @param world - World to remove building from
     */
    public void removeBuilding(World world) {
        world.removeEntity(this);
    }

    //Interaction method (onTick)

    /**
     * @return - cost of building the building
     */
    public Map<String, Integer> getCost(){
        return buildCost;
    }

    /**
     * @param newCost set the cost of building
     */
    public void setCost(Map<String, Integer> newCost){
        this.buildCost = newCost;
    }

    /**
     * @return - X coordinate of building
     */
    public float getXcoord() {return xcoord;}

    /**
     * @return - Y coordinate of building
     */
    public float getYcoord() {return ycoord;}

    /**
     * @return - X size of building
     */
    public int getXSize(){
        return sizeX;
    }

    /**
     * @return - Y size of building
     */
    public int getYSize(){
        return sizeY;
    }

    /**
     * @param newBuildTime - new construction time of the building
     */
    public void setBuildTime(int newBuildTime){
        this.buildTime = newBuildTime;
    }

    /**
     * @param newXcoord - New X coordinate of building
     */
    public void setXcoord(int newXcoord) {this.xcoord = newXcoord;}

    /**
     * @param newYcoord - New Y coordinate of building
     */
    public void setYcoord(int newYcoord) {this.ycoord = newYcoord;}

    /**
     * @param newXSize - New X length
     */
    public void setXSize(int newXSize) {this.sizeX = newXSize;}

    /**
     * @param newYSize - New Y length
     */
    public void setYSize(int newYSize) {this.sizeY = newYSize;}

    /**
     * @return - Build time
     */
    public int getBuildTime() {return this.buildTime;}

}


