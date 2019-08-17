package deco2800.skyfall.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColliderTest {

    private Collider collider;

    @Before
    public void setUp() throws Exception {
        collider = new Collider(0,0,100,100);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {
        assertEquals(0, collider.getX(),0);
        assertEquals(0, collider.getY(),0);
        assertEquals(100, collider.getXLength(),0);
        assertEquals(100, collider.getYLength(),0);
    }

    @Test
    public void getX() {
        assertEquals(0, collider.getX(),0);
    }

    @Test
    public void setX() {
        collider.setX(50);
        assertEquals(50, collider.getX(),0);
    }

    @Test
    public void getY() {
        assertEquals(0, collider.getY(),0);
    }

    @Test
    public void setY() {
        collider.setY(150);
        assertEquals(150, collider.getY(),0);
    }

    @Test
    public void getXLength() {
        assertEquals(100, collider.getXLength(),0);
    }

    @Test
    public void getYLength() {
        assertEquals(100, collider.getYLength(),0);
    }

    @Test
    public void overlaps() {
        Collider e1 = new Collider(50, 50, 100, 100);
        assertTrue(e1.overlaps(collider));

        Collider e2 = new Collider(150, 150, 100, 100);
        assertFalse(e2.overlaps(collider));

        assertTrue(e1.overlaps(e2));
    }

    @Test
    public void distance() {
        Collider e3 = new Collider(100, 100, 100, 100);
        assertEquals(141.42, e3.distance(collider), 0.1);
    }
}