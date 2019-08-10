package deco2800.skyfall.entities;

import org.junit.*;

public class MainCharacterTest {

    private MainCharacter testCharacter = new MainCharacter(0f, 0f,
            0.05f, "Main Piece", 10);

    @Test
    /**
     * Test setters and getter from Peon super Character class
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
     * Test main character is interacting correctly with basic inventory action
     */
    public void test2() {
        Assert.assertEquals(testCharacter.getInventories().size(), 0);
        testCharacter.pickUpInventory("Dagger");
        testCharacter.pickUpInventory("Sword");
        Assert.assertEquals(testCharacter.getInventories().size(), 2);
        testCharacter.dropInventory("Shield");
        testCharacter.dropInventory("Dagger");
        Assert.assertEquals(testCharacter.getInventories().size(), 1);

        Assert.assertEquals(testCharacter.equippedItems(), 1);
        testCharacter.equipItem("Sword");
        testCharacter.unequipItem("Shield");
        testCharacter.pickUpInventory("Shield");
        Assert.assertEquals(testCharacter.getInventories().size(), 2);
        testCharacter.equipItem("Shield");
        testCharacter.equipItem("Dagger");
        Assert.assertEquals(testCharacter.equippedItems(), 3);

        testCharacter.pickUpInventory("Armour");
        testCharacter.pickUpInventory("Dagger");
        testCharacter.pickUpInventory("Boots");
        testCharacter.pickUpInventory("Hat");
        testCharacter.equipItem("Armour");
        testCharacter.equipItem("Dagger");
        testCharacter.equipItem("Boots");
        testCharacter.equipItem("Hat");
        Assert.assertEquals(testCharacter.getInventories().size(), 6);
        Assert.assertEquals(testCharacter.equippedItems(), 5);

        Assert.assertEquals(testCharacter.getEquippedItems().size(),
                testCharacter.equippedItems());
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