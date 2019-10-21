package deco2800.skyfall.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Test;

public class GrassTuftTest {

    private GrassTuft grassTuft;

    @Before
    public void setup() throws Exception {
        World mockWorld = mock(World.class);
        grassTuft = new GrassTuft(mockWorld,1f,1f);
    }

    @Test
    public void constructorTest() {
        assertEquals("grass_tuft", grassTuft.getTexture());
    }

    @Test
    public void onTickTest() {
        try {
            grassTuft.onTick(0);
            grassTuft.onTick(1);
        } catch(Exception e) {
            fail("Exception Thrown");
        }
    }

}
