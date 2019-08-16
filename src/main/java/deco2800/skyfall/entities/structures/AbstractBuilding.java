package deco2800.skyfall.entities.structures;

import deco2800.skyfall.entities.AbstractEntity;

public abstract class AbstractBuilding extends AbstractEntity {

    private int width;
    private int height;
    private Object buildItem;
    private int numItems;

    /**
     * Constructor for abstract building
     *
     * @param width width of the building
     * @param height height of the building
     */
    public AbstractBuilding(int width, int height, Object buildItem, int numItems) {
        //Size ( Row : Col) Straight coordinates system, not shifted
        //What if we have funny shaped buildings? Like L shaped.
        this.width = width;
        this.height = height;
        //Cost ( What is needed to build / Blueprints)
        //This might need to be together, i.e. if a building requires multiple types of objects to build

        //Make an object to store an object and int

        this.buildItem = buildItem; //Object needed to build
        this.numItems = numItems; // Num of items needed to build
        //Texture  (Thumbnail to display in menu for player to see what it looks like, can just be the picture)
        //Description ( What does this building do?)
    }

    /**
     * @return - width of the structure.
     */
    public int getWidth() {return width;}

    /**
     * @return - height of the structure.
     */
    public int getHeight() {return height;}

    /**
     * @return - Return the "object"(needs to be integrated to items) required to build
     */
    public Object getBuildItem() {return buildItem;}

    /**
     * @return - Return number of the object required to build.
     */
    public int getNumItems() {return numItems;}

    /**
     * @param newWidth - Set new width for building
     */
    public void setWidth(int newWidth) {this.width = newWidth;}

    /**
     * @param newHeight - Set new height for building
     */
    public void setheight(int newHeight) {this.height = newHeight;}

    /**
     * @param newItem - Set new object required to build.
     */
    public void setBuildItem(Object newItem) {this.buildItem = newItem;}

    /**
     * @param newNumItems - Set number of objects required to build.
     */
    public void setNumItems(int newNumItems) {this.numItems = newNumItems;}
}
