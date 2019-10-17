package deco2800.skyfall.managers.database;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.MainCharacter.MainCharacterMemento;
import deco2800.skyfall.entities.SaveableEntity.SaveableEntityMemento;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.saving.*;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.biomes.*;
import deco2800.skyfall.worlds.biomes.AbstractBiome.AbstractBiomeMemento;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.VoronoiEdge.VoronoiEdgeMemento;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode.WorldGenNodeMemento;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.Chunk.ChunkMemento;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.World.WorldMemento;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.flywaydb.core.Flyway;

public class DataBaseConnector {

    public static final String RIVER = "river";
    public static final String SNOWY_MOUNTAINS = "snowy_mountains";
    public static final String SWAMP = "swamp";
    public static final String VOLCANIC_MOUNTAINS = "volcanic_mountains";
    public static final String MOUNTAIN = "mountain";
    public static final String OCEAN = "ocean";
    public static final String LAKE = "lake";
    public static final String FOREST = "forest";
    public static final String DESERT = "desert";
    public static final String BEACH = "beach";
    public static final String BIOME_ID = "biome_id";
    /* The connection to the database */
    private Connection connection;

    private Flyway flyway;

    private String dataBaseName;

    /**
     * Starts a connection to the database
     *
     * @param dataBaseName The name of the database to be connected to
     */
    public void start(String dataBaseName) {
        try {
            this.dataBaseName = dataBaseName;
            migrateDatabase();
            Driver derbyData = new EmbeddedDriver();
            DriverManager.registerDriver(derbyData);
            connection = DriverManager.getConnection(String.format("jdbc:derby:%s;create=true", dataBaseName));
        } catch (Exception e) {
            throw new DatabaseException("Failed to start database : " + dataBaseName, e);
        }
    }

    /**
     * Closes the connection to the database
     */
    public void close() {
        try {
            connection.close();
            DriverManager.getConnection(String.format("jdbc:derby:%s;shutdown=true", dataBaseName));
        } catch (SQLException ignore) {
            // Should ignore exception, as shutting down database always throws exceptions
        }
    }

    /**
     * Gets the database connection
     *
     * @return The database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Saves the game
     *
     * @param save the save file
     */
    public void saveGame(Save save) {
        try {
            // Given a save game
            long saveId = save.getSaveID();
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

            if (containsQueries.containsSave(saveId)) {
                updateQueries.updateSave(saveId, save.save());
            } else {
                insertQueries.insertSave(saveId, save.save());
            }

            saveMainCharacter();
            // Looping through the worlds in the save and saving them
            for (World world : save.getWorlds()) {
                saveWorld(world);
            }
        } catch (SQLException | IOException e) {
            throw new RunTimeSaveException("Failed to save the game ", e);
        }
    }

