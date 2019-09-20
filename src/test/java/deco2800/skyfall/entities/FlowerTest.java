package deco2800.skyfall.entities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class FlowerTest {
    Flower flower;
    MainCharacter mc;

    @Before
    public void setUp() throws Exception {
        mc = new MainCharacter(2, 4, 0.3f, "fh", 20);
        flower = new Flower(2f,2f, mc);
    }

    /**
     * Test the type of flower
     */
    @Test
    public void getEnemyType() {
        assertEquals("flower", flower.getEnemyType());
    }

    @Test
    public void getMoving() {
    }
    /**
     * Test the Biome of flower
     */
    @Test
    public void getBiome() {
        assertEquals("forest", flower.getBiome());
    }

    /**
     * Test the position of flower
     */
    @Test
    public void getPosition(){
        assertThat("", flower.getCol(), is(equalTo(2f)));
        assertThat("", flower.getRow(), is(equalTo(2f)));
    }
    @Test
    public void testToString() {
    }

    @Test
    public void onTick() {
    }

    @Test
    public void configureAnimations() {
    }

    @Test
    public void setDirectionTextures() {
    }
}