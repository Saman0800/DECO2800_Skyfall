package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WoodTest  {

    // create a Wood item
    private Wood wood;

    /**
     * Create a new wood resource
     */
    @Before
    public void setUp()  {
        wood = new Wood();
    }

    @After
    public void tearDown()  {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (wood) not just null
     */
    @Test
    public void getName1() {
       assertEquals("Wood", wood.getName());
    }


    /**
     * Ensure that wood is classified as carryable
     */
    @Test
    public void isCarryable1() {
        assertTrue(wood.isCarryable());
    }

    /**
     * Ensure that the correct subtype "Natural Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", wood.getSubtype());
    }




    @Test
    public void getCoords1() {
    }


    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable1() {

        assertTrue(wood.isExchangeable());
    }

    /**
     * Ensure the correct string representation is displayed for wood
     * It should be of the following format:
     *
     * <p>'{Natural Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {natural resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Natural Resource:Wood </p>
     */
    @Test
    public void toString1() {
        assertEquals("Natural Resource:Wood", wood.toString());
    }

    /**
     * Ensure that the wood's colour is correctly returned when getColour()
     * is called
     */
    @Test
    public void getColour() {
        assertEquals("brown", wood.getColour());
    }

    /**
     * Ensure that the wood's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getBiome() {
        assertEquals("Forest", wood.getBiome());
    }

    /**
     * Ensure that the wood's biome is correctly returned when getBiome()
     * is called
     */
    @Test
    public void getDescriptionTest() {
        assertEquals("This item can be found in the forest biome " + "\n" +
                "and can be used to create a pickaxe and start a fire.", wood.getDescription());
    }
}