    /**
     * Saves a world and its contents
     *
     * @param world the world to save
     * @throws SQLException if there is an error with the SQL queries
     */
    public void saveWorld(World world) throws SQLException, IOException {
        ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
        InsertDataQueries insertQueries = new InsertDataQueries(connection);
        UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

        if (containsQueries.containsWorld(world.getSave().getSaveID(), world.getID())) {
            updateQueries.updateWorld(world.getSave().getSaveID(), world.getID(),
                world.getSave().getCurrentWorld().getID() == world.getID(), world.save());
        } else {
            insertQueries.insertWorld(world.getSave().getSaveID(), world.getID(),
                world.getSave().getCurrentWorld().getID() == world.getID(), world.save());
        }

        for (AbstractBiome biome : world.getBiomes()) {
            if (containsQueries.containsBiome(biome.getBiomeID(), world.getID())) {
                updateQueries.updateBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), biome.save());
            } else {
                insertQueries.insertBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), biome.save());
            }
        }

        // Save nodes
        for (WorldGenNode worldGenNode : world.getWorldGenNodes()) {
            if (containsQueries.containsNode(world.getID(), worldGenNode.getX(), worldGenNode.getY())) {
                updateQueries.updateNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(), worldGenNode.save(),
                        worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            } else {
                insertQueries.insertNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(), worldGenNode.save(),
                    worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            }
        }
        saveEdges(world, containsQueries, insertQueries, updateQueries);

        for (Chunk chunk : world.getLoadedChunks().values()) {
            saveChunk(chunk);
        }
    }

    /**
     * Saves the edges
     * @param world The world from which the edges are beings saved
     * @param containsQueries Class that contains query selections
     * @param insertQueries Class that contains query insertions
     * @param updateQueries Class that contains query updates
     * @throws SQLException If an sql error occurs
     * @throws IOException If a writing error occurs
     */
    private void saveEdges(World world, ContainsDataQueries containsQueries, InsertDataQueries insertQueries,
        UpdateDataQueries updateQueries) throws SQLException, IOException {
        // Save beach edges
        for (VoronoiEdge voronoiEdge : world.getBeachEdges().keySet()) {
            if (containsQueries.containsEdge(voronoiEdge.getID())) {
                updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                    world.getBeachEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            } else {
                insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                    world.getBeachEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            }
        }

        // Save river edges
        for (VoronoiEdge voronoiEdge : world.getRiverEdges().keySet()) {
            if (containsQueries.containsEdge(voronoiEdge.getID())) {
                updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                    world.getRiverEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            } else {
                insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                    world.getRiverEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            }
        }
    }

    public void saveMainCharacter() throws SQLException {
        try {
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

            if (containsQueries.containsMainCharacter(MainCharacter.getInstance().getID(),
                MainCharacter.getInstance().getSave().getSaveID())) {
                updateQueries.updateMainCharacter(MainCharacter.getInstance().getID(),
                    MainCharacter.getInstance().getSave().getSaveID(), MainCharacter.getInstance().save());
            } else {
                insertQueries.insertMainCharacter(MainCharacter.getInstance().getID(),
                    MainCharacter.getInstance().getSave().getSaveID(), MainCharacter.getInstance().save());
            }
        } catch (IOException e) {
            throw new RunTimeSaveException("Unable to save the main character to the database", e);
        }
    }

    /**
     * Saves a chunk
     *
     * @param chunk the chunk to save
     */
    public void saveChunk(Chunk chunk) {
        try {
            World world = chunk.getWorld();
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

            if (containsQueries.containsChunk(world.getID(), chunk.getX(), chunk.getY())) {
                updateQueries.updateChunk(world.getID(), chunk.getX(), chunk.getY(), chunk.save());
            } else {
                insertQueries.insertChunk(world.getID(), chunk.getX(), chunk.getY(), chunk.save());
            }

            for (AbstractEntity entity : chunk.getEntities()) {
                if (entity instanceof StaticEntity && ((StaticEntity) entity).getEntityType() != null) {
                    if (containsQueries.containsEntity(world.getID(), entity.getEntityID())) {
                        updateQueries.updateEntity(((StaticEntity) entity).getEntityType(), entity.getCol(),
                            entity.getRow(), chunk.getX(), chunk.getY(), world.getID(),
                            ((StaticEntity) entity).save(), entity.getEntityID());
                    } else {
                        insertQueries.insertEntity(((StaticEntity) entity).getEntityType(), entity.getCol(),
                            entity.getRow(), chunk.getX(), chunk.getY(), world.getID(),
                            ((StaticEntity) entity).save(), entity.getEntityID());
                    }
                }
            }
            connection.commit();
        } catch (SQLException | IOException e) {
            throw new RunTimeSaveException("Failed to save the chunk", e);
        }
    }

    /**
     * Load the game
     *
     * @return loads the most recent save
     */
    public Save loadGame(long saveId) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SAVES WHERE save_id = "
            + "?")) {
            preparedStatement.setLong(1, saveId);
            long saveID;
            byte[] buffer;
            try (ResultSet result = preparedStatement.executeQuery()) {
                connection.setAutoCommit(false);

                if (!result.next()) {
                    connection.setAutoCommit(true);
                    throw new SQLException();
                }

                saveID = result.getLong("save_id");
                connection.setAutoCommit(true);

                buffer = result.getBytes("data");
            }
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            SaveMemento memento = (SaveMemento) objectIn.readObject();

            Save save = new Save(memento);
            World currentWorld = loadWorlds(save);
            save.setCurrentWorld(currentWorld);
            save.setSaveID(saveID);

            return save;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RunTimeLoadException("Failed to load the game", e);
        }
    }

    public void loadMainCharacter(Save save) {
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM MAIN_CHARACTER WHERE save_id = ?")) {
            preparedStatement.setLong(1, save.getSaveID());
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    connection.setAutoCommit(true);
                    throw new LoadException();
                }

                byte[] buffer = result.getBytes("data");
                ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                MainCharacterMemento memento = (MainCharacterMemento) objectIn.readObject();
                MainCharacter.resetInstance();
                MainCharacter.loadMainCharacter(memento, save);

                connection.setAutoCommit(true);


            }

        } catch (IOException | ClassNotFoundException | SQLException | LoadException e) {
            throw new RunTimeLoadException("Unable to load main character", e);
        }

    }

    /**
     * Loads the world of a save
     *
     * @param save        the save to load from
     * @return the save's current world
     */
    public World loadWorlds(Save save) {
        try {
            connection.setAutoCommit(false);
            World currentWorld;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM WORLDS WHERE save_id = ?")) {
                preparedStatement.setLong(1, save.getSaveID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    currentWorld = null;

                    do {
                        boolean isCurrentWorld = result.getBoolean("is_current_world");
                        long worldID = result.getLong("world_id");
                        if (!isCurrentWorld && save.getCurrentWorldId() == worldID
                            || isCurrentWorld && save.getCurrentWorldId() != worldID) {
                            throw new LoadException();
                        }

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        WorldMemento memento = (WorldMemento) objectIn.readObject();

                        World world = new World(memento, save);
                        if (isCurrentWorld) {
                            currentWorld = world;
                        }

                        List<AbstractBiome> biomeList = loadBiomes(world);
                        world.getWorldParameters().setBiomes(biomeList);
                        world.setWorldGenNodes(loadNodes(world, biomeList));
                        world.setRiverEdges((LinkedHashMap<VoronoiEdge, RiverBiome>) loadRiverEdges(world, biomeList));
                        world.setBeachEdges((LinkedHashMap<VoronoiEdge, BeachBiome>) loadBeachEdges(world, biomeList));
                        world.generateStartEntities();
                        save.addWorld(world);
                    } while (result.next());
                    connection.setAutoCommit(true);
                }
            }

            return currentWorld;

        } catch (ClassNotFoundException | IOException | LoadException | SQLException e) {
            throw new RunTimeLoadException("Failed to load the world", e);
        }
    }

    /**
     * Loads the biomes in a world being loaded
     *
     * @param world the world being loaded
     * @return A list of biomes in the world
     */
    public List<AbstractBiome> loadBiomes(World world) {
        try {
            connection.setAutoCommit(false);
            LinkedHashMap<AbstractBiome, Long> biomes;
            LinkedHashMap<Long, AbstractBiome> ids;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM biomes WHERE world_id = " + "?")) {
                preparedStatement.setLong(1, world.getID());

                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    biomes = new LinkedHashMap<>();
                    ids = new LinkedHashMap<>();

                    do {
                        String biomeType = result.getString("biome_type");

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        AbstractBiomeMemento memento = (AbstractBiomeMemento) objectIn.readObject();

                        AbstractBiome biome;

                        switch (biomeType) {
                            case BEACH:
                                biome = new BeachBiome(memento);
                                break;
                            case DESERT:
                                biome = new DesertBiome(memento);
                                break;
                            case FOREST:
                                biome = new ForestBiome(memento);
                                break;
                            case LAKE:
                                biome = new LakeBiome(memento);
                                break;
                            case MOUNTAIN:
                                biome = new MountainBiome(memento);
                                break;
                            case OCEAN:
                                biome = new OceanBiome(memento);
                                break;
                            case RIVER:
                                biome = new RiverBiome(memento);
                                break;
                            case SNOWY_MOUNTAINS:
                                biome = new SnowyMountainsBiome(memento);
                                break;
                            case SWAMP:
                                biome = new SwampBiome(memento);
                                break;
                            case VOLCANIC_MOUNTAINS:
                                biome = new VolcanicMountainsBiome(memento);
                                break;
                            default:
                                connection.setAutoCommit(false);
                                throw new LoadException();
                        }
                        biomes.put(biome, memento.getParentBiomeID());
                        ids.put(biome.getBiomeID(), biome);
                    } while (result.next());
                }
            }

            List<AbstractBiome> biomeList = new ArrayList<>();

            for (AbstractBiome biome : biomes.keySet()) {
                if (biomes.get(biome) == -1) {
                    biome.setParentBiome(null);
                } else {
                    // If there is an invalid parent id
                    if (!ids.containsKey(biomes.get(biome))) {
                        connection.setAutoCommit(false);
                        throw new LoadException();
                    } else {
                        biome.setParentBiome(ids.get(biomes.get(biome)));
                    }
                }
                biomeList.add(biome);
            }
            connection.setAutoCommit(true);
            return biomeList;
        } catch (ClassNotFoundException | IOException | SQLException | LoadException e) {
            throw new RunTimeLoadException("Failed to load biomes for a world", e);
        }
    }

    public List<WorldGenNode> loadNodes(World world, List<AbstractBiome> biomes) throws SQLException, LoadException {
        try {
            connection.setAutoCommit(false);
            List<WorldGenNode> nodes;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM nodes WHERE world_id = ?")) {
                preparedStatement.setLong(1, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    nodes = new ArrayList<>();
                    do {
                        long biomeID = result.getLong(BIOME_ID);

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        WorldGenNodeMemento memento = (WorldGenNodeMemento) objectIn.readObject();

                        WorldGenNode node = new WorldGenNode(memento);

                        boolean foundBiome = false;
                        for (AbstractBiome biome : biomes) {
                            if (biome.getBiomeID() == biomeID) {
                                node.setBiome(biome);
                                foundBiome = true;
                                break;
                            }
                        }
                        if (!foundBiome) {
                            connection.setAutoCommit(true);
                            throw new LoadException();
                        }

                        nodes.add(node);
                    } while (result.next());
                }
            }

            return nodes;
        } catch (ClassNotFoundException | IOException e) {
            throw new RunTimeLoadException("Failed to load nodes", e);
        }
    }

    /**
     * Loads the beach edges of a world
     *
     * @param world  the world being loaded
     * @param biomes the biomes in the world
     * @return a mapping from edges to beach biomes
     * @throws SQLException  If there is unexpected behaviour with the SQL queries
     * @throws LoadException If the save cannot construct a valid world
     */
    public Map<VoronoiEdge, BeachBiome> loadBeachEdges(World world, List<AbstractBiome> biomes)
        throws SQLException, LoadException {
        try {
            connection.setAutoCommit(false);
            LinkedHashMap<VoronoiEdge, BeachBiome> edges;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM EDGES WHERE world_id = ?")) {
                preparedStatement.setLong(1, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    edges = new LinkedHashMap<>();
                    do {
                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        VoronoiEdgeMemento memento = (VoronoiEdgeMemento) objectIn.readObject();
                        long biomeID = result.getLong(BIOME_ID);

                        VoronoiEdge edge = new VoronoiEdge(memento);
                        edge.setWorld(world);
                        boolean foundBiome = false;
                        for (AbstractBiome biome : biomes) {
                            if (biome.getBiomeID() == biomeID) {
                                foundBiome = true;
                                if (biome.getBiomeName().equals(BEACH)) {
                                    edges.put(edge, (BeachBiome) biome);
                                    break;
                                } else if (!biome.getBiomeName().equals(RIVER)) {
                                    connection.setAutoCommit(true);
                                    throw new LoadException();
                                }
                            }
                        }
                        if (!foundBiome) {
                            connection.setAutoCommit(true);
                            throw new LoadException();
                        }

                    } while (result.next());
                }
            }

            return edges;
        } catch (ClassNotFoundException | IOException e) {
            throw new RunTimeLoadException("Failed to load beach edges", e);
        }
    }

    /**
     * Loads the river edges of a world
     *
     * @param world  the world being loaded
     * @param biomes the biomes in the world
     * @return a mapping from edges to river biomes
     * @throws SQLException  If there is unexpected behaviour with the SQL queries
     * @throws LoadException If the save cannot construct a valid world
     */
    public Map<VoronoiEdge, RiverBiome> loadRiverEdges(World world, List<AbstractBiome> biomes)
        throws SQLException, LoadException {
        try {
            connection.setAutoCommit(false);

            LinkedHashMap<VoronoiEdge, RiverBiome> edges;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM EDGES WHERE world_id = ?")) {
                preparedStatement.setLong(1, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    edges = new LinkedHashMap<>();
                    do {
                        long biomeID = result.getLong(BIOME_ID);

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        VoronoiEdgeMemento memento = (VoronoiEdgeMemento) objectIn.readObject();

                        VoronoiEdge edge = new VoronoiEdge(memento);
                        edge.setWorld(world);
                        for (AbstractBiome biome : biomes) {
                            if (biome.getBiomeID() == biomeID && biome.getBiomeName().equals(RIVER)) {
                                edges.put(edge, (RiverBiome) biome);
                                break;
                            }
                        }

                    } while (result.next());
                }
            }

            return edges;
        } catch (ClassNotFoundException | IOException e) {
            throw new LoadException("Failed to load nodes", e);
        }
    }

    /**
     * Loads a chunk and the entities in chunk
     *
     * @param world The world where the chunk is
     * @param x     The x position of the chunk
     * @param y     The y positoin of the chunk
     * @return The chunk from the database if it exists in the database. A new chunk if the chunk does not exist in the
     * database.
     */
    public Chunk loadChunk(World world, int x, int y) {
        try {
            connection.setAutoCommit(false);
            byte[] buffer;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM CHUNKS WHERE X = ? and Y = ? and WORLD_ID = ?")) {
                preparedStatement.setInt(1, x);
                preparedStatement.setInt(2, y);
                preparedStatement.setLong(3, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {
                    connection.setAutoCommit(false);

                    if (!result.next()) {
                        Chunk chunk = new Chunk(world, x, y);
                        chunk.generateEntities();
                        return chunk;
                    }

                    buffer = result.getBytes("data");
                }
            }
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            ChunkMemento memento = (ChunkMemento) objectIn.readObject();
            Chunk chunk = new Chunk(world, memento);

            // Gets the entities within the chunk from the database and add them to the
            // chunk
            connection.setAutoCommit(false);
            try (PreparedStatement entityQuery = connection
                .prepareStatement("SELECT * FROM ENTITIES WHERE CHUNK_X = ? and CHUNK_Y = ? and WORLD_ID = ?")) {

                entityQuery.setInt(1, x);
                entityQuery.setInt(2, y);
                entityQuery.setLong(3, world.getID());
                try (ResultSet entityResult = entityQuery.executeQuery()) {

                    connection.setAutoCommit(false);
                    while (entityResult.next()) {
                        connection.setAutoCommit(false);
                        buffer = entityResult.getBytes("data");
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        SaveableEntityMemento entityMemento = (SaveableEntityMemento) objectIn.readObject();
                        chunk.addEntity(createEntityFromMemento(entityMemento));
                    }
                }
            }
            connection.setAutoCommit(true);
            return chunk;
        } catch (IOException | ClassNotFoundException | SQLException | LoadException e) {
            throw new RunTimeLoadException("Failed to load a chunk", e);
        }
    }

    public Flyway getFlyway() {
        return flyway;
    }

    /**
     * Creates an entity from a given memento
     *
     * @param entityMemento The entities memento that contains its data
     * @return The created entity
     * @throws LoadException If the entity type does not exist
     */
    public AbstractEntity createEntityFromMemento(SaveableEntityMemento entityMemento) throws LoadException {
        switch (entityMemento.getEntityType()) {
            case "Bone":
                return new Bone(entityMemento);
            case "DesertShrub":
                return new DesertShrub(entityMemento);
            case "DesertCacti":
                return new DesertCacti(entityMemento);
            case "DesertRock":
                return new DesertRock(entityMemento);
            case "ForestMushroom":
                return new ForestMushroom(entityMemento);
            case "ForestShrub":
                return new ForestShrub(entityMemento);
            case "Leaves":
                return new Leaves(entityMemento);
            case "TreeStump":
                return new TreeStump(entityMemento);
            case "OrganicMound":
                return new OrganicMound(entityMemento);
            case "MountainRock":
                return new MountainRock(entityMemento);
            case "MountainTree":
                return new MountainTree(entityMemento);
            case "ForestRock":
                return new ForestRock(entityMemento);
            case "SnowClump":
                return new SnowClump(entityMemento);
            case "SnowShrub":
                return new SnowShrub(entityMemento);
            case "ForestTree":
                return new ForestTree(entityMemento);
            case "SwampShrub":
                return new SwampShrub(entityMemento);
            case "SwampRock":
                return new SwampRock(entityMemento);
            case "SwampTree":
                return new SwampTree(entityMemento);
            case "VolcanicShrub":
                return new VolcanicShrub(entityMemento);
            case "VolcanicRock":
                return new VolcanicRock(entityMemento);
            case "VolcanicTree":
                return new VolcanicTree(entityMemento);
            case "GoldPiece":
                return new GoldPiece(entityMemento);
            case "Desert_Environment":
                return new DesertEnvironment(entityMemento);
            case "Shipwrecks":
                return new Shipwrecks(entityMemento);
                case "ruinedRobot":
                return new ruinedRobot(entityMemento);
            case "ruinedCity":
                return new ruinedCity(entityMemento);
            default:
                throw new LoadException(
                    String.format("Could not create %s from memento", entityMemento.getEntityType()));
        }
    }

    /**
     * Uses flyway to create the tables
     */
    private void migrateDatabase() {
        flyway = new Flyway();
        flyway.setDataSource(String.format("jdbc:derby:%s;create=true", dataBaseName), "", "");

        flyway.setCleanOnValidationError(true);
        flyway.setValidateOnMigrate(true);
        flyway.migrate();
    }

    /**
     * Gets all the saves and there corresponding worlds
     *
     * @return A list of all the saves
     */
    public List<Save> loadSaveInformation() {
        try {
            ArrayList<Save> saves = new ArrayList<>();

            try (PreparedStatement saveStatement = connection.prepareStatement("SELECT * FROM SAVES")) {
                // Go through and load all the worlds for each save
                try (ResultSet saveSet = saveStatement.executeQuery()) {
                    while (saveSet.next()) {

                        byte[] buffer = saveSet.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        SaveMemento memento = (SaveMemento) objectIn.readObject();

                        Save save = new Save(memento);

                        try (PreparedStatement worldsStatement = connection
                            .prepareStatement("SELECT * FROM WORLDS WHERE SAVE_ID = ?")) {
                            worldsStatement.setLong(1, save.getSaveID());

                            try (ResultSet worldSet = worldsStatement.executeQuery()) {
                                // Loop through those worlds and add them to the save
                                while (worldSet.next()) {
                                    World world = new World(worldSet.getLong("world_id"), save);
                                    save.addWorld(world);
                                }
                            }
                        }
                        saves.add(save);
                    }
                }
            }
            return saves;

        } catch (Exception e) {
            throw new RunTimeLoadException("Failed to load save information: " + e.getClass().getCanonicalName(), e);
        }
    }

    private void saveTable(String tableName) {
        try {
            try (PreparedStatement ps = DatabaseManager.get().getDataBaseConnector().getConnection()
                .prepareStatement("CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE_LOBS_TO_EXTFILE(?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, null);
                ps.setString(2, tableName);
                ps.setString(3, String.format("src/test/java/deco2800/skyfall/managers/database/PrebuiltData/%s.dat",
                    tableName));
                ps.setString(4, ",");
                ps.setString(5, "\"");
                ps.setString(6, "UTF-8");
                ps.setString(7, String.format("src/test/java/deco2800/skyfall/managers/database/PrebuiltData/%sLOB.dat",
                    tableName));
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RunTimeSaveException("Failed to save table : " + tableName, e);
        }
    }

    public void saveAllTables() {
        saveTable("SAVES");
        saveTable("MAIN_CHARACTER");
        saveTable("WORLDS");
        saveTable("BIOMES");
        saveTable("NODES");
        saveTable("EDGES");
        saveTable("CHUNKS");
        saveTable("ENTITIES");
    }

    public void loadAllTables() {
        loadTable("SAVES", 1);
        loadTable("MAIN_CHARACTER", 2);
        loadTable("WORLDS", 3);
        loadTable("BIOMES", 3);
        loadTable("NODES", 4);
        loadTable("EDGES", 3);
        loadTable("CHUNKS", 3);
        loadTable("ENTITIES", 7);
    }

    private void loadTable(String tableName, int dataIndex) {
        try {
            try (PreparedStatement ps = connection.prepareStatement(
                "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_LOBS_FROM_EXTFILE(?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, null);
                ps.setString(2, tableName);
                ps.setString(3, "src/test/java/deco2800/skyfall/managers/database/PrebuiltData/" + tableName + ".dat");
                ps.setString(4, ",");
                ps.setString(5, "\"");
                ps.setString(6, "UTF-8");
                ps.setInt(7, dataIndex);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RunTimeLoadException("Failed to load table : " + tableName, e);
        }
    }

    // FIXME:jeffvan12 implement delete save method
    public void deleteSave(long saveId) {

    }
}
