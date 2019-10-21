package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class MountainTreeTest {

    MountainTree testTree;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new MountainTree(testTile, false);
        assertEquals(testTree.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testTree.getRenderOrder());
        assertEquals(testTree.getCol(), 0.5f, 0.001f);
        assertEquals(testTree.getRow(), 0.5f, 0.001f);
        assertFalse(testTree.isObstructed());
        assertEquals("mountain_tree", testTree.getObjectName());
        assertEquals("MountainTree", testTree.getEntityType());
        assertEquals(15, testTree.getWoodAmount());
        String returnedTexture = testTree.getTexture();
        assertTrue(returnedTexture.equals("MTree1") || returnedTexture.equals("MTree2")
                || returnedTexture.equals("MTree3"));
    }

    @Test
    public void mementoConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        MountainTree toSave = new MountainTree(testTile, false);
        SaveableEntity.SaveableEntityMemento testTreeMemento = toSave.save();
        testTree = new MountainTree(testTreeMemento);
        assertEquals(testTree.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testTree.getRenderOrder());
        assertEquals(testTree.getCol(), 0.5f, 0.001f);
        assertEquals(testTree.getRow(), 0.5f, 0.001f);
        assertFalse(testTree.isObstructed());
        assertEquals("mountain_tree", testTree.getObjectName());
        assertEquals("MountainTree", testTree.getEntityType());
        assertEquals(15, testTree.getWoodAmount());
        String returnedTexture = testTree.getTexture();
        assertTrue(returnedTexture.equals("MTree1") || returnedTexture.equals("MTree2")
                || returnedTexture.equals("MTree3"));
    }

    @Test
    public void equalsTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new MountainTree(testTile, false);
        MountainTree testTreeEq = new MountainTree(testTile, false);

        assertEquals(testTree, testTree);
        assertEquals(testTree, testTreeEq);
    }

    @Test
    public void hashTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new MountainTree(testTile, false);
        MountainTree testTreeEq = new MountainTree(testTile, false);

        assertEquals(testTree.hashCode(), testTreeEq.hashCode());
    }


    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new MountainTree(testTile, false);
        MountainTree testTreeNew = testTree.newInstance(testTile);
        assertEquals(testTree, testTreeNew);
    }
}