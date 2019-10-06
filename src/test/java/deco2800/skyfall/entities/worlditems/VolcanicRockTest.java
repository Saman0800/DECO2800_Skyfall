package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class VolcanicRockTest {

    VolcanicRock testVolcanicRock;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void mementoConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        VolcanicRock toSave = new VolcanicRock(testTile, false);
        SaveableEntity.SaveableEntityMemento testVolcanicRockMemento = toSave.save();
        testVolcanicRock = new VolcanicRock(testVolcanicRockMemento);
        assertEquals(testVolcanicRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testVolcanicRock.getRenderOrder());
        assertEquals(100, testVolcanicRock.getHealth());
        testVolcanicRock.setHealth(50);
        assertEquals(50, testVolcanicRock.getHealth());
        assertEquals(testVolcanicRock.getCol(), 0.5f, 0.001f);
        assertEquals(testVolcanicRock.getRow(), 0.5f, 0.001f);
        assertFalse(testVolcanicRock.isObstructed());
        assertEquals("volcanic_rock", testVolcanicRock.getObjectName());
        assertEquals("VolcanicRock", testVolcanicRock.getEntityType());
        String returnedTexture = testVolcanicRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("vRock1")
                || returnedTexture.equals("vRock2") || returnedTexture.equals("vRock3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testVolcanicRock = new VolcanicRock(testTile, false);
        assertEquals(testVolcanicRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testVolcanicRock.getRenderOrder());
        assertEquals(100, testVolcanicRock.getHealth());
        assertEquals(testVolcanicRock.getCol(), 0.5f, 0.001f);
        assertEquals(testVolcanicRock.getRow(), 0.5f, 0.001f);
        assertFalse(testVolcanicRock.isObstructed());
        assertEquals("volcanic_rock", testVolcanicRock.getObjectName());
        assertEquals("VolcanicRock", testVolcanicRock.getEntityType());
        String returnedTexture = testVolcanicRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("vRock1")
                || returnedTexture.equals("vRock2") || returnedTexture.equals("vRock3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testVolcanicRock = new VolcanicRock(testTile, false);
        VolcanicRock newInstanceRock = testVolcanicRock.newInstance(testTile);

        assertEquals(newInstanceRock.getPosition(), testVolcanicRock.getPosition());
        assertEquals(100, newInstanceRock.getHealth());
        assertEquals(newInstanceRock.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.isObstructed(), testVolcanicRock.isObstructed());
        assertEquals("volcanic_rock", newInstanceRock.getObjectName());
        assertEquals("VolcanicRock", newInstanceRock.getEntityType());
        String returnedTexture = newInstanceRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("vRock1")
                || returnedTexture.equals("vRock2") || returnedTexture.equals("vRock3"));
    }
}