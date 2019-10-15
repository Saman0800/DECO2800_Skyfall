package deco2800.skyfall.worlds.world;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.buildings.*;
import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.weapons.Weapon;
import deco2800.skyfall.entities.worlditems.EntitySpawnRule;
import deco2800.skyfall.gamemenu.popupmenu.BlueprintShopTable;
import deco2800.skyfall.gamemenu.popupmenu.ChestTable;
import deco2800.skyfall.graphics.HasPointLight;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Saveable;
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
public class World implements TouchDownObserver , Saveable<World.WorldMemento> {
    public static final int LOADED_RADIUS = 50;
    protected long id;

    protected int width;
    protected int length;

    //Used to generate random numbers
    protected Random random;

    public Map<String, Float> frictionMap;

    protected HashMap<Pair<Integer, Integer>, Chunk> loadedChunks;

    private int loadedAreaLowerX;
    private int loadedAreaLowerY;
    private int loadedAreaUpperX;
    private int loadedAreaUpperY;

    //A list of all the tiles within a world
    protected CopyOnWriteArrayList<WorldGenNode> worldGenNodes;
    protected CopyOnWriteArrayList<VoronoiEdge> voronoiEdges;

    // The noise generators used when adding offset during the biome assignment.
    protected NoiseGenerator tileOffsetNoiseGeneratorX;
    protected NoiseGenerator tileOffsetNoiseGeneratorY;

    protected LinkedHashMap<VoronoiEdge, RiverBiome> riverEdges;
    protected LinkedHashMap<VoronoiEdge, BeachBiome> beachEdges;

    protected Map<AbstractBiome, List<EntitySpawnRule>> spawnRules;
    protected NoiseGenerator staticEntityNoise;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();

    protected WorldParameters worldParameters;

    private GameMenuManager gmm = GameManager.getManagerFromInstance(GameMenuManager.class);

    private Save save;

    private AbstractEntity entityToBeDeleted = null;

    // Import coin sound effect
    public static final String GOLD_SOUND_EFFECT = "coins";


    /**
     * The constructor used to create a simple dummey world, used for displaying world information on the
     * home screen
     * @param worldId The id of the world
     * @param save The save the world is in
     */
    public World(long worldId, Save save){
        this.save = save;
        this.id = worldId;
    }

    /**
     * The constructor for a world being loaded from a memento
     *
     * @param memento the memento to load from
     * @param save the save file this world is in
     */
    public World(WorldMemento memento, Save save) {
        this.worldParameters = new WorldParameters();
        this.load(memento);
        this.save = save;
        this.loadedChunks = new HashMap<>();

        EntitySpawnRule.setNoiseSeed(this.worldParameters.getSeed());
        initialiseFrictionmap();
        staticEntityNoise = new NoiseGenerator((new Random(this.worldParameters.getSeed())).nextLong(), 3, 4, 1.3);

    }

    private Map<AbstractBiome, List<EntitySpawnRule>> generateStartEntitiesInternal() {
        return new WorldBuilder().generateStartEntities(this);
    }

    public void generateStartEntities() {
        this.spawnRules = this.generateStartEntitiesInternal();
    }

