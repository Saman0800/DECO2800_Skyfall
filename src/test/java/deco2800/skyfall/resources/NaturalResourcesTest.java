package deco2800.skyfall.resources;

import deco2800.skyfall.resources.items.Wood;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NaturalResourcesTest extends NaturalResources {

    private NaturalResources naturalResource;

    @Before
    public void setUp() throws Exception {
        naturalResource = new Wood();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName1() {
        assertEquals("Wood", naturalResource.getName());
    }

    @Test
    public void isCarryable1() {
        assertTrue(naturalResource.isCarryable());
    }

    @Test
    public void getSubtype1() {
        assertEquals("Natural Resource", naturalResource.getSubtype());
    }

    @Test
    public void hasHealingPower1() {
        assertFalse(naturalResource.hasHealingPower());
    }

    @Test
    public void getCoords1() {
    }

    @Test
    public void getExchangeable1() {
        assertTrue(naturalResource.getExchangeable());
    }

    @Test
    public void toString1() {
        assertEquals("Natural Resource:Wood", naturalResource.toString());
    }


}