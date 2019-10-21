package deco2800.skyfall.worlds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.LakeBiome;
import java.util.Random;
import org.junit.Test;

public class BiomeTest {
        @Test
        public void parentBiomeTest() {
                AbstractBiome forest = new ForestBiome(new Random(0));
                assertNull(forest.getParentBiome());
                AbstractBiome lake = new LakeBiome(forest, new Random(0));
                assertEquals(forest, lake.getParentBiome());
        }
}
