package deco2800.skyfall.resources;

import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.Aloe_vera;
import deco2800.skyfall.resources.items.Berry;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HealthResourcesTest extends HealthResources{
    private HealthResources healthResources;

    @Before
    public void setUp() throws Exception {
        healthResourcesResource = new Apple();
        healthResourcesResource = new Aloe_Vera();
        healthResourcesResource = new Berry();
        healthResourcesResource = new PoisonousMushroom();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName2() {
        assertEquals("Apple", naturalResource.getName());
        assertEquals("Aloe_Vera", naturalResource.getName());
        assertEquals("Berry", naturalResource.getName());
        assertEquals("PoisonousMushroom", naturalResource.getName());
    }

    @Test
    public void isCarryable2() {
        assertTrue(healthResources.isCarryable());
    }

    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", naturalResource.getSubtype());
    }

    @Test
    public void hasHealingPower2() {
        assertTrue(healthResources.hasHealingPower());
    }

    @Test
    public void getCoords2() {
        assertEquals(Tile.getCoordinates(),healthResources.getCoords());
    }

    @Test
    public void getExchangeable2() {
        assertTrue(healthResources.getExchangeable());
    }

    @Test
    public void getamoutoffoodeffect2() {
        assertTrue(healthResources.getamoutoffoodeffect());
    }


}