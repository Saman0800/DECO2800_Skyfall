package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.world.World;
import java.io.IOException;

/**
 * Creates the example world for testing, used because the algorithm has a lot of slight changes.
 * This should only be run when a change in the world gen algorithm has been made and also only after checking the
 * actual world by playing the game.
 */
public class GenerateExampleWorld {
    public static void main(String[] args){

//        AbstractWorld world = new RocketWorld(0, 10, 1, new int[] {20,10,10}, 3,2);
//        AbstractWorld world = new RocketWorld(0, 30, 5, new int[] {20,10,10}, 2,5);
//        AbstractWorld world = new RocketWorld(0, 30, 5, new int[] {10,10,10,10,10}, 2,5);
        World world = new RocketWorld(0, 30, 5, new int[] {5,5,5,5,5}, 2,new int[] {5,5});
        try {
            world.saveWorld("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt");
        } catch (IOException e){
    	    System.out.println("Could not save world");
        }
    }
}
