package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MetalTest {

    // create a Metal item
    private Metal metal;

    /**
     * Create a Metal natural resource
     */
    @Before
    public void setUp() {
        metal = new Metal();
    }

    @After
    public void tearDown() {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (metal) not just null
     */
    @Test
    public void getName1() {
        assertEquals("Metal", metal.getName());
    }


    /**
     * Ensure that metal is classified as carryable
     */
    @Test
    public void isCarryable1() {
        assertTrue(metal.isCarryable());
    }

    /**
     *Ensure that the correct subtype "Natural Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", metal.getSubtype());
    }


    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable1() {
        assertTrue(metal.isExchangeable());
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
     * <p>Natural Resource:Metal </p>
     */
    @Test
    public void toString1() {
        assertEquals("Natural Resource:Metal", metal.toString());
    }

    /**
     * Ensure that the correct biome is returned when getBiome() is called.
     */
    @Test
    public void getBiome() {
        assertEquals("Ruined City", metal.getBiome());
    }
}