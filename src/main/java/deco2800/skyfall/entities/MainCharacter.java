package deco2800.skyfall.entities;

import java.util.*;

public class MainCharacter extends Character {

    // List of the player's inventories
    // TODO need to replace List<String> with List<InventoryClass>
    private List<String> inventories;

    public MainCharacter(float col, float row, String name, int health) {
        super(col, row, name, health);

        this.inventories = new ArrayList<>();
    }

    /**
     * Adds item to player's collection
     * @param item inventory being added
     */
    public void pickUpInventory(String item) {
        inventories.add(item);
    }

    /**
     * Removes items from player's collection
     * @param item inventory being removed
     */
    public void dropInventory(String item) {
        inventories.remove(item);
    }

    /**
     * Gets the player's inventories, modification of the returned list
     * doesn't impact the internal class
     * @return a list of the player's inventories
     */
    public List<String> getInventories() {
        return new ArrayList<>(inventories);
    }
}
