package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.Rock;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PickAxeTest {

    private PickAxe pickAxe;
    private AgentEntity owner;
    private HexVector position;
    private Rock rockToFarm;
    private InventoryManager ownerInventory;

    @Before
    public void setUp()  {
        pickAxe = new PickAxe(owner,position, "Pick Axe" );
        rockToFarm = new Rock();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getName() {
        assertEquals("Pick Axe", pickAxe.getName());

    }

    @Test
    public void getSubtype() {
        assertEquals("Manufactured Resource", pickAxe.getSubtype());

    }

    @Test
    public void getCoords() {
    }

    @Test
    public void toStringTest() {
        assertEquals("Manufactured Resource:Pick Axe",pickAxe.toString());

    }

    @Test
    public void isExchangeable() {
        assertTrue(pickAxe.isExchangeable());

    }

    @Test
    public void farmRock() {

    }

}