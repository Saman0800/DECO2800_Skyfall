package deco2800.skyfall.entities;

import deco2800.skyfall.entities.enemies.Spider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class SpiderTest {
    Spider spider;
    @Before
    public void setup() throws Exception {
        spider=new Spider(1f,2f);
    }

    /**
     * To test spider position
     */
    @Test
    public void positionTest(){
        assertThat("", spider.getCol(), is(equalTo(1f)));
        assertThat("", spider.getRow(), is(equalTo(2f)));
    }

    /**
     * To test the height of spider
     */
    @Test
    public void heightTest(){
        Assert.assertEquals(1,spider.getHeight());
    }

    /**
     * To test the health of spider
     */
    @Test
    public void healTest(){
        Assert.assertEquals(10,spider.getHealth());
    }

    /**
     * To test spider armour
     */
    /*
    @Test
    public void testArmour(){
        Assert.assertEquals(1,spider.getArmour());
    }
    */

    /**
     * To test whether moving
     */
    @Test
    public void getMovingTest() {
        Assert.assertEquals(false,spider.getMoving());
    }

    /**
     * To test spider belongs to which biome
     */
    @Test
    public void getBiome() {
        Assert.assertEquals("forest",spider.getBiomeLocated());
    }


    /**
     * Test spider toString method
     */
    @Test
    public void toStringTest() {
        Assert.assertEquals("spider at (1, 2) forest biome",spider.toString());
    }
}