package deco2800.skyfall.inventory;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.*;
import java.util.*;
import java.lang.*;


/***
 * A player's inventory within the game, stored as mappings between
 * the item type name String and a list of items. Player is able to add to, remove
 * from and get inventory contents.
 */
public class Inventory {
    //Map that stores the inventory contents
    private Map<String, List<Item>> inventory;

    //Quick Access Inventory
    private List<Item> quickAccessInventory;


    /***
     * Create an inventory and add default items to the inventory.
     */
    public Inventory(){
        this.inventory = new HashMap<String, List<Item>>();

        //Add default items to inventory
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Wood());
        this.inventoryAdd(new Wood());
    }


    /***
     * Create an inventory and add a custom map of items to the inventory.
     * @param inventoryContents a Map<String, List<Item>> where String is the item's name and List<Item>
     * is a list of objects that implement the Item interface.
     */
    public Inventory(Map<String, List<Item>> inventoryContents){
        this.inventory = new HashMap<String, List<Item>>();

        this.inventory.putAll(inventoryContents);
    }


    /***
     * Get a copy of the inventory contents.
     * @return a copy of the inventory
     */
    public Map<String, List<Item>> getInventoryContents(){
        return Collections.unmodifiableMap(this.inventory);
    }

    /***
     * Get the amount of each item in the inventory
     * @return a map of item name to the integer amount in the inventory for all inventory items.
     */
    public Map<String, Integer> getInventoryAmounts(){

        Map<String, Integer> inventoryAmounts = new HashMap<String, Integer>();

        for (String key : this.inventory.keySet()) {
            Integer amount = this.inventory.get(key).size();
            inventoryAmounts.put(key, amount);
        }

        return inventoryAmounts;
    }


    /***
     * Get the amount of a single item in the inventory.
     * @param item the string name for the item
     * @return the integer number of that item type in the inventory
     */
    public Integer getAmount(String item){
        Map<String, Integer> inventoryAmounts = this.getInventoryAmounts();

        if(inventoryAmounts.get(item) != null){
            return inventoryAmounts.get(item);
        } else{
            return 0;
        }

    }


    /***
     * Get the inventory as a string.
     * @return a string representation of the inventory.
     */
    @Override
    public String toString(){
        Map<String, Integer> inventoryAmounts = this.getInventoryAmounts();
        return "Inventory Contents " + inventoryAmounts.toString();
    }


    /***
     * Add an item to the inventory.
     * @param item the item to add to the inventory, implements Item interface.
     */
    public void inventoryAdd(Item item){
        String name = item.getName();

        if(this.inventory.get(name) != null){

            List<Item> itemsList = this.inventory.get(name);
            itemsList.add(item);
            this.inventory.put(name, itemsList);

        }else{
            List<Item> itemsList = new ArrayList<>();
            itemsList.add(item);
            this.inventory.put(name, itemsList);

        }
    }


    /***
     * Removes an item from the inventory and returns it.
     * @param itemName the String name of the item to drop from the inventory.
     * @return the Item dropped from the inventory
     */
    public Item inventoryDrop(String itemName){

        if(this.inventory.get(itemName) != null){
            Integer num = this.inventory.get(itemName).size();

            if(num == 1){
                Item item = this.inventory.get(itemName).get(0);
                this.inventory.remove(itemName);
                return item;
            } else if(num > 1){
                List<Item> itemsList = this.inventory.get(itemName);
                Item item = itemsList.get(num-1);
                itemsList.remove(num-1);
                this.inventory.put(itemName, itemsList);
                return item;
            }

        }

        System.out.println("You can't remove what you don't have!");
        return null;
    }


    /***
     * Drop multiple items of the same type from the inventory, and return as a list.
     * @param itemName the string name of the item to drop from the inventory
     * @param amount the number of the item type to drop from the inventory
     * @return a list of the items dropped from the inventory
     */
    public List<Item> inventoryDropMultiple(String itemName, Integer amount){
        List<Item> itemsDropped = new ArrayList<Item>();
        List<Item> itemsList = this.inventory.get(itemName);

        if(itemsList != null){
            Integer num = this.inventory.get(itemName).size();

            if(amount < num){

                for(int i = 1; i <= amount; i ++){
                    Item item = itemsList.get(num-i);
                    itemsDropped.add(item);
                    itemsList.remove(num-i);
                }

                this.inventory.put(itemName, itemsList);

            } else if(amount.equals(num)){
                itemsDropped.addAll(itemsList);
                this.inventory.remove(itemName);

            } else if(amount > num){
                System.out.println("You don't have that many " + itemName + "s!");
                itemsDropped = null;
            }


        }else{
            itemsDropped = null;
        }

        return itemsDropped;

    }

}
