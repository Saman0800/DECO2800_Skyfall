package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MetalTest {

    private Metal metal;

    @Before
    public void setUp() throws Exception {
        metal = new Metal();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName1() {
        assertEquals("Metal", metal.getName());
    }


    @Test
    public void isCarryable1() {
        assertTrue(metal.isCarryable());
    }

    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", metal.getSubtype());
    }


    @Test
    public void getCoords1() {
    }

    @Test
    public void isExchangeable1() {
        assertTrue(metal.isExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Metal", metal.toString());
    }

    @Test
    public void getBiome() {
        assertEquals("Ruined City", metal.getBiome());
    }
}