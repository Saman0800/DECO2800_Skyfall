package deco2800.skyfall.entities.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import deco2800.skyfall.buildings.AbstractPortal;
import deco2800.skyfall.buildings.MountainPortal;
import org.junit.Before;
import org.junit.Test;

public class MountainPortalTest {

    public AbstractPortal testPortal;

    @Before
    public void setup() {
        testPortal = new MountainPortal(1.0f, 0.5f, 2);
    }

    @Test
    public void testProperties() {
        assertEquals("portal_mountain", testPortal.getTexture());
        assertEquals(0.5f, testPortal.getRow(), 0.001f);
        assertEquals(1.0f, testPortal.getCol(), 0.001f);
        assertEquals(2, testPortal.getRenderOrder());

        assertEquals("MountainPortal", testPortal.getEntityType());
        assertEquals("mountain", testPortal.getCurrentBiome());
        assertEquals("volcanic_mountains", testPortal.getNextBiome());
        assertEquals("mountainPortal", testPortal.getName());
        assertFalse(testPortal.isBlueprintLearned());
    }
}