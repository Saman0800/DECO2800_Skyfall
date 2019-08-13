package deco2800.skyfall.resources.items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoneTest {

    private Stone stone;

    @Before
    public void setUp() throws Exception {

        stone = new Stone();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName1() {
        assertEquals("Stone", stone.getName());
    }


    @Test
    public void isCarryable1() {
        assertTrue(stone.isCarryable());
    }

    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", stone.getSubtype());
    }


    @Test
    public void getCoords1() {
    }

    @Test
    public void isExchangeable1() {
        assertTrue(stone.isExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Stone", stone.toString());
    }

    @Test
    public void getColour() {
        assertEquals("white", stone.getColour());
    }

    @Test
    public void getBiome() {
        assertEquals("Forest", stone.getBiome());
    }
}