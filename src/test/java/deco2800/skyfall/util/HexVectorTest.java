package deco2800.skyfall.util;

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
        assertEquals(0, new HexVector(0, 0).distance(new HexVector(0, 0)), 0.01);

        // Pythagoras
        assertEquals(2, new HexVector(0, 0).distance(new HexVector(1, 1)), 0.01);

        // Longer distance
        assertEquals(15, new HexVector(10, 10).distance(new HexVector(0, 0)), 0.01);

        // Non right angled
        assertEquals(13, new HexVector(5, 10).distance(new HexVector(0, 0)), 0.01);

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

    @Test
    public void parseString() {
        HexVector vector = new HexVector("2.0,-1.5");
        assertEquals(2.0, vector.getCol(), 0.001);
        assertEquals(-1.5, vector.getRow(), 0);
    }

    @Test
    public void calcDistance() {
        float col1 = -2;
        float row1 = 1;

        float col2 = 0;
        float row2 = -1;

        HexVector vector1 = new HexVector(col1, row1);
        HexVector vector2 = new HexVector(col2, row2);

        Cube vectorCube1 = Cube.oddqToCube(col1, row1);
        Cube vectorCube2 = Cube.oddqToCube(col2, row2);

        float expectedDistance = Cube.cubeDistance(vectorCube1, vectorCube2);

        assertEquals(expectedDistance, vector1.distance(vector2), 0.0001);
    }

    @Test
    public void moveTowardTest() {
        float col1 = -2;
        float row1 = 1;

        float col2 = 100;
        float row2 = 100;

        double distance = 2.0;

        HexVector vector1 = new HexVector(col1, row1);
        HexVector vector2 = new HexVector(col2, row2);

        double deltaCol = vector1.getCol() - vector2.getCol();
        double deltaRow = vector1.getRow() - vector2.getRow();

        double angle = Math.atan2(deltaRow, deltaCol) + Math.PI;

        double xShift = Math.cos(angle) * distance;
        double yShift = Math.sin(angle) * distance;

        vector1.moveToward(vector2, distance);

        assertEquals(vector1.getCol(), xShift + col1, 0.0001);
        assertEquals(vector1.getRow(), yShift + row1, 0.0001);
        //assertEquals(vector1.getAngle(), angle, 0.0001);
    }

    @Test
    public void equalByDistance() {
        HexVector vector1 = new HexVector(0, 0);
        HexVector vector2 = new HexVector(0, 0);
        HexVector vector3 = new HexVector((float) 0.00001, 0);

        assertTrue(vector1.isCloseEnoughToBeTheSame(vector2));
        assertTrue(vector1.isCloseEnoughToBeTheSameByDistance(vector3, 1));
    }

    @Test
    public void testEquals() {
        HexVector vector1 = new HexVector(0, 0);
        Cube cube1 = new Cube(0, 0, 0);
        HexVector vector2 = new HexVector(0, 0);
        HexVector vector3 = new HexVector(5, 0);

        assertFalse(vector1.equals(cube1));
        assertTrue(vector1.equals(vector2));
        assertFalse(vector1.equals(vector3));
    }

    @Test
    public void testRounding() {
        HexVector vector1 = new HexVector((float) 0.5, (float) -2.5);
        HexVector vector2 = vector1.getInt();

        assertEquals(Math.round(vector1.getCol()), vector2.getCol(), 0.0001);
        assertEquals(Math.round(vector1.getRow()), vector2.getRow(), 0.0001);
    }

    @Test
    public void testAdd() {
        HexVector vector1 = new HexVector((float) 0.5, (float) -2.5);
        HexVector vector2 = new HexVector((float) 1, (float) 0);
        HexVector vector3 = vector1.add(vector2);

        assertEquals((float) 1.5, vector3.getCol(), 0.0001);
        assertEquals((float) -2.5, vector3.getRow(), 0.0001);
    }
}
