package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class ForestRockTest {

    ForestRock testForestRock;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testForestRock = new ForestRock();
        assertEquals(testForestRock.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(2, testForestRock.getRenderOrder());
        assertEquals(100, testForestRock.getHealth());
        testForestRock.setHealth(50);
        assertEquals(50, testForestRock.getHealth());
        assertEquals(testForestRock.getCol(), 0.0f, 0.001f);
        assertEquals(testForestRock.getRow(), 0.0f, 0.001f);
        assertTrue(testForestRock.isObstructed());
        assertEquals("forest_rock", testForestRock.getObjectName());
        assertEquals("ForestRock", testForestRock.getEntityType());
        String returnedTexture = testForestRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name",
                returnedTexture.equals("rock1") || returnedTexture.equals("rock2") || returnedTexture.equals("rock3"));
    }

    @Test
    public void mementoConstructorTest() {

        ForestRock toSave = new ForestRock();
        SaveableEntity.SaveableEntityMemento testForestRockMemento = toSave.save();
        testForestRock = new ForestRock(testForestRockMemento);
        assertEquals(testForestRock.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(2, testForestRock.getRenderOrder());
        assertEquals(100, testForestRock.getHealth());
        assertEquals(testForestRock.getCol(), 0.0f, 0.001f);
        assertEquals(testForestRock.getRow(), 0.0f, 0.001f);
        assertTrue(testForestRock.isObstructed());
        assertEquals("forest_rock", testForestRock.getObjectName());
        assertEquals("ForestRock", testForestRock.getEntityType());
        String returnedTexture = testForestRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name",
                returnedTexture.equals("rock1") || returnedTexture.equals("rock2") || returnedTexture.equals("rock3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testForestRock = new ForestRock(testTile, false);
        assertEquals(testForestRock.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testForestRock.getRenderOrder());
        assertEquals(100, testForestRock.getHealth());
        assertEquals(testForestRock.getCol(), 0.5f, 0.001f);
        assertEquals(testForestRock.getRow(), 0.5f, 0.001f);
        assertFalse(testForestRock.isObstructed());
        assertEquals("forest_rock", testForestRock.getObjectName());
        assertEquals("ForestRock", testForestRock.getEntityType());
        String returnedTexture = testForestRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name",
                returnedTexture.equals("rock1") || returnedTexture.equals("rock2") || returnedTexture.equals("rock3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testForestRock = new ForestRock(testTile, false);
        ForestRock newInstanceRock = testForestRock.newInstance(testTile);

        assertEquals(newInstanceRock.getPosition(), testForestRock.getPosition());
        assertEquals(100, newInstanceRock.getHealth());
        assertEquals(newInstanceRock.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.isObstructed(), testForestRock.isObstructed());
        assertEquals("forest_rock", newInstanceRock.getObjectName());
        assertEquals("ForestRock", newInstanceRock.getEntityType());
        String returnedTexture = newInstanceRock.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name",
                returnedTexture.equals("rock1") || returnedTexture.equals("rock2") || returnedTexture.equals("rock3"));
    }
}