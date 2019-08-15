package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.NaturalResources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WoodTest extends NaturalResources {

    private Wood wood;


    @Before
    public void setUp()  {
        wood = new Wood();
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void getName1() {
       assertEquals("Wood", wood.getName());
    }


    @Test
    public void isCarryable1() {
        assertTrue(wood.isCarryable());
    }

    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", wood.getSubtype());
    }

    //@Test
    //public void hasHealingPower1() {
        //assertFalse(wood.hasHealingPower());
    //}

    @Test
    public void getCoords1() {
    }

    @Test
    public void getExchangeable1() {
        assertTrue(wood.isExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Wood", wood.toString());
    }

    @Test
    public void getColour() {
        assertEquals("brown", wood.getColour());
    }

    @Test
    public void getBiome() {
    }
}