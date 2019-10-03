package deco2800.skyfall.managers;

import deco2800.skyfall.gui.Tuple;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.*;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class InventoryManager extends TickableManager {

    // Logger for class to display messages
    private final transient Logger logger =
            LoggerFactory.getLogger(InventoryManager.class);

    // Map that stores the inventory contents
    private Map<String, List<Item>> inventory;

    // List that stores the names of items in quick access inventory
    private List<String> quickAccess;

    // Maximum size of quick access inventory
    private static final int QA_MAX_SIZE = 4;

    public boolean HAS_QUICK_ACCESS = true;

    public static final int COLS = 4;
    public static final int ROWS = 3;

    private Map<String, Tuple> positions;

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }

    /**
     * Creates Inventory Manager and adds default items to the inventory (2x
     * Stone, 2x Wood), as well as initialises an empty quick access inventory.
     */
    public InventoryManager() {
        initInventory(new HashMap<>());

        // Add default items to inventory
        this.add(new Stone());
        this.add(new Stone());
        this.add(new Wood());
        this.add(new Wood());
        this.add(new Wood());
        this.add(new Wood());
        this.add(new Wood());
        this.add(new Hatchet());
        this.add(new PickAxe());
        this.quickAccessAdd("Hatchet");
        this.quickAccessAdd("Pick Axe");
        this.quickAccessAdd("Wood");
        this.quickAccessAdd("Stone");
    }

    public void initInventory(Map<String, List<Item>> inventory) {
        this.inventory = inventory;
        quickAccess = new ArrayList<>();
        positions = new HashMap<>();
        List<String> items = new ArrayList<>();
        for (Map.Entry<String, List<Item>> e : inventory.entrySet()) {
            items.add(e.getKey());
        }
        int counter = 0;
        for (int i = 0; i < ROWS; i++) {
            if (counter >= items.size()) {
                break;
            }
            for (int j = 0; j < COLS; j++) {
                if (counter >= items.size()) {
                    break;
                }
                positions.put(items.get(counter), new Tuple(j, i));
                counter++;
            }
        }
    }

    /**
     * Create an Inventory Manager and adds a custom map of items to the
     * inventory.
     * Creates a quick access inventory and adds items to it.
     *
     * @param inventoryContents a Map<String, List<Item>> where String is the
     *                         item's name and List<Item> is a list of
     *                          objects that implement the Item interface.
     * @param quickAccessContents a List<String> where String is the name of
     *                            item in quick access inventory
     */

    public InventoryManager(Map<String, List<Item>> inventoryContents,
                            List<String> quickAccessContents){
        initInventory(inventoryContents);

        for (String quickAccessContent : quickAccessContents) {
            this.quickAccessAdd(quickAccessContent);
        }
    }

    /**
     * Get a copy of the inventory contents.
     * @return a copy of the inventory
     */
    public Map<String, List<Item>> getContents() {
        return Collections.unmodifiableMap(this.inventory);
    }

    /**
     * Get the amount of each item in the inventory
     * @return a map of item name to the integer amount in the inventory for
     * all inventory items.
     */
    public Map<String, Integer> getAmounts() {
        Map<String, Integer> inventoryAmounts = new HashMap<>();

        for (Map.Entry<String, List<Item>> entry : this.inventory.entrySet()) {
            Integer amount = entry.getValue().size();
            inventoryAmounts.put(entry.getKey(), amount);
        }

        return inventoryAmounts;
    }

    /**
     * Gets the total number of inventory items
     * @return total number of inventory items
     */
    public int getTotalAmount() {
        int total = 0;

        List<Integer> inventoryAmount =
                new ArrayList<>(this.getAmounts().values());

        for (Integer count: inventoryAmount) {
            total += count;
        }

        return total;
    }

    /**
     * Get the items in the quick access inventory.
     * @return a map of the items in the quick access inventory, and the
     * number of each item
     */
    public Map<String, Integer> getQuickAccess() {
        Map<String, Integer> qai = new HashMap<>();

        for (String item : quickAccess) {
            qai.put(item, getAmount(item));
        }

        return qai;
    }

    /**
     * Add an item type from the full inventory to the quick access inventory.
     * Ensures that the item being added to quick access inventory is in full
     * inventory and that quick access inventory doesn't contain more than 6
     * item type.
     * @param item the String name of the item to add to the quick access
     *             inventory.
     */
    public void quickAccessAdd(String item) {
        if ((this.getAmount(item) > 0)
                && (this.quickAccess.size() < QA_MAX_SIZE)) {
            this.quickAccess.add(item);
        } else {
            logger.warn("Sorry I can't add that!");
        }
    }

    /**
     * Remove an item type from the quick access inventory.
     * @param item the String name of the item to remove from quick access.
     */
    public void quickAccessRemove(String item) {
        this.quickAccess.remove(item);
    }

    /**
     * Get the amount of a single item in the inventory.
     * @param item the string name for the item
     * @return the integer number of that item type in the inventory
     */
    public int getAmount(String item) {
        Map<String, Integer> inventoryAmounts = this.getAmounts();

        if (inventoryAmounts.get(item) != null) {
            return inventoryAmounts.get(item);
        } else {
            return 0;
        }
    }

    /**
     * Get the full inventory as a string.
     * @return a string representation of the inventory.
     */
    @Override
    public String toString() {
        Map<String, Integer> inventoryAmounts = this.getAmounts();
        return "Inventory Contents " + inventoryAmounts.toString();
    }

    public Map<String, Tuple> getPositions() {
        return positions;
    }

    public void setPositions(Map<String, Tuple> positions) {
        this.positions = positions;
    }

    /**
     * Add an item to the full inventory.
     * @param item the item to add to the inventory, implements Item interface.
     */
    public boolean add(Item item) {
        String name = item.getName();

        if (this.inventory.get(name) != null) {
            List<Item> itemsList = this.inventory.get(name);
            itemsList.add(item);
            this.inventory.put(name, itemsList);
            return true;
        } else {
            List<Item> itemsList = new ArrayList<>();
            itemsList.add(item);
            List<Tuple> pos = new ArrayList<>();
            for (Map.Entry<String, Tuple> entry : this.positions.entrySet()) {
                pos.add(entry.getValue());
            }

            if (pos.isEmpty()) {
                positions.put(name, new Tuple(0, 0));
                inventory.put(name, itemsList);
                return true;
            } else {
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (!pos.contains(new Tuple(j, i))) {
                            positions.put(name, new Tuple(j, i));
                            inventory.put(name, itemsList);
                            return true;
                        }
                    }
                }
            }
            logger.warn("Not enough space in inventory");
            return false;
        }
    }

    public void inventoryAddMultiple(Map<String, List<Item>> items) {
        for (Map.Entry<String, List<Item>> e : items.entrySet()) {
            if (inventory.get(e.getKey()) != null) {
                inventory.get(e.getKey()).addAll(e.getValue());
            } else {
                inventory.put(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * Removes an item from the inventory and returns it. If the item is the
     * last of a specific type present in the inventory (and quick access
     * inventory) it is also removed from these stores.
     * @param itemName the String name of the item to drop from the inventory.
     * @return the Item dropped from the inventory
     */
    public Item drop(String itemName) {
        if(this.inventory.get(itemName) != null){
            int num = this.inventory.get(itemName).size();

            if (num == 1) {
                Item item = this.inventory.get(itemName).get(0);
                remove(itemName);
                return item;
            } else if(num > 1) {
                List<Item> itemsList = this.inventory.get(itemName);
                Item item = itemsList.get(num - 1);
                itemsList.remove(num - 1);
                this.inventory.put(itemName, itemsList);
                return item;
            }
        }

        logger.warn("You can't remove what you don't have!");

        return null;
    }


    /**
     * Removes all of a specific item type from the inventory, and quick access inventory.
     * Returns as a list of items.
     * @param itemName the String name of the item type to drop from the inventory.
     * @return List of instances of item type dropped from inventory
     */
    public List<Item> dropAll(String itemName) {
        if(this.inventory.get(itemName) != null){
            List<Item> items = this.inventory.get(itemName);
            remove(itemName);
            return items;
        }

        logger.warn("You can't remove what you don't have!");

        return null;
    }

    /**
     * Drop multiple items of the same type from the inventory, and return as
     * a list.
     * If these items are the last of a specific type present in the inventory
     * (and quick access inventory) the type is also removed from these stores.
     *
     * @param itemName the string name of the item to drop from the inventory
     * @param amount the number of the item type to drop from the inventory
     * @return a list of the items dropped from the inventory
     */
    public List<Item> dropMultiple(String itemName, int amount){
        List<Item> itemsDropped = new ArrayList<>();
        List<Item> itemsList = this.inventory.get(itemName);

        if (itemsList != null) {
            int num = this.inventory.get(itemName).size();

            if (amount < num) {
                for (int i = 1; i <= amount; i++) {
                    Item item = itemsList.get(num - i);
                    itemsDropped.add(item);
                    itemsList.remove(num - i);
                }
                this.inventory.put(itemName, itemsList);
            } else if (amount == num) {
                itemsDropped.addAll(itemsList);
                remove(itemName);
            } else {
                logger.warn("You don't have that many " + itemName +
                        "s!");
                itemsDropped = null;
            }
        } else {
            itemsDropped = null;
        }
        return itemsDropped;
    }

    /**
     * Removes an item from the inventory
     */
    private void remove(String itemName) {
        this.inventory.remove(itemName);
        this.quickAccessRemove((itemName));
        this.positions.remove(itemName);
    }

    /**
     * Returns the description of item
     * @param itemName String name of item
     * @return Description of item
     */
    public String getItemDescription(String itemName){
        if(this.inventory.get(itemName) != null){
            int num = this.inventory.get(itemName).size();

            if (num == 1) {
                Item item = this.inventory.get(itemName).get(0);
                return item.getDescription();
            } else if(num > 1) {
                List<Item> itemsList = this.inventory.get(itemName);
                Item item = itemsList.get(num - 1);
                return item.getDescription();
            }
        }

        logger.warn("You don't have that!");

        return null;
    }
}
