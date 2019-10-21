package deco2800.skyfall.resources.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.ForestTree;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Test;

public class HatchetTest {

    private Hatchet hatchet;
    private MainCharacter owner;
    private HexVector position;
    private ForestTree treeToFarm;
    private Tile testTile;
    private World w = null;


    @Before

    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        MainCharacter.resetInstance();
        hatchet = new Hatchet();
        MainCharacter.resetInstance();
        position = new HexVector(1.1f, 1.1f);
        owner = MainCharacter.getInstance(1f, 1f, 0.05f, "player", 10);
        testTile = new Tile(null, 1.1f, 1.1f);
        treeToFarm = new ForestTree(testTile, true);
        treeToFarm.setPosition(1,1);
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

    @Test
    public void getRequiredWoodTest() {
        assertEquals(25, hatchet.getRequiredWood());
    }

    @Test
    public void getRequiredStoneTest() {
        assertEquals(10, hatchet.getRequiredStone());
    }

    @Test
    public void getRequiredMetalTest() {
        assertEquals(0, hatchet.getRequiredMetal());
    }

    @Test
    public void getCostTest() {
        assertEquals(20, hatchet.getCost());
    }










}