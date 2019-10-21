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

public class OrganicMoundTest {

    OrganicMound testOrganicMound;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testOrganicMound = new OrganicMound();
        assertEquals(testOrganicMound.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testOrganicMound.getRenderOrder());
        assertEquals(testOrganicMound.getCol(), 0.0f, 0.001f);
        assertEquals(testOrganicMound.getRow(), 0.0f, 0.001f);
        assertTrue(testOrganicMound.isObstructed());
        assertEquals("organic_mound", testOrganicMound.getObjectName());
        assertEquals("OrganicMound", testOrganicMound.getEntityType());
        String returnedTexture = testOrganicMound.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("mound1"));
    }

    @Test
    public void mementoConstructorTest() {

        OrganicMound toSave = new OrganicMound();
        SaveableEntity.SaveableEntityMemento testOrganicMoundMemento = toSave.save();
        testOrganicMound = new OrganicMound(testOrganicMoundMemento);
        assertEquals(testOrganicMound.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testOrganicMound.getRenderOrder());
        assertEquals(testOrganicMound.getCol(), 0.0f, 0.001f);
        assertEquals(testOrganicMound.getRow(), 0.0f, 0.001f);
        assertTrue(testOrganicMound.isObstructed());
        assertEquals("organic_mound", testOrganicMound.getObjectName());
        assertEquals("OrganicMound", testOrganicMound.getEntityType());
        String returnedTexture = testOrganicMound.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("mound1"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testOrganicMound = new OrganicMound(testTile, false);
        assertEquals(testOrganicMound.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testOrganicMound.getRenderOrder());
        assertEquals(testOrganicMound.getCol(), 0.5f, 0.001f);
        assertEquals(testOrganicMound.getRow(), 0.5f, 0.001f);
        assertFalse(testOrganicMound.isObstructed());
        assertEquals("organic_mound", testOrganicMound.getObjectName());
        assertEquals("OrganicMound", testOrganicMound.getEntityType());
        String returnedTexture = testOrganicMound.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("mound1"));
    }
}