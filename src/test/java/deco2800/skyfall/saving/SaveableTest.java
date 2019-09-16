package deco2800.skyfall.saving;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SaveableTest {

    @Test
    public void testWorldGenNode() {
        WorldGenNode node = new WorldGenNode(3, 2);
        long id = node.getID();
        WorldGenNode.WorldGenNodeMemento memento = node.save();
        WorldGenNode nodeCopy = new WorldGenNode(memento);
        assertTrue(nodeCopy.getX() == 3);
        assertTrue(nodeCopy.getY() == 2);
        assertEquals(id, nodeCopy.getID());
    }

    @Test
    public void biomeTest() {
        AbstractBiome.AbstractBiomeMemento memento;
        long id;
        // Check that save works in different scopes
        {
            Random random = new Random(0);
            AbstractBiome forest = new ForestBiome(random);
            AbstractBiome river = new RiverBiome(forest, random);
            id = river.getBiomeID();
            memento = river.save();
        }
        AbstractBiome riverCopy = new RiverBiome(memento);
        assertEquals(id, riverCopy.getBiomeID());
        assertEquals("river", riverCopy.getBiomeName());
    }
}
