package deco2800.skyfall.entities.spells;

import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FlameWallTest {

    FlameWall flameWall;

    @Before
    public void setup() throws Exception {
        flameWall = new FlameWall(new HexVector(), "flame_wall",
                "spell", 2f, 2f,
                1,
                0.1f,
                10);
    }

    /**
     * Test the tornado position.
     */
    @Test
    public void positionTest(){
        //assertThat("", flameWall.getCol(), is(equalTo(2f)));
        //assertThat("", flameWall.getRow(), is(equalTo(2f)));
    }

    @Test
    public void getManaTest() {
        assertThat("", flameWall.getManaCost(), is(equalTo(20)));
    }
}
