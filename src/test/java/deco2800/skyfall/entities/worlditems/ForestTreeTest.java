package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class ForestTreeTest {

    ForestTree testTree;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new ForestTree(testTile, false);
        assertEquals(testTree.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testTree.getRenderOrder());
        assertEquals(testTree.getCol(), 0.5f, 0.001f);
        assertEquals(testTree.getRow(), 0.5f, 0.001f);
        assertFalse(testTree.isObstructed());
        assertEquals("forest_tree", testTree.getObjectName());
        assertEquals("ForestTree", testTree.getEntityType());
        assertEquals(15, testTree.getWoodAmount());
        String returnedTexture = testTree.getTexture();
        assertTrue(
                returnedTexture.equals("tree1") || returnedTexture.equals("tree2") || returnedTexture.equals("tree3"));
    }

    @Test
    public void mementoConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        ForestTree toSave = new ForestTree(testTile, false);
        SaveableEntity.SaveableEntityMemento testTreeMemento = toSave.save();
        testTree = new ForestTree(testTreeMemento);
        assertEquals(testTree.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(5, testTree.getRenderOrder());
        assertEquals(testTree.getCol(), 0.5f, 0.001f);
        assertEquals(testTree.getRow(), 0.5f, 0.001f);
        assertFalse(testTree.isObstructed());
        assertEquals("forest_tree", testTree.getObjectName());
        assertEquals("ForestTree", testTree.getEntityType());
        assertEquals(15, testTree.getWoodAmount());
        String returnedTexture = testTree.getTexture();
        assertTrue(
                returnedTexture.equals("tree1") || returnedTexture.equals("tree2") || returnedTexture.equals("tree3"));
    }

    @Test
    public void equalsTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new ForestTree(testTile, false);
        ForestTree testTreeEq = new ForestTree(testTile, false);

        assertEquals(testTree, testTree);
        assertEquals(testTree, testTreeEq);
    }

    @Test
    public void hashTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new ForestTree(testTile, false);
        ForestTree testTreeEq = new ForestTree(testTile, false);

        assertEquals(testTree.hashCode(), testTreeEq.hashCode());
    }

    @Test
    public void harvestTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new ForestTree(testTile, false);
        int woodDropped = testTree.harvest(testTile).size();

        assertTrue((woodDropped >= 1) && (woodDropped <= 16));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTree = new ForestTree(testTile, false);
        ForestTree testTreeNew = testTree.newInstance(testTile);
        assertEquals(testTree, testTreeNew);
    }
}