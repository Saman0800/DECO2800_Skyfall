package deco2800.skyfall.worlds;

import java.util.ArrayList;
import java.util.Random;

/**
 * Perlin noise generation for landscapes
 * Algorithm done by https://longwelwind.net/2017/02/09/perlin-noise.html
 */
public class PerlinNoiseGenerator {

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
     * The linear interpolation between two values
     * @param a
     * @param b
     * @param t
     * @return
     */
    public double lerp(double a, double b, double t){
        return a * (1-t) + b * t;
    }

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

    public void setPerlinValues(ArrayList<Tile> tiles, double period){


        if (tiles.size() > 0){

            double lowestCol = tiles.get(0).getCol();
            double lowestRow = tiles.get(0).getRow();
            double highestCol = tiles.get(0).getCol();
            double highestRow = tiles.get(0).getRow();
            for (Tile tile : tiles){
                lowestCol = (tile.getCol() < lowestCol) ? tile.getCol() : lowestCol;
                lowestRow = (tile.getRow() < lowestRow) ? tile.getRow() : lowestRow;
                highestCol = (tile.getCol() > highestCol) ? tile.getCol() : highestCol;
                highestRow = (tile.getRow() > highestRow) ? tile.getRow() : highestRow;
            }

            double[][][] gradientVectors = getGradientVectors((int) Math.ceil(highestRow - lowestRow)
                , (int) Math.ceil(highestCol - lowestCol), period);
            for (Tile tile : tiles){
                tile.setPerlinValue((getPerlinValue((tile.getRow() - lowestRow) , (tile.getCol() - lowestCol), gradientVectors,period)+
                    getPerlinValue((tile.getRow() - lowestRow + period/2) , (tile.getCol() - lowestCol + period/2), gradientVectors,period))/2);
            }


            double minPerlinValue = tiles.get(0).getPerlinValue();
            double maxPerlinValue = tiles.get(0).getPerlinValue();
            for (Tile tile : tiles){
                minPerlinValue = Math.min(tile.getPerlinValue(), minPerlinValue);
                maxPerlinValue = Math.max(tile.getPerlinValue(), maxPerlinValue);
            }
            System.out.println(maxPerlinValue);
            for (Tile tile : tiles){
                tile.setPerlinValue(tile.getPerlinValue() - minPerlinValue);
                tile.setPerlinValue(Math.floor(tile.getPerlinValue()/(maxPerlinValue-minPerlinValue)*3));
            }

        }
        //TODO handle cast with not enough itles

    }


//    public ArrayList[][] getOctavedPerlinNoiseGrid(int width, int height, double startPeriod, int octaves, double attenuation){
//        ArrayList[][] finalGrid = new ArrayList[height][width];
//
//    }

}
