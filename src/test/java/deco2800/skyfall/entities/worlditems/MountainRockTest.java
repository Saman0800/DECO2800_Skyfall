package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class MountainRockTest {

    MountainRock testMountainRock;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testMountainRock = new MountainRock();
        assertEquals(testMountainRock.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testMountainRock.getRenderOrder());
        assertEquals(100, testMountainRock.getHealth());
        testMountainRock.setHealth(50);
        assertEquals(50, testMountainRock.getHealth());
        assertEquals(testMountainRock.getCol(), 0.0f, 0.001f);
        assertEquals(testMountainRock.getRow(), 0.0f, 0.001f);
        assertTrue(testMountainRock.isObstructed());
        assertEquals("mountain_rock", testMountainRock.getObjectName());
        assertEquals("MountainRock", testMountainRock.getEntityType());
        String returnedTexture = testMountainRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("MRock1")
                || returnedTexture.equals("MRock2") || returnedTexture.equals("MRock3"));
    }

    @Test
    public void mementoConstructorTest() {

        MountainRock toSave = new MountainRock();
        SaveableEntity.SaveableEntityMemento testMountainRockMemento = toSave.save();
        testMountainRock = new MountainRock(testMountainRockMemento);
        assertEquals(testMountainRock.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testMountainRock.getRenderOrder());
        assertEquals(100, testMountainRock.getHealth());
        assertEquals(testMountainRock.getCol(), 0.0f, 0.001f);
        assertEquals(testMountainRock.getRow(), 0.0f, 0.001f);
        assertTrue(testMountainRock.isObstructed());
        assertEquals("mountain_rock", testMountainRock.getObjectName());
        assertEquals("MountainRock", testMountainRock.getEntityType());
        String returnedTexture = testMountainRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("MRock1")
                || returnedTexture.equals("MRock2") || returnedTexture.equals("MRock3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testMountainRock = new MountainRock(testTile, false);
        assertEquals(testMountainRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testMountainRock.getRenderOrder());
        assertEquals(100, testMountainRock.getHealth());
        assertEquals(testMountainRock.getCol(), 0.5f, 0.001f);
        assertEquals(testMountainRock.getRow(), 0.5f, 0.001f);
        assertFalse(testMountainRock.isObstructed());
        assertEquals("mountain_rock", testMountainRock.getObjectName());
        assertEquals("MountainRock", testMountainRock.getEntityType());
        String returnedTexture = testMountainRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("MRock1")
                || returnedTexture.equals("MRock2") || returnedTexture.equals("MRock3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testMountainRock = new MountainRock(testTile, false);
        MountainRock newInstanceRock = testMountainRock.newInstance(testTile);

        assertEquals(newInstanceRock.getPosition(), testMountainRock.getPosition());
        assertEquals(100, newInstanceRock.getHealth());
        assertEquals(newInstanceRock.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.isObstructed(), testMountainRock.isObstructed());
        assertEquals("mountain_rock", newInstanceRock.getObjectName());
        assertEquals("MountainRock", newInstanceRock.getEntityType());
        String returnedTexture = newInstanceRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("MRock1")
                || returnedTexture.equals("MRock2") || returnedTexture.equals("MRock3"));
    }
}