package deco2800.skyfall.inventory;

import deco2800.skyfall.resources.Item;
import java.util.*;
import java.lang.*;

/***
 * The inventory associated with the player's current game, and stores collected
 * resources. Able to add and remove items from the inventory, as well as return
 * inventory contents.
 */
public class Inventory {
    //Map that stores the inventory contents
    private Map<Item, Integer> inventory;

    //private List<Item> quickAccessInventory;



    public Inventory(){
        this.inventory = new HashMap<Item, Integer>();

        //Add default items to inventory - eventually use static final constant to populate?

    }


    public Inventory(Map<Item, Integer> inventory){
        //custom constructor
        this.inventory = new HashMap<Item, Integer>();

        //Add inventoryContents to inventory


    }

    public void getInventory(){
        //Return a copy of the inventory
    }

    public void displayInventory(){
        //Sort Items into types for display in GUI and return as 3 Maps rather than 1
    }

    public void inventoryAdd(){
        //Add item to inventory
    }

    public void inventoryRemove(){
        //Remove item from inventory
        //Include ability to remove more than one??
    }




}
