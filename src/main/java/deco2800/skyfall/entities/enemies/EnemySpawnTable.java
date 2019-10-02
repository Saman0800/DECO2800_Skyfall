package deco2800.skyfall.entities.enemies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.observers.TimeObserver;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.Chunk;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnemySpawnTable implements TimeObserver {

    /**
     * The radius in the which the enemies may spawn in
     */
    private float spawnRadius;

    /**
     * The maximum number of enemies allowed within the specified radius
     */
    private int maxInRadius;

    /**
     * A frequency (in hours) of how often the EnemySpawnTable should re-spawn
     * enemies.
     */
    private long spawnFrequency;

    /**
     * The world in which the enemies are being spawned into
     */
    private World world;

    /**
     * A map for determining where each enemy is to be spawned. Here the key is the
     * string of the biome name and the values a list of all the classes to be
     * spawned within the corresponding biome.
     */
    private Map<String, List<Class<? extends AbstractEnemy>>> biomeToConstructor;

    /**
     * A reference to the environment manager to make queries to.
     */
    EnvironmentManager environManager;

    /**
     * A function that dictates how likely it is to spawn an enemy based on various
     * environment manager parameters
     */
    Function<EnvironmentManager, Double> probAdjFunc;

    /**
     * A reference to a main character for easy queries.
     */
    MainCharacter mainCharacter = MainCharacter.getInstance(0, 0, 0.05f, "Main Piece", 10);

    public EnemySpawnTable(int spawnRadius, int maxInRadius, int frequency,
            Map<String, List<Class<? extends AbstractEnemy>>> biomeToConstructor,
            Function<EnvironmentManager, Double> probAdjFunc) {

        this(spawnRadius, maxInRadius, frequency, biomeToConstructor, probAdjFunc, GameManager.get().getWorld());
    }

    public EnemySpawnTable(int spawnRadius, int maxInRadius, int frequency,
            Map<String, List<Class<? extends AbstractEnemy>>> biomeToConstructor,
            Function<EnvironmentManager, Double> probAdjFunc, World world) {

        this.spawnRadius = spawnRadius;
        this.maxInRadius = maxInRadius;
        this.world = world;
        this.environManager = GameManager.get().getManager(EnvironmentManager.class);
        this.biomeToConstructor = biomeToConstructor;
        this.probAdjFunc = probAdjFunc;
    }

    /**
     * @return Gets all the enemies from within the world.
     */
    public List<AbstractEnemy> getAllAbstractEnemies() {

        return world.getSortedAgentEntities().stream().filter(AbstractEnemy.class::isInstance)
                .map(AbstractEnemy.class::cast).collect(Collectors.toList());

    }

    /**
     * Determines if an entity is within range of a target tuple (x,y).
     * 
     * @param entity The entity that we would like to check.
     * @param x      The x position of the centre of the target.
     * @param y      The y position of the centre of the target.
     * @param radius The radius of the target.
     * 
     * @return true if the entity is in range of the target. false otherwise.
     */
    public boolean inRange(AbstractEntity entity, float x, float y, float radius) {

        return Math.pow(entity.getRow() - x, 2) + Math.pow(entity.getCol() - y, 2) < radius * radius;

    }

    /**
     * Gets all the abract enemies in the world that are within range of a target
     * tuple (x,y).
     * 
     * @param x      The x position of the centre of the target.
     * @param y      The y position of the centre of the target.
     * @param radius The radius of the target.
     * 
     * @return A list of all the enemies within range of the target.
     */
    public List<AbstractEnemy> enemiesInTarget(float x, float y, float radius) {

        return getAllAbstractEnemies().stream().filter(enemy -> inRange(enemy, x, y, radius))
                .collect(Collectors.toList());

    }

    /**
     * Returns a list of all the enemies within range of the main character.
     */
    public List<AbstractEnemy> enemiesNearCharacter() {

        return enemiesInTarget(mainCharacter.getRow(), mainCharacter.getCol(), spawnRadius);

    }

    /**
     * Returns how many enemies are with close proximity of another enemy.
     * 
     * @param targetEnemy The enemy that we are making the count for.
     */
    public int enemiesNearTargetCount(float x, float y) {

        return enemiesInTarget(x, y, 50).size();

    }

    /**
     * Check to see if it is time to start spawn more enemies
     */
    public void notifyTimeUpdate(long i) {

        if ((i % spawnFrequency) == 0) {
            spawnEnemies();
        }

        return;
    }

    private void spawnEnemies() {

        // Find how many enemies are within range of the maincharacter
        int numberToSpawn = maxInRadius - enemiesNearCharacter().size();
        if (numberToSpawn <= 0) {
            // There are already enough enemies within range
            return;
        }

        // Get all the tiles within the current chunk
        List<Tile> chunkTiles = new ArrayList<>();

        for (Chunk chunk : world.getLoadedChunks().values()) {
            for (Tile tile : chunk.getTiles()) {
                chunkTiles.add(tile);
            }
        }

        // Shuffle the tile list
        Collections.shuffle(chunkTiles);

        Iterator<Tile> tileIter = chunkTiles.iterator();

        int enemiesPlaced = 0;
        Tile nextTile = null;
        Random rand = new Random();

        while (tileIter.hasNext() && (enemiesPlaced <= numberToSpawn)) {

            nextTile = tileIter.next();

            if (nextTile.isObstructed()) {
                continue;
            }

            // Check if the tile is in sight of the player
            float[] tileWorldCord = WorldUtil.colRowToWorldCords(nextTile.getCol(), nextTile.getRow());

            if (!WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], GameManager.get().getCamera())) {
                continue;
            }

            // Get the chance to spawn the enemy using the provided lambda function
            double spawnChance = probAdjFunc.apply(environManager);

            // Find all the enemies within close proximity to this tile and adjust the
            // spawning chance
            spawnChance = Math.pow(spawnChance, Math.log(enemiesNearTargetCount(nextTile.getRow(), nextTile.getCol())));

            // Create an enemy using on of the appropriate constructors
            List<Class<? extends AbstractEnemy>> possibleConstructors = biomeToConstructor
                    .get(nextTile.getBiome().getBiomeName());

            if (possibleConstructors.size() == 0) {
                // There are no suitable enemies to spawn on this tile
                continue;
            }

            // Pick a class, any class!
            Class<? extends AbstractEnemy> randEnemyType = possibleConstructors
                    .get(rand.nextInt(possibleConstructors.size()));

            AbstractEnemy newEnemy;

            try {
                newEnemy = randEnemyType.getDeclaredConstructor(Float.class, Float.class).newInstance(nextTile.getRow(),
                        nextTile.getCol());
                world.addEntity(newEnemy);
                enemiesPlaced += 1;
            } catch (Exception E) {
                System.err.println("Could not create new AbstractEnemy: " + E.getMessage());
            }
        }
    }
}