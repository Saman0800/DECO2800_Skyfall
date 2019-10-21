package deco2800.skyfall.entities.enemies;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AbductorTest {
    Abductor abductor1 = new Abductor(4, 9, 1f,"Forest");
    Abductor abductor2 = new Abductor(4, 9, 1f);

    @Test
    public void testInit() {
        assertEquals("Forest", abductor1.getBiome());
        assertEquals(1f, abductor1.getScale(), 0.01);
        assertEquals(4, abductor1.getCol(), 0.01);
        assertEquals(9, abductor1.getRow(), 0.01);

        assertEquals(1f, abductor2.getScale(), 0.01);
        assertEquals(4, abductor2.getCol(), 0.01);
        assertEquals(9, abductor2.getRow(), 0.01);
    }
}