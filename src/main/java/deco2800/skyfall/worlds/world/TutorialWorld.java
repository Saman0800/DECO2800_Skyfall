package deco2800.skyfall.worlds.world;

import deco2800.skyfall.observers.TouchDownObserver;


/**
 * This is a tutorial world created to help the player understand the world and
 * the mechanics of the game.
 */
public class TutorialWorld extends World implements TouchDownObserver {
    /**
     * Constructs a tutorial world using the Rocket World constructor
     * 
     * @param worldParameters Class that contains all the world parameters
     */
    public TutorialWorld(WorldParameters worldParameters) {
        super(worldParameters);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
    }
}
