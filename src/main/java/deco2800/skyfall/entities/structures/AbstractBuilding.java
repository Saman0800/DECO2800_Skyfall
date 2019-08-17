package deco2800.skyfall.entities.structures;

//Most likely extend from this to render structures.
import deco2800.skyfall.entities.AbstractEntity;

/**
 * An abstract building is an item that can be placed in the world
 * by the player.
 */
public abstract class AbstractBuilding {

    private int xcoord;
    private int ycoord;

    /**
     *
     * @param x - X coordinate of building
     * @param y - Y coordinate of building
     */
    public AbstractBuilding(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    /**
     * @return - X coordinate of building
     */
    public int getXcoord() {return xcoord;}

    /**
     * @return - Y coordinate of building
     */
    public int getYcoord() {return ycoord;}

    /**
     * @param newXcoord - New X coordinate of building
     */
    public void setXcoord(int newXcoord) {this.xcoord = newXcoord;}

    /**
     * @param newYcoord - New Y coordinate of building
     */
    public void setYcoord(int newYcoord) {this.ycoord = newYcoord;}

    //place method

    //remove method

    //Interaction method

}
