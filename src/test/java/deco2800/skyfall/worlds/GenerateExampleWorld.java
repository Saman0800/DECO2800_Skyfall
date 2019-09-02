package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.io.IOException;

/**
 * Creates the example world for testing, used because the algorithm has a lot of slight changes.
 * This should only be run when a change in the world gen algorithm has been made and also only after checking the
 * actual world by playing the game.
 */
public class GenerateExampleWorld {
    public static void main(String[] args){

        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        worldBuilder.setType("single_player");
        worldBuilder.setStaticEntities(false);
        World world = worldBuilder.getWorld();

        try {
            world.saveWorld("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt");
        } catch (IOException e){
    	    System.out.println("Could not save world");
        }
    }
}
