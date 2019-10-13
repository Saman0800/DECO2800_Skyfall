package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class DesertCactiTest {

    private DesertCacti testCacti;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testCacti = new DesertCacti(testTile, false);
        testCacti.onTick(0);
        assertEquals(testCacti.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testCacti.getRenderOrder());
        assertEquals(testCacti.getCol(), 0.5f, 0.001f);
        assertEquals(testCacti.getRow(), 0.5f, 0.001f);
        assertFalse(testCacti.isObstructed());
        assertEquals("desert_cacti", testCacti.getObjectName());
        assertEquals("DesertCacti", testCacti.getEntityType());
        assertEquals(3, testCacti.getWoodAmount());
        String returnedTexture = testCacti.getTexture();
        assertTrue(returnedTexture.equals("DCactus1") || returnedTexture.equals("DCactus2")
                || returnedTexture.equals("DCactus3") || returnedTexture.equals("DCactus4"));
    }

    @Test
    public void mementoConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        DesertCacti toSave = new DesertCacti(testTile, false);
        SaveableEntity.SaveableEntityMemento testCactiMemento = toSave.save();
        testCacti = new DesertCacti(testCactiMemento);
        assertEquals(testCacti.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testCacti.getRenderOrder());
        assertEquals(testCacti.getCol(), 0.5f, 0.001f);
        assertEquals(testCacti.getRow(), 0.5f, 0.001f);
        assertFalse(testCacti.isObstructed());
        assertEquals("desert_cacti", testCacti.getObjectName());
        assertEquals("DesertCacti", testCacti.getEntityType());
        assertEquals(3, testCacti.getWoodAmount());
        String returnedTexture = testCacti.getTexture();
        assertTrue(returnedTexture.equals("DCactus1") || returnedTexture.equals("DCactus2")
                || returnedTexture.equals("DCactus3") || returnedTexture.equals("DCactus4"));
    }

    @Test
    public void equalsTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testCacti = new DesertCacti(testTile, false);
        DesertCacti testCactiEq = new DesertCacti(testTile, false);

        MountainTree testRock = new MountainTree(testTile, false);

        assertEquals(testCacti, testCacti);
        assertEquals(testCacti, testCactiEq);
        assertNotEquals(testCacti, null);
        assertNotEquals(testCacti, testRock);
    }

    @Test
    public void hashTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testCacti = new DesertCacti(testTile, false);
        DesertCacti testCactiEq = new DesertCacti(testTile, false);

        assertEquals(testCacti.hashCode(), testCactiEq.hashCode());
    }

    @Test
    public void harvestTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testCacti = new DesertCacti(testTile, false);
        int woodDropped = testCacti.harvest(testTile).size();

        assertTrue((woodDropped > 1) && (woodDropped < 6));

        assertEquals(testCacti.getWoodAmount(), 3);
        testCacti.decreaseWoodAmount();
        assertEquals(testCacti.getWoodAmount(), 2);
    }
}