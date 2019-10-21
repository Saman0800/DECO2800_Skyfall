package deco2800.skyfall.entities.structures;

import deco2800.skyfall.entities.SaveableEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract building is an item that can be placed in the world by the
 * player.
 */
public abstract class AbstractBuilding extends SaveableEntity {

    private float xcoord;
    private float ycoord;

    private int sizeX;
    private int sizeY;

    private int buildTime;

    private Map<String, Integer> buildCost = new HashMap<>();

    /**
     * @param x - X coordinate of building
     * @param y - Y coordinate of building
     */
    public AbstractBuilding(float x, float y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    // Interaction method (onTick)

    /**
     * @return - cost of building the building
     */
    public Map<String, Integer> getCost() {
        return buildCost;
    }

    /**
     * @return - X coordinate of building
     */
    public float getXcoord() {
        return xcoord;
    }

    /**
     * @return - Y coordinate of building
     */
    public float getYcoord() {
        return ycoord;
    }

    /**
     * @return - X size of building
     */
    public int getXSize() {
        return sizeX;
    }

    /**
     * @return - Y size of building
     */
    public int getYSize() {
        return sizeY;
    }

    /**
     * @param newBuildTime - new construction time of the building
     */
    public void setBuildTime(int newBuildTime) {
        this.buildTime = newBuildTime;
    }

    /**
     * @return - Build time
     */
    public int getBuildTime() {
        return this.buildTime;
    }

}
