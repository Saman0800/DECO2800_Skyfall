package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.SaveableEntity;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class DesertEnvironmentTest {
    DesertEnvironment desertEnvironment;

    @Before
    public void setUp() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    /**
     * test the basic constructor
     */
    @Test
    public void basicConstructorTest() {

        desertEnvironment = new DesertEnvironment();
        assertEquals(desertEnvironment.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, desertEnvironment.getRenderOrder());

        assertEquals(desertEnvironment.getCol(), 0.0f, 0.001f);
        assertEquals(desertEnvironment.getRow(), 0.0f, 0.001f);
        assertEquals(true,desertEnvironment.isObstructed());
        assertEquals("Desert_Environment", desertEnvironment.getObjectName());
        assertEquals("Desert_Environment", desertEnvironment.getEntityType());
    }

    /**
     * test by memento constructor
     */
    @Test
    public void mementoConstructorTest() {

        DesertEnvironment desertEnvironment1 = new DesertEnvironment();
        SaveableEntity.SaveableEntityMemento testDesertEnvironmentMemento = desertEnvironment1.save();
        desertEnvironment = new DesertEnvironment(testDesertEnvironmentMemento);
        assertEquals(desertEnvironment.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, desertEnvironment.getRenderOrder());
        assertEquals(desertEnvironment.getCol(), 0.0f, 0.001f);
        assertEquals(desertEnvironment.getRow(), 0.0f, 0.001f);
        assertTrue(desertEnvironment.isObstructed());
        assertEquals("Desert_Environment", desertEnvironment.getObjectName());
        assertEquals("Desert_Environment", desertEnvironment.getEntityType());
    }

    /**
     * test the tile constructor
     */
    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        desertEnvironment = new DesertEnvironment(testTile, false);
        assertEquals(desertEnvironment.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, desertEnvironment.getRenderOrder());
        assertEquals(desertEnvironment.getCol(), 0.5f, 0.001f);
        assertEquals(desertEnvironment.getRow(), 0.5f, 0.001f);
        assertFalse(desertEnvironment.isObstructed());
        assertEquals("Desert_Environment", desertEnvironment.getObjectName());
        assertEquals("Desert_Environment", desertEnvironment.getEntityType());
    }

    /**
     * test the new instance
     */
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        desertEnvironment = new DesertEnvironment(testTile, false);
        DesertEnvironment newInstanceRock = desertEnvironment.newInstance(testTile);

        assertEquals(newInstanceRock.getPosition(), desertEnvironment.getPosition());
        assertEquals(newInstanceRock.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceRock.isObstructed(), desertEnvironment.isObstructed());
        assertEquals("Desert_Environment", newInstanceRock.getObjectName());
        assertEquals("Desert_Environment", newInstanceRock.getEntityType());
    }
}