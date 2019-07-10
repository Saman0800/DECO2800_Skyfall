package deco2800.thomas.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HexVectorTest {

    @Before
    public void Setup() {

    }

    @Test
    public void TestDistance() {
        // Test float version with exactly the same values

        // Same place
        assertEquals(0, new HexVector(0,0).distance(new HexVector(0, 0)), 0.01);

        // Pythagoras
        assertEquals(2, new HexVector(0,0).distance(new HexVector(1, 1)), 0.01);

        // Longer distance
        assertEquals(15, new HexVector(10,10).distance(new HexVector(0, 0)), 0.01);

        // Non right angled
        assertEquals(13, new HexVector(5,10).distance(new HexVector(0, 0)), 0.01);

    }

    @Test
    public void testSetCol() {
        HexVector vector = new HexVector(3, 4);
        vector.setCol(19);
        assertEquals(19, vector.getCol(), 0);
    }

    @Test
    public void testSetRow() {
        HexVector vector = new HexVector(3, 4);
        vector.setRow(19);
        assertEquals(19, vector.getRow(), 0);
    }


    @Test
    public void testConstructor() {
        HexVector vector = new HexVector(5, 4);
        assertEquals(4, vector.getRow(), 0);
        assertEquals(5, vector.getCol(), 0);
    }

    @Test
    public void testCloneConstructor() {
        HexVector vector1 = new HexVector(1, 1);
        HexVector vector2 = new HexVector(vector1);

        assertEquals(1, vector2.getCol(), 0);
        assertEquals(1, vector2.getRow(), 0);
        assertEquals(vector1, vector2);
    }

    @Test
    public void testEmptyConstructor() {
        HexVector vector = new HexVector();
        assertEquals(0, vector.getCol(), 0);
        assertEquals(0, vector.getRow(), 0);
    }


}
