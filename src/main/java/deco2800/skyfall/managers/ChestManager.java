package deco2800.skyfall.managers;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.LootRarity;
import deco2800.skyfall.resources.items.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Chest class for basic storage/loot
 */
public class ChestManager extends InventoryManager {

    /**
     * Generates a random assortment of loot based on the rarity given
     * @param amount how much loot you want to generate
     * @param rarity the rarity of the loot
     * @return the generated loot
     */
    public static Map<String, List<Item>> generateRandomLoot(
            int amount, LootRarity rarity) {
        Map<String, List<Item>> loot = new HashMap<>();
        List<Item> itemPool = new ArrayList<>();

        // Omitted breaks to avoid repetition of code.
        // Just add items to the corresponding loot pools
        switch (rarity) {
            case LEGENDARY:
                itemPool.add(new Apple());
                // Fall through
            case EPIC:
                itemPool.add(new Aloe_Vera());
                // Fall through
            case RARE:
                itemPool.add(new Berry());
                // Fall through
            case UNCOMMON:
                itemPool.add(new Sand());
                // Fall through
            case COMMON:
                itemPool.add(new Metal());
                break;
        }

        for (int i = 0; i < amount; i++) {
            double randInt = Math.random();
            int index = (int) Math.round(randInt * (itemPool.size() - 1));
            Item item = itemPool.get(index);
            // If already has the item, then stack. Else, add to loot.
            if (loot.get(item.getName()) != null) {
                loot.get(item.getName()).add(item);
            } else {
                List<Item> itemToAdd = new ArrayList<>();
                itemToAdd.add(item);
                loot.put(item.getName(), itemToAdd);
            }
        }

        return loot;
    }

    /**
     * Init chest with empty inventory
     */
    public ChestManager() {
        initInventory(new HashMap<>());
        HAS_QUICK_ACCESS = false;
    }

    /**
     * Init chest with given inventory
     * @param inventoryContents the inventory to init with
     */
    public ChestManager(Map<String, List<Item>> inventoryContents) {
        initInventory(inventoryContents);
        HAS_QUICK_ACCESS = false;
    }
}
