package deco2800.skyfall.resources.items;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.ForestRock;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;

public class PickAxeTest {

    private PickAxe pickAxe;
    private MainCharacter owner;
    private HexVector position;
    private ForestRock rockToFarm;
    private Tile testTile;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        pickAxe = new PickAxe();
        MainCharacter.resetInstance();
        owner = MainCharacter.getInstance(1f, 1f, 0.05f, "player", 10);
        position = new HexVector(1f, 1f);
        testTile = new Tile(null, 1f, 1f);
        rockToFarm = new ForestRock(testTile, true);

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

    @Test
    public void getRequiredWoodTest() {
        assertEquals(20, pickAxe.getRequiredWood());
    }

    @Test
    public void getRequiredStoneTest() {
        assertEquals(10, pickAxe.getRequiredStone());
    }

    @Test
    public void getRequiredMetalTest() {
        assertEquals(4, pickAxe.getRequiredMetal());
    }

    @Test
    public void getCostTest() {
        assertEquals(20, pickAxe.getCost());
    }

}