    /**
     * The constructor for a world
     * @param worldParameters A class that contains the world parameters
     */
    public World(WorldParameters worldParameters) {
        this.id = System.nanoTime();
        this.worldParameters = worldParameters;

        random = new Random(worldParameters.getSeed());

        riverEdges = new LinkedHashMap<>();
        beachEdges = new LinkedHashMap<>();
        worldGenNodes = new CopyOnWriteArrayList<>();
        voronoiEdges = new CopyOnWriteArrayList<>();

        tileOffsetNoiseGeneratorX = Tile.getOffsetNoiseGenerator(random, worldParameters.getNodeSpacing());
        tileOffsetNoiseGeneratorY = Tile.getOffsetNoiseGenerator(random, worldParameters.getNodeSpacing());

        staticEntityNoise = new NoiseGenerator(random.nextLong(), 3, 4, 1.3);

        loadedChunks = new HashMap<>();

        generateWorld();

        generateTileIndices();
        initialiseFrictionmap();

        spawnRules = worldParameters.getGenerateSpawnRules().apply(this);

        for (AbstractEntity entity : worldParameters.getEntities()) {
            addEntity(entity);
        }


        // Set this to null once generation is complete since using this after construction is likely not deterministic
        // due to ordering of events being affected by external factors like player movement. If you just want to
        // generate random numbers, then this isn't appropriate either, since it is seeded, so not properly random.
        // DON'T REMOVE THIS JUST BECAUSE YOUR CODE IS THROWING `NullPointerException`, YOU PROBABLY HAND DEEP AND
        // FUNDAMENTAL ISSUES WITH WHAT YOU ARE DOING.
        random = null;
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
            } catch (WorldGenException | DeadEndGenerationException | NotEnoughPointsException ignored) {
                // Repeat if generation fails.
            }
        }

        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }

    /**
     * Generates the tiles and biomes in a world
     * @throws NotEnoughPointsException When there are not enough points
     * @throws DeadEndGenerationException
     * @throws WorldGenException
     */
    private void generateTiles() throws NotEnoughPointsException, DeadEndGenerationException, WorldGenException {
        ArrayList<WorldGenNode> localWorldGenNodes = new ArrayList<>();
        loadedChunks = new HashMap<>();
        ArrayList<VoronoiEdge> localVoronoiEdges = new ArrayList<>();

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
            WorldGenNode node = new WorldGenNode(x, y);
            localWorldGenNodes.add(node);
        }
        localWorldGenNodes.sort(null);

        // Apply Delaunay triangulation to the nodes, so that vertices and
        // adjacencies can be calculated. Also apply Lloyd Relaxation twice
        // for more smooth looking polygons
        WorldGenNode.calculateVertices(localWorldGenNodes, worldSize);
        WorldGenNode.lloydRelaxation(localWorldGenNodes, 2, worldSize);
        worldGenNodes = new CopyOnWriteArrayList<>(localWorldGenNodes);

        WorldGenNode.removeZeroTileNodes(this, localWorldGenNodes, nodeSpacing, worldSize);
        WorldGenNode.assignNeighbours(localWorldGenNodes, localVoronoiEdges, this);
        VoronoiEdge.assignNeighbours(localVoronoiEdges);

        BiomeGenerator biomeGenerator = new BiomeGenerator(this, localWorldGenNodes, localVoronoiEdges, random, worldParameters);
        biomeGenerator.generateBiomes();
    }

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
     * Gets all the luminous entities from the world.
     *
     * @return A list a luminous entities from the game world
     */
    public List<AbstractEntity> getLuminousEntities() {
        return getEntities().stream().filter(HasPointLight.class::isInstance).collect(Collectors.toList());
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

    public Chunk getLoadedChunk(int x, int y) {
        return loadedChunks.get(new Pair<>(x, y));
    }

    public Chunk getChunk(int x, int y) {
        Chunk chunk = loadedChunks.get(new Pair<>(x, y));
        if (chunk == null) {
            chunk = Chunk.loadChunkAt(this, x, y);
        }
        return chunk;
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
        for (Chunk chunk : loadedChunks.values()) {
            chunk.getEntities().clear();
        }
        for (Tile tile : tileMap) {
            addTile(tile);
        }
    }

    protected void addTile(Tile tile) {
        Pair<Integer, Integer> chunkCoords = Chunk.getChunkForCoordinates(tile.getCol(), tile.getRow());
        Chunk chunk = getChunk(chunkCoords.getValue0(), chunkCoords.getValue1());
        chunk.getTiles().add(tile);
    }

    public HashMap<Pair<Integer, Integer>, Chunk> getLoadedChunks() {
        return loadedChunks;
    }

    public void onTick(long i) {
        // Don't tick the entities in the outer band. This allows entities to detect collisions with entities which
        // are not in the loaded area, since nothing in the outer band will ever be able to move.
        List<AbstractEntity> entities = getLoadedChunks().values().stream()
                .filter(chunk -> chunk.getX() > loadedAreaLowerX && chunk.getY() > loadedAreaLowerY &&
                        chunk.getX() < loadedAreaUpperX - 1 && chunk.getY() < loadedAreaUpperY - 1)
                .flatMap(chunk -> chunk.getEntities().stream())
                .collect(Collectors.toList());

        for (AbstractEntity entity : entities) {
            entity.onTick(i);
        }

        for (AbstractEntity e : entitiesToDelete) {
            removeEntity(e);
        }

        MainCharacter mc = MainCharacter.getInstance();
        setLoadedArea(mc.getCol() - LOADED_RADIUS, mc.getRow() - LOADED_RADIUS,
                      mc.getCol() + LOADED_RADIUS, mc.getRow() + LOADED_RADIUS);
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
        if (e1 instanceof Projectile && e2 instanceof Enemy) {
            if (((Enemy) e2).getHealth() > 0) {
                ((Enemy) e2).takeDamage(((Projectile) e1).getDamage());
                ((Enemy) e2).setHurt(true);
                ((Projectile) e1).destroy();
            } else {
                ((Enemy) e2).setDead(true);
            }

        } else if (e2 instanceof Projectile && e1 instanceof Enemy) {
            if (((Enemy) e1).getHealth() > 0) {
                ((Enemy) e1).takeDamage(((Enemy) e1).getDamage());
                ((Enemy) e1).setHurt(true);
                ((Projectile) e2).destroy();
            } else {
                ((Enemy) e1).setDead(true);
            }

        }
    }

    public void saveWorld(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(worldToString());
        }
    }

    public String worldToString() {
        StringBuilder string = new StringBuilder();
        loadedChunks.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .flatMap(entry -> entry.getValue().getTiles().stream()
                        .sorted(Comparator.comparing(tile -> new Pair<>(tile.getCol(), tile.getRow()))))
                .forEachOrdered(tile -> {
                    String out = String.format("%f, %f, %s, %s", tile.getCol(), tile.getRow(),
                                               tile.getBiome().getBiomeName(),
                                               tile.getTextureName()) + '\n';
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

    /**
     * Gets the save the world is in
     * @return The save
     */
    public Save getSave() {
        return save;
    }


    /**
     * Gets the nodes of the world
     * @return The nodes of the world
     */
    public List<WorldGenNode> getWorldGenNodes() {
        return worldGenNodes;
    }

    /**
     * Sets the nodes in this world
     *
     * @param nodes the nodes in this world
     */
    public void setWorldGenNodes(List<WorldGenNode> nodes) {
        this.worldGenNodes = new CopyOnWriteArrayList<>(nodes);
    }

    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow right clicks to collect resources
        if (button != 1) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
        Pair<Integer, Integer> clickedChunkCoords =
                Chunk.getChunkForCoordinates(clickedPosition[0], clickedPosition[1]);
        Chunk clickedChunk = getChunk(clickedChunkCoords.getValue0(), clickedChunkCoords.getValue1());

        Tile tile = getTile(clickedPosition[0], clickedPosition[1]);

        if (tile == null) {
            return;
        }
        for (AbstractEntity entity : clickedChunk.getEntities()) {
            //NOTE: DO NOT RUN REMOVE ENTITY IN THIS LOOP, IT WILL CAUSE
            //ConcurrentModificationException
            if (!tile.getCoordinates().equals(entity.getPosition())) {
                continue;
            }

            if (entity instanceof Harvestable) {
                entityToBeDeleted = entity;
                List<AbstractEntity> drops = ((Harvestable) entity).harvest(tile);
                drops.forEach(this::addEntity);
            } else if (entity instanceof Weapon) {
                MainCharacter mc = gmm.getMainCharacter();
                if (tile.getCoordinates().distance(mc.getPosition()) <= 2) {
                    entityToBeDeleted = entity;
                    gmm.getInventory().add((Item) entity);
                }
            } else if (entity instanceof Chest) {
                ChestTable chest = (ChestTable) gmm.getPopUp("chestTable");
                chest.setWorldAndChestEntity(this, (Chest) entity);
                chest.updateChestPanel((Chest) entity);
                gmm.setPopUp("chestTable");
            } else if (entity instanceof Item) {
                    MainCharacter mc = gmm.getMainCharacter();
                    if (tile.getCoordinates().distance(mc.getPosition()) > 2) {
                        continue;
                    }
                entityToBeDeleted = entity;
                    gmm.getInventory().add((Item) entity);
            } else if (entity instanceof GoldPiece) {
                MainCharacter mc = gmm.getMainCharacter();
                if (tile.getCoordinates().distance(mc.getPosition()) <= 3) {
                    mc.addGold((GoldPiece) entity, 1);
                    SoundManager.playSound(GOLD_SOUND_EFFECT);
                    // remove the gold piece instance from the world
                    entityToBeDeleted = entity;
                }
            }
            else if (entity instanceof BlueprintShop) {
                GameMenuManager menuManager = GameManager.getManagerFromInstance(GameMenuManager.class);
                BlueprintShopTable bs = (BlueprintShopTable) menuManager.getPopUp("blueprintShopTable");
                bs.updateBlueprintShopPanel();
                gmm.setPopUp("blueprintShopTable");
            } else if (entity instanceof BuildingEntity) {
                BuildingEntity e = (BuildingEntity) entity;
                MainCharacter mc = gmm.getMainCharacter();
                switch (e.getBuildingType()) {
                    case FORESTPORTAL:
                        ForestPortal forestPortal = new ForestPortal(0, 0, 0);
                        forestPortal.teleport(this.save);
                        break;
                    case MOUNTAINPORTAL:
                        MountainPortal mountainPortal = new MountainPortal(0, 0, 0);
                        mountainPortal.teleport(this.save);
                        break;
                    case DESERTPORTAL:
                        DesertPortal desertPortal = new DesertPortal(0, 0, 0);
                        desertPortal.teleport(this.save);
                        break;
                    case VOLCANOPORTAL:
                        VolcanoPortal volcanoPortal = new VolcanoPortal(0, 0, 0);
                        volcanoPortal.teleport(this.save);
                        break;
                    default:
                        break;
                }
            }
        }

        if (entityToBeDeleted != null) {
            removeEntity(entityToBeDeleted);
            entityToBeDeleted = null;
        }
    }

    /**
     * Gets the noise generator for the X coordinate used for selecting the {@code WorldGenNode} for the tiles.
     * @return the noise generator for the X coordinate used for selecting the {@code WorldGenNode} for the tiles
     */
    public NoiseGenerator getTileOffsetNoiseGeneratorX() {
        return tileOffsetNoiseGeneratorX;
    }

    /**
     * Gets the noise generator for the Y coordinate used for selecting the {@code WorldGenNode} for the tiles.
     *
     * @return the noise generator for the Y coordinate used for selecting the {@code WorldGenNode} for the tiles
     */
    public NoiseGenerator getTileOffsetNoiseGeneratorY() {
        return tileOffsetNoiseGeneratorY;
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

    public Map<AbstractBiome, List<EntitySpawnRule>> getSpawnRules() {
        return spawnRules;
    }

    /**
     * Gets the noise generator used to generate the static entities.
     *
     * @return the noise generator used to generate the static entities.
     */
    public NoiseGenerator getStaticEntityNoise() {
        return staticEntityNoise;
    }

    /**
     * Gets the world parameter object for this world.
     *
     * @return the world parameter object for this world
     */
    public WorldParameters getWorldParameters() {
        return worldParameters;
    }


    /**
     * Sets the id of a world
     * @param id The id that the will be set to
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Sets the area loaded by the world.
     *
     * @param lowerCol the left bound of the area loaded
     * @param lowerRow the bottom bound of the area loaded
     * @param upperCol the right bound of the area loaded
     * @param upperRow the top bound of the area loaded
     */
    public void setLoadedArea(double lowerCol, double lowerRow, double upperCol, double upperRow) {
        // Add an extra band of chunks around the selected area which will be loaded but not active (entities will not
        // be ticked).
        loadedAreaLowerX = (int) Math.floor(lowerCol / Chunk.CHUNK_SIDE_LENGTH) - 1;
        loadedAreaLowerY = (int) Math.floor(lowerRow / Chunk.CHUNK_SIDE_LENGTH) - 1;
        loadedAreaUpperX = (int) Math.ceil(upperCol / Chunk.CHUNK_SIDE_LENGTH) + 1;
        loadedAreaUpperY = (int) Math.ceil((upperRow + 0.5) / Chunk.CHUNK_SIDE_LENGTH) + 1;

        HashSet<Pair<Integer, Integer>> removedChunks = new HashSet<>(loadedChunks.keySet());
        for (int x = loadedAreaLowerX; x < loadedAreaUpperX; x++) {
            for (int y = loadedAreaLowerY; y < loadedAreaUpperY; y++) {
                Pair<Integer, Integer> pair = new Pair<>(x, y);
                if (loadedChunks.containsKey(pair)) {
                    removedChunks.remove(pair);
                } else {
                    Chunk.loadChunkAt(this, x, y);
                }
            }
        }
        for (Pair<Integer, Integer> pair : removedChunks) {
            loadedChunks.get(pair).unload();
        }
    }

    /**
     * Gets the id of this world
     *
     * @return the id of this world
     */
    public long getID() {
        return this.id;
    }

    /**
     * Sets the save for this world
     *
     * @param save the save for this world
     */
    public void setSave(Save save) {
        this.save = save;
    }

    @Override
    public WorldMemento save() {
        return new WorldMemento(this);
    }

    @Override
    public void load(WorldMemento worldMemento) {
        this.id = worldMemento.worldID;
        // TODO
        this.worldParameters.setBeachWidth(worldMemento.beachWidth);
        this.worldParameters.setBeachWidth(worldMemento.riverWidth);
        this.worldParameters.setNodeSpacing(worldMemento.nodeSpacing);
        this.worldParameters.setSeed(worldMemento.seed);
        this.tileOffsetNoiseGeneratorX = worldMemento.tileOffsetNoiseGeneratorX;
        this.tileOffsetNoiseGeneratorY = worldMemento.tileOffsetNoiseGeneratorY;
        this.worldParameters.setWorldSize(worldMemento.worldSize);
    }




    public static class WorldMemento extends AbstractMemento implements Serializable {
        private long saveID;
        private long worldID;
        private int nodeSpacing;
        private double riverWidth;
        private double beachWidth;
        private NoiseGenerator tileOffsetNoiseGeneratorX;
        private NoiseGenerator tileOffsetNoiseGeneratorY;
        private long seed;
        private int worldSize;

        public WorldMemento(World world) {
            // TODO (probably in the main save method)
            this.saveID = world.save.getSaveID();
            this.worldID = world.id;
            this.nodeSpacing = world.worldParameters.getNodeSpacing();
            this.riverWidth = world.worldParameters.getRiverWidth();
            this.beachWidth = world.worldParameters.getBeachWidth();
            this.tileOffsetNoiseGeneratorX = world.tileOffsetNoiseGeneratorX;
            this.tileOffsetNoiseGeneratorY = world.tileOffsetNoiseGeneratorY;
            this.seed = world.getWorldParameters().getSeed();
            this.worldSize = world.getWorldParameters().getWorldSize();
        }
    }
}
