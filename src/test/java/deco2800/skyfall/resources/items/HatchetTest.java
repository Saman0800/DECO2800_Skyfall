package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HatchetTest {

    private Hatchet hatchet;
    private AgentEntity owner;
    private HexVector position;
    private Tree treeToFarm;
    private InventoryManager ownerInventory;
    private Tile testTile;

    @Before
    public void setUp() {
        hatchet = new Hatchet(owner,position, "Hatchet" );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getName() {
        assertEquals("Hatchet", hatchet.getName());
    }

    @Test
    public void getSubtype() {
        assertEquals("Manufactured Resource", hatchet.getSubtype());
    }

    @Test
    public void getCoords() {
    }

    @Test
    public void toStringtest() {
        assertEquals("Manufactured Resource:Hatchet",hatchet.toString());

    }

    @Test
    public void isExchangeable() {
        assertTrue(hatchet.isExchangeable());
    }

    @Test
    public void farmTreeTest() {


    }
}