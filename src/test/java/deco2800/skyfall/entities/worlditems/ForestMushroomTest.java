package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.SaveableEntity;
import deco2800.skyfall.graphics.types.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import deco2800.skyfall.graphics.*;

import static org.powermock.api.mockito.PowerMockito.whenNew;

public class ForestMushroomTest {

    ForestMushroom testMushroom;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testMushroom = new ForestMushroom();
        testMushroom.onTick(0);
        assertEquals(testMushroom.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testMushroom.getRenderOrder());
        assertEquals(testMushroom.getCol(), 0.0f, 0.001f);
        assertEquals(testMushroom.getRow(), 0.0f, 0.001f);
        assertTrue(testMushroom.isObstructed());
        assertEquals("forest_mushrooms", testMushroom.getObjectName());
        assertEquals("ForestMushroom", testMushroom.getEntityType());
        String returnedTexture = testMushroom.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name",
                returnedTexture.equals("mushrooms1") || returnedTexture.equals("mushrooms2"));
    }

    @Test
    public void mementoConstructorTest() {

        ForestMushroom toSave = new ForestMushroom();
        SaveableEntity.SaveableEntityMemento testMushroomMemento = toSave.save();
        testMushroom = new ForestMushroom(testMushroomMemento);
        assertEquals(testMushroom.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testMushroom.getRenderOrder());
        assertEquals(testMushroom.getCol(), 0.0f, 0.001f);
        assertEquals(testMushroom.getRow(), 0.0f, 0.001f);
        assertTrue(testMushroom.isObstructed());
        assertEquals("forest_mushrooms", testMushroom.getObjectName());
        assertEquals("ForestMushroom", testMushroom.getEntityType());
        String returnedTexture = testMushroom.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name",
                returnedTexture.equals("mushrooms1") || returnedTexture.equals("mushrooms2"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testMushroom = new ForestMushroom(testTile, false);
        assertEquals(testMushroom.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testMushroom.getRenderOrder());
        assertEquals(testMushroom.getCol(), 0.5f, 0.001f);
        assertEquals(testMushroom.getRow(), 0.5f, 0.001f);
        assertFalse(testMushroom.isObstructed());
        assertEquals("forest_mushrooms", testMushroom.getObjectName());
        assertEquals("ForestMushroom", testMushroom.getEntityType());
        String returnedTexture = testMushroom.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name",
                returnedTexture.equals("mushrooms1") || returnedTexture.equals("mushrooms2"));
    }

    @Test
    public void getLightTest() {
        // Make sure the same point light is returned between calls.
        testMushroom = new ForestMushroom();
        testMushroom.pointLightSetUp();
        assertEquals(testMushroom.getPointLight(), testMushroom.getPointLight());

        PointLight testLight = new PointLight(new Vec2(0, 0), new Vec3(0.17f, 0.98f, 0.31f), 3.0f, 5.0f);
        PointLight mushroomLight = testMushroom.getPointLight();

        assertEquals(testLight.getColour(), mushroomLight.getColour());
        assertEquals(testLight.getK(), mushroomLight.getK(), 0.001);
        assertEquals(testLight.getA(), mushroomLight.getA(), 0.001);

        testMushroom.updatePointLight();

        // Nothing should change from the update
        assertEquals(testLight.getColour(), mushroomLight.getColour());
        assertEquals(testLight.getK(), mushroomLight.getK(), 0.001);
        assertEquals(testLight.getA(), mushroomLight.getA(), 0.001);
    }
}