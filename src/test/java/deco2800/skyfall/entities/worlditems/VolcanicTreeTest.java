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

public class VolcanicTreeTest {

    VolcanicTree testTree;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new VolcanicTree(testTile, false);
        assertEquals(testTree.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testTree.getRenderOrder());
        assertEquals(testTree.getCol(), 0.5f, 0.001f);
        assertEquals(testTree.getRow(), 0.5f, 0.001f);
        assertFalse(testTree.isObstructed());
        assertEquals("volcanic_tree", testTree.getObjectName());
        assertEquals("VolcanicTree", testTree.getEntityType());
        assertEquals(15, testTree.getWoodAmount());
        String returnedTexture = testTree.getTexture();
        assertTrue(returnedTexture.equals("vTree1") || returnedTexture.equals("vTree2")
                || returnedTexture.equals("vTree3"));
    }

    @Test
    public void mementoConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        VolcanicTree toSave = new VolcanicTree(testTile, false);
        SaveableEntity.SaveableEntityMemento testTreeMemento = toSave.save();
        testTree = new VolcanicTree(testTreeMemento);
        assertEquals(testTree.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testTree.getRenderOrder());
        assertEquals(testTree.getCol(), 0.5f, 0.001f);
        assertEquals(testTree.getRow(), 0.5f, 0.001f);
        assertFalse(testTree.isObstructed());
        assertEquals("volcanic_tree", testTree.getObjectName());
        assertEquals("VolcanicTree", testTree.getEntityType());
        assertEquals(15, testTree.getWoodAmount());
        String returnedTexture = testTree.getTexture();
        assertTrue(returnedTexture.equals("vTree1") || returnedTexture.equals("vTree2")
                || returnedTexture.equals("vTree3"));
    }

    @Test
    public void equalsTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new VolcanicTree(testTile, false);
        VolcanicTree testTreeEq = new VolcanicTree(testTile, false);

        assertEquals(testTree, testTree);
        assertEquals(testTree, testTreeEq);
    }

    @Test
    public void hashTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new VolcanicTree(testTile, false);
        VolcanicTree testTreeEq = new VolcanicTree(testTile, false);

        assertEquals(testTree.hashCode(), testTreeEq.hashCode());
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new VolcanicTree(testTile, false);
        VolcanicTree testTreeNew = testTree.newInstance(testTile);
        assertEquals(testTree, testTreeNew);
    }
}