package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import java.util.List;
import java.util.Random;

public interface BiomeGeneratorInterface {
//    static void generateBiomes(List<WorldGenNode> nodes, Random random, int[] biomeSizes,
//        List<AbstractBiome> biomes, int noLakes, int[] lakeSizes) throws DeadEndGenerationException;
    void generateBiomes() throws DeadEndGenerationException;
}
