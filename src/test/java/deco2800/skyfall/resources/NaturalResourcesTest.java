package deco2800.skyfall.resources;

import deco2800.skyfall.resources.items.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NaturalResourcesTest extends NaturalResources {

    // create Wood, Sand, Stone, Metal and Vine instances of Natural Resource
    private NaturalResources wood;
    private NaturalResources sand;
    private NaturalResources stone;
    private NaturalResources metal;
    private NaturalResources vine;

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

    @Test
    public void getName1() {
        assertEquals("Wood", wood.getName());
        assertEquals("Sand", sand.getName());
        assertEquals("Stone", stone.getName());
        assertEquals("Metal", metal.getName());
        assertEquals("Vine", vine.getName());
    }

    @Test
    public void isCarryable1() {
        assertTrue(wood.isCarryable());
        assertTrue(sand.isCarryable());
        assertTrue(stone.isCarryable());
        assertTrue(metal.isCarryable());
        assertTrue(vine.isCarryable());
    }

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

    @Test
    public void isExchangeable1() {
        assertTrue(wood.isExchangeable());
        assertTrue(sand.isExchangeable());
        assertTrue(stone.isExchangeable());
        assertTrue(metal.isExchangeable());
        assertTrue(vine.isExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Wood", wood.toString());
        assertEquals("Natural Resource:Sand", sand.toString());
        assertEquals("Natural Resource:Stone", stone.toString());
        assertEquals("Natural Resource:Metal", metal.toString());
        assertEquals("Natural Resource:Vine", vine.toString());
    }


}