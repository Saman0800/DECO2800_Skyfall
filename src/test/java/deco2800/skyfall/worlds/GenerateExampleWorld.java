package deco2800.skyfall.worlds;

import java.io.IOException;

/**
 * Creates the example world for testing, use because the algorithm has a lot of slight changes
 * Should only be run when the algorithm has been changed to generate a new world
 * The world should be first checked in the actual game to see if it looks correct
 */
public class GenerateExampleWorld {
    public static void main(String[] args){

        AbstractWorld world = new RocketWorld(0, 10, 1);

    	try {
            world.saveWorld("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt");
        } catch (IOException e){
    	    System.out.println("Could not save world");
        }
    }
}
