package deco2800.skyfall.util.lightinghelpers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TDTupleTest {

    private TDTuple testTuple;

    @Test
    public void testConstructorEmpty() {
        testTuple = new TDTuple();
        assertEquals(0, testTuple.getHour());
        assertEquals(1.0, testTuple.getIntensity(), 0.0001);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid1() {
        testTuple = new TDTuple(-1, 0.5);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid2() {
        testTuple = new TDTuple(25, 0.5);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid3() {
        testTuple = new TDTuple(1, -0.1);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid4() {
        testTuple = new TDTuple(1, 1.1);
    }

    @Test
    public void testInvalid() {
        testTuple = new TDTuple(3, 0.7);

        assertEquals(3, testTuple.getHour());
        assertEquals(0.7, testTuple.getIntensity(), 0.0001);
    }

}
