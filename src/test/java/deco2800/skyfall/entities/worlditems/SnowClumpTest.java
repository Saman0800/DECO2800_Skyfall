package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnowClumpTest {
    private SnowClump snowClump;

    @Before
    public void setUp() throws Exception {
    }


    /**
     * test for new Instance
     */
    @Test
    public void newInstance() {

        Tile tile = new Tile(null, 1f, 2.5f);
        snowClump = new SnowClump(tile, true);
        SnowClump snowClump1 = snowClump.newInstance(tile);
        assertEquals(snowClump1.getPosition(), snowClump.getPosition());

        assertEquals(snowClump1.isObstructed(), snowClump.isObstructed());
        assertEquals("rock", snowClump1.getObjectName());
        assertEquals("SnowClump", snowClump1.getEntityType());
        String returnedTexture = snowClump1.getTexture();

    }

    /**
     * test the set health and get health method
     */
    @Test
    public void getHealth() {
        snowClump = new SnowClump();
        snowClump.setHealth(5);
        assertEquals(5,snowClump.getHealth());
    }
}