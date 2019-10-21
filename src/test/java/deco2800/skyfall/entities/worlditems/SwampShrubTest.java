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

public class SwampShrubTest {

    SwampShrub testSwampShrub;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testSwampShrub = new SwampShrub();
        assertEquals(testSwampShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testSwampShrub.getRenderOrder());
        assertEquals(testSwampShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testSwampShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testSwampShrub.isObstructed());
        assertEquals("swamp_shrub", testSwampShrub.getObjectName());
        assertEquals("SwampShrub", testSwampShrub.getEntityType());
        String returnedTexture = testSwampShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("sBush1")
                || returnedTexture.equals("sBush2") || returnedTexture.equals("sBush3"));
    }

    @Test
    public void mementoConstructorTest() {

        SwampShrub toSave = new SwampShrub();
        SaveableEntity.SaveableEntityMemento testSwampShrubMemento = toSave.save();
        testSwampShrub = new SwampShrub(testSwampShrubMemento);
        assertEquals(testSwampShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testSwampShrub.getRenderOrder());
        assertEquals(testSwampShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testSwampShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testSwampShrub.isObstructed());
        assertEquals("swamp_shrub", testSwampShrub.getObjectName());
        assertEquals("SwampShrub", testSwampShrub.getEntityType());
        String returnedTexture = testSwampShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("sBush1")
                || returnedTexture.equals("sBush2") || returnedTexture.equals("sBush3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testSwampShrub = new SwampShrub(testTile, false);
        assertEquals(testSwampShrub.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testSwampShrub.getRenderOrder());
        assertEquals(testSwampShrub.getCol(), 0.5f, 0.001f);
        assertEquals(testSwampShrub.getRow(), 0.5f, 0.001f);
        assertFalse(testSwampShrub.isObstructed());
        assertEquals("swamp_shrub", testSwampShrub.getObjectName());
        assertEquals("SwampShrub", testSwampShrub.getEntityType());
        String returnedTexture = testSwampShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("sBush1")
                || returnedTexture.equals("sBush2") || returnedTexture.equals("sBush3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testSwampShrub = new SwampShrub(testTile, false);
        SwampShrub newInstanceShrub = testSwampShrub.newInstance(testTile);

        assertEquals(newInstanceShrub.getPosition(), testSwampShrub.getPosition());
        assertEquals(newInstanceShrub.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.isObstructed(), testSwampShrub.isObstructed());
        assertEquals("swamp_shrub", newInstanceShrub.getObjectName());
        assertEquals("SwampShrub", newInstanceShrub.getEntityType());
        String returnedTexture = newInstanceShrub.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("sBush1")
                || returnedTexture.equals("sBush2") || returnedTexture.equals("sBush3"));
    }
}