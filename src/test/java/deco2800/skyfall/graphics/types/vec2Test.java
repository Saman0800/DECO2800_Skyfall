package deco2800.skyfall.graphics.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.util.Array2D;
import org.junit.Test;

public class vec2Test {
    @Test
    public void testConstructor() {
        Vec2 testVec = new Vec2(0.5f, 0.1f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);

        testVec = new Vec2(1.0f);
        assertEquals(1.0f, testVec.getX(), 0.01f);
        assertEquals(1.0f, testVec.getY(), 0.01f);
    }

    @Test
    public void testAccessor() {
        Vec2 testVec = new Vec2(0.5f, 0.1f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.1f, testVec.getY(), 0.01f);
    }

    @Test
    public void testClamp() {
        // min
        Vec2 testVec = new Vec2(-0.1f, -0.1f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.0f, testVec.getX(), 0.01f);
        assertEquals(0.0f, testVec.getY(), 0.01f);

        // value
        testVec = new Vec2(0.5f, 0.5f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(0.5f, testVec.getX(), 0.01f);
        assertEquals(0.5f, testVec.getY(), 0.01f);

        // max
        testVec = new Vec2(30.0f, 30.0f);
        testVec = testVec.getClampedComponents(0.0f, 1.0f);
        assertEquals(1.0f, testVec.getX(), 0.01f);
        assertEquals(1.0f, testVec.getY(), 0.01f);
    }

    @Test
    public void testEquals() {
        Vec2 a = new Vec2(0.1f, 0.2f);
        Vec2 b = new Vec2(0.1f, 0.2f);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        Vec2 c = new Vec2(0.3f, 0.2f);
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));

        // test vec3 comparison
        Vec3 d = new Vec3(0.1f, 0.2f, 0.0f);
        assertTrue(a.equals(d));

        // test comparison against invalid object
        Array2D e = new Array2D(10, 10);
        assertFalse(a.equals(e));
    }
}
