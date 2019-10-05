package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class VolcanicShrubTest {

    VolcanicShrub testVolcanicShrub;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testVolcanicShrub = new VolcanicShrub();
        assertEquals(testVolcanicShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testVolcanicShrub.getRenderOrder());
        assertEquals(testVolcanicShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testVolcanicShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testVolcanicShrub.isObstructed());
        assertEquals("volcanic_shrub", testVolcanicShrub.getObjectName());
        assertEquals("VolcanicShrub", testVolcanicShrub.getEntityType());
        String returnedTexture = testVolcanicShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("vBush1")
                || returnedTexture.equals("vBush2") || returnedTexture.equals("vBush3"));
    }

    @Test
    public void mementoConstructorTest() {

        VolcanicShrub toSave = new VolcanicShrub();
        SaveableEntity.SaveableEntityMemento testVolcanicShrubMemento = toSave.save();
        testVolcanicShrub = new VolcanicShrub(testVolcanicShrubMemento);
        assertEquals(testVolcanicShrub.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testVolcanicShrub.getRenderOrder());
        assertEquals(testVolcanicShrub.getCol(), 0.0f, 0.001f);
        assertEquals(testVolcanicShrub.getRow(), 0.0f, 0.001f);
        assertTrue(testVolcanicShrub.isObstructed());
        assertEquals("volcanic_shrub", testVolcanicShrub.getObjectName());
        assertEquals("VolcanicShrub", testVolcanicShrub.getEntityType());
        String returnedTexture = testVolcanicShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("vBush1")
                || returnedTexture.equals("vBush2") || returnedTexture.equals("vBush3"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testVolcanicShrub = new VolcanicShrub(testTile, false);
        assertEquals(testVolcanicShrub.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testVolcanicShrub.getRenderOrder());
        assertEquals(testVolcanicShrub.getCol(), 0.5f, 0.001f);
        assertEquals(testVolcanicShrub.getRow(), 0.5f, 0.001f);
        assertFalse(testVolcanicShrub.isObstructed());
        assertEquals("volcanic_shrub", testVolcanicShrub.getObjectName());
        assertEquals("VolcanicShrub", testVolcanicShrub.getEntityType());
        String returnedTexture = testVolcanicShrub.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("vBush1")
                || returnedTexture.equals("vBush2") || returnedTexture.equals("vBush3"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testVolcanicShrub = new VolcanicShrub(testTile, false);
        VolcanicShrub newInstanceShrub = testVolcanicShrub.newInstance(testTile);

        assertEquals(newInstanceShrub.getPosition(), testVolcanicShrub.getPosition());
        assertEquals(newInstanceShrub.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceShrub.isObstructed(), testVolcanicShrub.isObstructed());
        assertEquals("volcanic_shrub", newInstanceShrub.getObjectName());
        assertEquals("VolcanicShrub", newInstanceShrub.getEntityType());
        String returnedTexture = newInstanceShrub.getTexture();
        assertTrue("" + returnedTexture + "is an unexpected texture name", returnedTexture.equals("vBush1")
                || returnedTexture.equals("vBush2") || returnedTexture.equals("vBush3"));
    }
}