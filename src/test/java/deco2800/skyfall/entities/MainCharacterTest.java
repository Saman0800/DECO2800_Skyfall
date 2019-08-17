package deco2800.skyfall.entities;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Stone;
import org.junit.*;

public class MainCharacterTest {

    private MainCharacter testCharacter = new MainCharacter(0f, 0f,
            0.05f, "Main Piece", 10);

    @Test
    /**
     * Test getters and setters from Peon super Character class
     */
    public void test1() {
        Assert.assertEquals(testCharacter.getName(), "Main Piece");
        testCharacter.setName("Side Piece");
        Assert.assertEquals(testCharacter.getName(), "Side Piece");

        Assert.assertFalse(testCharacter.isDead());
        Assert.assertEquals(testCharacter.getHealth(), 10);
        testCharacter.changeHealth(5);
        Assert.assertEquals(testCharacter.getHealth(), 15);
        testCharacter.changeHealth(-20);
        Assert.assertEquals(testCharacter.getHealth(), 0);
        Assert.assertTrue(testCharacter.isDead());
    }

    @Test
    /**
     * Test main character is interacting correctly with basic weapon action
     */
    public void test2() {
        Assert.assertEquals(testCharacter.getWeapons().size(), 0);
        testCharacter.pickUpWeapon("Dagger");
        testCharacter.pickUpWeapon("Sword");
        Assert.assertEquals(testCharacter.getWeapons().size(), 2);
        testCharacter.dropWeapon("Shield");
        testCharacter.dropWeapon("Dagger");
        Assert.assertEquals(testCharacter.getWeapons().size(), 1);
        Assert.assertEquals((int)testCharacter.inventories.getAmount("Stone"), 2);
        Assert.assertEquals((int)testCharacter.inventories.getAmount("Wood"), 2);
        Stone stone = new Stone();
        testCharacter.pickUpInventory(stone);
        Assert.assertEquals((int)testCharacter.inventories.getAmount("Stone"), 3);
        testCharacter.dropInventory("Stone");
        Assert.assertEquals((int)testCharacter.inventories.getAmount("Stone"), 2);
        pickUpInventoryMultiple(stone, 500);
        Assert.assertEquals((int)testCharacter.inventories.getAmount("Stone"), 502);
        /* Had to change inventory method inventoryDropMultiple
            -   if(amount == num)
            to:
            -   if(amount.equals(num)
            for this to work
        */
        testCharacter.inventories.inventoryDropMultiple("Stone",502);
        Assert.assertEquals((int)testCharacter.inventories.getAmount("Stone"), 0);
    }

    private void pickUpInventoryMultiple(Item item, int amount) {
        for(int i = 0; i < amount; i++) {
            testCharacter.pickUpInventory(item);
        }
        // TODO writes tests with new InventoryManager
    }

    @Test
    /**
     * Test level changing of main character
     */
    public void test3() {
        testCharacter.changeLevel(4);
        Assert.assertEquals(testCharacter.getLevel(), 5);

        testCharacter.changeLevel(-5);
        Assert.assertEquals(testCharacter.getLevel(), 5);

        testCharacter.changeLevel(-4);
        Assert.assertEquals(testCharacter.getLevel(), 1);
    }
}