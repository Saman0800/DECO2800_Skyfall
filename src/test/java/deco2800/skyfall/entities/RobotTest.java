package deco2800.skyfall.entities;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class RobotTest {
    private Robot robot;
    @Before
    public void setup() throws Exception {
        robot=new Robot(1f,2f);
    }


    /**
     * To test robot position
     */
    @Test
    public void positionTest(){
        assertThat("", robot.getCol(), is(equalTo(1f)));
        assertThat("", robot.getRow(), is(equalTo(2f)));
    }

    /**
     * To test the height of robot
     */
    @Test
    public void heightTest(){
        Assert.assertEquals(1,robot.getHeight());
    }

    /**
     * To test the health of robot
     */
    @Test
    public void healTest(){
        Assert.assertEquals(10,robot.getHealth());
    }


    /**
     * To test robot armour
     */
    @Test
    public void testArmour(){
        Assert.assertEquals(2,robot.getArmour());
    }


    /**
     * To test robot enemy type
     */
    @Test
    public void getEnemyTypeTest() {
        String type=robot.getEnemyType();
        Assert.assertEquals("robot",type);

    }

    /**
     * To test whether moving
     */
    @Test
    public void getMovingTest() {
        Assert.assertEquals(false,robot.getMoving());
    }

    /**
     * To test robot belongs to which biome
     */
    @Test
    public void getBiome() {
        Assert.assertEquals("forest",robot.getBiome());
    }


    /**
     * Test robot toString method
     */
    @Test
    public void toStringTest() {
        Assert.assertEquals("robot at (1, 2) forest biome",robot.toString());
    }
}
