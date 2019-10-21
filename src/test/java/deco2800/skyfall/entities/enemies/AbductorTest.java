package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbductorTest {
    Abductor abductor1 = new Abductor(4, 9, 0.8f,"Forest");
    Abductor abductor2 = new Abductor(4, 9, 0.8f);

    @Test
    public void testInit() {
        assertEquals("Forest", abductor1.getBiome());
        assertEquals(0.8f, abductor1.getScale(), 0.01);
        assertEquals(4, abductor1.getCol(), 0.01);
        assertEquals(9, abductor1.getRow(), 0.01);

        assertEquals(0.8f, abductor2.getScale(), 0.01);
        assertEquals(4, abductor2.getCol(), 0.01);
        assertEquals(9, abductor2.getRow(), 0.01);
    }
}