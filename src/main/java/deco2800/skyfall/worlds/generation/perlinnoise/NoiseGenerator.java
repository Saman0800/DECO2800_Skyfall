package deco2800.skyfall.worlds.generation.perlinnoise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Used to allow for generation of perlin noise value for coordinates
 * Implementation inspired by
 * <a>https://longwelwind.net/2017/02/09/perlin-noise.html</a>
 */
public class NoiseGenerator {

    private static final double NORMALISATION_VALUE = Math.sqrt(2);

    //The random given, allows for seeding
    private Random random;
    /**
     * Width - The width of the generation
     * Height - The height of the generation
     * octaves - The number of octaves generated and then combined, the higher the value the more
     * sporatic the noise values will be
     */
    private int width, height, octaves;
    /**
     * startPeriod - The initial period, determines how long it takes to transition from one value to another
     * attenuation - Determines how important the octaves are, the higher this value the more sporatic the generated
     * values will be
     */
    private double startPeriod, attenuation;
    /** Stores the gradient vectors for the octaves **/
    private ArrayList<double[][][]> gradientVectorSets;


    /**
     * The Constructor used to create a perlin noise generator
     * @param random The random number generator, allows for seeding
     * @param width The width of the set of perlin values generated
     * @param height The height of the set of perlin values generated
     * @param octaves The number of sets of perlin values that will be generated and than combined. The higher the value
     *                The more small details and anomlies in the world there will and there more perceived realism
     *                world will have, as it will have small bits of chaos
     * @param startPeriod The intial period, the higher the value the longer it will take to go from one value to another
     * @param attenuation The weight of the octaves, the higher the value the more sporatic the land will be when there
     *                    are many octaves
     */
    public NoiseGenerator(Random random, int width, int height, int octaves, double startPeriod,
        double attenuation) {
        this.random = random;
        this.width = width;
        this.height = height;
        if (octaves < 1){
            throw new IllegalArgumentException("The octaves must be greater than 1");
        }
        this.octaves = octaves;
        this.startPeriod = startPeriod;
        this.attenuation = attenuation;
        this.gradientVectorSets = new ArrayList<>();
        precomputeGradientVectors();
    }


    /**
     * Computes the gradient vectors for each octave
     */
    public void precomputeGradientVectors(){
        for (int octave = 0; octave < octaves; octave++){
            double period = startPeriod * Math.pow(0.5, octave);
            gradientVectorSets.add(getGradientVectors(period));
        }
    }


    /**
     * Calculates the dot product
     *
     * @param x1 Value in vector 1
     * @param y1 value in vector 1
     * @param x2 value in vector 2
     * @param y2 value in vector 2
     * @return The dot product of two vectors
     */
    public double dot(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }


    /**
     * The linear interpolation between a and b
     *
     * @param a Value to interpolate between
     * @param b Value to interpolate between
     * @param t A weighting factor
     * @return The interpolated value
     */
    public double lerp(double a, double b, double t) {
        return a * (1 - t) + b * t;
    }

    /**
     * Smoothes a value
     *
     * @param x The value to be smoothed
     * @return The smoothed value
     */
    public static double fade(double x) {
        return 6 * Math.pow(x, 5) - 15 * Math.pow(x, 4) + 10 * Math.pow(x, 3);
    }

    /**
     * Returns a random gradient vector with length 1
     *
     * @return A random gradient vector with x as first value and y as second value
     */
    public double[] randomVector() {
        double angle = random.nextDouble() * 2 * Math.PI;
        double[] gradientVector = new double[2];
        gradientVector[0] = Math.cos(angle);
        gradientVector[1] = Math.sin(angle);
        return gradientVector;
    }


    /**
     * Creates a 2D array of gradient vectors
     * More information about this can be found at
     * <a>https://flafla2.github.io/2014/08/09/perlinnoise.html</a>
     * @param period The period, a higher values results in slower fading of values,
     * @return A grid of gradient vectors
     */
    public double[][][] getGradientVectors(double period){
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

        yRel = fade(yRel);
        xRel = fade(xRel);

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

            return (finalLerp * NORMALISATION_VALUE + 1) / 2;
        } catch (RuntimeException e){
            throw e;
        }
    }


    /**
     * Gets a perlin noise value for a coordinate
     *
     * @param x The row of the coordinate
     * @param y The column of the coordinate
     * @return The noise value for the given position
     */
    public double getOctavedPerlinValue(double x, double y){
        double perlinValue = 0;
        double period = startPeriod;
        double octaveAttenuation = attenuation;
        double attenuationSum = 0;
        for (int octave = 0; octave < octaves; octave++){
            attenuationSum += octaveAttenuation;
            perlinValue += getPerlinValue(x,y,gradientVectorSets.get(octave), period) * octaveAttenuation;
            period *= 0.5;
            octaveAttenuation *= attenuation;
        }
        return perlinValue / attenuationSum;
    }


//    public static double multipleFade(int numOfTimes, double value){
//
//        for (int i = 0; i < numOfTimes; i++){
//
//        }
//    }



}
