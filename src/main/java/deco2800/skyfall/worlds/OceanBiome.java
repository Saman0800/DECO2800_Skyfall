package deco2800.skyfall.worlds;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ocean Biome
 */
public class OceanBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();

    /**
     * Constructor for a Biome
     */
    public OceanBiome() {
        super("ocean");
    }


    /**
     * Method that will determine the textures of the forest biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    @Override
    protected void setTileTextures(Random random) {
        textures.add("water_0");
        for (Tile tile : getTiles()) {
            int randInt = random.nextInt(textures.size());
            tile.setTexture(textures.get(randInt));
        }
    }
}
