package deco2800.skyfall.managers;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.*;
import java.util.*;

public class InventoryManager extends TickableManager {

    //Map that stores the inventory contents
    private Map<String, List<Item>> inventoryContents;

    //List that stores the names of items in quick access inventory
    private List<String> quickAccess;

    //Maximum size of quick access inventory
    private static final int QA_MAX_SIZE = 6;

    @Override
    public void onTick(long i) {

    }


    /***
     * Creates an inventory and adds default items to the inventory, as well
     * as initialises an empty quick access inventory.
     */
    public InventoryManager(){
        this.inventoryContents = new HashMap<>();
        this.quickAccess = new ArrayList<>();

        //Add default items to inventory
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Wood());
        this.inventoryAdd(new Wood());
    }


    /***
     * Create an inventory and adds a custom map of items to the inventory.
     * Creates a quick access inventory and adds items to it.
     *
     * @param inventoryContents a Map<String, List<Item>> where String is the item's name and List<Item>
     *                          is a list of objects that implement the Item interface.
     * @param quickAccessContents a List<String> where String is the name of item in quick access inventory
     */

    public InventoryManager(Map<String, List<Item>> inventoryContents, List<String> quickAccessContents){
        this.inventoryContents = new HashMap<>();
        this.quickAccess = new ArrayList<>();

        this.inventoryContents.putAll(inventoryContents);

        for (String quickAccessContent : quickAccessContents) {
            this.quickAccessAdd(quickAccessContent);
        }
    }


    /***
     * Get a copy of the inventory contents.
     * @return a copy of the inventory
     */
    public Map<String, List<Item>> getInventoryContents(){
        return Collections.unmodifiableMap(this.inventoryContents);
    }

    /***
     * Get the amount of each item in the inventory
     * @return a map of item name to the integer amount in the inventory for all inventory items.
     */
    public Map<String, Integer> getInventoryAmounts(){

        Map<String, Integer> inventoryAmounts = new HashMap<>();

        for (Map.Entry<String, List<Item>> entry : this.inventoryContents.entrySet()) {
            Integer amount = entry.getValue().size();
            inventoryAmounts.put(entry.getKey(), amount);
        }

        return inventoryAmounts;
    }

    /***
     * Get the items in the quick access inventory.
     * @return a map of the items in the quick access inventory, and the number of each item
     */
    public Map<String, Integer> getQuickAccess(){

        Map<String, Integer> qai = new HashMap<>();

        for (String item : quickAccess) {
            qai.put(item, getAmount(item));
        }

        return qai;
    }


    /***
     * Add an item type from the full inventory to the quick access inventory.
     * Ensures that the item being added to quick access inventory is in full inventory
     * and that quick access inventory doesn't contain more than 6 item type.
     * @param item the String name of the item to add to the quick access inventory.
     */
    public void quickAccessAdd(String item){
        if((this.getAmount(item) > 0) && (this.quickAccess.size() < QA_MAX_SIZE)){
            this.quickAccess.add(item);
        } else{
            System.out.println("Sorry I can't add that!");
        }
    }


    /***
     * Remove an item type from the quick access inventory.
     * @param item the String name of the item to remove from quick access.
     */
    public void quickAccessRemove(String item){
        this.quickAccess.remove(item);
    }



    /***
     * Get the amount of a single item in the inventory.
     * @param item the string name for the item
     * @return the integer number of that item type in the inventory
     */
    public int getAmount(String item){
        Map<String, Integer> inventoryAmounts = this.getInventoryAmounts();

        if(inventoryAmounts.get(item) != null){
            return inventoryAmounts.get(item);
        } else{
            return 0;
        }

    }


    /***
     * Get the full inventory as a string.
     * @return a string representation of the inventory.
     */
    @Override
    public String toString(){
        Map<String, Integer> inventoryAmounts = this.getInventoryAmounts();
        return "Inventory Contents " + inventoryAmounts.toString();
    }


    /***
     * Add an item to the full inventory.
     * @param item the item to add to the inventory, implements Item interface.
     */
    public void inventoryAdd(Item item){
        String name = item.getName();

        if(this.inventoryContents.get(name) != null){

            List<Item> itemsList = this.inventoryContents.get(name);
            itemsList.add(item);
            this.inventoryContents.put(name, itemsList);

        }else{
            List<Item> itemsList = new ArrayList<>();
            itemsList.add(item);
            this.inventoryContents.put(name, itemsList);

        }
    }




    /***
     * Removes an item from the inventory and returns it. If the item is the last of a specific type
     * present in the inventory (and quick access inventory) it is also removed from these stores.
     * @param itemName the String name of the item to drop from the inventory.
     * @return the Item dropped from the inventory
     */
    public Item inventoryDrop(String itemName){

        if(this.inventoryContents.get(itemName) != null){
            Integer num = this.inventoryContents.get(itemName).size();

            if(num == 1){
                Item item = this.inventoryContents.get(itemName).get(0);
                this.inventoryContents.remove(itemName);
                this.quickAccessRemove((itemName));
                return item;
            } else if(num > 1){
                List<Item> itemsList = this.inventoryContents.get(itemName);
                Item item = itemsList.get(num-1);
                itemsList.remove(num-1);
                this.inventoryContents.put(itemName, itemsList);
                return item;
            }

        }

        System.out.println("You can't remove what you don't have!");
        return null;
    }


    /***
     * Drop multiple items of the same type from the inventory, and return as a list.
     * If these items are the last of a specific type present in the inventory
     * (and quick access inventory) the type is also removed from these stores.
     *
     * @param itemName the string name of the item to drop from the inventory
     * @param amount the number of the item type to drop from the inventory
     * @return a list of the items dropped from the inventory
     */
    public List<Item> inventoryDropMultiple(String itemName, Integer amount){
        List<Item> itemsDropped = new ArrayList<>();
        List<Item> itemsList = this.inventoryContents.get(itemName);

        if(itemsList != null){
            int num = this.inventoryContents.get(itemName).size();

            if(amount < num){

                for(int i = 1; i <= amount; i ++){
                    Item item = itemsList.get(num-i);
                    itemsDropped.add(item);
                    itemsList.remove(num-i);
                }

                this.inventoryContents.put(itemName, itemsList);

            } else if(amount == num){
                itemsDropped.addAll(itemsList);
                this.inventoryContents.remove(itemName);
                this.quickAccessRemove((itemName));


            } else {
                System.out.println("You don't have that many " + itemName + "s!");
                itemsDropped = null;
            }


        }else{
            itemsDropped = null;
        }

        return itemsDropped;

    }

}
