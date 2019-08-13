package deco2800.skyfall.worlds;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ForestBiome extends AbstractBiome {
    private ArrayList<String> textures = new ArrayList<>();
    private Random randomGen;


    /**
     * Constructer for a Biome
     *
     */
    public ForestBiome() {
        super("forest");
        randomGen = new Random();
    }

    /**
     * Constructor for the forest biome
     * @param tiles Tiles that the biome contains
     */
    public ForestBiome(ArrayList<Tile> tiles) {
        super("forest", tiles);
        randomGen = new Random();
    }


    //TODO implement algorithem ? That determines the ground patterns
    //Likes grouped with likes
    /**
     * Method that will determine the textures of the forest biome textures
     *
     */
    public void setTileTextures() {
        textures.add("grass_0");
        textures.add("grass_1");
        textures.add("grass_2");
        for (Tile tile : getTiles()) {
            int randInt = randomGen.nextInt(textures.size());
            tile.setTexture(textures.get(randInt));
        }
    }
}
