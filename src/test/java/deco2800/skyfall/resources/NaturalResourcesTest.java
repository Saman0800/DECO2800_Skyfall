package deco2800.skyfall.resources;

import deco2800.skyfall.resources.items.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A test class for the NaturalResources Abstract Class.
 */
public class NaturalResourcesTest extends NaturalResources {

    // create Wood, Sand, Stone, Metal and Vine instances of Natural Resource
    private NaturalResources wood;
    private NaturalResources sand;
    private NaturalResources stone;
    private NaturalResources metal;
    private NaturalResources vine;

    /**
     * Create a new Wood, Sand, Stone, Metal and Vine Natural Resource
     */
    @Before
    public void setUp() throws Exception {
        wood = new Wood();
        sand = new Sand();
        stone = new Stone();
        metal = new Metal();
        vine = new Vine();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (wood, sand etc.) not just null
     */
    @Test
    public void getName1() {
        assertEquals("Wood", wood.getName());
        assertEquals("Sand", sand.getName());
        assertEquals("Stone", stone.getName());
        assertEquals("Metal", metal.getName());
        assertEquals("Vine", vine.getName());
    }

    /**
     * Ensure that all the items are classified as carryable
     */
    @Test
    public void isCarryable1() {
        assertTrue(wood.isCarryable());
        assertTrue(sand.isCarryable());
        assertTrue(stone.isCarryable());
        assertTrue(metal.isCarryable());
        assertTrue(vine.isCarryable());
    }

    /**
     * Ensure that the correct subtype "Natural Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", wood.getSubtype());
        assertEquals("Natural Resource", sand.getSubtype());
        assertEquals("Natural Resource", stone.getSubtype());
        assertEquals("Natural Resource", metal.getSubtype());
        assertEquals("Natural Resource", vine.getSubtype());
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
        assertTrue(sand.isExchangeable());
        assertTrue(stone.isExchangeable());
        assertTrue(metal.isExchangeable());
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
     * <p>Natural Resource:Wood </p>
     */
    @Test
    public void toString1() {
        assertEquals("Natural Resource:Wood", wood.toString());
        assertEquals("Natural Resource:Sand", sand.toString());
        assertEquals("Natural Resource:Stone", stone.toString());
        assertEquals("Natural Resource:Metal", metal.toString());
        assertEquals("Natural Resource:Vine", vine.toString());
    }



}