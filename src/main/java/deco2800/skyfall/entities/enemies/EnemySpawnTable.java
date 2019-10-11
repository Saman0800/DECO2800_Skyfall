package deco2800.skyfall.entities.enemies;

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
    private Map<String, List<Class<? extends Enemy>>> biomeToConstructor;

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
            Map<String, List<Class<? extends Enemy>>> biomeToConstructor,
            Function<EnvironmentManager, Double> probAdjFunc,
            EnvironmentManager gameEnvironManag) {
        this(spawnRadius, maxInRadius, frequency, biomeToConstructor, probAdjFunc, GameManager.get().getWorld(),
             gameEnvironManag);
    }

    public EnemySpawnTable(int spawnRadius, int maxInRadius, int frequency,
            Map<String, List<Class<? extends Enemy>>> biomeToConstructor,
            Function<EnvironmentManager, Double> probAdjFunc, World world,
            EnvironmentManager gameEnvironManag) {
        this.spawnRadius = spawnRadius;
        this.maxInRadius = maxInRadius;
        this.spawnFrequency = frequency;
        this.world = world;
        this.environManager = gameEnvironManag;
        this.biomeToConstructor = biomeToConstructor;
        this.probAdjFunc = probAdjFunc;
    }

    public EnemySpawnTable(int spawnRadius, int maxInRadius, int frequency,
                           Map<String, List<Class<? extends Enemy>>> biomeToConstructor,
                           Function<EnvironmentManager, Double> probAdjFunc,
                           World world) {

        this(spawnRadius, maxInRadius, frequency, biomeToConstructor, probAdjFunc, world,
             GameManager.get().getManager(EnvironmentManager.class));
    }

    public EnemySpawnTable(int spawnRadius, int maxInRadius, int frequency,
                           Map<String, List<Class<? extends Enemy>>> biomeToConstructor,
                           Function<EnvironmentManager, Double> probAdjFunc) {

        this(spawnRadius, maxInRadius, frequency, biomeToConstructor, probAdjFunc, GameManager.get().getWorld());
    }

    /**
     * @return Gets all the enemies from within the world.
     */
    public List<Enemy> getAllEnemies() {

        return world.getSortedAgentEntities().stream().filter(Enemy.class::isInstance)
                .map(Enemy.class::cast).collect(Collectors.toList());

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
    public List<Enemy> enemiesInTarget(float x, float y, float radius) {

        return getAllEnemies().stream().filter(enemy -> inRange(enemy, x, y, radius))
                .collect(Collectors.toList());

    }

    /**
     * Returns a list of all the enemies within range of the main character.
     */
    public List<Enemy> enemiesNearCharacter() {

        return enemiesInTarget(mainCharacter.getRow(), mainCharacter.getCol(), spawnRadius);

    }

    /**
     * Returns how many enemies are with close proximity of another enemy.
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
    }

    /**
     * Separates tiles into different lists based on the biome the tile was from.
     */
    public static Map<String, List<Tile>> partitonTiles(World gameWorld) {

        // Create the new hashmap that will contain all the tiles
        Map<String, List<Tile>> partitionedTiles = new HashMap<>();

        for (Chunk chunk : gameWorld.getLoadedChunks().values()) {
            for (Tile tile : chunk.getTiles()) {
                // Try to get the list that curresponds to the current tile's
                // biome string
                List<Tile> tileBiomeList = partitionedTiles.get(tile.getBiome().getBiomeName());

                if (tileBiomeList == null) {
                    // A list for this biome does not exist yet
                    List<Tile> newBiomeList = new ArrayList<>();
                    newBiomeList.add(tile);
                    partitionedTiles.put(tile.getBiome().getBiomeName(), newBiomeList);
                    continue;
                }

                tileBiomeList.add(tile);
            }
        }

        return partitionedTiles;
    }

    /**
     * Spawns the enemies into the world under the conditions specified by input
     * parameters.
     */
    private void spawnEnemies() {

        // Find how many enemies are within range of the maincharacter
        int numberToSpawn = maxInRadius - enemiesNearCharacter().size();
        if (numberToSpawn <= 0) {
            // There are already enough enemies within range
            return;
        }

        Map<String, List<Tile>> partitionedTiles = partitonTiles(world);

        for (String biomeName : biomeToConstructor.keySet()) {

            // Get all the tiles within the current chunk
            List<Tile> chunkTiles = partitionedTiles.get(biomeName);

            if (chunkTiles == null || chunkTiles.isEmpty()) {
                continue;
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

                // if (!WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1],
                //         GameManager.get().getCamera())) {
                //     continue;
                // }

                // Create an enemy using one of the appropriate constructors
                List<Class<? extends Enemy>> possibleConstructors = biomeToConstructor
                        .get(nextTile.getBiome().getBiomeName());

                if ((possibleConstructors == null) || (possibleConstructors.isEmpty())) {
                    // There are no suitable enemies to spawn on this tile
                    continue;
                }

                // Get the chance to spawn the enemy using the provided lambda function
                double spawnChance = probAdjFunc.apply(environManager);

                // Find all the enemies within close proximity to this tile and adjust the
                // spawning chance accordingly
                spawnChance = Math.pow(spawnChance,
                        Math.log(enemiesNearTargetCount(nextTile.getRow(), nextTile.getCol())));

                // Pick a class, any class!
                Class<? extends Enemy> randEnemyType = possibleConstructors
                        .get(rand.nextInt(possibleConstructors.size()));

                Enemy newEnemy;

                try {
                    newEnemy = randEnemyType.getDeclaredConstructor(Float.class, Float.class).newInstance(nextTile.getRow(),
                            nextTile.getCol());
                    world.addEntity(newEnemy);
                    enemiesPlaced += 1;
                } catch (Exception e) {
                    System.err.println("Could not create new AbstractEnemy: " + e.toString());
                }
            }
        }
    }
}