package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VineTest {

    private Vine vine;

    @Before
    public void setUp() throws Exception {

        vine = new Vine();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName1() {
        assertEquals("Vine", vine.getName());
    }


    @Test
    public void isCarryable1() {
        assertTrue(vine.isCarryable());
    }

    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", vine.getSubtype());
    }


    @Test
    public void getCoords1() {
    }

    @Test
    public void isExchangeable1() {
        assertTrue(vine.isExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Vine", vine.toString());
    }



    @Test
    public void getBiome() {
        assertEquals("Forest", vine.getBiome());
    }
}