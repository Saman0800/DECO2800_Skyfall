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

public class BoneTest {

    Bone testBone;

    @Before
    public void Setup() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }

    @Test
    public void basicConstructorTest() {

        testBone = new Bone();
        assertEquals(testBone.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testBone.getRenderOrder());
        assertEquals(testBone.getCol(), 0.0f, 0.001f);
        assertEquals(testBone.getRow(), 0.0f, 0.001f);
        assertTrue(testBone.isObstructed());
        assertEquals("bone", testBone.getObjectName());
        assertEquals("Bone", testBone.getEntityType());
        String returnedTexture = testBone.getTexture();
        assertTrue(returnedTexture.equals("DSkull") || returnedTexture.equals("DRibs"));
    }

    @Test
    public void mementoConstructorTest() {

        Bone toSave = new Bone();
        SaveableEntity.SaveableEntityMemento testBoneMemento = toSave.save();
        testBone = new Bone(testBoneMemento);
        assertEquals(testBone.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testBone.getRenderOrder());
        assertEquals(testBone.getCol(), 0.0f, 0.001f);
        assertEquals(testBone.getRow(), 0.0f, 0.001f);
        assertTrue(testBone.isObstructed());
        assertEquals("bone", testBone.getObjectName());
        assertEquals("Bone", testBone.getEntityType());
        String returnedTexture = testBone.getTexture();
        assertTrue(returnedTexture.equals("DSkull") || returnedTexture.equals("DRibs"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testBone = new Bone(testTile, false);
        assertEquals(testBone.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testBone.getRenderOrder());
        assertEquals(testBone.getCol(), 0.5f, 0.001f);
        assertEquals(testBone.getRow(), 0.5f, 0.001f);
        assertFalse(testBone.isObstructed());
        assertEquals("bone", testBone.getObjectName());
        assertEquals("Bone", testBone.getEntityType());
        String returnedTexture = testBone.getTexture();
        assertTrue(returnedTexture.equals("DSkull") || returnedTexture.equals("DRibs"));
    }

    @Test
    public void getNextTextureTest() {
        assertEquals(Bone.getNextTexture(0), "DSkull");
        assertEquals(Bone.getNextTexture(1), "DRibs");
        assertEquals(Bone.getNextTexture(-50), "DRibs");
    }
}