package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BerryTest{

    // create a Berry item
    private Berry berry;

    /**
     * Create a Berry health resource
     */
    @Before
    public void setUp() {
        berry = new Berry();
    }

    @After
    public void tearDown() {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (berry) not just null
     */
    @Test
    public void getName2() {
        assertEquals("Berry", berry.getName());
    }


    /**
     * Ensure that berry is classified as carryable
     */
    @Test
    public void isCarryable2() {
        assertTrue(berry.isCarryable());
    }

    /**
     * Ensure that berry is classified as food effect
     */
    @Test
    public void hasFoodEffect() {
        assertTrue(berry.hasFoodEffect());
    }

    /**
     * Ensure that the correct subtype "Health Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", berry.getSubtype());
    }


    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable2() {
        assertTrue(berry.isExchangeable());
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
     * <p>Health Resource:Berry </p>
     */
    @Test
    public void toString2() {
        assertEquals("Health Resource:Berry", berry.toString());
    }

    /**
     * Ensure that the berry's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Forest", berry.getBiome());
    }
}