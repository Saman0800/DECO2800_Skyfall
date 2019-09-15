package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.biomes.*;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BiomeTest {
        @Test
        public void parentBiomeTest() {
                AbstractBiome forest = new ForestBiome(new Random(0));
                assertNull(forest.getParentBiome());
                AbstractBiome lake = new LakeBiome(forest, new Random(0));
                assertEquals(forest, lake.getParentBiome());
        }
}
