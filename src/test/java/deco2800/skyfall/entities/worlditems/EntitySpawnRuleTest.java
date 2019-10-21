package deco2800.skyfall.entities.worlditems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import java.util.Random;
import java.util.function.Function;
import org.junit.Test;

public class EntitySpawnRuleTest {

    @Test
    public void testAccessorsAndMutators() {
        SpawnControl pieceWise = x -> {
            if ((0 < x) && (x <= 0.5)) {
                return 0;
            } else if ((0.5 < x) && (x <= 0.8)) {
                return 0.05;
            } else {
                return 0.4;
            }
        };

        EntitySpawnRule rule = new EntitySpawnRule(tile -> null, 10, true, pieceWise);
        rule.setChance(0.1);

        assertEquals(0.1, rule.getChance(), 0.001);

        assertEquals(rule.getAdjustMap(), pieceWise);
        assertTrue(rule.getUsePerlin());
        assertFalse(rule.getLimitAdjacent());

        rule.setLimitAdjacent(true);
        rule.setLimitAdjacentValue(5.0);

        assertEquals(5.0, rule.getLimitAdjacentValue(), 0.001);
        assertTrue(rule.getLimitAdjacent());

        NoiseGenerator newGenerator = new NoiseGenerator(new Random().nextLong(), 2, 2.0, 0.3);
        rule.setNoiseGenerator(newGenerator);
        assertEquals(newGenerator, rule.getNoiseGenerator());
    }

    @Test
    public void testConstructors() {

        Function<Tile, StaticEntity> newInstanceTest = tile -> null;

        EntitySpawnRule rule = new EntitySpawnRule(null, 20, false);

        rule.setNewInstance(newInstanceTest);
        rule.setIndex(25);
        EntitySpawnRule.setNoiseSeed(9999);

        // The default chance and perlin noise value boolean should be set
        assertEquals(0.1, rule.getChance(), 0.001);
        assertFalse(rule.getUsePerlin());
        assertNull(rule.getNoiseGenerator());
        assertEquals(newInstanceTest, rule.getNewInstance());
        assertEquals(25, rule.getIndex());
        assertEquals(9999, EntitySpawnRule.getNoiseSeed());
    }

}
