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

        Assert.assertEquals(testCharacter.getEquippedItem(), "Rusty Sword");
        testCharacter.equipItem("Sword",0);
        testCharacter.unequipItem("Shield");
        testCharacter.pickUpInventory("Shield");
        Assert.assertEquals(testCharacter.getInventories().size(), 2);
        testCharacter.equipItem("Shield",1);
        testCharacter.equipItem("Dagger",2);
        Assert.assertEquals(testCharacter.getHotbar().size(), 1);

        testCharacter.pickUpInventory("Armour");
        testCharacter.pickUpInventory("Dagger");
        testCharacter.pickUpInventory("Boots");
        testCharacter.pickUpInventory("Hat");
        testCharacter.equipItem("Armour",0);
        testCharacter.equipItem("Dagger",1);
        testCharacter.equipItem("Boots",2);
        testCharacter.equipItem("Hat",3);
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