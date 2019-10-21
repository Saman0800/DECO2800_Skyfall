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

public class TreeStumpTest {

    TreeStump testTreeStump;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testTreeStump = new TreeStump();
        testTreeStump.onTick(0);
        assertEquals(testTreeStump.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testTreeStump.getRenderOrder());
        assertEquals(testTreeStump.getCol(), 0.0f, 0.001f);
        assertEquals(testTreeStump.getRow(), 0.0f, 0.001f);
        assertTrue(testTreeStump.isObstructed());
        assertEquals("tree_stump", testTreeStump.getObjectName());
        assertEquals("TreeStump", testTreeStump.getEntityType());
        String returnedTexture = testTreeStump.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("trunk1"));
    }

    @Test
    public void mementoConstructorTest() {

        TreeStump toSave = new TreeStump();
        SaveableEntity.SaveableEntityMemento testTreeStumpMemento = toSave.save();
        testTreeStump = new TreeStump(testTreeStumpMemento);
        assertEquals(testTreeStump.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testTreeStump.getRenderOrder());
        assertEquals(testTreeStump.getCol(), 0.0f, 0.001f);
        assertEquals(testTreeStump.getRow(), 0.0f, 0.001f);
        assertTrue(testTreeStump.isObstructed());
        assertEquals("tree_stump", testTreeStump.getObjectName());
        assertEquals("TreeStump", testTreeStump.getEntityType());
        String returnedTexture = testTreeStump.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("trunk1"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTreeStump = new TreeStump(testTile, false);
        assertEquals(testTreeStump.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testTreeStump.getRenderOrder());
        assertEquals(testTreeStump.getCol(), 0.5f, 0.001f);
        assertEquals(testTreeStump.getRow(), 0.5f, 0.001f);
        assertFalse(testTreeStump.isObstructed());
        assertEquals("tree_stump", testTreeStump.getObjectName());
        assertEquals("TreeStump", testTreeStump.getEntityType());
        String returnedTexture = testTreeStump.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("trunk1"));
    }
}