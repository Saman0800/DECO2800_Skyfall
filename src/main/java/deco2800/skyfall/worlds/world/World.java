package deco2800.skyfall.worlds.world;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.SaveLoadInterface;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.BeachBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.generation.BiomeGenerator;
import deco2800.skyfall.worlds.generation.DeadEndGenerationException;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.WorldGenException;
import deco2800.skyfall.worlds.generation.delaunay.NotEnoughPointsException;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import org.javatuples.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level
 * items.
 */
public class World implements TouchDownObserver , Serializable, SaveLoadInterface {
    protected int width;
    protected int length;

    // TODO:Ontonator Reconsider whether this should exist.
    //Used to generate random numbers
    protected Random random;

    public Map<String, Float> frictionMap;

    // TODO:Ontonator Does it matter that this is not `CopyOnWrite` like the tiles and enities used to be?
    protected HashMap<Pair<Integer, Integer>, Chunk> loadedChunks;

    //A list of all the tiles within a world
    protected CopyOnWriteArrayList<WorldGenNode> worldGenNodes;
    protected CopyOnWriteArrayList<VoronoiEdge> voronoiEdges;

    // The noise generators used when adding offset during the biome assignment.
    protected NoiseGenerator tileOffsetNoiseGeneratorX;
    protected NoiseGenerator tileOffsetNoiseGeneratorY;

    protected LinkedHashMap<VoronoiEdge, RiverBiome> riverEdges;
    protected LinkedHashMap<VoronoiEdge, BeachBiome> beachEdges;

    // TODO:Ontonator Reconsider this for chunks.
    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    protected WorldParameters worldParameters;

    /**
     * The constructor for a world
     * @param worldParameters A class that contains the world parameters
     */
    public World(WorldParameters worldParameters){
        // TODO:Ontonator Consider whether `worldSize` must be a multiple of `CHUNK_SIDE_LENGTH`.

        this.worldParameters = worldParameters;

        random = new Random(worldParameters.getSeed());

        riverEdges = new LinkedHashMap<>();
        beachEdges = new LinkedHashMap<>();
        worldGenNodes = new CopyOnWriteArrayList<>();
    	voronoiEdges = new CopyOnWriteArrayList<>();

    	// FIXME:Ontonator Sort this out.
        // tileOffsetNoiseGeneratorX = WorldGenNode.getOffsetNoiseGenerator(random, worldParameters.getNodeSpacing());
        // tileOffsetNoiseGeneratorY = WorldGenNode.getOffsetNoiseGenerator(random, worldParameters.getNodeSpacing());

        loadedChunks = new HashMap<>();

        generateWorld();
        generateTileIndices();
        initialiseFrictionmap();
    }

    /**
     * Generates the tiles and biomes in the world and adds the world to a listener to allow for interaction.
     * Continuously repeats generation until it reaches a stable world
     */
    protected void generateWorld() {
        while (true) {
            try {
                generateTiles();
                break;
            } catch (WorldGenException | DeadEndGenerationException | NotEnoughPointsException ignored) {}
        }

        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }

