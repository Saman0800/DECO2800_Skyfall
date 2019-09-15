package deco2800.skyfall.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CamelTest {
    private Camel camel;

    @Before
    public void setup() throws Exception {
        camel = new Camel(5f,4f);
    }

    /**
     * Check the position of camel
     */
    @Test
    public void positionTest() {
        assertThat("", camel.getCol(), is(equalTo(5f)));
        assertThat("", camel.getRow(), is(equalTo(4f)));
    }

    /**
     * Check what type of vehicle it was
     */
    @Test
    public void getTypeTest() {
        Assert.assertEquals("camel", camel.getVehicleType());
    }

    /**
     * Test the camel belongs to which biome
     */
    @Test
    public void getBIOMETest() {
        Assert.assertEquals("desert", camel.getBiome());
    }

    /**
     * Check whether vehicle is moving
     */
    @Test
    public void isMovingTest() {
        Assert.assertFalse(camel.getMove());
    }

    /**
     * Check whether the vehicle is available for this biome
     */
    @Test
    public void isAvailableTest() {
        Assert.assertTrue(camel.isAvailable());
        Assert.assertFalse(camel.checkAvailable("grass"));
        Assert.assertTrue(camel.checkAvailable("desert"));
    }
}
