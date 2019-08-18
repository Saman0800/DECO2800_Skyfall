package deco2800.skyfall.entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;

public class EntitySpawnRuleTest {
    @Test
    public void testAccessorsAndMutators() {
        EntitySpawnRule rule = new EntitySpawnRule(0);

        rule.setChance(0.5);
        assertEquals(rule.getChance(), 0.5, 0.001);

        rule.setMin(10);
        assertEquals(rule.getMin(), 10);

        rule.setMax(100);
        assertEquals(rule.getMax(), 100);

        ForestBiome biome = new ForestBiome();
        rule.setBiome(biome);
        assertEquals(rule.getBiome(), "forest");
        //test null biome
        rule.setBiome((AbstractBiome)null);
        assertEquals(rule.getBiome(), "");
        //test setting biome name with string
        rule.setBiome(biome.getBiomeName());
        assertEquals(rule.getBiome(), "forest");
    }

    @Test
    public void testConstructors() {
        EntitySpawnRule rule = new EntitySpawnRule(0);
        assertEquals(rule.getChance(), 0.0, 0.001);

        rule = new EntitySpawnRule(5, 10);
        assertEquals(rule.getMin(), 5);
        assertEquals(rule.getMax(), 10);

        rule = new EntitySpawnRule(0.5, 5, 10);
        assertEquals(rule.getChance(), 0.5, 0.001);
        assertEquals(rule.getMin(), 5);
        assertEquals(rule.getMax(), 10);

    }

}
