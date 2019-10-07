package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppleTest{

    // create a Apple item
    private Apple apple;

    /**
     * Create a Apple health resource
     */
    @Before
    public void setUp() {
        apple = new Apple();
    }

    @After
    public void tearDown() {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (apple) not just null
     */
    @Test
    public void getName2() {
        assertEquals("Apple", apple.getName());
    }

    /**
     * Tests the correct colour is returned for the health resource
     */
    @Test
    public void getColourTest(){
        assertEquals("red", apple.getColour());

    }


    /**
     * Ensure that apple is classified as carryable
     */
    @Test
    public void isCarryable2() {
        assertTrue(apple.isCarryable());
    }


    /**
     * Ensure that the correct subtype "Health Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", apple.getSubtype());
    }


    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable2() {
        assertTrue(apple.isExchangeable());
    }

    /**
     * Ensure the correct string representation is displayed for each type
     * It should be of the following format:
     *
     * <p>'{Health Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {health resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Health Resource:Apple </p>
     */
    @Test
    public void toString2() {
        assertEquals("Health Resource:Apple", apple.toString());
    }

    /**
     * Ensure that the apple's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Forest", apple.getBiome());
    }

}