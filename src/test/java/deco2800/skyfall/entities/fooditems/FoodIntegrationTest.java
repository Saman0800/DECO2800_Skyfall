package deco2800.skyfall.entities.fooditems;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoodIntegrationTest {
    private MainCharacter mc;
    private InventoryManager inventory;
    private Cheese cheese;
    private Cheery cherry;
    /**
     * Setup function to initialise variables before each test
     */
    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MainCharacter.resetInstance();
        mc = MainCharacter.getInstance(0f, 0f, 0.05f, "Main Character", 20);
        inventory = new InventoryManager();
        cheese = new Cheese(new Tile(null, 0, 0),false);
        cherry = new Cheery(new Tile(null, 0, 0),false);
    }

    /**
     * Tear down function to set everything to null after each test
     */
    @After
    public void tearDown() {
        mc = null;
        inventory = null;
    }

    @Test
    public void pickupTest() {
        inventory.add(cheese);
        Assert.assertEquals(1, inventory.getAmount(cheese.getName()));
        Assert.assertEquals(10, inventory.getTotalAmount());

        inventory.add(cherry);
        Assert.assertEquals(11, inventory.getTotalAmount());

        inventory.add(cherry);
        Assert.assertEquals(2, inventory.getAmount("cherry"));
        Assert.assertEquals(12, inventory.getTotalAmount());
    }

    /**
     * Tests drop functionality and integration with inventory UI
     */
    @Test
    public void dropTest() {
        inventory.add(cherry);
        inventory.add(cherry);
        inventory.add(cheese);
        inventory.drop("cheese");

        Assert.assertEquals(0, inventory.getAmount("cheese"));
        Assert.assertEquals(2, inventory.getAmount("cherry"));
        Assert.assertEquals(11, inventory.getTotalAmount());

        inventory.dropMultiple("cherry", 2);

        Assert.assertEquals(0, inventory.getAmount("cherry"));
        Assert.assertEquals(9, inventory.getTotalAmount());
    }

    /**
     * Tests that main character can equip a food item
     */
    @Test
    public void equipTest() {
        mc.setEquippedItem(cherry);
        Assert.assertEquals(cherry, mc.getEquippedItem());
        mc.setEquippedItem(cheese);
        Assert.assertEquals(cheese, mc.getEquippedItem());
    }

    /**
     * Tests that the quick access inventory can be accessed and modified
     * correctly when dealing with weapons
     */
    @Test
    public void quickAccessTest() {
        inventory.add(cheese);
        inventory.quickAccessAdd("cheese");
        Assert.assertTrue(inventory.getQuickAccess().containsKey("cheese"));

        inventory.quickAccessRemove("cheese");
        Assert.assertFalse(inventory.getQuickAccess().containsKey("cheese"));
    }
}
