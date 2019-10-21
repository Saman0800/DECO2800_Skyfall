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

public class RuinedRobotTest {

    RuinedRobot testRuinedRobot;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testRuinedRobot = new RuinedRobot();
        assertEquals(testRuinedRobot.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testRuinedRobot.getRenderOrder());
        assertEquals(testRuinedRobot.getCol(), 0.0f, 0.001f);
        assertEquals(testRuinedRobot.getRow(), 0.0f, 0.001f);
        assertTrue(testRuinedRobot.isObstructed());
        assertEquals("ruined_robot", testRuinedRobot.getObjectName());
        assertEquals("ruinedRobot", testRuinedRobot.getEntityType());
        String returnedTexture = testRuinedRobot.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedRobot"));
    }

    @Test
    public void mementoConstructorTest() {

        RuinedRobot toSave = new RuinedRobot();
        SaveableEntity.SaveableEntityMemento testRuinedRobotMemento = toSave.save();
        testRuinedRobot = new RuinedRobot(testRuinedRobotMemento);
        assertEquals(testRuinedRobot.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testRuinedRobot.getRenderOrder());
        assertEquals(testRuinedRobot.getCol(), 0.0f, 0.001f);
        assertEquals(testRuinedRobot.getRow(), 0.0f, 0.001f);
        assertTrue(testRuinedRobot.isObstructed());
        assertEquals("ruined_robot", testRuinedRobot.getObjectName());
        assertEquals("ruinedRobot", testRuinedRobot.getEntityType());
        String returnedTexture = testRuinedRobot.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedRobot"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testRuinedRobot = new RuinedRobot(testTile, false);
        assertEquals(testRuinedRobot.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testRuinedRobot.getRenderOrder());
        assertEquals(testRuinedRobot.getCol(), 0.5f, 0.001f);
        assertEquals(testRuinedRobot.getRow(), 0.5f, 0.001f);
        assertFalse(testRuinedRobot.isObstructed());
        assertEquals("ruined_robot", testRuinedRobot.getObjectName());
        assertEquals("ruinedRobot", testRuinedRobot.getEntityType());
        String returnedTexture = testRuinedRobot.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedRobot"));
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testRuinedRobot = new RuinedRobot(testTile, true);
        RuinedRobot testRuinedRobotNew = testRuinedRobot.newInstance(testTile);

        assertEquals(testRuinedRobotNew.getPosition(), testRuinedRobot.getPosition());
        assertEquals(testRuinedRobotNew.getCol(), 0.5f, 0.001f);
        assertEquals(testRuinedRobotNew.getRow(), 0.5f, 0.001f);
        assertEquals(testRuinedRobotNew.isObstructed(), testRuinedRobot.isObstructed());
        assertEquals("ruined_robot", testRuinedRobotNew.getObjectName());
        assertEquals("ruinedRobot", testRuinedRobotNew.getEntityType());
        String returnedTexture = testRuinedRobotNew.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("ruinedRobot"));
    }
}