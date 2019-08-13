package deco2800.skyfall.worlds;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class DesertBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();
    private Random randomGen;


    /**
     * Constructer for a Biome
     *
     */
    public DesertBiome() {
        super("desert");
        randomGen = new Random();
    }


    //TODO implement algorithem ? That determines the ground patterns
    //TODO add seeding to the random generation so it can be tested
    //Likes grouped with likes
    /**
     * Method that will determine the textures of the forest biome textures
     *
     */
    protected void setTileTextures() {
        textures.add("desert_0");
        for (Tile tile : getTiles()) {
            int randInt = randomGen.nextInt(textures.size());
            tile.setTexture(textures.get(randInt));
        }
    }
}
