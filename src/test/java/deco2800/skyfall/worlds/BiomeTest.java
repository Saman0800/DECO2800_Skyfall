package deco2800.skyfall.worlds;

import deco2800.skyfall.util.Cube;
import java.lang.reflect.Array;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;


public class BiomeTest {
        private AbstractBiome forestBiome = new ForestBiome();
        private AbstractBiome mountainBiome = new MountainBiome();
        private AbstractBiome desertBiome = new DesertBiome();

        @Test
        public void testBiomesTiles(){
                //Testing the forest biome
                ArrayList<AbstractBiome> biomes = new ArrayList<>();
                biomes.add(forestBiome);
                biomes.add(mountainBiome);
                biomes.add(desertBiome);


                for (AbstractBiome biome : biomes){
                        assertEquals(0, biome.getTiles().size());
                }


                new Tile(desertBiome);
                new Tile(forestBiome);
                new Tile(mountainBiome);

                assertEquals("mountain", mountainBiome.getBiomeName());
                assertEquals("forest", forestBiome.getBiomeName());
                assertEquals("desert", desertBiome.getBiomeName());


                for (AbstractBiome biome : biomes){
                        assertEquals(1, biome.getTiles().size());
                }


                desertBiome.setTileTextures();
                for (Tile tile : desertBiome.getTiles()){
                        assertEquals("desert_0", tile.getTextureName());
                }
                forestBiome.setTileTextures();
                //Implement test for seeding
                for (Tile tile : forestBiome.getTiles()){
                        assertEquals("grass_0", tile.getTextureName());
                }
                mountainBiome.setTileTextures();
                for (Tile tile : mountainBiome.getTiles()){
                        assertEquals("mountain_0", tile.getTextureName());
                }




//                ArrayList<Tile> tiles = new ArrayList<>();
//                for (int i = 0; i < 5; i ++){
//                        tiles.add(new Tile(biome));
//                }
//





        }
}
