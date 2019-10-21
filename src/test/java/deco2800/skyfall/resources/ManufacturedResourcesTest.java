package deco2800.skyfall.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.items.Hatchet;
import deco2800.skyfall.resources.items.PickAxe;
import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Test;

public class ManufacturedResourcesTest {
    private ManufacturedResources hatchet;
    private ManufacturedResources pickAxe;
    private MainCharacter testOwner;
    private HexVector testPosition;

    @Before
    public void setUp() {


        hatchet = new Hatchet();
        pickAxe = new PickAxe();

    }


    @Test
    public void isCarryableTest() {
        assertTrue(hatchet.isCarryable());
        assertTrue(pickAxe.isCarryable());
    }

    @Test
    public void getSubtypeTest() {

        assertEquals("Manufactured Resource", hatchet.getSubtype());
        assertEquals("Manufactured Resource", pickAxe.getSubtype());
    }


    @Test
    public void toStringTest() {
        assertEquals("Manufactured Resource:Hatchet", hatchet.toString());
        assertEquals("Manufactured Resource:Pick Axe", pickAxe.toString());

    }
}


