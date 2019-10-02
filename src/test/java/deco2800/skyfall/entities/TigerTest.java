package deco2800.skyfall.entities;

import deco2800.skyfall.entities.pets.Tiger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TigerTest {
    Tiger tiger;
    MainCharacter mc;

    @Before
    public void setup(){
        mc = new MainCharacter(2, 4, 0.3f, "fh", 20);
        tiger = new Tiger(3f,3f, mc);

    }

    /**
     * To test tiger position
     */
    @Test
    public void positionTest(){
        assertThat("", tiger.getCol(), is(equalTo(3f)));
        assertThat("", tiger.getRow(), is(equalTo(3f)));

    }

    /**
     * To test the height of tiger
     */
    @Test
    public void heightTest(){
        Assert.assertEquals(5,tiger.getHeight());
    }

    /**
     * To test the health of tiger
     */
    @Test
    public void healTest(){
        Assert.assertEquals(10,tiger.getHealth());
    }

    /**
     * To test tiger armour
     */
    /*
    @Test
    public void testArmour(){
        Assert.assertEquals(5,tiger.getArmour());
    }
    */


    /**
     * To test tiger enemy type
     */
    @Test
    public void getEnemyTypeTest() {
        String type = tiger.getPetType();
        Assert.assertEquals("tiger",type);

    }

    /**
     * To test whether tiger moving
     */
    @Test
    public void getMovingTest() {
        Assert.assertEquals(false,tiger.getMoving());
    }

    /**
     * To test tiger belongs to which biome
     */
    @Test
    public void getBiome() {
        Assert.assertEquals("forest",tiger.getBiome());
    }


    /**
     * Test tiger toString method
     */
    @Test
    public void toStringTest() {
        Assert.assertEquals("tiger at (3, 3) forest biome",
                tiger.toString());
    }

    /**
     * Test the level of tiger
     */
    @Test
    public void testLevel(){
        Assert.assertEquals(2,tiger.getLevel());
    }



}