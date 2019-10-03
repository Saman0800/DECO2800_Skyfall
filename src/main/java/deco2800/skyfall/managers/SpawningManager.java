package deco2800.skyfall.managers;

import deco2800.skyfall.graphics.types.vec2;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.enemies.Spawnable;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

/*Handles spawning enemies into the game on tick*/
public class SpawningManager extends TickableManager  {
    //After a single construction, this will be set to true for error checking
    private static boolean constructed = false;
    //As a singleton, able to keep a single reference
    private static SpawningManager reference = null;

    //the random used for enemy generation
    private Random random;

    //A map of enemy strength to enemy
    //enemy must implement Spawnable
    //float is the k value used for spawning
    private Map<Spawnable, Float> spawnTable;

    //All enemies that have spawned will be kept with a referece
    //kept by spawn order
    protected Vector<Enemy> enemyReferences;

    public static SpawningManager getGlobalSpawningManager() {
        return reference;
    }

    //Useful for controlling enemy counts
    final int MAXIMUM_ENEMIES = 100;

    //Enemies spawn in a circle around the player
    private final float SPAWN_DISTANCE = 100;

    /**
     * Use createdSpawningManager instead of constructor
     */
    private SpawningManager() {
        random = new Random();
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
     * Spawns the enemy a given distance from the player
     * @param enemy A reference to Spawnable
     */
    private void spawnEnemy(Spawnable enemy) {
        vec2 location = ((Enemy)enemy).getPlayerLocation();

        //calculate the location of the player
        double angle = random.nextDouble() * 2.0f * Math.PI;
        location = new vec2(
                location.x + SPAWN_DISTANCE*(float)Math.cos(angle),
                location.y + SPAWN_DISTANCE*(float)Math.sin(angle)
        );

        enemyReferences.add((Enemy) enemy.newInstance(location.x, location.y));
    }

    /**
     * Will look at the internal spawn table to determine
     * if its time to spawn
     * @param i standard tick time
     */
    @Override
    public void onTick(long i) {
        //return if day
        if (GameManager.getManagerFromInstance(EnvironmentManager.class).isDay()) {
            return;
        }

        Iterator it = spawnTable.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            if (random.nextFloat() < (Float)pair.getValue()) {
                spawnEnemy((Spawnable)pair.getKey());
            }

            it.remove();
        }
    }

    /**
     * Adds an enemy to be spawned with the spawn manager, also acts as a gateway to enforce
     * correct enemy types are added
     * @param template The enemy that wises to be spawned, has a complex class requirement <T>
     * @param k Enemy spawning peaks at midnight, this is the horrizontal scaling value
     * @param <T> The Enemy must be an Enemy, and implement Spawnable
     */
     public <T extends Enemy & Spawnable>
     void addEnemyForSpawning(T template, float k) {
         spawnTable.put(template, k);
     }
}
