package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.Spider;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TornadoTest {

    Tornado tornado;

    @Before
    public void setup() throws Exception {
        tornado = new Tornado(new HexVector(), "tornado_placeholder",
                "spell", 0f, 0f,
                20,
                0.1f,
                10);
    }

    /**
     * Test the tornado position.
     */
    @Test
    public void positionTest(){
        assertThat("", tornado.getCol(), is(equalTo(0f)));
        assertThat("", tornado.getRow(), is(equalTo(0f)));
    }

    @Test
    public void getManaTest() {
        assertThat("", tornado.getManaCost(), is(equalTo(10)));
    }

}
