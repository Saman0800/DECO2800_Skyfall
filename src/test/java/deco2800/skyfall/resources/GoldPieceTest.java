package deco2800.skyfall.resources;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoldPieceTest {

    // create instances of a gold piece
    private GoldPiece g5;
    private GoldPiece g10;
    private GoldPiece g50;
    private GoldPiece g100;

    @Before
    public void setUp()  {
        g5 = new GoldPiece(5);
        g10 = new GoldPiece(10);
        g50 = new GoldPiece(50);
        g100 = new GoldPiece(100);
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
}