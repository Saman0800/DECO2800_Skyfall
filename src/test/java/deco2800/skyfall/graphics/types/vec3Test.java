package deco2800.skyfall.graphics.types;

import deco2800.skyfall.util.Array2D;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class vec3Test {
    @Test
    public void testConstructor() {
        vec3 testVec = new vec3(0.5f, 0.1f, 0.3f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);
        assertEquals(0.3f, testVec.getZ(), 0.01f);

        testVec = new vec3(1.0f);
        assertEquals(1.0f, testVec.getX(), 0.01f);
        assertEquals(1.0f, testVec.getY(), 0.01f);
        assertEquals(1.0f, testVec.getZ(), 0.01f);
    }

    @Test
    public void testAccessor() {
        vec3 testVec = new vec3(0.5f, 0.1f, 1.0f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);
        assertEquals(1.0f, testVec.getZ(), 0.01f);
    }

    @Test
    public void testClamp() {
        // min
        vec3 testVec = new vec3(-0.1f, -0.1f, -0.1f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.0f, testVec.getX(), 0.01f);
        assertEquals(0.0f, testVec.getY(), 0.01f);
        assertEquals(0.0f, testVec.getZ(), 0.01f);

        // value
        testVec = new vec3(0.5f, 0.5f, 0.5f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.5f, testVec.getY(), 0.01f);
        assertEquals(0.5f, testVec.getZ(), 0.01f);

        // max
        testVec = new vec3(30.0f, 30.0f, 30.0f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(1.0f, testVec.getX(), 0.01f);
        assertEquals(1.0f, testVec.getY(), 0.01f);
        assertEquals(1.0f, testVec.getZ(), 0.01f);
    }

    @Test
    public void testEquals() {
        vec3 a = new vec3(0.1f, 0.2f, 0.3f);
        vec3 b = new vec3(0.1f, 0.2f, 0.3f);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        vec3 c = new vec3(0.3f, 0.2f, 0.1f);
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));

        // test vec2 comparison
        vec2 d = new vec2(0.1f, 0.2f);
        vec3 e = new vec3(0.1f, 0.2f, 0.0f);
        assertTrue(e.equals(d));

        // test comparison against invalid object
        Array2D f = new Array2D(10, 10);
        assertFalse(a.equals(f));
    }
}
