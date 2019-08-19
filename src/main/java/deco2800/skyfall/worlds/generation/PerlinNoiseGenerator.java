package deco2800.skyfall.worlds.generation;

import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Perlin noise generation for biome landscapes
 * Algorithm done by https://longwelwind.net/2017/02/09/perlin-noise.html
 */
public class PerlinNoiseGenerator {
    //The random number generator being used
    private Random random;

    public PerlinNoiseGenerator(Random random){
        this.random = random;
    }
    /**
     * Calculates the dot product
     * @param x1 Value in vector 1
     * @param y1 value in vector 1
     * @param x2 value in vector 2
     * @param y2 value in vector 2
     * @return The dot product of two vectors
     */
    public double dot(double x1, double y1, double x2, double y2){
        return x1 * x2 + y1 * y2;
    }

    /**
     * The linear interpolation between a and b
     * @param a Value to interpolate between
     * @param b Value to interpolate between
     * @param t A weighting factor
     * @return The interpolated value
     */
    public double lerp(double a, double b, double t){
        return a * (1-t) + b * t;
    }

    /**
     * Smoothes a value
     * @param x The value to be smoothed
     * @return The smoothed value
     */
    public double fade(double x){
        return 6 * Math.pow(x, 5) - 15 * Math.pow(x, 4) + 10 * Math.pow(x, 3);
    }

    /**
     * Returns a random gradient vector with length 1
     * @return A random gradient vector with x as first value and y as second value
     */
    public double[] randomVector(){
        double angle = random.nextDouble();
        double[] gradientVector = new double[2];
        gradientVector[0] = Math.cos(angle);
        gradientVector[1] = Math.sin(angle);
        return gradientVector;
    }


    /**
     * Creates a 2D array of gradient vectors
     * More information about this can be found at
     * <a>https://flafla2.github.io/2014/08/09/perlinnoise.html</a>
     * @param width The width of the array
     * @param height The height of the array
     * @param period The period, a higher values results in slower fading of values,
     * @return A grid of gradient vectors
     */
    public double[][][] getGradientVectors(int width, int height, double period){
        int gridWidth = (int) Math.ceil(width/period) + 2;
        int gridHeight = (int) Math.ceil(height/period) + 2;

        double[][][] grid = new double[gridHeight][gridWidth][];

        for (int y = 0; y < gridHeight; y++){
            for (int x = 0; x < gridWidth; x++){
                grid[y][x] = randomVector();
            }
        }

        return grid;
    }


    /**
     * Gets a perlin value at a certain point
     * @param x The x value
     * @param y The y value
     * @param gradientVectors A array of gradient vectors calculated with getGradientVectors
     * @param period The period that determines how long it takes to fade between values,
     *               a higher period is a longer fade
     * @return A noise value at the certain point
     */
    public double getPerlinValue(double x, double y, double[][][] gradientVectors, double period){
        double xRel = x / period;
        int xInt = (int) Math.floor(xRel);
        xRel -= xInt;

        double yRel = y / period;
        int yInt = (int) Math.floor(yRel);
        yRel -= yInt;
        try {
            double[] topLeftGradient = gradientVectors[yInt][xInt];
            double[] topRightGradient = gradientVectors[yInt][xInt + 1];
            double[] bottomLeftGradient = gradientVectors[yInt + 1][xInt];
            double[] bottomRightGradient = gradientVectors[yInt+1][xInt+1];

            double topLeftContribution = dot(topLeftGradient[0], topLeftGradient[1], xRel, yRel);
            double topRightContribution = dot(topRightGradient[0], topRightGradient[1], xRel - 1, yRel);
            double bottomLeftContribution = dot(bottomLeftGradient[0], bottomLeftGradient[1], xRel , yRel - 1);
            double bottomRightContribution = dot(bottomRightGradient[0], bottomRightGradient[1] , xRel - 1, yRel -1);

            double topLerp = lerp(topLeftContribution, topRightContribution, xRel);
            double bottomLerp = lerp(bottomLeftContribution, bottomRightContribution, xRel);

            double finalLerp = lerp(topLerp, bottomLerp, yRel);

            return finalLerp/(Math.sqrt(2)/2);
        } catch (RuntimeException e){
            throw e;
        }

    }

    /**
     * Sets the noise values for each hexagon tile
     * @param tiles A list of tiles where each tile in it will receive a noise value
     * @param period The period which determines how long it takes to fade between values
     */
    public void setPerlinValues(ArrayList<Tile> tiles, double period){


        if (tiles.size() > 0){

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

            double[][][] gradientVectors = getGradientVectors((int) Math.ceil(highestRow - lowestRow)
                , (int) Math.ceil(highestCol - lowestCol), period);
            for (Tile tile : tiles){
                tile.setPerlinValue(getPerlinValue((tile.getRow() - lowestRow) , (tile.getCol() - lowestCol), gradientVectors,period));
//                tile.setPerlinValue((getPerlinValue((tile.getRow() - lowestRow) , (tile.getCol() - lowestCol), gradientVectors,period)+
//                    getPerlinValue((tile.getRow() - lowestRow + period/2) , (tile.getCol() - lowestCol + period/2), gradientVectors,period))/2);
            }


        }
        //TODO handle case not enough itles
    }


    /**
     * Uses octaves to generate rougher perline noise, more details can be found at
     * <a>https://flafla2.github.io/2014/08/09/perlinnoise.html</a>
     * @param tiles A list of tiles that receive a noise value, which determine there texture
     * @param octaves A number of octaves, this determines the roughness of the noise
     * @param startPeriod The initial period, determines how long it takes to fade between values
     * @param attenuation This allows grid sizes to have an effect on the noise, a larger value results
     *                    in smaller grids having a large impact on the noise
     */
    public void getOctavedPerlinNoiseGrid(ArrayList<Tile> tiles,int octaves, double startPeriod, double attenuation){
        for (int octave = 0; octave < octaves; octave++){
            double period = startPeriod * Math.pow(0.5, octave);
            double octaveAttenutation = Math.pow(attenuation, octave);
            setPerlinValues(tiles, period);
            for (Tile tile : tiles){
                tile.setPerlinValue(tile.getPerlinValue() * octaveAttenutation);
            }
        }

        double maxValue = 0 ;
        for (int octave = 0; octave < octaves; octave++){
                maxValue += Math.pow(attenuation, octave);
        }
        for (Tile tile: tiles){
            if (maxValue == 0){
                tile.setPerlinValue(0);
            } else {
                tile.setPerlinValue(tile.getPerlinValue() / maxValue);
            }
        }

    }

    /**
     * Used to convert the seemingly random perlin numbers in integers from 0 - numOfTextures
     * These ints can then be used to determine the tile texture
     * @param numOfTextures The number of textures that are used in that biome
     */
    public void normalisePerlinValues(ArrayList<Tile> tiles, int numOfTextures){
        double minPerlinValue = tiles.get(0).getPerlinValue();
        double maxPerlinValue = tiles.get(0).getPerlinValue();
        for (Tile tile : tiles){
            minPerlinValue = Math.min(tile.getPerlinValue(), minPerlinValue);
            maxPerlinValue = Math.max(tile.getPerlinValue(), maxPerlinValue);
        }
        for (Tile tile : tiles){
            tile.setPerlinValue(tile.getPerlinValue() - minPerlinValue);
            tile.setPerlinValue(Math.floor(tile.getPerlinValue()/(maxPerlinValue-minPerlinValue)*(numOfTextures)));
        }
    }
}
