package deco2800.skyfall.resources.items;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoneTest {

    // create a new Stone item
    private Stone stone;

    /**
     * Create a new Stone item
     *
     */
    @Before
    public void setUp()  {

        stone = new Stone();
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (stone) not just null
     */
    @Test
    public void getName1() {
        assertEquals("Stone", stone.getName());
    }


    /**
     * Ensure that stone is classified as carryable
     */
    @Test
    public void isCarryable1() {
        assertTrue(stone.isCarryable());
    }

    /**
     * Ensure that the correct subtype "Natural Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", stone.getSubtype());
    }

    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable1() {
        assertTrue(stone.isExchangeable());
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
     * <p>Natural Resource:Stone </p>
     */
    @Test
    public void toString1() {
        assertEquals("Natural Resource:Stone", stone.toString());
    }

    /**
     * Ensure that the stone's colour is correctly returned when getColour()
     * is called
     */
    @Test
    public void getColour() {
        assertEquals("white", stone.getColour());
    }

    /**
     * Ensure that the stone's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Forest", stone.getBiome());
    }

    /**
     * Ensure that the wood's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getDescriptionTest() {
        assertEquals("This resource can be found in the forest and mountain" + "\n" +
                "biomes and can be used to build a Pickaxe.", stone.getDescription());
    }
}