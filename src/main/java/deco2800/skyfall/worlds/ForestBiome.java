package deco2800.skyfall.worlds;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Forest Biome
 */
public class ForestBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();

    /**
     * Constructor for a Biome
     */
    public ForestBiome() {
        super("forest");
    }

    // TODO implement an algorithm that determines the ground patterns
    // TODO add seeding to the random generation so it can be tested
    // Likes grouped with likes
    /**
     * Method that will determine the textures of the forest biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    protected void setTileTextures(Random random) {
        PerlinNoiseGenerator perlinNoise = new PerlinNoiseGenerator(random);
        // perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 2, 30, 0.5);
        perlinNoise.getOctavedPerlinNoiseGrid(getTiles(), 2, 15, 0.5);
        perlinNoise.normalisePerlinValues(getTiles(),3);

        textures.add("grass_0");
        textures.add("grass_1");
        textures.add("grass_2");
//        textures.add("mountain_0");
//        textures.add("water_0");

        for (Tile tile : getTiles()) {
//            int randInt = random.nextInt(textures.size());
            switch ((int) tile.getPerlinValue()){
                case 0:
                    tile.setTexture("grass_0");
                    break;
                case 1:
                    tile.setTexture("grass_1");
                    break;
//                case 2:
                default:
                    tile.setTexture("grass_2");
                    break;
//                case 3:
//                    tile.setTexture("mountain_0");
//                    break;
//                default:
//                    tile.setTexture("water_0");
            }
        }
    }
}
