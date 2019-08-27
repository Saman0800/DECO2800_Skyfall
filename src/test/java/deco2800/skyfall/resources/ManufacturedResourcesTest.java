package deco2800.skyfall.resources;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.resources.items.*;
import deco2800.skyfall.util.HexVector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManufacturedResourcesTest {
    private ManufacturedResources hatchet;
    private ManufacturedResources pickAxe;
    private MainCharacter testOwner;
    private HexVector testPosition;

    @Before
    public void setUp() throws Exception {


        hatchet = new Hatchet(testOwner,testPosition);
        pickAxe = new PickAxe(testOwner,testPosition);

    }

    @After
    public void tearDown() throws Exception {

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
    public void getCoordsTest() {

    }

    @Test
    public void toStringTest() {
        assertEquals("Manufactured Resource:Hatchet", hatchet.toString());
        assertEquals("Manufactured Resource:Pick Axe", pickAxe.toString());

    }
}


