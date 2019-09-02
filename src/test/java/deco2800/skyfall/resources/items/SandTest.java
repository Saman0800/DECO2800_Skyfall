package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SandTest {

    // Create a sand item
    private Sand sand;

    /**
     * Create a new sand item.
     *
     */
    @Before
    public void setUp() {
        sand = new Sand();
    }

    @After
    public void tearDown() {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (sand) not just null
     */
    @Test
    public void getName1() {
        assertEquals("Sand", sand.getName());
    }


    /**
     * Ensure that sand is classified as carryable
     */
    @Test
    public void isCarryable1() {
        assertTrue(sand.isCarryable());
    }

    /**
     * Ensure that the correct subtype "Natural Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", sand.getSubtype());
    }

    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable1() {
        assertTrue(sand.isExchangeable());
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
     * <p>Natural Resource:Sand </p>
     */
    @Test
    public void toString1() {
        assertEquals("Natural Resource:Sand", sand.toString());
    }


    /**
     * Ensure that the metal's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Beach", sand.getBiome());
    }


    /**
     * Ensure that the wood's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getDescriptionTest() {
        assertEquals("This resource can be found in the Desert or Beach " +
                "biomes.", sand.getDescription());
    }
}