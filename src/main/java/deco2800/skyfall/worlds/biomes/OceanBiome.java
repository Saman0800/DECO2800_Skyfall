package deco2800.skyfall.worlds.biomes;

import deco2800.skyfall.worlds.Tile;

import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Ocean Biome
 */
public class OceanBiome extends AbstractBiome {
    public static final String NAME = "ocean";

    /**
     * Constructor for a Biome
     */
    public OceanBiome(Random random) {
        super(NAME, null);

        textureGenerator = new NoiseGenerator(random.nextLong(), 5, 160, 0.9);
    }

    /**
     * Loads a biome from a memento
     *
     * @param memento The memento that holds the save data
     */
    public OceanBiome(AbstractBiomeMemento memento) {
        super(memento);
        textureGenerator = new NoiseGenerator(memento.noiseGeneratorSeed, 5, 160, 0.9);
    }


    /**
     * Method that will determine the textures of the ocean biome textures
     *
     * @param random the RNG to use to generate the textures
     */
    public void setTileTextures(Random random) {
        ArrayList<String> textures = new ArrayList<>();
        textures.add("ocean_1");
        textures.add("ocean_2");
        textures.add("ocean_3");

        // Perlin noise generation
        // new TileNoiseGenerator(getTiles(), random, 5, 160, 0.9, Tile::setPerlinValue)

        // Remap the Perlin values to be a linear combination of the noise and the distance from the coast.
        ArrayList<Tile> borderTiles =
                getTiles().stream().filter(this::tileIsBorder).collect(Collectors.toCollection(ArrayList::new));
        HashSet<Tile> checkedTiles = new HashSet<>(borderTiles);
        ArrayList<Tile> newBorderTiles = new ArrayList<>();
        for (int distance = 0; !borderTiles.isEmpty(); distance++) {
            for (Tile tile : borderTiles) {
                tile.setPerlinValue(Math.min(tile.getPerlinValue() / 3 + distance / 35.0, 1));
                for (Tile neighbour : tile.getNeighbours().values()) {
                    if (!checkedTiles.contains(neighbour)) {
                        newBorderTiles.add(neighbour);
                        checkedTiles.add(neighbour);
                    }
                }
            }
            borderTiles = newBorderTiles;
            newBorderTiles = new ArrayList<>();
        }

        for (Tile tile : getTiles()) {
            int perlinValue = (int) Math.floor(tile.getPerlinValue() * textures.size());
            tile.setTexture(textures.get(perlinValue < textures.size() ? perlinValue : textures.size() - 1));
        }
    }

    private boolean tileIsBorder(Tile tile) {
        return tile.getNeighbours().values().stream().anyMatch(neighbour -> neighbour.getBiome() != this);
    }

    @Override
    public void setTileTexture(Tile tile) {
        tile.setTexture("ocean_1");
    }
}
