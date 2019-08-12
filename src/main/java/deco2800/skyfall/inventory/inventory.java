package deco2800.skyfall.inventory;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.Wood;
import java.util.*;
import java.lang.*;

/***
 * The inventory associated with the player's current game, and stores collected
 * resources. Able to add and remove items from the inventory, as well as return
 * inventory contents.
 */
public class Inventory {
    //Map that stores the inventory contents
    private Map<Class<? extends Item>, Integer> inventory;

    //private List<Item> quickAccessInventory;


    /***
     *Create an inventory and add initial default items to the inventory.
     */
    public Inventory(){
        this.inventory = new HashMap<Class<? extends Item>, Integer>();

        //Add default items to inventory - eventually find a way to populate more flexibly
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Wood());
        this.inventoryAdd(new Wood());

    }

    /***
     * Create an inventory and populate it with custom mappings of resource
     * type to the number of that particular resource.
     * @param inventory Map of Class to integer containing inventory items to add
     */
    public Inventory(Map<Class<? extends Item>, Integer> inventory){
        this.inventory = new HashMap<Class<? extends Item>, Integer>();

        //Add inventoryContents to inventory
        this.inventory.putAll(inventory);

    }


    /***
     * Return the inventory.
     * @return Map that represents the inventory.
     */
    public Map<Class<? extends Item>, Integer> getInventory(){
        return Collections.unmodifiableMap(this.inventory);
    }


    /***
     * Print the inventory
     */
    public void printInventory(){
        //Print inventory
    }


    /***
     * Sort inventory items into sub types for display
     */
    public void displayInventory(){
        //Sort Items into types for display in GUI and return as 3 Maps rather than 1
    }


    /***
     * Add an item to the inventory.
     * @param item Item to add to the inventory.
     */
    public void inventoryAdd(Item item){
        if(item.isCarryable()){
            Class<? extends Item> type = item.getClass();

            if(inventory.get(type) != null){

                inventory.put(type, inventory.get(type) + 1);

            }else{
                inventory.put(type, 1);
            }

        }else{
            //Eventually make this throw an exception?
            System.out.println("Item can not be carried in the inventory");
        }

    }


    /***
     * Remove an item from the inventory.
     * @param type The type of item to remove
     */
    public void inventoryDrop(Class<? extends Item> type){
        Integer num = this.inventory.get(type);

        if(num == null){
            System.out.println("You can't remove what you don't have!");
        } else if(num == 1){
            this.inventory.remove(type);
        } else if(num > 1){
            this.inventory.put(type, inventory.get(type) - 1);
        }

    }




}
