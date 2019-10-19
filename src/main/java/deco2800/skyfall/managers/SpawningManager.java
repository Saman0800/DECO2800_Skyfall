package deco2800.skyfall.managers;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.enemies.Spawnable;
import deco2800.skyfall.graphics.types.Vec2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*Handles spawning enemies into the game on tick*/
public class SpawningManager extends TickableManager {
    // After a single construction, this will be set to true for error checking
    private static boolean constructed = false;
    // As a singleton, able to keep a single reference
    private static SpawningManager reference = null;

    // A maximum to the number of enemies allowed to be managed
    private static final int MAXENTITIES = 5;

    // the random used for enemy generation
    private Random random;

    // A map of enemy strength to enemy
    // enemy must implement Spawnable
    // float is the k value used for spawning
    private Map<Spawnable, Float> spawnTable;

    // All enemies that have spawned will be kept with a referece
    // kept by spawn order
    protected List<Enemy> enemyReferences;

    public static SpawningManager getGlobalSpawningManager() {
        return reference;
    }

    // Useful for controlling enemy counts
    static final int MAXIMUM_ENEMIES = 10;

    // Enemies spawn in a circle around the player
    private static final float SPAWN_DISTANCE = 100;

    // Enemies will be culled if they get too far
    private static final float CULL_DISTANCE = 1000;

    /**
     * Use createdSpawningManager instead of constructor
     */
    private SpawningManager() {
        random = new Random();
        spawnTable = new HashMap<>();
        enemyReferences = new ArrayList<>();
    }

    /**
     * Allows SpawningManager to be created and attached to GameManager with no
     * local instance
     *
     * @throws ExceptionInInitializerError if another SpawningManager already
     *                                     existed
     */
    public static void createSpawningManager() throws ExceptionInInitializerError {
        if (constructed) {
            throw new ExceptionInInitializerError("Only one SpawningManager should exist");
        }

        SpawningManager local = new SpawningManager();

        // set singleton patterns
        constructed = true;
        reference = local;
        // add to game manager
        GameManager.addManagerToInstance(local);

    }

    /**
     * Spawns the enemy a given distance from the player
     * 
     * @param enemy A reference to Spawnable
     */
    private void spawnEnemy(Spawnable enemy) {
        if (MAXENTITIES <= enemyReferences.size()) {
            return;
        }

        // calculate the location of the player
        MainCharacter mc = MainCharacter.getInstance();
        Vec2 location = new Vec2(mc.getRow(), mc.getCol());

        double angle = random.nextDouble() * 2.0f * Math.PI;
        location = new Vec2(location.getX() + SPAWN_DISTANCE * (float) Math.cos(angle),
                location.getY() + SPAWN_DISTANCE * (float) Math.sin(angle));

        // create new instance
        Enemy instance = enemy.newInstance(location.getX(), location.getY());
        instance.setMainCharacter(MainCharacter.getInstance());
        // add to references
        enemyReferences.add(instance);
        // add to game world
        GameManager.get().getWorld().addEntity(instance);
    }

    /**
     * clears the list of old references to dead enemies
     */
    void updateReferences() {
        enemyReferences.removeIf(Peon::isDead);
        enemyReferences.removeIf(s -> s.distance(MainCharacter.getInstance()) > CULL_DISTANCE);
    }

    /**
     * Returns an enemy of the given type If no such enemy can be found, returns
     * null
     * 
     * @param type The class type to pull
     * @param <T>  The class must inherit Enemy
     * @return returns the first enemy found, or null
     */
    public <T extends Enemy> T getFirstEnemy(Class<T> type) {
        for (Enemy e : enemyReferences) {
            if (type.isInstance(e)) {
                return (T) e;
            }
        }
        return null;
    }

    /**
     * Will look at the internal spawn table to determine if its time to spawn
     * 
     * @param i standard tick time
     */
    @Override
    public void onTick(long i) {
        // return if day
        if (GameManager.getManagerFromInstance(EnvironmentManager.class).isDay()) {
            return;
        }

        updateReferences();

        for (Map.Entry<Spawnable, Float> entry : spawnTable.entrySet()) {
            if (random.nextFloat() < (Float) entry.getValue()) {
                spawnEnemy((Spawnable) entry.getKey());
            }
        }
    }

    /**
     * Adds an enemy to be spawned with the spawn manager, also acts as a gateway to
     * enforce correct enemy types are added
     * 
     * @param template The enemy that wises to be spawned, has a complex class
     *                 requirement <T>
     * @param k        Enemy spawning peaks at midnight, this is the horrizontal
     *                 scaling value
     * @param <T>      The Enemy must be an Enemy, and implement Spawnable
     */
    public <T extends Enemy & Spawnable> void addEnemyForSpawning(T template, float k) {
        spawnTable.put(template, k);
    }

    /**
     * Specifically, enemies spawned by this manager
     * 
     * @return number of enemy instances managed
     */
    public int getNumberOfEntsManaged() {
        return enemyReferences.size();
    }

    /**
     * Counts templates only
     * 
     * @return size of spawn table
     */
    public int getEntCountInSpawnTable() {
        return spawnTable.size();
    }

    /**
     * Deletes all templates
     */
    public void clearSpawnTable() {
        spawnTable.clear();
    }

    /**
     * @return gets the current culling distance
     */
    public float getCullingDistance() {
        return CULL_DISTANCE;
    }

}
