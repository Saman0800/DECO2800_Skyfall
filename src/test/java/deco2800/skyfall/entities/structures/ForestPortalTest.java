package deco2800.skyfall.entities.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.buildings.AbstractPortal;
import deco2800.skyfall.buildings.ForestPortal;
import org.junit.Before;
import org.junit.Test;

public class ForestPortalTest {

    public AbstractPortal testPortal;

    @Before
    public void setup() {
        testPortal = new ForestPortal(1.0f, 0.5f, 2);
    }

    @Test
    public void testProperties() {
        assertEquals("portal_forest", testPortal.getTexture());
        assertEquals(0.5f, testPortal.getRow(), 0.001f);
        assertEquals(1.0f, testPortal.getCol(), 0.001f);
        assertEquals(2, testPortal.getRenderOrder());

        assertEquals("ForestPortal", testPortal.getEntityType());
        assertEquals("forest", testPortal.getCurrentBiome());
        assertEquals("desert", testPortal.getNextBiome());
        assertEquals("forestPortal", testPortal.getName());
        assertFalse(testPortal.isBlueprintLearned());

        testPortal.toggleBlueprintLearned();

        assertTrue(testPortal.isBlueprintLearned());
    }
}