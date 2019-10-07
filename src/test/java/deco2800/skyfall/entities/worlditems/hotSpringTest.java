package deco2800.skyfall.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import deco2800.skyfall.entities.worlditems.hotSpring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
// A test for hot spring class
public class hotSpringTest {
    private hotSpring HotSpring;

    private MainCharacter mainCharacter;
    @Before
    public void setup() throws Exception {
        HotSpring = new hotSpring(2f,2f, mainCharacter);
    }


    /**
     * To test hot spring position
     */
    @Test
    public void positionTest(){
        assertThat("", HotSpring.getCol(), is(equalTo(2f)));
        assertThat("", HotSpring.getRow(), is(equalTo(2f)));
    }

    /**
     * To test the height of hot spring
     */
    @Test
    public void heightTest(){
        Assert.assertEquals(0, HotSpring.getHeight());
    }



    /**
     * To test biome
     */
    @Test
    public void testBiome(){
        Assert.assertEquals("forest", HotSpring.getBiome());
    }


    /**
     * To test recovering value
     */
    @Test
    public void getRecoveringValue() {
        Assert.assertEquals(3, HotSpring.amountOfRecovering());

    }


}


