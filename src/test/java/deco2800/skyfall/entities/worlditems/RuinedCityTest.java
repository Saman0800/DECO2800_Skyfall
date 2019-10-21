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

public class RuinedCityTest {

    RuinedCity testRuinedCity;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testRuinedCity = new RuinedCity();
        assertEquals(testRuinedCity.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testRuinedCity.getRenderOrder());
        assertEquals(testRuinedCity.getCol(), 0.0f, 0.001f);
        assertEquals(testRuinedCity.getRow(), 0.0f, 0.001f);
        assertTrue(testRuinedCity.isObstructed());
        assertEquals("ruined_city", testRuinedCity.getObjectName());
        assertEquals("ruinedCity", testRuinedCity.getEntityType());
        String returnedTexture = testRuinedCity.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedCity"));
    }

    @Test
    public void mementoConstructorTest() {

        RuinedCity toSave = new RuinedCity();
        SaveableEntity.SaveableEntityMemento testRuinedCityMemento = toSave.save();
        testRuinedCity = new RuinedCity(testRuinedCityMemento);
        assertEquals(testRuinedCity.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testRuinedCity.getRenderOrder());
        assertEquals(testRuinedCity.getCol(), 0.0f, 0.001f);
        assertEquals(testRuinedCity.getRow(), 0.0f, 0.001f);
        assertTrue(testRuinedCity.isObstructed());
        assertEquals("ruined_city", testRuinedCity.getObjectName());
        assertEquals("ruinedCity", testRuinedCity.getEntityType());
        String returnedTexture = testRuinedCity.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedCity"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testRuinedCity = new RuinedCity(testTile, false);
        assertEquals(testRuinedCity.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testRuinedCity.getRenderOrder());
        assertEquals(testRuinedCity.getCol(), 0.5f, 0.001f);
        assertEquals(testRuinedCity.getRow(), 0.5f, 0.001f);
        assertFalse(testRuinedCity.isObstructed());
        assertEquals("ruined_city", testRuinedCity.getObjectName());
        assertEquals("ruinedCity", testRuinedCity.getEntityType());
        String returnedTexture = testRuinedCity.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedCity"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testRuinedCity = new RuinedCity(testTile, true);
        RuinedCity testRuinedCityNew = testRuinedCity.newInstance(testTile);

        assertEquals(testRuinedCityNew.getPosition(), testRuinedCity.getPosition());
        assertEquals(testRuinedCityNew.getCol(), 0.5f, 0.001f);
        assertEquals(testRuinedCityNew.getRow(), 0.5f, 0.001f);
        assertEquals(testRuinedCityNew.isObstructed(), testRuinedCity.isObstructed());
        assertEquals("ruined_city", testRuinedCityNew.getObjectName());
        assertEquals("ruinedCity", testRuinedCityNew.getEntityType());
        String returnedTexture = testRuinedCityNew.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedCity"));
    }
}