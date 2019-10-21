package deco2800.skyfall.util;

import static deco2800.skyfall.util.MathUtil.clamp;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilTest {
    @Test
    public void testClampInt() {
        //int test
        int value = clamp(-10, 0, 10);
        assertEquals(0, value);
        value = clamp(5, 0, 10);
        assertEquals(5, value);
        value = clamp(20, 0, 10);
        assertEquals(10, value);
    }

    @Test
    public void testClampFloat() {
        //float test
        float value = clamp(-10.0f, 0.0f, 10.0f);
        assertEquals(0.0f, value, 0.1f);
        value = clamp(5.0f, 0.0f, 10.0f);
        assertEquals(5.0f, value, 0.1f);
        value = clamp(20.0f, 0.0f, 10.0f);
        assertEquals(10.0f, value, 0.1f);
    }
}
