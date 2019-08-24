package deco2800.skyfall.managers;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.LootRarity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ChestManagerTest {

    private ChestManager chestManager;
    private ChestManager loot;

    @Before
    public void initialize() {
        chestManager = new ChestManager();
    }

    @Test
    public void testRandomLoot() {
        for (int i = 0; i < 50; i ++) {
            Map<String, List<Item>> loot = ChestManager.generateRandomLoot(
                    100, LootRarity.values()[i % 5]);
            ChestManager test = new ChestManager(loot);
            System.out.println(LootRarity.values()[i % 5] + ": " + test.toString());
        }
    }
}
