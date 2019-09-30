package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class PickAxeTest {

    private PickAxe pickAxe;
    private MainCharacter owner;
    private HexVector position;
    private ForestRock rockToFarm;
    private InventoryManager ownerInventory;
    private Tile testTile;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MainCharacter.resetInstance();
        owner = MainCharacter.getInstance(1f, 1f, 0.05f, "player", 10);
        position = new HexVector(1f, 1f);
        pickAxe = new PickAxe(owner, position);
        testTile = new Tile(null, 1f, 1f);
        rockToFarm = new ForestRock(testTile, true);
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
        assertEquals(1f, position.getCol(), 0);
        assertEquals(1f, position.getRow(), 0);
    }

    @Test
    public void toStringTest() {
        assertEquals("Manufactured Resource:Pick Axe", pickAxe.toString());

    }

    @Test
    public void isExchangeable() {
        assertTrue(pickAxe.isExchangeable());

    }

    @Test
    public void farmRock() {
        pickAxe.farmRock(rockToFarm);
        assertEquals(90, rockToFarm.getHealth());

    }

}