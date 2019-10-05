package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Snowy Mountains biome
 */
public class SnowyMountainsBiome extends AbstractBiome {

    /**
     * Constructor for the snowy mountains biome
     */
    public SnowyMountainsBiome(Random random) {
        super("snowy_mountains", null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 3, 60, 0.5);
    }

    /**
     * Loads a biome from a memento
     * @param memento The memento that holds the save data
     */
    public SnowyMountainsBiome(AbstractBiomeMemento memento){
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 3, 60, 0.5);
    }

    @Override
    public void setTileTexture(Tile tile) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("sMountain1");
        textures.add("sMountain2");
        textures.add("sMountain3");

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
