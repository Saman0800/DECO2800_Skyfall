package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SandTest {

    private Sand sand;

    @Before
    public void setUp() throws Exception {
        sand = new Sand();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName1() {
        assertEquals("Sand", sand.getName());
    }


    @Test
    public void isCarryable1() {
        assertTrue(sand.isCarryable());
    }

    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", sand.getSubtype());
    }

    @Test
    public void getCoords1() {
    }

    @Test
    public void isExchangeable1() {
        assertTrue(sand.isExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Sand", sand.toString());
    }


    @Test
    public void getBiome() {
        assertEquals("Beach", sand.getBiome());
    }
}