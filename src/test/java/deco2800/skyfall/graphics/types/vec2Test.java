package deco2800.skyfall.graphics.types;

import deco2800.skyfall.util.Array2D;
import org.junit.Test;

import static org.junit.Assert.*;

public class vec2Test {
    @Test
    public void testConstructor() {
        vec2 testVec = new vec2(0.5f, 0.1f);
        assertEquals(0.5f,testVec.x, 0.01f);
        assertEquals(0.1f, testVec.y, 0.01f);

        testVec = new vec2(1.0f);
        assertEquals(1.0f,testVec.x, 0.01f);
        assertEquals(1.0f, testVec.y, 0.01f);
    }

    @Test
    public void testAccessor() {
        vec2 testVec = new vec2(0.5f, 0.1f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);
    }

    @Test
    public void testClamp() {
        //min
        vec2 testVec = new vec2(-0.1f, -0.1f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.0f, testVec.x, 0.01f);
        assertEquals(0.0f, testVec.y, 0.01f);

        //value
        testVec = new vec2(0.5f, 0.5f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.5f, testVec.x, 0.01f);
        assertEquals(0.5f, testVec.y, 0.01f);

        //max
        testVec = new vec2(30.0f, 30.0f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(1.0f, testVec.x, 0.01f);
        assertEquals(1.0f, testVec.y, 0.01f);
    }

    @Test
    public void testEquals() {
        vec2 a = new vec2(0.1f, 0.2f);
        vec2 b = new vec2(0.1f, 0.2f);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        vec2 c = new vec2(0.3f, 0.2f);
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));

        //test vec3 comparison
        vec3 d = new vec3(0.1f, 0.2f, 0.0f);
        assertTrue(a.equals(d));

        //test comparison against invalid object
        Array2D e = new Array2D(10, 10);
        assertFalse(a.equals(e));
    }
}
