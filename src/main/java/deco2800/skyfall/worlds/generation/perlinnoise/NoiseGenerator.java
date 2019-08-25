package deco2800.skyfall.worlds.generation.perlinnoise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Used to allow for generation of perlin noise value for coordinates
 * Implementation inspired by
 * <a>https://longwelwind.net/2017/02/09/perlin-noise.html</a>
 */
public class NoiseGenerator {

    private static final double NORMALISATION_VALUE = 1;

    //The random given, allows for seeding
    private Random random;
    /**
     * octaves - The number of octaves generated and then combined, the higher the value the more
     * sporatic the noise values will be
     */
    private int  octaves;
    /**
     * startPeriod - The initial period, determines how long it takes to transition from one value to another
     * attenuation - Determines how important the octaves are, the higher this value the more sporatic the generated
     * values will be
     */
    private double startPeriod, attenuation;
    /** Stores the gradient vectors for the octaves **/

    //TODO randomise the order of this each time
    private int[] permutation = new int[512];



    /**
     * The Constructor used to create a perlin noise generator
     * @param random The random number generator, allows for seeding
     * @param octaves The number of sets of perlin values that will be generated and than combined. The higher the value
     *                The more small details and anomlies in the world there will and there more perceived realism
     *                world will have, as it will have small bits of chaos
     * @param startPeriod The intial period, the higher the value the longer it will take to go from one value to another
     * @param attenuation The weight of the octaves, the higher the value the more sporatic the land will be when there
     *                    are many octaves
     */
    public NoiseGenerator(Random random,   int octaves, double startPeriod, double attenuation) {
        this.random = random;
        if (octaves < 1){
            throw new IllegalArgumentException("The number of octaves must be greater than 0");
        }
        if (attenuation <= 0){
            throw new IllegalArgumentException("Attenuation must be greater than 0");
        }
        if (startPeriod <= 0){
            throw new IllegalArgumentException("The startPeriod must be greater than 0");
        }

        //Adding 0 to 15 to the permutation array
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        //Shuffling the permutation array
        for (int i = 0; i < permutation.length/2; i++){
            int rand = random.nextInt(permutation.length/2 - i);
            int temp = permutation[i];
            permutation[i] = permutation[i + rand];
            permutation[i + rand] = temp;
        }

        //Doubling the array
        System.arraycopy(permutation, 0, permutation, permutation.length/2, permutation.length/2);


        this.octaves = octaves;
        this.startPeriod = startPeriod;
        this.attenuation = attenuation;

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
        // return 6 * Math.pow(x, 5) - 15 * Math.pow(x, 4) + 10 * Math.pow(x, 3);
        return x * x * x * (x * (x * 6 - 15) + 10);
    }


    /**
     * Gets a perlin value at a certain point
     * @param x The x value
     * @param y The y value
     * @param period The period that determines how long it takes to fade between values,
     *               a higher period is a longer fade
     * @return A noise value at the certain point
     */
    public double getPerlinValue(double x, double y,  double period){
        int xInt = (int) Math.floor(x/period) & 255;
        double xRel = x/period - Math.floor(x/period);

        int yInt = (int) Math.floor(y/period) & 255;
        double yRel = y/period - Math.floor(y/period);

        yRel = fade(yRel);
        xRel = fade(xRel);

        try {
            int xy = permutation[permutation[xInt] + yInt];
            int xpy = permutation[permutation[xInt+1] + yInt];
            int xyp = permutation[permutation[xInt] + yInt + 1];
            int xpyp = permutation[permutation[xInt+1] + yInt+1];

            double xyGrad = determineGradientVector(xRel, yRel, xy);
            double xpyGrad = determineGradientVector(1 - xRel, yRel, xpy);
            double xypGrad = determineGradientVector(xRel, 1 - yRel, xyp);
            double xpypGrad = determineGradientVector(1 - xRel, 1 - yRel, xpyp);

            double topLerp = lerp(xyGrad, xpyGrad, xRel);
            double bottomLerp = lerp(xypGrad, xpypGrad, xRel);

            double finalLerp = lerp(topLerp, bottomLerp, yRel);

            return (finalLerp * NORMALISATION_VALUE + 1) / 2;
        } catch (RuntimeException e){
            throw e;
        }
    }

    private double determineGradientVector(double x, double y, int hash) {
        return (((1 & hash) != 0) ? x : -x) + (((2 & hash) != 0) ? y : -y);
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
            perlinValue += getPerlinValue(x,y, period) * octaveAttenuation;
            period *= 0.5;
            octaveAttenuation *= attenuation;
        }
        return perlinValue / attenuationSum;
    }

}
