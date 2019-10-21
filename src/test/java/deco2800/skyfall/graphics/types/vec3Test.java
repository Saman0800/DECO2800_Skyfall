package deco2800.skyfall.graphics.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.util.Array2D;
import org.junit.Test;

public class vec3Test {
    @Test
    public void testConstructor() {
        Vec3 testVec = new Vec3(0.5f, 0.1f, 0.3f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);
        assertEquals(0.3f, testVec.getZ(), 0.01f);

        testVec = new Vec3(1.0f);
        assertEquals(1.0f, testVec.getX(), 0.01f);
        assertEquals(1.0f, testVec.getY(), 0.01f);
        assertEquals(1.0f, testVec.getZ(), 0.01f);
    }

    @Test
    public void testAccessor() {
        Vec3 testVec = new Vec3(0.5f, 0.1f, 1.0f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);
        assertEquals(1.0f, testVec.getZ(), 0.01f);
    }

    @Test
    public void testClamp() {
        // min
        Vec3 testVec = new Vec3(-0.1f, -0.1f, -0.1f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.0f, testVec.getX(), 0.01f);
        assertEquals(0.0f, testVec.getY(), 0.01f);
        assertEquals(0.0f, testVec.getZ(), 0.01f);

        // value
        testVec = new Vec3(0.5f, 0.5f, 0.5f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.5f, testVec.getY(), 0.01f);
        assertEquals(0.5f, testVec.getZ(), 0.01f);

        // max
        testVec = new Vec3(30.0f, 30.0f, 30.0f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(1.0f, testVec.getX(), 0.01f);
        assertEquals(1.0f, testVec.getY(), 0.01f);
        assertEquals(1.0f, testVec.getZ(), 0.01f);
    }

    @Test
    public void testEquals() {
        Vec3 a = new Vec3(0.1f, 0.2f, 0.3f);
        Vec3 b = new Vec3(0.1f, 0.2f, 0.3f);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        Vec3 c = new Vec3(0.3f, 0.2f, 0.1f);
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));

        // test vec2 comparison
        Vec2 d = new Vec2(0.1f, 0.2f);
        Vec3 e = new Vec3(0.1f, 0.2f, 0.0f);
        assertTrue(e.equals(d));

        // test comparison against invalid object
        Array2D f = new Array2D(10, 10);
        assertFalse(a.equals(f));
    }
}
