package deco2800.skyfall.worlds.generation.perlinnoise;

import deco2800.skyfall.worlds.Tile;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.DoubleStream;

/**
 * Used to generate noise values for biomes
 */
public class TileNoiseGenerator {
    /** List of the tiles that will have a noise value generated for them **/
    private List<Tile> tiles;

    /**
     *  octaves - The number of octaves that will be generated, the higher the value the more slight chaos that will introduced,
     *  this will simulate more realistic terrain.
     */
    private int  octaves;

    /**
     * startPeriod - The initial period, the higher this value the longer it will take to go from one value to another
     * , this results in textures occurring for larger periods when the value is larger
     * attenuation - How much weight hte octaves have, the higher the value and the higher the octaves the more c
     * chaotic the land will be
      */
    private double startPeriod, attenuation;
    /**
     * The random generator given, allows for seeding
     */
    private Random random;

    /**
     * The Constructor, calculates the width and height of the board and the min col and min row values, then
     * gives each tile a perlin value, then normalises those values to integers into the given range
     * @param tiles The list of tiles that
     * @param random The random number generator
     * @param octaves The number of octaves, Determines how many grids will be generated and combined, a higher value
     *                results in more chaotic land
     * @param startPeriod The initial period, the higher this value the longer it will take to go from one value to
     *                    another value
     * @param attenuation The weight of the octaves, the higher this values the more chaotic the land will be
     * @param setter A function that sets a perlin value within a tile
     */
    public TileNoiseGenerator(List<Tile> tiles, Random random,int octaves,  double startPeriod, double attenuation, BiConsumer<Tile, Double> setter){
        this.tiles = tiles;
        this.random = random;
        this.startPeriod = startPeriod;
        this.attenuation = attenuation;
        if (octaves < 1){
            throw new IllegalArgumentException("The octaves must be greater than 0");
        }
        this.octaves = octaves;
        setTilesNoiseValues(setter);
        fadeNoiseValues();
    }



    /**
     * Assigns each tile a perlin noise value
     */
    public void setTilesNoiseValues(BiConsumer<Tile, Double> setter){
        NoiseGenerator noiseGenerator = new NoiseGenerator(random,  octaves, startPeriod, attenuation);
        for (Tile tile : tiles){
            setter.accept(tile, noiseGenerator.getOctavedPerlinValue(tile.getRow() , tile.getCol()));
        }
    }

    /**
     * Applies fading to the tile values to smooth out the transitions between tiles
     */
    public void fadeNoiseValues(){
        for (Tile tile : tiles) {
            double value = tile.getPerlinValue();
            for (int i = 0; i < 3; i++) {
                value = NoiseGenerator.fade(value);
            }
            tile.setPerlinValue(value);
        }
    }
}
