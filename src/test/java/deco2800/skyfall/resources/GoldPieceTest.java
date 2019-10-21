package deco2800.skyfall.resources;

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

public class GoldPieceTest {

    // create instances of a gold piece
    private GoldPiece g5;
    private GoldPiece g10;
    private GoldPiece g50;
    private GoldPiece g100;
    private GoldPiece testGoldPiece;
    private String goldPieceText;

    @Before
    public void setUp() throws Exception {
        g5 = new GoldPiece(5);
        g10 = new GoldPiece(10);
        g50 = new GoldPiece(50);
        g100 = new GoldPiece(100);
        goldPieceText = "gold_piece";
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);
    }



    /**
     * Ensure that each gold piece returns the correct value
     */
    @Test
    public void getValue() {
        assertTrue(g5.getValue().equals(5));
        assertTrue(g10.getValue().equals(10));
        assertTrue(g50.getValue().equals(50));
        assertTrue(g100.getValue().equals(100));
    }


    @Test
    public void basicConstructorTest() {
        testGoldPiece = new GoldPiece(5);
        assertEquals(testGoldPiece.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(testGoldPiece.getCol(), 0.0f, 0.001f);
        assertEquals(testGoldPiece.getRow(), 0.0f, 0.001f);
        assertTrue(testGoldPiece.isObstructed());
        assertEquals(goldPieceText, testGoldPiece.getObjectName());
    }

    @Test
    public void mementoConstructorTest() {

        GoldPiece toSave = new GoldPiece(5);
        SaveableEntity.SaveableEntityMemento testGoldPieceMemento = toSave.save();
        testGoldPiece = new GoldPiece(testGoldPieceMemento);
        assertEquals(testGoldPiece.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(testGoldPiece.getCol(), 0.0f, 0.001f);
        assertEquals(testGoldPiece.getRow(), 0.0f, 0.001f);
        assertTrue(testGoldPiece.isObstructed());
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testGoldPiece = new GoldPiece(testTile, false);
        assertEquals(testGoldPiece.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testGoldPiece.getRenderOrder());
        assertEquals(testGoldPiece.getCol(), 0.5f, 0.001f);
        assertEquals(testGoldPiece.getRow(), 0.5f, 0.001f);
        assertFalse(testGoldPiece.isObstructed());
        assertEquals(goldPieceText, testGoldPiece.getObjectName());
        assertEquals("GoldPiece", testGoldPiece.getEntityType());
    }

    @Test
    public void newInstanceTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testGoldPiece = new GoldPiece(testTile, false);
        GoldPiece newInstanceGold = testGoldPiece.newInstance(testTile);

        assertEquals(newInstanceGold.getPosition(), testGoldPiece.getPosition());
        assertEquals(newInstanceGold.getCol(), 0.5f, 0.001f);
        assertEquals(newInstanceGold.getRow(), 0.5f, 0.001f);
        assertEquals(newInstanceGold.isObstructed(), testGoldPiece.isObstructed());
        assertEquals(goldPieceText, newInstanceGold.getObjectName());
        assertEquals("GoldPiece",newInstanceGold.getEntityType());
    }
}
