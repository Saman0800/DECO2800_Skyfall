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

public class LeavesTest {

    Leaves testLeaves;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testLeaves = new Leaves();
        assertEquals(testLeaves.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testLeaves.getRenderOrder());
        assertEquals(testLeaves.getCol(), 0.0f, 0.001f);
        assertEquals(testLeaves.getRow(), 0.0f, 0.001f);
        assertTrue(testLeaves.isObstructed());
        assertEquals("leaves", testLeaves.getObjectName());
        assertEquals("Leaves", testLeaves.getEntityType());
        String returnedTexture = testLeaves.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("leaves1"));
    }

    @Test
    public void mementoConstructorTest() {

        Leaves toSave = new Leaves();
        SaveableEntity.SaveableEntityMemento testLeavesMemento = toSave.save();
        testLeaves = new Leaves(testLeavesMemento);
        assertEquals(testLeaves.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testLeaves.getRenderOrder());
        assertEquals(testLeaves.getCol(), 0.0f, 0.001f);
        assertEquals(testLeaves.getRow(), 0.0f, 0.001f);
        assertTrue(testLeaves.isObstructed());
        assertEquals("leaves", testLeaves.getObjectName());
        assertEquals("Leaves", testLeaves.getEntityType());
        String returnedTexture = testLeaves.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("leaves1"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testLeaves = new Leaves(testTile, false);
        assertEquals(testLeaves.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testLeaves.getRenderOrder());
        assertEquals(testLeaves.getCol(), 0.5f, 0.001f);
        assertEquals(testLeaves.getRow(), 0.5f, 0.001f);
        assertFalse(testLeaves.isObstructed());
        assertEquals("leaves", testLeaves.getObjectName());
        assertEquals("Leaves", testLeaves.getEntityType());
        String returnedTexture = testLeaves.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("leaves1"));
    }
}