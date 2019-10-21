package deco2800.skyfall.util.lightinghelpers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TFTupleTest {

    private TFTuple testTuple;

    @Test
    public void testConstructorEmpty() {
        testTuple = new TFTuple();
        assertEquals(0f, testTuple.getHour(), 0.0001);
        assertEquals(1.0f, testTuple.getIntensity(), 0.0001);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid1() {
        testTuple = new TFTuple(-1f, 0.5f);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid2() {
        testTuple = new TFTuple(25f, 0.5f);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid3() {
        testTuple = new TFTuple(1f, -0.1f);
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalid4() {
        testTuple = new TFTuple(1f, 1.1f);
    }

    @Test
    public void testInvalid() {
        testTuple = new TFTuple(3f, 0.7f);

        assertEquals(3f, testTuple.getHour(), 0.0001);
        assertEquals(0.7f, testTuple.getIntensity(), 0.0001);
    }

}
