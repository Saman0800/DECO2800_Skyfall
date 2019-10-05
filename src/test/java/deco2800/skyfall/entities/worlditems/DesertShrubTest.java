package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class DesertShrubTest {

    DesertShrub testDesertShrub;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testDesertShrub = new DesertShrub();
        assertEquals(testDesertShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testDesertShrub.getRenderOrder());
        assertEquals(testDesertShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testDesertShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testDesertShrub.isObstructed());
        assertEquals("desert_shrub", testDesertShrub.getObjectName());
        assertEquals("DesertShrub", testDesertShrub.getEntityType());
        String returnedTexture = testDesertShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("DBush1")
                || returnedTexture.equals("DBush2") || returnedTexture.equals("DBush3"));
    }

    @Test
    public void mementoConstructorTest() {

        DesertShrub toSave = new DesertShrub();
        SaveableEntity.SaveableEntityMemento testDesertShrubMemento = toSave.save();
        testDesertShrub = new DesertShrub(testDesertShrubMemento);
        assertEquals(testDesertShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testDesertShrub.getRenderOrder());
        assertEquals(testDesertShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testDesertShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testDesertShrub.isObstructed());
        assertEquals("desert_shrub", testDesertShrub.getObjectName());
        assertEquals("DesertShrub", testDesertShrub.getEntityType());
        String returnedTexture = testDesertShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("DBush1")
                || returnedTexture.equals("DBush2") || returnedTexture.equals("DBush3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testDesertShrub = new DesertShrub(testTile, false);
        assertEquals(testDesertShrub.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testDesertShrub.getRenderOrder());
        assertEquals(testDesertShrub.getCol(), 0.5f, 0.001f);
        assertEquals(testDesertShrub.getRow(), 0.5f, 0.001f);
        assertFalse(testDesertShrub.isObstructed());
        assertEquals("desert_shrub", testDesertShrub.getObjectName());
        assertEquals("DesertShrub", testDesertShrub.getEntityType());
        String returnedTexture = testDesertShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("DBush1")
                || returnedTexture.equals("DBush2") || returnedTexture.equals("DBush3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testDesertShrub = new DesertShrub(testTile, false);
        DesertShrub newInstanceShrub = testDesertShrub.newInstance(testTile);

        assertEquals(newInstanceShrub.getPosition(), testDesertShrub.getPosition());
        assertEquals(newInstanceShrub.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.isObstructed(), testDesertShrub.isObstructed());
        assertEquals("desert_shrub", newInstanceShrub.getObjectName());
        assertEquals("DesertShrub", newInstanceShrub.getEntityType());
        String returnedTexture = newInstanceShrub.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("DBush1")
                || returnedTexture.equals("DBush2") || returnedTexture.equals("DBush3"));
    }
}