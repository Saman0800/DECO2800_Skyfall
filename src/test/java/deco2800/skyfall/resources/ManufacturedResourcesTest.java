package deco2800.skyfall.resources;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManufacturedResourcesTest {
    private ManufacturedResources hatchet;
    private ManufacturedResources pickAxe;
    private MainCharacter testOwner;
    private HexVector testPosition;

    @Before
    public void setUp() {


        hatchet = new Hatchet(testOwner,testPosition);
        pickAxe = new PickAxe(testOwner,testPosition);

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