    /**
     * Generates the tiles and biomes in a world
     * @throws NotEnoughPointsException When there are not enough points
     * @throws DeadEndGenerationException
     * @throws WorldGenException
     */
    // FIXME:Ontonator Make this work with chunks.
    private void generateTiles() throws NotEnoughPointsException, DeadEndGenerationException, WorldGenException {
        ArrayList<WorldGenNode> worldGenNodes = new ArrayList<>();
        /*HashMap<Pair<Integer, Integer>, Chunk>*/ loadedChunks = new HashMap<>();
        ArrayList<VoronoiEdge> voronoiEdges = new ArrayList<>();

        // TODO:Ontonator Remove this.
        for (Tile tile : getTileMap()) {
            assert false : "Should never get here.";
            // tile.setBiome(null);
        }

        for (AbstractBiome biome : worldParameters.getBiomes()) {
            biome.getTiles().clear();
        }

        int worldSize = worldParameters.getWorldSize();
        int nodeSpacing = worldParameters.getNodeSpacing();
        int nodeCount = Math.round((float) worldSize * worldSize * 4 / nodeSpacing / nodeSpacing);
        // TODO: if nodeCount is less than the number of biomes, throw an exception

        for (int i = 0; i < nodeCount; i++) {
            // Sets coordinates to a random number from -WORLD_SIZE to WORLD_SIZE
            float x = (float) (random.nextFloat() - 0.5) * 2 * worldSize;
            float y = (float) (random.nextFloat() - 0.5) * 2 * worldSize;
            worldGenNodes.add(new WorldGenNode(x, y));
        }

        // Apply Delaunay triangulation to the nodes, so that vertices and
        // adjacencies can be calculated. Also apply Lloyd Relaxation twice
        // for more smooth looking polygons
        try {
            WorldGenNode.calculateVertices(worldGenNodes, worldSize);
            WorldGenNode.lloydRelaxation(worldGenNodes, 2, worldSize);
        } catch (WorldGenException e) {
            throw e;
        }
        this.worldGenNodes = new CopyOnWriteArrayList<>(worldGenNodes);

        Tile.setNoiseGenerators(random, nodeSpacing);
        for (int q = -worldSize / Chunk.CHUNK_SIDE_LENGTH; q <= worldSize / Chunk.CHUNK_SIDE_LENGTH; q++) {
            for (int r = -worldSize / Chunk.CHUNK_SIDE_LENGTH; r <= worldSize / Chunk.CHUNK_SIDE_LENGTH; r++) {
                Chunk chunk = new Chunk(this, q, r);
                loadedChunks.put(new Pair<>(q, r), chunk);
            }
        }

        // Neighbours are generated when the tile is loaded.

        // TODO:Ontonator Sort this out.
        worldGenNodes.sort(Comparable::compareTo);

        WorldGenNode.removeZeroTileNodes(worldGenNodes, nodeSpacing, worldSize);
        WorldGenNode.assignNeighbours(worldGenNodes, voronoiEdges);

        // TODO:Ontonator Remove this.
        // VoronoiEdge.assignTiles(voronoiEdges, tiles, worldSize);
        VoronoiEdge.assignNeighbours(voronoiEdges);

        BiomeGenerator biomeGenerator = new BiomeGenerator(this, worldGenNodes, voronoiEdges, random,worldParameters);
        biomeGenerator.generateBiomes();

        for (Chunk chunk : loadedChunks.values()) {
            for (Tile tile : chunk.getTiles()) {
                // TODO:Ontonator Fix the parameters of this.
                tile.assignEdge(this.riverEdges, this.beachEdges, worldParameters.getRiverWidth(),
                                worldParameters.getBeachWidth());
            }
        }

        // this.worldGenNodes.addAll(worldGenNodes);
        //this.loadedChunks = loadedChunks;
    }

    // TODO:Ontonator Consider removing this method.
    private void generateTileIndices() {
        for (Chunk chunk : loadedChunks.values()) {
            for (Tile tile : chunk.getTiles()) {
                tile.calculateIndex();
            }
        }
    }

