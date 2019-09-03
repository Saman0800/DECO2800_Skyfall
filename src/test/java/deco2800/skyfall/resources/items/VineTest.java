package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VineTest {

    // create a new vine item
    private Vine vine;

    /**
     * Create a new Vine object
     *
     */
    @Before
    public void setUp() {

        vine = new Vine();
    }

    @After
    public void tearDown() {
    }


    /**
     * Ensure that the getName method returns the name of the subclass
     * (vine) not just null
     */
    @Test
    public void getName1() {
        assertEquals("Vine", vine.getName());
    }


    /**
     * Ensure that vine is classified as carryable
     */
    @Test
    public void isCarryable1() {
        assertTrue(vine.isCarryable());
    }

    /**
     * Ensure that the correct subtype "Natural Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", vine.getSubtype());
    }

    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable1() {
        assertTrue(vine.isExchangeable());
    }

    /**
     * Ensure the correct string representation is displayed for each type
     * It should be of the following format:
     *
     * <p>'{Natural Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {natural resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Natural Resource:Vine </p>
     */
    @Test
    public void toString1() {
        assertEquals("Natural Resource:Vine", vine.toString());
    }

    /**
     * Ensure that the stone's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Forest", vine.getBiome());
    }

    /**
     * Ensure that the wood's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getDescriptionTest() {
        assertEquals("This item can be found in the forest biome " +
                "and can be used to produce rope.", vine.getDescription());
    }
}