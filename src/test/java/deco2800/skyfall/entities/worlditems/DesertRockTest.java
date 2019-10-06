package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class DesertRockTest {

    DesertRock testDesertRock;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testDesertRock = new DesertRock();
        assertEquals(testDesertRock.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testDesertRock.getRenderOrder());
        assertEquals(100, testDesertRock.getHealth());
        testDesertRock.setHealth(50);
        assertEquals(50, testDesertRock.getHealth());
        assertEquals(testDesertRock.getCol(), 0.0f, 0.001f);
        assertEquals(testDesertRock.getRow(), 0.0f, 0.001f);
        assertTrue(testDesertRock.isObstructed());
        assertEquals("desert_rock", testDesertRock.getObjectName());
        assertEquals("DesertRock", testDesertRock.getEntityType());
        String returnedTexture = testDesertRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("DRock1")
                || returnedTexture.equals("DRock2") || returnedTexture.equals("DRock3"));
    }

    @Test
    public void mementoConstructorTest() {

        DesertRock toSave = new DesertRock();
        SaveableEntity.SaveableEntityMemento testDesertRockMemento = toSave.save();
        testDesertRock = new DesertRock(testDesertRockMemento);
        assertEquals(testDesertRock.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testDesertRock.getRenderOrder());
        assertEquals(100, testDesertRock.getHealth());
        assertEquals(testDesertRock.getCol(), 0.0f, 0.001f);
        assertEquals(testDesertRock.getRow(), 0.0f, 0.001f);
        assertTrue(testDesertRock.isObstructed());
        assertEquals("desert_rock", testDesertRock.getObjectName());
        assertEquals("DesertRock", testDesertRock.getEntityType());
        String returnedTexture = testDesertRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("DRock1")
                || returnedTexture.equals("DRock2") || returnedTexture.equals("DRock3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testDesertRock = new DesertRock(testTile, false);
        assertEquals(testDesertRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testDesertRock.getRenderOrder());
        assertEquals(100, testDesertRock.getHealth());
        assertEquals(testDesertRock.getCol(), 0.5f, 0.001f);
        assertEquals(testDesertRock.getRow(), 0.5f, 0.001f);
        assertFalse(testDesertRock.isObstructed());
        assertEquals("desert_rock", testDesertRock.getObjectName());
        assertEquals("DesertRock", testDesertRock.getEntityType());
        String returnedTexture = testDesertRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("DRock1")
                || returnedTexture.equals("DRock2") || returnedTexture.equals("DRock3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testDesertRock = new DesertRock(testTile, false);
        DesertRock newInstanceRock = testDesertRock.newInstance(testTile);

        assertEquals(newInstanceRock.getPosition(), testDesertRock.getPosition());
        assertEquals(100, newInstanceRock.getHealth());
        assertEquals(newInstanceRock.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.isObstructed(), testDesertRock.isObstructed());
        assertEquals("desert_rock", newInstanceRock.getObjectName());
        assertEquals("DesertRock", newInstanceRock.getEntityType());
        String returnedTexture = newInstanceRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("DRock1")
                || returnedTexture.equals("DRock2") || returnedTexture.equals("DRock3"));
    }
}