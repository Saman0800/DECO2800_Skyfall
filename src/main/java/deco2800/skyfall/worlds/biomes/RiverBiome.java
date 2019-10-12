package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Biomes that gets assigned to rivers on the map
 */
public class RiverBiome extends AbstractBiome {
    public static final String NAME = "river";

    /**
     * Constructor for the RiverBiome
     */
    public RiverBiome(AbstractBiome parentBiome, Random random) {
        super(NAME, parentBiome);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 40, 0.7);
    }

    /**
     * Loads a biome from a memento
     * @param memento The memento that holds the save data
     */
    public RiverBiome(AbstractBiomeMemento memento){
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 3, 40, 0.7);
    }

    @Override
    public void setTileTexture(Tile tile) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("lake_1");
        textures.add("lake_2");

        double perlinValue =
                NoiseGenerator.fade(textureGenerator.getOctavedPerlinValue(tile.getCol(), tile.getRow()), 2);
        int adjustedPerlinValue = (int) Math.floor(perlinValue * textures.size());
        if (adjustedPerlinValue >= textures.size()) {
            adjustedPerlinValue = textures.size() - 1;
        }
        // TODO Is `setPerlinValue` still required?
        tile.setPerlinValue(adjustedPerlinValue);
        tile.setTexture(textures.get(adjustedPerlinValue));
    }
}
