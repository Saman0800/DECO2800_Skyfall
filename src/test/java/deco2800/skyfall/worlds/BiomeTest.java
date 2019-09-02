package deco2800.skyfall.worlds;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import deco2800.skyfall.worlds.biomes.*;
import org.junit.Ignore;
import org.junit.Test;


public class BiomeTest {
        private AbstractBiome forestBiome = new ForestBiome();
        private AbstractBiome mountainBiome = new MountainBiome();
        private AbstractBiome desertBiome = new DesertBiome();

        @Test
        @Ignore
        public void testBiomesTiles(){
                //Testing the forest biome
                ArrayList<AbstractBiome> biomes = new ArrayList<>();
                biomes.add(forestBiome);
                biomes.add(mountainBiome);
                biomes.add(desertBiome);


                for (AbstractBiome biome : biomes){
                        assertEquals(0, biome.getTiles().size());
                }


                desertBiome.addTile(new Tile());
                forestBiome.addTile(new Tile());
                mountainBiome.addTile(new Tile());

                assertEquals("mountain", mountainBiome.getBiomeName());
                assertEquals("forest", forestBiome.getBiomeName());
                assertEquals("desert", desertBiome.getBiomeName());


                for (AbstractBiome biome : biomes){
                        assertEquals(1, biome.getTiles().size());
                }


                desertBiome.setTileTextures(new Random(0));
                for (Tile tile : desertBiome.getTiles()){
                        assertEquals("desert_1", tile.getTextureName());
                }

                forestBiome.setTileTextures(new Random(0));
                //Implement test for seeding
                for (Tile tile : forestBiome.getTiles()){
                        assertEquals("forest_3", tile.getTextureName());
                }
                forestBiome.setTileTextures(new Random(2));
                //Implement test for seeding
                for (Tile tile : forestBiome.getTiles()){
                        assertEquals("forest_3", tile.getTextureName());
                }

                mountainBiome.setTileTextures(new Random(0));
                for (Tile tile : mountainBiome.getTiles()){
                        assertEquals("mountain_10", tile.getTextureName());
                }




//                ArrayList<Tile> tiles = new ArrayList<>();
//                for (int i = 0; i < 5; i ++){
//                        tiles.add(new Tile(biome));
//                }
//





        }

        @Test
        public void parentBiomeTest() {
                AbstractBiome forest = new ForestBiome();
                assertNull(forest.getParentBiome());
                AbstractBiome lake = new LakeBiome(forest);
                assertEquals(forest, lake.getParentBiome());
        }
}
