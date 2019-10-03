package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import static org.junit.Assert.*;

public class PoisonousMushroomTest{

    // create a Apple item
    private PoisonousMushroom poisonousMushroom;

    /**
     * Create a PoisonousMushroom health resource
     */
    @Before
    public void setUp() {
        poisonousMushroom = new PoisonousMushroom();
    }

    @After
    public void tearDown() {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (PoisonousMushrrom) not just null
     */
    @Test
    public void getName2() {
        assertEquals("PoisonousMushroom", poisonousMushroom.getName());
    }

    /**
     * Tests the correct colour is returned for the health resource
     */
    @Test
    public void getColourTest(){
        assertEquals("black white", poisonousMushroom.getColour());

    }

    /**
     * Ensure that PoisonousMushroom is classified as carryable
     */
    @Test
    public void isCarryable2() {
        assertTrue(poisonousMushroom.isCarryable());
    }

    /**
     * Ensure that poisonous mushroom is classified as food effect
     */
    @Test
    public void hasFoodEffect() {
        assertTrue(poisonousMushroom.hasFoodEffect());
    }

    /**
     * Ensure that the correct subtype "Health Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", poisonousMushroom.getSubtype());
    }


    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable2() {
        assertTrue(poisonousMushroom.isExchangeable());
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
     * <p>Health Resource:PoisonousMushroom </p>
     */
    @Test
    public void toString2() {
        assertEquals("Health Resource:PoisonousMushroom", poisonousMushroom.toString());
    }

    /**
     * Ensure that the PoisonousMushroom's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Forest", poisonousMushroom.getBiome());
    }

}