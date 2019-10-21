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

public class ForestShrubTest {

    ForestShrub testForestShrub;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testForestShrub = new ForestShrub();
        assertEquals(testForestShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testForestShrub.getRenderOrder());
        assertEquals(testForestShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testForestShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testForestShrub.isObstructed());
        assertEquals("forest_shrub", testForestShrub.getObjectName());
        assertEquals("ForestShrub", testForestShrub.getEntityType());
        String returnedTexture = testForestShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name",
                returnedTexture.equals("bush1") || returnedTexture.equals("bush2") || returnedTexture.equals("bush3"));
    }

    @Test
    public void mementoConstructorTest() {

        ForestShrub toSave = new ForestShrub();
        SaveableEntity.SaveableEntityMemento testForestShrubMemento = toSave.save();
        testForestShrub = new ForestShrub(testForestShrubMemento);
        assertEquals(testForestShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testForestShrub.getRenderOrder());
        assertEquals(testForestShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testForestShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testForestShrub.isObstructed());
        assertEquals("forest_shrub", testForestShrub.getObjectName());
        assertEquals("ForestShrub", testForestShrub.getEntityType());
        String returnedTexture = testForestShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name",
                returnedTexture.equals("bush1") || returnedTexture.equals("bush2") || returnedTexture.equals("bush3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testForestShrub = new ForestShrub(testTile, false);
        assertEquals(testForestShrub.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testForestShrub.getRenderOrder());
        assertEquals(testForestShrub.getCol(), 0.5f, 0.001f);
        assertEquals(testForestShrub.getRow(), 0.5f, 0.001f);
        assertFalse(testForestShrub.isObstructed());
        assertEquals("forest_shrub", testForestShrub.getObjectName());
        assertEquals("ForestShrub", testForestShrub.getEntityType());
        String returnedTexture = testForestShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name",
                returnedTexture.equals("bush1") || returnedTexture.equals("bush2") || returnedTexture.equals("bush3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testForestShrub = new ForestShrub(testTile, false);
        ForestShrub newInstanceShrub = testForestShrub.newInstance(testTile);

        assertEquals(newInstanceShrub.getPosition(), testForestShrub.getPosition());
        assertEquals(newInstanceShrub.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.isObstructed(), testForestShrub.isObstructed());
        assertEquals("forest_shrub", newInstanceShrub.getObjectName());
        assertEquals("ForestShrub", newInstanceShrub.getEntityType());
        String returnedTexture = newInstanceShrub.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name",
                returnedTexture.equals("bush1") || returnedTexture.equals("bush2") || returnedTexture.equals("bush3"));
    }
}