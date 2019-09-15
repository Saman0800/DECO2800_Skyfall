package deco2800.skyfall.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


public class TreemanTest {
    Treeman treeman;
    @Before
    public void setup(){
        treeman = new Treeman(2f,3f);
    }

    /**
     * To test treeman position
     */
    @Test
    public void positionTest(){
        assertThat("", treeman.getCol(), is(equalTo(2f)));
        assertThat("", treeman.getRow(), is(equalTo(3f)));

    }

    /**
     * To test the height of treeman
     */
    @Test
    public void heightTest(){
        Assert.assertEquals(5,treeman.getHeight());
    }

    /**
     * To test the health of treeman
     */
    @Test
    public void healTest(){
        Assert.assertEquals(10,treeman.getHealth());
    }

    /**
     * To test treeman armour
     */
    @Test
    public void testArmour(){
        Assert.assertEquals(5,treeman.getArmour());
    }


    /**
     * To test treeman enemy type
     */
    @Test
    public void getEnemyTypeTest() {
        String type=treeman.getEnemyType();
        Assert.assertEquals("treeman",type);

    }

    /**
     * To test whether treeman moving
     */
    @Test
    public void getMovingTest() {
        Assert.assertEquals(false,treeman.getMoving());
    }

    /**
     * To test treeman belongs to which biome
     */
    @Test
    public void getBiome() {
        Assert.assertEquals("forest",treeman.getBiome());
    }


    /**
     * Test treeman toString method
     */
    @Test
    public void toStringTest() {
        Assert.assertEquals("treeman at (2, 3) forest biome",
                treeman.toString());
    }

    /**
     * Test the level of treeman
     */
    @Test
    public void testLevel(){
        Assert.assertEquals(2,treeman.getLevel());
    }


    /**
     * To test movement direction
     */
    @Test
    public void movementDirection() {
        Assert.assertEquals(treeman.movementDirection
                (this.treeman.position.getAngle()),Direction.SOUTH_EAST);
    }



}