    /**
     * Returns a list of entities in this world
     *
     * @return All Entities in the world
     */
    public List<AbstractEntity> getEntities() {
        // TODO:Ontonator consider deprecating this method.
        return loadedChunks.values().stream()
                .flatMap(chunk -> chunk.getEntities().stream())
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    /**
     * Creates a friction map that assigns a friction to every tile type
     */
    public void initialiseFrictionmap() {
        frictionMap = new HashMap<>();
        frictionMap.put("grass", 0.8f);
        frictionMap.put("forest", 0.76f);
        frictionMap.put("water", 0.4f);
        frictionMap.put("ocean", 0.4f);
        frictionMap.put("lake", 0.4f);
        frictionMap.put("volcanic", 0.6f);
        frictionMap.put("mountain", 0.67f);
        frictionMap.put("desert", 0.59f);
        frictionMap.put("ice", 1f);
        frictionMap.put("snow", 1f);
    }

    /**
     * Returns a list of entities in this world, ordered by their render level
     *
     * @return all entities in the world
     */
    public List<AbstractEntity> getSortedEntities() {
        // `List.sort` works quite efficiently for partially-sorted lists.
        // TODO:Ontonator Does this need to be a `CopyOnWriteArrayList`?
        ArrayList<AbstractEntity> entities = new ArrayList<>();
        for (Chunk chunk : loadedChunks.values()) {
            entities.addAll(chunk.getEntities());
        }
        entities.sort(null);
        return entities;
    }

    /**
     * Returns a list of entities in this world, ordered by their render level
     *
     * @return all entities in the world
     */
    public List<AgentEntity> getSortedAgentEntities() {
        return getSortedEntities().stream()
                .filter(AgentEntity.class::isInstance)
                .map(AgentEntity.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Adds an entity to the world
     *
     * @param entity the entity to add
     */
    public void addEntity(AbstractEntity entity) {
        Pair<Integer, Integer> chunkCoords = Chunk.getChunkForCoordinates(entity.getCol(), entity.getRow());
        Chunk chunk = getChunk(chunkCoords.getValue0(), chunkCoords.getValue1());
        chunk.addEntity(entity);
    }

    /**
     * Removes an entity from the world
     *
     * @param entity the entity to remove
     */
    public void removeEntity(AbstractEntity entity) {
        for (Chunk chunk : loadedChunks.values()) {
            if (chunk.getEntities().remove(entity)) {
                break;
            }
        }
        // Does not need to be kept sorted because removing an entity will not affect the ordering of the remaining
        // elements.
    }

    // TODO:Ontonator Consider removing entirely.
    /**
     * Sets all the entities in the loaded chunks.
     *
     * @param entities the new entities to use
     *
     * @deprecated since this only affects the loaded chunks and is no longer a trivial replacement of lists
     */
    @Deprecated
    public void setEntities(List<AbstractEntity> entities) {
        for (Chunk chunk : loadedChunks.values()) {
            chunk.getEntities().clear();
        }
        for (AbstractEntity entity : entities) {
            addEntity(entity);
        }
    }

    /**
     * Returns a list of all of the tiles in the world. This list is <em>not</em> the list used internally (due to
     * chunking) so modifying this list will not modify the list of tiles.
     *
     * @return a list of all of the tiles in the world
     *
     * @deprecated since this is no longer a trivial operation. Getting the chunk map and iterating through the tiles of
     * the individual chunks should be preferred since it does not perform an unnecesary copy.
     */
    @Deprecated
    public List<Tile> getTileMap() {
        // TODO:Ontonator Look for usages which assume that this is the internal list used (i.e. mutate the provided
        //  list).
        return loadedChunks.values().stream()
                .flatMap(chunk -> chunk.getTiles().stream())
                .collect(Collectors.toList());
    }

    public Tile getTile(float col, float row) {
        return getTile(new HexVector(col, row));
    }

    public Tile getTile(HexVector position) {
        Pair<Integer, Integer> chunkCoords = Chunk.getChunkForCoordinates(position.getCol(), position.getRow());
        Chunk chunk = getChunk(chunkCoords.getValue0(), chunkCoords.getValue1());
        for (Tile t : chunk.getTiles()) {
            if (t.getCoordinates().equals(position)) {
                return t;
            }
        }
        return null;
    }

    // TODO:Ontonator Is it a problem that this does not find unloaded tiles?
    public Tile getTile(int tileID) {
        for (Chunk chunk : loadedChunks.values()) {
            for (Tile t : chunk.getTiles()) {
                if (t.getTileID() == tileID) {
                    return t;
                }
            }
        }
        return null;
    }

    public Chunk getLoadedChunk(int x, int y) {
        return loadedChunks.get(new Pair<>(x, y));
    }

    public Chunk getChunk(int x, int y) {
        return loadedChunks.computeIfAbsent(new Pair<>(x, y), pair -> Chunk.loadChunkAt(this, x, y));
    }

    /**
     * Sets all the tiles in the loaded chunks.
     *
     * @param tileMap the new tiles to use
     *
     * @deprecated since this only affects the loaded chunks and is no longer a trivial replacement of lists
     */
    @Deprecated
    public void setTileMap(CopyOnWriteArrayList<Tile> tileMap) {
        // TODO:Ontonator Check that this works.
        for (Chunk chunk : loadedChunks.values()) {
            chunk.getEntities().clear();
        }
        for (Tile tile : tileMap) {
            addTile(tile);
        }
    }

    // TODO ADDRESS THIS..?
    // TODO:Ontonator I don't really know what the point of this is, so I don't know what to do here.
    /**
     * This updates a tile with a given ID. This only works properly if the tile being updated is currently loaded; if
     * the tile is not loaded, the original tile will remain in place and the new tile will still be added, even if it
     * is being added to an area that is not loaded (and even if the value of the tile has not changed).
     *
     * @param tile the tile to add/update
     *
     * @deprecated because it does not properly account for unloaded chunks and does not seem to be used anywhere
     */
    @Deprecated
    public void updateTile(Tile tile) {
        for (Chunk chunk : loadedChunks.values()) {
            for (Tile t : chunk.getTiles()) {
                if (t.getTileID() == tile.getTileID()) {
                    if (!t.equals(tile)) {
                        chunk.getTiles().remove(t);
                        addTile(tile);
                    }
                    break;
                }
            }
        }
        addTile(tile);
    }

    protected void addTile(Tile tile) {
        Pair<Integer, Integer> chunkCoords = Chunk.getChunkForCoordinates(tile.getCol(), tile.getRow());
        Chunk chunk = getChunk(chunkCoords.getValue0(), chunkCoords.getValue1());
        chunk.getTiles().add(tile);
    }

    public HashMap<Pair<Integer, Integer>, Chunk> getLoadedChunks() {
        return loadedChunks;
    }

    // TODO ADDRESS THIS..?
    // TODO:Ontonator See above.
    /**
     * This updates an entity with a given ID. This only works properly if the entity being updated is currently loaded;
     * if the entity is not loaded, the original entity will remain in place and the new entity will still be added,
     * even if it is being added to an area that is not loaded (and even if the value of the entity has not changed).
     *
     * @param entity the entity to add/update
     *
     * @deprecated because it does not properly account for unloaded chunks and does not seem to be used anywhere
     */
    public void updateEntity(AbstractEntity entity) {
        for (Chunk chunk : loadedChunks.values()) {
            for (AbstractEntity e : chunk.getEntities()) {
                if (e.getEntityID() == entity.getEntityID()) {
                    chunk.getEntities().remove(e);
                    addEntity(entity);
                    return;
                }
            }
        }
        addEntity(entity);

        // Since MultiEntities need to be attached to the tiles they live on, setup that
        // connection.
        // TODO:Ontonator Is this needed in the loop as well, or only for first-time setup?
        if (entity instanceof StaticEntity) {
            ((StaticEntity) entity).setup();
        }
    }

    // TODO:Ontonator This whole system might need to be rethought.
    public void onTick(long i) {
        for (AbstractEntity e : entitiesToDelete) {
            removeEntity(e);
        }

        for (Tile t : tilesToDelete) {
            deleteTile(t.getTileID());
        }

        // Collision detection for entities
        for (AbstractEntity e1 : this.getEntities()) {
            if (e1 instanceof StaticEntity) {
                // Static entities can't move into other entities. Only worry
                // about entities that can move themselves into other entities
                continue;
            }
            e1.onTick(0);
            //if (e1.getCollider() == null) {
            //    break;
            //}
            Collider c1 = e1.getCollider();
            for (AbstractEntity e2 : this.getEntities()) {
                if (e2.getCollider() == null) {
                    break;
                }
                Collider c2 = e2.getCollider();
                if (e1 != e2 && c1.overlaps(c2)) {
                    if (e1 instanceof MainCharacter || e2 instanceof
                    MainCharacter) {
                        break;
                    }
                    //collision handler
                //    this.handleCollision(e1, e2);
                //    break;
                }
            }
            // no collision here
        }
    }

    // TODO:Ontonator Why does this operate with an id?
    public void deleteTile(int tileid) {
        Tile tile = GameManager.get().getWorld().getTile(tileid);
        if (tile != null) {
            tile.dispose();
        }
    }

    public void deleteEntity(int entityID) {
        for (AbstractEntity e : this.getEntities()) {
            if (e.getEntityID() == entityID) {
                e.dispose();
            }
        }
    }

    public void queueEntitiesForDelete(List<AbstractEntity> entities) {
        entitiesToDelete.addAll(entities);
    }

    public void queueTilesForDelete(List<Tile> tiles) {
        tilesToDelete.addAll(tiles);
    }

    /**
     * Adds a biome to a world
     *
     * @param biome The biome getting added
     */
    public void addBiome(AbstractBiome biome) {
        worldParameters.addBiome(biome);
    }

    /**
     * Gets the list of biomes in a world
     */
    public List<AbstractBiome> getBiomes() {
        return this.worldParameters.getBiomes();
    }

    public void handleCollision(AbstractEntity e1, AbstractEntity e2) {
        // TODO: implement proper game logic for collisions between different types of
        // entities.

        // TODO: this needs to be internalized into classes for cleaner code.
        if (e1 instanceof Projectile && e2 instanceof EnemyEntity) {
            if(((EnemyEntity) e2).getHealth()>0){
                ((EnemyEntity) e2).takeDamage(((Projectile) e1).getDamage());
                ((EnemyEntity) e2).setAttacked(true);
                ((Projectile) e1).destroy();
            }else{
                ((EnemyEntity) e2).setDead(true);
            }


        } else if (e2 instanceof Projectile && e1 instanceof EnemyEntity) {
            if(((EnemyEntity) e1).getHealth()>0){
                ((EnemyEntity) e1).takeDamage(((EnemyEntity) e1).getDamage());
                ((EnemyEntity) e1).setAttacked(true);
                ((Projectile) e2).destroy();
            }else{
                ((EnemyEntity) e1).setDead(true);
            }


        }
    }

    public void saveWorld(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(worldToString());
        }
    }

    public String worldToString() {
        // TODO:Ontonator Check that this works.
        StringBuilder string = new StringBuilder();
        loadedChunks.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .flatMap(entry -> entry.getValue().getTiles().stream())
                .forEachOrdered(tile -> {
                    String out = String.format("%f, %f, %s, %s\n", tile.getCol(), tile.getRow(),
                                               tile.getBiome().getBiomeName(),
                                               tile.getTextureName());
                    string.append(out);
                });
        return string.toString();
    }

    /**
     * Returns the seed used in the world
     *
     * @return the seed used in the world
     */
    public long getSeed() {
        return worldParameters.getSeed();
    }


    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow right clicks to collect resources
        if (button != 1) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        Tile tile = getTile(clickedPosition[0], clickedPosition[1]);

        if (tile == null) {
            return;
        }
        // todo: more efficient way to find entities
        for (AbstractEntity entity : getEntities()) {
            if (!tile.getCoordinates().equals(entity.getPosition())) {
                continue;
            }

            if (entity instanceof Harvestable) {
                removeEntity(entity);
                List<AbstractEntity> drops = ((Harvestable) entity).harvest(tile);

                for (AbstractEntity drop : drops) {
                    addEntity(drop);
                }
            }
        }
    }

    /**
     * Sets the list of river edges with their associated biomes
     *
     * @param edges the list of edges that are rivers
     */
    public void setRiverEdges(LinkedHashMap<VoronoiEdge, RiverBiome> edges) {
        this.riverEdges = edges;
    }

    /**
     * Sets the list of beach edges with their associated biomes
     *
     * @param edges the list of edges that are beaches
     */
    public void setBeachEdges(LinkedHashMap<VoronoiEdge, BeachBiome> edges) {
        this.beachEdges = edges;
    }

    /**
     * Returns the edges that are in rivers, and their associated biomes
     *
     * @return the edges that are in rivers, and their associated biomes
     */
    public LinkedHashMap<VoronoiEdge, RiverBiome> getRiverEdges() {
        return this.riverEdges;
    }

    /**
     * Returns the edges that are in beaches, and their associated biomes
     *
     * @return the edges that are in beaches, and their associated biomes
     */
    public LinkedHashMap<VoronoiEdge, BeachBiome> getBeachEdges() {
        return this.beachEdges;
    }

    @Override
    public String formatData() {
        return new Gson().toJson(this);
    }
}
