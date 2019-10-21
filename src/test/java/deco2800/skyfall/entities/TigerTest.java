package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.pets.Tiger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TigerTest {
    Tiger tiger;
    Tiger tiger1;
    MainCharacter mc;

    @Before
    public void setup(){
        mc = new MainCharacter(0, 0, 0.3f, "fh", 20);
        tiger = new Tiger(3f,3f);
        tiger1 = new Tiger(2f,4f,mc);

    }

    /**
     * To test tiger position
     */
    @Test
    public void positionTest(){
        assertThat("", tiger.getCol(), is(equalTo(3f)));
        assertThat("", tiger.getRow(), is(equalTo(3f)));
        assertThat("", tiger1.getCol(), is(equalTo(2f)));
        assertThat("", tiger1.getRow(), is(equalTo(4f)));

    }

    /**
     * get the type of pet
     */
    @Test
    public void getPetType() {
        Assert.assertEquals("tiger",tiger.getPetType());
    }

    /**
     * test the moving
     */
    @Test
    public void getMoving() {
        Assert.assertFalse(tiger.getMoving());
    }

    /**
     * test the injure
     */
    @Test
    public void getInjure() {
        Assert.assertFalse(tiger.getInjure());
    }

    /**
     * test the moving direction
     */
    @Test
    public void getMovingDirection() {
        Assert.assertEquals(Direction.SOUTH,tiger.getMovingDirection());
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
        Assert.assertFalse(tiger.getMoving());
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

}