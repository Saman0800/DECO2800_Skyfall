package deco2800.skyfall.util;

import org.junit.Test;

import static deco2800.skyfall.util.MathUtil.clamp;
import static org.junit.Assert.assertEquals;

public class MathUtilTest {
    @Test
    public void testClamp() {
        //int test
        int value = clamp(-10, 0, 10);
        assertEquals(0, value);
        value = clamp(5, 0, 10);
        assertEquals(5, value);
        value = clamp(20, 0, 10);
        assertEquals(10, value);
    }
}
