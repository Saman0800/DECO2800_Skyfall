package deco2800.skyfall.worlds.generation.perlinnoise;

import deco2800.skyfall.worlds.Tile;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Used to generate noise values for biomes
 */
public class TileNoiseGenerator {
    /** List of the tiles that will have a noise value generated for them **/
    private List<Tile> tiles;
    /**
     *  height - The height of the biome
     *  width - The width of the biome
     *  octaves - The number of octaves that will be generated, the higher the value the more slight chaos that will introduced,
     *  this will simulate more realistic terrain.
     *  normaliseRange - The integer range that the values will be normalised to, from 0 - normaliseRange,
     *  this allows the noise values which are doubles to be much more easily used
     */
    private int height, width, octaves, normaliseRange;
    /**
     * lowestRow - The lowestRow position that occurs amongst the given tiles
     * lowestCol - The lowestCol position that occurs amongst the given tiles
     */
    private double lowestRow, lowestCol;
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
     */
    public TileNoiseGenerator(List<Tile> tiles, Random random,int octaves,  double startPeriod, double attenuation, BiConsumer<Tile, Double> setter){
        this.tiles = tiles;
        this.random = random;
        this.startPeriod = startPeriod;
        this.attenuation = attenuation;
        this.octaves = octaves;
        setWidthAndHeight();
        setTilesNoiseValues(setter);
        fadeNoiseValues();
    }

    /**
     * Finds the width,height, lowestCol, and lowestRow values
     */
    public void setWidthAndHeight(){

        double lowestCol = tiles.get(0).getCol();
        double lowestRow = tiles.get(0).getRow();
        double highestCol = tiles.get(0).getCol();
        double highestRow = tiles.get(0).getRow();
        for (Tile tile : tiles){
            lowestCol = Math.min(lowestCol, tile.getCol());
            lowestRow = Math.min(lowestRow, tile.getRow());
            highestCol = Math.max(highestCol, tile.getCol());
            highestRow = Math.max(highestRow, tile.getRow());
        }

        height = (int) Math.ceil(highestCol - lowestCol);
        width = (int) Math.ceil(highestRow - lowestRow);
        this.lowestCol = lowestCol;
        this.lowestRow = lowestRow;
    }

    /**
     * Assigns each tile a perlin noise value
     */
    public void setTilesNoiseValues(BiConsumer<Tile, Double> setter){
        NoiseGenerator noiseGenerator = new NoiseGenerator(random, width, height, octaves, startPeriod, attenuation);
        for (Tile tile : tiles){
            setter.accept(tile, noiseGenerator.getOctavedPerlinValue(tile.getRow() - lowestRow, tile.getCol() - lowestCol));
        }
    }

    /**
     * Applies fading the the tile values to smooth out the transitions
     */
    public void fadeNoiseValues(){
        for (Tile tile : tiles) {
            tile.setPerlinValue(NoiseGenerator.fade(NoiseGenerator.fade(tile.getPerlinValue())));
        }
    }
}
