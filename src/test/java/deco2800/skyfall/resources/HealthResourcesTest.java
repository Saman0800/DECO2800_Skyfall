package deco2800.skyfall.resources;

import deco2800.skyfall.resources.items.Apple;
//import deco2800.skyfall.resources.items.Aloe_Vera;
//import deco2800.skyfall.resources.items.Berry;
//import deco2800.skyfall.resources.items.PoisonousMushroom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HealthResourcesTest extends HealthResources{
    private HealthResources healthResources;

    @Before
    public void setUp() throws Exception {
        healthResources = new Apple();
        //healthResources = new Aloe_Vera();
        //healthResources= new Berry();
        //healthResources= new PoisonousMushroom();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName2() {
        assertEquals("Apple", healthResources.getName());
        //assertEquals("Aloe_Vera", healthResources.getName());
        //assertEquals("Berry", healthResources.getName());
        //assertEquals("PoisonousMushroom", healthResources.getName());
    }

    @Test
    public void isCarryable2() {
        assertTrue(healthResources.isCarryable());
    }

    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", healthResources.getSubtype());
    }

    @Test
    public void hasHealingPower2() {
        assertTrue(healthResources.hasHealingPower());
    }

    @Test
    public void getCoords2() {
        //assertEquals(Tile.getCoordinates(),healthResources.getCoords());
    }

    @Test
    public void isExchangeable2() {
        assertTrue(healthResources.isExchangeable());
    }




}