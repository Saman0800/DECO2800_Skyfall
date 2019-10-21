package deco2800.skyfall.entities.structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import deco2800.skyfall.buildings.AbstractPortal;
import deco2800.skyfall.buildings.DesertPortal;
import org.junit.Before;
import org.junit.Test;

public class DesertPortalTest {

    public AbstractPortal testPortal;

    @Before
    public void setup() {
        testPortal = new DesertPortal(1.0f, 0.5f, 2);
    }

    @Test
    public void testProperties() {
        assertEquals("portal_desert", testPortal.getTexture());
        assertEquals(0.5f, testPortal.getRow(), 0.001f);
        assertEquals(1.0f, testPortal.getCol(), 0.001f);
        assertEquals(2, testPortal.getRenderOrder());

        assertEquals("DesertPortal", testPortal.getEntityType());
        assertEquals("desert", testPortal.getCurrentBiome());
        assertEquals("mountain", testPortal.getNextBiome());
        assertEquals("desertPortal", testPortal.getName());
        assertFalse(testPortal.isBlueprintLearned());
    }
}