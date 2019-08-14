package deco2800.skyfall.inventory;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.Wood;
import java.util.*;
import java.lang.*;


public class Inventory {
    //Map that stores the inventory contents
    private Map<String, List<Item>> inventory;

    //Quick Access Inventory
    private List<Item> quickAccessInventory;



    public Inventory(){
        this.inventory = new HashMap<String, List<Item>>();

        //Add default items to inventory - eventually find a way to populate more flexibly
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Stone());
        this.inventoryAdd(new Wood());
        this.inventoryAdd(new Wood());
    }


    public Inventory(Map<Class<? extends Item>, Integer> inventory){


    }


    public void getInventory(){
        System.out.println(this.inventory);
    }



    public void printInventory(){
        //Print inventory with numbers
    }



    public void displayInventory(){
        //Sort Items into types for display in GUI and return as 3 Maps rather than 1
    }



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




}
