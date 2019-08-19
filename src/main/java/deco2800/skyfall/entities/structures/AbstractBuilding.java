package deco2800.skyfall.entities.structures;

import com.google.gson.annotations.Expose;
import deco2800.skyfall.entities.AbstractEntity;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import deco2800.skyfall.worlds.AbstractWorld;

import java.util.TreeMap;

/**
 * An abstract building is an item that can be placed in the world
 * by the player.
 */
public abstract class AbstractBuilding extends AbstractEntity {

    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "staticEntityID";
    private int renderOrder;

    @Expose
    public Map<HexVector, String> children;


    private float xcoord;
    private float ycoord;

    private int sizeX;
    private int sizeY;

    private int buildTime;

    private TreeMap<String, Integer> buildCost = new TreeMap<String, Integer>();

    /**
     *
     * @param x - X coordinate of building
     * @param y - Y coordinate of building
     */
    public AbstractBuilding(float x, float y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    /**
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param height - Render height
     * @param world - World to place building in
     */
    public void placeBuilding(float x, float y, int height, AbstractWorld world) {
        //Construction_Permissions
        setPosition(x, y, height);
        world.addEntity(this);
    }

    /**
     * @param world - World to remove building from
     */
    public void removeBuilding(AbstractWorld world) {
        world.removeEntity(this);
    }

    //Interaction method (onTick)

    /**
     * @return - cost of building the building
     */
    public TreeMap<String, Integer> getCost(){
        return buildCost;
    }

    /**
     * @param newCost set the cost of building
     */
    public void setCost(TreeMap<String, Integer> newCost){
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
