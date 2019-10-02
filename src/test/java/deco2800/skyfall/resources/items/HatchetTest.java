package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class HatchetTest {

    private Hatchet hatchet;
    private MainCharacter owner;
    private HexVector position;
    private ForestTree treeToFarm;
    private Tile testTile;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MainCharacter.resetInstance();
        position = new HexVector(1f, 1f);
        owner = MainCharacter.getInstance(1f, 1f, 0.05f, "player", 10);
        hatchet = new Hatchet(owner, position);
        testTile = new Tile(null, 1f, 1f);
        treeToFarm = new ForestTree(testTile, true);
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