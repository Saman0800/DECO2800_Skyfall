package deco2800.skyfall.worlds.generation;

import static org.junit.Assert.assertEquals;

import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;
import org.junit.Test;

public class PerlinNoiseTest {

    @Test
    public void noiseTest(){
        NoiseGenerator noiseGenerator = new NoiseGenerator(0, 4, 10, 0.5);
        assertEquals(0.4997893052902059, noiseGenerator.getOctavedPerlinValue(1.5,1.5), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEnoughOctavesTest(){
        new NoiseGenerator(0, 0, 4, 0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAttenuationTest(){
        new NoiseGenerator(0, 1, 4, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidStartPeriodTest(){
        new NoiseGenerator(0, 1 , 0, 1);
    }

}
