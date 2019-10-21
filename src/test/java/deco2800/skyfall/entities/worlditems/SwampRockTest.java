package deco2800.skyfall.entities.worlditems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import deco2800.skyfall.entities.SaveableEntity;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class SwampRockTest {

    SwampRock testSwampRock;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void mementoConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        SwampRock toSave = new SwampRock(testTile, false);
        SaveableEntity.SaveableEntityMemento testSwampRockMemento = toSave.save();
        testSwampRock = new SwampRock(testSwampRockMemento);
        assertEquals(testSwampRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testSwampRock.getRenderOrder());
        assertEquals(100, testSwampRock.getHealth());
        testSwampRock.setHealth(50);
        assertEquals(50, testSwampRock.getHealth());
        assertEquals(testSwampRock.getCol(), 0.5f, 0.001f);
        assertEquals(testSwampRock.getRow(), 0.5f, 0.001f);
        assertFalse(testSwampRock.isObstructed());
        assertEquals("swamp_rock", testSwampRock.getObjectName());
        assertEquals("SwampRock", testSwampRock.getEntityType());
        String returnedTexture = testSwampRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("sRock1")
                || returnedTexture.equals("sRock2") || returnedTexture.equals("sRock3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testSwampRock = new SwampRock(testTile, false);
        assertEquals(testSwampRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testSwampRock.getRenderOrder());
        assertEquals(100, testSwampRock.getHealth());
        assertEquals(testSwampRock.getCol(), 0.5f, 0.001f);
        assertEquals(testSwampRock.getRow(), 0.5f, 0.001f);
        assertFalse(testSwampRock.isObstructed());
        assertEquals("swamp_rock", testSwampRock.getObjectName());
        assertEquals("SwampRock", testSwampRock.getEntityType());
        String returnedTexture = testSwampRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("sRock1")
                || returnedTexture.equals("sRock2") || returnedTexture.equals("sRock3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testSwampRock = new SwampRock(testTile, false);
        SwampRock newInstanceRock = testSwampRock.newInstance(testTile);

        assertEquals(newInstanceRock.getPosition(), testSwampRock.getPosition());
        assertEquals(100, newInstanceRock.getHealth());
        assertEquals(newInstanceRock.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.isObstructed(), testSwampRock.isObstructed());
        assertEquals("swamp_rock", newInstanceRock.getObjectName());
        assertEquals("SwampRock", newInstanceRock.getEntityType());
        String returnedTexture = newInstanceRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("sRock1")
                || returnedTexture.equals("sRock2") || returnedTexture.equals("sRock3"));
    }
}