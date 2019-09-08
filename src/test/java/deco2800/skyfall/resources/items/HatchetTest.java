package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HatchetTest {

    private Hatchet hatchet;
    private MainCharacter owner;
    private HexVector position;
    private Tree treeToFarm;
    private Tile testTile;

    @Before
    public void setUp() {
        position = new HexVector(1f, 1f);
        owner = new MainCharacter(1f, 1f, 0.05f, "player", 100);
        hatchet = new Hatchet(owner, position);
        testTile = new Tile(1f, 1f);
        treeToFarm = new Tree(testTile, true);
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
        assertEquals(1f, position.getCol(), 0);
        assertEquals(1f, position.getRow(), 0);

    }

    @Test
    public void toStringtest() {
        assertEquals("Manufactured Resource:Hatchet", hatchet.toString());
    }

    @Test
    public void isExchangeable() {
        assertTrue(hatchet.isExchangeable());
    }

    @Test
    public void farmTreeTest() {
        hatchet.farmTree(treeToFarm);
        assertEquals(14, treeToFarm.getWoodAmount());

    }
}