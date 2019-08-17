package deco2800.skyfall.worlds;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Desert Biome
 */
public class DesertBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();

    /**
     * Constructor for a Biome
     */
    public DesertBiome() {
        super("desert");
    }


    //TODO implement algorithm ? That determines the ground patterns
    //TODO add seeding to the random generation so it can be tested
    //Likes grouped with likes
    /**
     * Method that will determine the textures of the forest biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    protected void setTileTextures(Random random) {
        textures.add("desert_0");
        for (Tile tile : getTiles()) {
            int randInt = random.nextInt(textures.size());
            tile.setTexture(textures.get(randInt));
        }
    }
}
