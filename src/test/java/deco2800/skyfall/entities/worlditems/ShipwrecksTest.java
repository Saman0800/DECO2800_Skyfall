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

public class ShipwrecksTest {

    Shipwrecks testShipwrecks;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testShipwrecks = new Shipwrecks();
        assertEquals(testShipwrecks.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testShipwrecks.getRenderOrder());
        assertEquals(testShipwrecks.getCol(), 0.0f, 0.001f);
        assertEquals(testShipwrecks.getRow(), 0.0f, 0.001f);
        assertTrue(testShipwrecks.isObstructed());
        assertEquals("ship_wrecks", testShipwrecks.getObjectName());
        assertEquals("Shipwrecks", testShipwrecks.getEntityType());
        String returnedTexture = testShipwrecks.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("shipwrecks"));
    }

    @Test
    public void mementoConstructorTest() {

        Shipwrecks toSave = new Shipwrecks();
        SaveableEntity.SaveableEntityMemento testShipwrecksMemento = toSave.save();
        testShipwrecks = new Shipwrecks(testShipwrecksMemento);
        assertEquals(testShipwrecks.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testShipwrecks.getRenderOrder());
        assertEquals(testShipwrecks.getCol(), 0.0f, 0.001f);
        assertEquals(testShipwrecks.getRow(), 0.0f, 0.001f);
        assertTrue(testShipwrecks.isObstructed());
        assertEquals("ship_wrecks", testShipwrecks.getObjectName());
        assertEquals("Shipwrecks", testShipwrecks.getEntityType());
        String returnedTexture = testShipwrecks.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("shipwrecks"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testShipwrecks = new Shipwrecks(testTile, false);
        assertEquals(testShipwrecks.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testShipwrecks.getRenderOrder());
        assertEquals(testShipwrecks.getCol(), 0.5f, 0.001f);
        assertEquals(testShipwrecks.getRow(), 0.5f, 0.001f);
        assertFalse(testShipwrecks.isObstructed());
        assertEquals("ship_wrecks", testShipwrecks.getObjectName());
        assertEquals("Shipwrecks", testShipwrecks.getEntityType());
        String returnedTexture = testShipwrecks.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("shipwrecks"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testShipwrecks = new Shipwrecks(testTile, true);
        Shipwrecks testShipwrecksNew = testShipwrecks.newInstance(testTile);

        assertEquals(testShipwrecksNew.getPosition(), testShipwrecks.getPosition());
        assertEquals(testShipwrecksNew.getCol(), 0.5f, 0.001f);
        assertEquals(testShipwrecksNew.getRow(), 0.5f, 0.001f);
        assertEquals(testShipwrecksNew.isObstructed(), testShipwrecks.isObstructed());
        assertEquals("ship_wrecks", testShipwrecksNew.getObjectName());
        assertEquals("Shipwrecks", testShipwrecksNew.getEntityType());
        String returnedTexture = testShipwrecksNew.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("shipwrecks"));
    }
}