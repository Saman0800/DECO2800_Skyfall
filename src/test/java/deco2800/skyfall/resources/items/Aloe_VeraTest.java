package deco2800.skyfall.resources.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class Aloe_VeraTest{

    // create a Aloe_Vera item
    private AloeVera aloe_vera;

    /**
     * Create a Aloe vera health resource
     */
    @Before
    public void setUp() {
        aloe_vera = new AloeVera();
    }


    /**
     * Ensure that the getName method returns the name of the subclass
     * (aloe_vera) not just null
     */
    @Test
    public void getName2() {
        assertEquals("Aloe_Vera", aloe_vera.getName());
    }


    /**
     * Ensure that aloe vera is classified as carryable
     */
    @Test
    public void isCarryable2() {
        assertTrue(aloe_vera.isCarryable());
    }
    

    /**
     * Ensure that the correct subtype "Health Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", aloe_vera.getSubtype());
    }

    /**
     * Tests the correct colour is returned for the health resource
     */
    @Test
    public void getColourTest(){
        assertEquals("green", aloe_vera.getColour());

    }

    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable2() {
        assertTrue(aloe_vera.isExchangeable());
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
     * <p>Health Resource:Aloe_vera </p>
     */
    @Test
    public void toString2() {
        assertEquals("Health Resource:Aloe_Vera", aloe_vera.toString());
    }

    /**
     * Ensure that the aloe_vera's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Desert", aloe_vera.getBiome());
    }

}