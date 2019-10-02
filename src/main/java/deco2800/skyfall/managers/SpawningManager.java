package deco2800.skyfall.managers;

import deco2800.skyfall.entities.enemies.AbstractEnemy;
import deco2800.skyfall.entities.enemies.Spawnable;
import javafx.util.Pair;

import java.util.Map;
import java.util.Vector;

/*Handles spawning enemies into the game on tick*/
public class SpawningManager extends TickableManager  {
    //After a single construction, this will be set to true for error checking
    private static boolean constructed = false;
    //As a singleton, able to keep a single reference
    private static SpawningManager reference = null;

    //A map of enemy strength to enemy
    //enemy must implement Spawnable
    //float is the (k, a) value used for spawning
    Map<Spawnable, Pair<Float, Float>> spawnTable;

    //All enemies that have spawned will be kept with a referece
    //kept by spawn order
    Vector<AbstractEnemy> enemyReferences;

    public static SpawningManager getGlobalSpawningManager() {
        return reference;
    }

    //Useful for controlling enemy counts
    final int MAXIMUM_ENEMIES = 100;

    //Enemies spawn in a circle around the player
    final float SPAWN_DISTANCE = 100;

    /**
     * Use createdSpawningManager instead of constructor
     */
    private SpawningManager() {
    }

    /** Allows SpawningManager to be created and attached to GameManager with
     * no local instance
     *
     * @throws ExceptionInInitializerError if another SpawningManager already existed
     */
    public static void createSpawningManager() throws ExceptionInInitializerError {
        if (constructed) {
            throw new ExceptionInInitializerError("Only one SpawningManager should exist");
        }

        SpawningManager local = new SpawningManager();

        //set singleton patterns
        constructed = true;
        reference = local;
        //add to game manager
        GameManager.addManagerToInstance(local);
    }

    /**
     * Will look at the internal spawn table to determine
     * if its time to spawn
     * @param i standard tick time
     */
    @Override
    public void onTick(long i) {
    }

    /**
     * Adds an enemy to be spawned with the spawn manager, also acts as a gateway to enforce
     * correct enemy types are added
     * @param template The enemy that wises to be spawned, has a complex class requirement <T>
     * @param k Enemy spawning peaks at midnight, this is the horrizontal scaling value
     * @param a A scaling value which defines the maximum probability an enemy spawns this tick
     * @param <T> The Enemy must be an AbstractEnemy, and implement Spawnable
     */
     public <T extends AbstractEnemy & Spawnable>
     void addEnemyForSpawning(T template, float k, float a) {
         spawnTable.put(template, new Pair<>(k ,a));
     }
}
